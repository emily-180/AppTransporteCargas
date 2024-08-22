package br.edu.ifsuldeminas.mch.apptransportecargas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsuldeminas.mch.apptransportecargas.model.Driver;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializando o mapa
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); // Registra o callback para quando o mapa estiver pronto
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_history) {
            Intent intent = new Intent(this, ActivityHistory.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        List<Driver> drivers = getDrivers();
        for (Driver driver : drivers) {
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(driver.getLocation())
                    .title(driver.getName())
                    .snippet(driver.getTruckModel() + " - " + driver.getLicensePlate())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            marker.setTag(driver); // Adiciona o objeto Driver como uma tag do marcador
        }

        //clicou em cima do marcador vai para active Driver
        googleMap.setOnMarkerClickListener(marker -> {
            Driver driver = (Driver) marker.getTag();
            if (driver != null) {
                Intent intent = new Intent(MainActivity.this, DriverDetailsActivity.class);
                intent.putExtra("DRIVER_NAME", driver.getName());
                intent.putExtra("TRUCK_MODEL", driver.getTruckModel());
                intent.putExtra("LICENSE_PLATE", driver.getLicensePlate());
                startActivity(intent);
            }
            return true;
        });

        // Configura o mapa para uma visualização inicial
        if (!drivers.isEmpty()) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(drivers.get(0).getLocation(), 10));
        }
    }
        private List<Driver> getDrivers() {
            List<Driver> drivers = new ArrayList<>();
            drivers.add(new Driver("1", "João", new LatLng(-23.55052, -46.633308), "Scania R450", "ABC-1234"));
            drivers.add(new Driver("2", "Maria", new LatLng(-23.556, -46.626), "Volvo FH", "DEF-5678"));
            drivers.add(new Driver("3", "Carlos", new LatLng(-23.558, -46.629), "Mercedes-Benz Actros", "GHI-9012"));
            return drivers;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
