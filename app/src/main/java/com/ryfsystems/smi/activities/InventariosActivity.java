package com.ryfsystems.smi.activities;

import static com.ryfsystems.smi.R.id;
import static com.ryfsystems.smi.R.string;
import static com.ryfsystems.smi.utils.Constants.MODULE;
import static com.ryfsystems.smi.utils.Constants.SERVER_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ryfsystems.smi.R;

import java.text.MessageFormat;

public class InventariosActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle extras;
    CardView cvInventarioC;
    CardView cvInventarioA;
    Intent nextIntent;
    int serverId;
    int userId;
    SharedPreferences preferences;
    String rol;
    String usuario;
    String serverAddress;
    TextView tvBienvenida2;
    TextView tvSucursal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventarios);

        extras = new Bundle();

        recuperarPreferencias();

        cvInventarioC = findViewById(id.cvInventarioC);
        cvInventarioC.setOnClickListener(this);

        cvInventarioA = findViewById(id.cvInventarioA);
        cvInventarioA.setOnClickListener(this);
        cvInventarioA.setAlpha(0.3f);
        cvInventarioA.setEnabled(false);

        tvBienvenida2 = findViewById(id.tvBienvenida2);
        tvBienvenida2.setText(MessageFormat.format("{0} {1}", getString(string.bienvenido), usuario));

        tvSucursal2 = findViewById(id.tvSucursal2);
        tvSucursal2.setText(MessageFormat
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

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case id.cvInventarioC:
                extras.putInt(MODULE, 1);
                extras.putInt(SERVER_ID, serverId);
                i = new Intent(getApplicationContext(), InventarioCiclicoActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            case id.cvInventarioA:
                extras.putInt(MODULE, 1);
                extras.putInt(SERVER_ID, serverId);
                i = new Intent(getApplicationContext(), InventarioAzarActivity.class);
                i.putExtras(extras);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}