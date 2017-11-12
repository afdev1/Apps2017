package afdev.unal.edu.co.directory;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

import db.CompanyOperations;
import model.Company;

public class EditorAlert extends DialogFragment {
    private EditText name;
    private EditText url;
    private EditText telephone;
    private EditText email;
    private EditText services;
    private Spinner type;
    private Company newCompany;
    private Company oldCompany;
    private long comId;
    private CompanyOperations companyData;

    String mode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.editor, null);

        companyData = new CompanyOperations(view.getContext());
        companyData.open();
        newCompany = new Company();
        oldCompany = new Company();
        name = view.findViewById(R.id.ti_name);
        url = view.findViewById(R.id.ti_url);
        telephone = view.findViewById(R.id.ti_telephone);
        email = view.findViewById(R.id.ti_email);
        services = view.findViewById(R.id.ti_services);
        type = view.findViewById(R.id.sp_type);

        mode = getArguments().getString("mode");

        if (mode.equals("Update")) {
            comId = getArguments().getLong("id");
            initializeCompany(comId);
        }


        builder.setView(view)
                .setPositiveButton(mode.equals("Update") ? "Actualizar":"Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mode.equals("Add")) {
                            newCompany.setName(name.getText().toString());
                            newCompany.setUrl(url.getText().toString());
                            newCompany.setTelephone(Long.parseLong(telephone.getText().toString()));
                            newCompany.setEmail(email.getText().toString());
                            newCompany.setServices(services.getText().toString());
                            newCompany.setType(type.getSelectedItem().toString());
                            companyData.addCompany(newCompany);
                        } else {
                            oldCompany.setName(name.getText().toString());
                            oldCompany.setUrl(url.getText().toString());
                            oldCompany.setTelephone(Long.parseLong(telephone.getText().toString()));
                            oldCompany.setEmail(email.getText().toString());
                            oldCompany.setServices(services.getText().toString());
                            oldCompany.setType(type.getSelectedItem().toString());
                            companyData.updateCompany(oldCompany);
                        }
                        ((MainActivity) getActivity()).refresh();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditorAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private void initializeCompany(long comId) {
        oldCompany = companyData.getCompany(comId);
        Log.v("PRUEBAA", oldCompany.toString());
        name.setText(oldCompany.getName());
        url.setText(oldCompany.getUrl());
        telephone.setText(oldCompany.getTelephone().toString());
        email.setText(oldCompany.getEmail());
        services.setText(oldCompany.getServices());
        type.setSelection(Arrays.asList(getResources().getStringArray(R.array.types)).indexOf(oldCompany.getType()));
    }
}
