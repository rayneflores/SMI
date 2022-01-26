package com.ryfsystems.smi.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ryfsystems.smi.R;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Bundle extras;
    Button btnSettings;
    CardView cvInventario, cvEtiquetas, cvChecklist, cvSeguimiento, cvConsulta, cvPedido, cvVencimiento;
    Intent nextIntent;
    ProgressDialog progressDialog;
    String rol, usuario;
    TextView tvBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        extras = new Bundle();

        SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");

        cvInventario = findViewById(R.id.cvInventario);
        cvInventario.setOnClickListener(this);

        cvEtiquetas = findViewById(R.id.cvEtiquetas);
        cvEtiquetas.setOnClickListener(this);

        cvChecklist = findViewById(R.id.cvChecklist);
        cvChecklist.setOnClickListener(this);

        cvSeguimiento = findViewById(R.id.cvSeguimiento);
        cvSeguimiento.setOnClickListener(this);

        cvConsulta = findViewById(R.id.cvConsulta);
        cvConsulta.setOnClickListener(this);

        cvPedido = findViewById(R.id.cvPedido);
        cvPedido.setOnClickListener(this);

        cvVencimiento = findViewById(R.id.cvVencimiento);
        cvVencimiento.setOnClickListener(this);

        tvBienvenida = findViewById(R.id.tvBienvenida);

        tvBienvenida.setText(MessageFormat.format("{0} {1}", getString(R.string.bienvenido), usuario));

        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);

        getPermissions();

        validateRole(rol);
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, 0);
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
                .setNegativeButton("No", (d, which) -> {
                    d.dismiss();
                })
                .setIcon(R.drawable.magallean)
                .setTitle(" ");
        dialog.show();
    }

    private void validateRole(String role) {
        switch (role) {
            case "1":
                break;
            case "2":
                btnSettings.setEnabled(false);
                btnSettings.setAlpha(0.3f);
                break;
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
                extras.putInt("module", 1);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvEtiquetas:
                extras.putInt("module", 2);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvChecklist:
                i = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.cvSeguimiento:
                extras.putInt("module", 3);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvConsulta:
                extras.putInt("module", 4);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvPedido:
                extras.putInt("module", 5);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case R.id.cvVencimiento:
                extras.putInt("module", 6);
                i = new Intent(getApplicationContext(), TomaInventarioActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
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
}
