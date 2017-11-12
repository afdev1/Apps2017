package afdev.unal.edu.co.directory;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import db.CompanyOperations;
import model.Company;

public class MainActivity extends AppCompatActivity {

    private CompanyOperations companyOps;

    ArrayAdapter<Company> adapter;

    SearchView search;

    public void refresh(){
        adapter.clear();
        adapter.addAll(companyOps.getAllCompanies());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.sv_search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.clear();
                List<Company> list = companyOps.getAllCompanies(s);
                adapter.addAll(list);
                return false;
            }
        });

        companyOps = new CompanyOperations(this);
        companyOps.open();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("mode", "Add");

                FragmentManager fm = getFragmentManager();

                DialogFragment newFragment = new EditorAlert();
                newFragment.setArguments(args);
                newFragment.show(fm, "editor");
            }
        });

        ListView listView = findViewById(R.id.lv_companies);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, companyOps.getAllCompanies());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Company company = (Company) adapterView.getAdapter().getItem(i);

                Bundle args = new Bundle();
                args.putLong("id", company.getComId());
                args.putString("mode", "Update");

                DialogFragment newFragment = new EditorAlert();
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "editor");
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final Company company = (Company) adapterView.getAdapter().getItem(i);

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Eliminar contacto")
                        .setMessage("¿Está seguro que quiere eliminar a " + company.getName() + "?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                companyOps.removeCompany(company);
                                adapter.remove(company);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        companyOps.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        companyOps.close();
    }
}
