package br.edu.ifsuldeminas.mch.apptransportecargas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsuldeminas.mch.apptransportecargas.R;
import br.edu.ifsuldeminas.mch.apptransportecargas.model.Trip;

public class TripAdapter extends ArrayAdapter<Trip> {
    private final Context context;
    private final List<Trip> trips;

    public TripAdapter(Context context, List<Trip> trips) {
        super(context, 0, trips);
        this.context = context;
        this.trips = trips;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_trip, parent, false);
        }
        Trip trip = trips.get(position);
        TextView textViewTripDetails = convertView.findViewById(R.id.textViewTripDetails);
        Button buttonShare = convertView.findViewById(R.id.buttonShare);
        textViewTripDetails.setText(trip.toString());
        buttonShare.setOnClickListener(v -> shareTrip(trip));
        return convertView;
    }

    private void shareTrip(Trip trip) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, trip.toString());
        context.startActivity(Intent.createChooser(shareIntent, "Compartilhando texto"));
    }
}
