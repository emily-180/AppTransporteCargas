package br.edu.ifsuldeminas.mch.apptransportecargas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.apptransportecargas.model.Trip;

public class TripDAO extends DAO {

    public TripDAO(Context context) {
        super(context);
    }

    public boolean save(Trip trip) {
        SQLiteDatabase database = openToWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("driver_name", trip.getDriverName());
        contentValues.put("date", trip.getDate());
        contentValues.put("origin", trip.getOrigin());
        contentValues.put("destination", trip.getDestination());
        contentValues.put("service_type", trip.getServiceType());

        long result = database.insert("trips", null, contentValues);
        database.close();
        return result != -1;
    }

    public List<Trip> loadTrips() {
        SQLiteDatabase database = openToRead();
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM trips;";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String driverName = cursor.getString(cursor.getColumnIndexOrThrow("driver_name"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String origin = cursor.getString(cursor.getColumnIndexOrThrow("origin"));
            String destination = cursor.getString(cursor.getColumnIndexOrThrow("destination"));
            String serviceType = cursor.getString(cursor.getColumnIndexOrThrow("service_type"));

            Trip trip = new Trip(id, driverName, date, origin, destination, serviceType);
            trips.add(trip);
        }
        cursor.close();
        database.close();
        return trips;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = openToWrite();
        int rowsAffected = db.delete("trips", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

}
