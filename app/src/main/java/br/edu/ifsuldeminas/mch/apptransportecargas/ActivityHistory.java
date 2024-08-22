package br.edu.ifsuldeminas.mch.apptransportecargas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.edu.ifsuldeminas.mch.apptransportecargas.adapter.TripAdapter;
import br.edu.ifsuldeminas.mch.apptransportecargas.dao.TripDAO;
import br.edu.ifsuldeminas.mch.apptransportecargas.model.Trip;
public class ActivityHistory extends AppCompatActivity {

    private ListView listViewHistory;
    private TripDAO tripDAO;
    private Trip selectedTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listViewHistory = findViewById(R.id.listViewHistory);
        tripDAO = new TripDAO(this);

        loadHistory();
        registerForContextMenu(listViewHistory);


    }

    private void loadHistory() {
        List<Trip> trips = tripDAO.loadTrips();
        TripAdapter adapter = new TripAdapter(this, trips);
        listViewHistory.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);

        // Obter o item que foi clicado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectedTrip = (Trip) listViewHistory.getItemAtPosition(info.position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showDeleteConfirmationDialog();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.tituloExcluirViagemDiaalogo)
                .setMessage(R.string.tituloExcluirViagemDiaalogoConfirm)
                .setPositiveButton(R.string.excluir, (dialog, which) -> {
                    if (selectedTrip != null) {
                        boolean deleted = tripDAO.delete(selectedTrip.getId());
                        if (deleted) {
                            loadHistory(); // Atualiza a lista após a exclusão
                            Toast.makeText(this, R.string.msgViagemExcluidaSuceso, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, R.string.msgViagemExcluidaErro, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
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
