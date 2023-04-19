package com.ryfsystems.smi.activities;

import static com.ryfsystems.smi.utils.Constants.MODULE;
import static com.ryfsystems.smi.utils.Constants.SERVER_ID;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.ryfsystems.smi.R;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    ImageButton btnSettings;
    CardView cvInventario;
    CardView cvEtiquetas;
    CardView cvChecklist;
    CardView cvSeguimiento;
    CardView cvConsulta;
    CardView cvPedido;
    CardView cvVencimiento;
    CardView cvManualSearch;
    Intent nextIntent;
    int serverId;
    int userId;
    ProgressDialog progressDialog;
    String rol;
    String usuario;
    String serverAddress;
    SharedPreferences preferences;
    TextView tvBienvenida;
    TextView tvSucursal;

    /**
     * @version 2.0.1 Generar Etiquetas y Checklist
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        extras = new Bundle();

        recuperarPreferencias();

        cvInventario = findViewById(R.id.cvInventario);
        cvInventario.setOnClickListener(this);
        cvInventario.setAlpha(0.3f);
        cvInventario.setEnabled(false);

        cvEtiquetas = findViewById(R.id.cvEtiquetas);
        cvEtiquetas.setOnClickListener(this);

        cvChecklist = findViewById(R.id.cvChecklist);
        cvChecklist.setOnClickListener(this);

        cvSeguimiento = findViewById(R.id.cvSeguimiento);
        cvSeguimiento.setOnClickListener(this);
        cvSeguimiento.setAlpha(0.3f);
        cvSeguimiento.setEnabled(false);

        cvConsulta = findViewById(R.id.cvConsulta);
        cvConsulta.setOnClickListener(this);
        cvConsulta.setAlpha(0.3f);
        cvConsulta.setEnabled(false);

        cvPedido = findViewById(R.id.cvPedido);
        cvPedido.setOnClickListener(this);
        cvPedido.setAlpha(0.3f);
        cvPedido.setEnabled(false);

        cvVencimiento = findViewById(R.id.cvVencimiento);
        cvVencimiento.setOnClickListener(this);
        cvVencimiento.setAlpha(0.3f);
        cvVencimiento.setEnabled(false);

        cvManualSearch = findViewById(R.id.cvManualSearch);
        cvManualSearch.setOnClickListener(this);

        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);

        getPermissions();

        validateRole(rol);

        tvBienvenida = findViewById(R.id.tvBienvenida);
        tvBienvenida.setText(MessageFormat.format("{0} {1}", getString(R.string.bienvenido), usuario));

        tvSucursal = findViewById(R.id.tvSucursal);
        tvSucursal.setText(MessageFormat
                .format(
                        "{0} {1}",
                        "Sucursal:",
                        serverAddress)
        );
    }

    private void recuperarPreferencias() {
        preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");
        serverAddress = preferences.getString("serverAddress", "");
        userId = preferences.getInt("userId",1);
        serverId = preferences.getInt(SERVER_ID, 1);
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[] {Manifest.permission.CAMERA},
                0);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere...");
        progressDialog.setMessage("Cargando Detalles de Producto...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setMessage("Esta seguro que desea salir de la Aplicacion?")
                .setPositiveButton("Si", (d, which) -> {
                    d.dismiss();
                    borrarPreferencias();
                })
                .setNegativeButton("No", (d, which) -> d.dismiss())
                .setIcon(R.drawable.magallean)
                .setTitle(" ");
        dialog.show();
    }

    private void validateRole(String role) {
        if (role.equals("2")){
            btnSettings.setEnabled(false);
            btnSettings.setAlpha(0.3f);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btnSettings:
                nextIntent = new Intent(getApplicationContext(), UsersListActivity.class);
                startActivity(nextIntent);
                finish();
                break;
            case R.id.cvInventario:
                extras.putInt(MODULE, 1);
                extras.putInt(SERVER_ID, serverId);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvEtiquetas:
                extras.putInt(MODULE, 2);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvChecklist:
                extras.putInt(SERVER_ID, serverId);
                i = new Intent(getApplicationContext(), NewProductListActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.cvSeguimiento:
                extras.putInt(MODULE, 3);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvConsulta:
                extras.putInt(MODULE, 4);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvPedido:
                extras.putInt(MODULE, 5);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvVencimiento:
                extras.putInt(MODULE, 6);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvManualSearch:
                extras.putInt(SERVER_ID, serverId);
                extras.putInt("userId", userId);
                i = new Intent(getApplicationContext(), BusquedaManualActivity.class);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
    }

    public void borrarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
        Intent mainIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
