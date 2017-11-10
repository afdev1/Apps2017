package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CompanyDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "companies.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_COMPANIES = "companies";
    public static final String COLUMN_ID = "comId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TYPE = "type";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_COMPANIES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_URL + " TEXT, " +
                    COLUMN_TELEPHONE + " NUMERIC, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_TYPE + " NUMERIC " +
                    ")";


    public CompanyDBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANIES);
        db.execSQL(TABLE_CREATE);
    }
}
