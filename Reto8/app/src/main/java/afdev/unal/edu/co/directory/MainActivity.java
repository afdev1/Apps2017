package afdev.unal.edu.co.directory;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import db.CompanyOperations;
import model.Company;

public class MainActivity extends AppCompatActivity {

    private CompanyOperations companyOps;
    List<Company> companies = new ArrayList<Company>() {
        {
            add(new Company(100l, "Test", "URL", 65656, "EMAIL", "Empleo", "Consultoría"));//getString(R.string.consultoria)));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new EditorAlert();
                newFragment.show(getFragmentManager(), "editor");
            }
        });

        ListView listView = findViewById(R.id.lv_companies);
        ArrayAdapter<Company> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, companies);
        listView.setAdapter(adapter);
    }
}
