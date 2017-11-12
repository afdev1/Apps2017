package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import model.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyOperations {
    public static final String LOGTAG = "COM_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            CompanyDBHandler.COLUMN_ID,
            CompanyDBHandler.COLUMN_NAME,
            CompanyDBHandler.COLUMN_URL,
            CompanyDBHandler.COLUMN_TELEPHONE,
            CompanyDBHandler.COLUMN_EMAIL,
            CompanyDBHandler.COLUMN_SERVICES,
            CompanyDBHandler.COLUMN_TYPE
    };

    public CompanyOperations(Context context) {
        dbhandler = new CompanyDBHandler(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public Company addCompany(Company Company) {
        ContentValues values = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_NAME, Company.getName());
        values.put(CompanyDBHandler.COLUMN_URL, Company.getUrl());
        values.put(CompanyDBHandler.COLUMN_TELEPHONE, Company.getTelephone());
        values.put(CompanyDBHandler.COLUMN_EMAIL, Company.getEmail());
        values.put(CompanyDBHandler.COLUMN_SERVICES, Company.getServices());
        values.put(CompanyDBHandler.COLUMN_TYPE, Company.getType());
        long insertid = database.insert(CompanyDBHandler.TABLE_COMPANIES, null, values);
        Company.setComId(insertid);
        return Company;
    }

    // Getting single Company
    public Company getCompany(long id) {
        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANIES, allColumns, CompanyDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Company e = new Company(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return e;
    }

    public List<Company> getAllCompanies() {
        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANIES, allColumns, null, null, null, null, CompanyDBHandler.COLUMN_NAME);
        List<Company> employees = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Company company = new Company();
                company.setComId(cursor.getLong(cursor.getColumnIndex(CompanyDBHandler.COLUMN_ID)));
                company.setName(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_NAME)));
                company.setUrl(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_URL)));
                company.setTelephone(cursor.getInt(cursor.getColumnIndex(CompanyDBHandler.COLUMN_TELEPHONE)));
                company.setEmail(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_EMAIL)));
                company.setServices(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_SERVICES)));
                company.setType(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_TYPE)));
                employees.add(company);
            }
        }
        return employees;
    }

    public List<Company> getAllCompanies(String s) {
        Cursor cursor = database.query(CompanyDBHandler.TABLE_COMPANIES, allColumns, CompanyDBHandler.COLUMN_NAME + " like '%" + s + "%' or " + CompanyDBHandler.COLUMN_TYPE + " like '%" + s + "%'", null, null, null, CompanyDBHandler.COLUMN_NAME);
        List<Company> employees = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Company company = new Company();
                company.setComId(cursor.getLong(cursor.getColumnIndex(CompanyDBHandler.COLUMN_ID)));
                company.setName(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_NAME)));
                company.setUrl(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_URL)));
                company.setTelephone(cursor.getInt(cursor.getColumnIndex(CompanyDBHandler.COLUMN_TELEPHONE)));
                company.setEmail(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_EMAIL)));
                company.setServices(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_SERVICES)));
                company.setType(cursor.getString(cursor.getColumnIndex(CompanyDBHandler.COLUMN_TYPE)));
                employees.add(company);
            }
        }
        return employees;
    }

    // Updating Company
    public int updateCompany(Company company) {
        ContentValues values = new ContentValues();
        values.put(CompanyDBHandler.COLUMN_NAME, company.getName());
        values.put(CompanyDBHandler.COLUMN_URL, company.getUrl());
        values.put(CompanyDBHandler.COLUMN_TELEPHONE, company.getTelephone());
        values.put(CompanyDBHandler.COLUMN_EMAIL, company.getEmail());
        values.put(CompanyDBHandler.COLUMN_SERVICES, company.getServices());
        values.put(CompanyDBHandler.COLUMN_TYPE, company.getType());
        return database.update(CompanyDBHandler.TABLE_COMPANIES, values,CompanyDBHandler.COLUMN_ID + "=?", new String[]{String.valueOf(company.getComId())});
    }

    // Deleting Company
    public void removeCompany(Company company) {
        database.delete(CompanyDBHandler.TABLE_COMPANIES, CompanyDBHandler.COLUMN_ID + "=" + company.getComId(), null);
    }
}
