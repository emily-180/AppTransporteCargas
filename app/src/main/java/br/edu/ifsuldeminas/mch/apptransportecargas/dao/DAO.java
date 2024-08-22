package br.edu.ifsuldeminas.mch.apptransportecargas.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

abstract class DAO {
    private DBHandler dbHandler;

    DAO(Context context) {
        dbHandler = new DBHandler(context);
    }

    SQLiteDatabase openToWrite() throws SQLException {
        return dbHandler.getWritableDatabase();
    }

    SQLiteDatabase openToRead() throws SQLException {
        return dbHandler.getReadableDatabase();
    }
}

