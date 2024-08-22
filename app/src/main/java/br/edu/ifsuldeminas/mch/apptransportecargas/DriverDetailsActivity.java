package br.edu.ifsuldeminas.mch.apptransportecargas;

import static br.edu.ifsuldeminas.mch.apptransportecargas.R.id.listViewHistory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

import br.edu.ifsuldeminas.mch.apptransportecargas.adapter.TripAdapter;
import br.edu.ifsuldeminas.mch.apptransportecargas.dao.TripDAO;
import br.edu.ifsuldeminas.mch.apptransportecargas.model.Trip;

// DriverDetailsActivity.java
public class DriverDetailsActivity extends AppCompatActivity {

    private ListView listViewServices;
    private ListView listViewHistory;
    private TripDAO tripDAO;
    private String selectedService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tripDAO = new TripDAO(this);

        String driverName = getIntent().getStringExtra("DRIVER_NAME");
        String truckModel = getIntent().getStringExtra("TRUCK_MODEL");
        String licensePlate = getIntent().getStringExtra("LICENSE_PLATE");

        TextView textViewDriverName = findViewById(R.id.textViewDriverName);
        TextView textViewTruckModel = findViewById(R.id.textViewCaminhao);
        TextView textViewLicensePlate = findViewById(R.id.textViewLicensePlate);

        textViewDriverName.setText("Motorista " + driverName);
        textViewTruckModel.setText("Modelo: " + truckModel);
        textViewLicensePlate.setText("Placa: " + licensePlate);

        listViewServices = findViewById(R.id.listViewServices);
        String[] services = {"Mudança Residencial, R$250,00", "Transporte de Cargas Empresarial, R$450,00",
                "Entrega Rápida, R$100,00", "Frete para outro estado, > R$2.000,00"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, services);
        listViewServices.setAdapter(adapter);

        listViewServices.setOnItemClickListener((parent, view, position, id) -> {
            selectedService = (String) parent.getItemAtPosition(position);
            showAddressInputDialog();
        });


    }

    private void showAddressInputDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_address, null);

        EditText editTextOrigin = dialogView.findViewById(R.id.editTextOrigin);
        EditText editTextDestination = dialogView.findViewById(R.id.editTextDestination);

        new AlertDialog.Builder(this)
                .setTitle(R.string.tituloEnderecoDiaalogo)
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    String origin = editTextOrigin.getText().toString();
                    String destination = editTextDestination.getText().toString();

                    if (origin.isEmpty() || destination.isEmpty()) {
                        Toast.makeText(DriverDetailsActivity.this, R.string.msgAddressVazia, Toast.LENGTH_LONG).show();
                        return;
                    }

                    Trip trip = new Trip(null, getIntent().getStringExtra("DRIVER_NAME"), new Date().toString(), origin, destination, selectedService);
                    boolean saved = tripDAO.save(trip);

                    if (saved) {
                        Toast.makeText(DriverDetailsActivity.this, R.string.msgAddressConfirmada, Toast.LENGTH_LONG).show();
                        loadHistory();
                    } else {
                        Toast.makeText(DriverDetailsActivity.this, R.string.msgAddresErro, Toast.LENGTH_LONG).show();
                    }

                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void loadHistory() {
        List<Trip> trips = tripDAO.loadTrips();
        TripAdapter adapter = new TripAdapter(this, trips);
        listViewHistory.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
