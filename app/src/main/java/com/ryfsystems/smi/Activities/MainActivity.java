package com.ryfsystems.smi.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ryfsystems.smi.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSettings;
    Intent nextIntent;
    String rol, usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("usuario", "");

        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);

        validateRole(rol);
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
                .setIcon(R.drawable.inventario)
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
        switch (view.getId()) {
            case R.id.btnSettings:
                nextIntent = new Intent(getApplicationContext(), UsersListActivity.class);
                startActivity(nextIntent);
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