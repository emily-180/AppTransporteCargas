package br.edu.ifsuldeminas.mch.apptransportecargas.dao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "transportecargas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TRIPS_CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS trips (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "driver_name TEXT, " +
                    "date TEXT, " +
                    "origin TEXT, " +
                    "destination TEXT, " +
                    "service_type TEXT);";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_TRIPS_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // nao vou usar atualização
    }
}
