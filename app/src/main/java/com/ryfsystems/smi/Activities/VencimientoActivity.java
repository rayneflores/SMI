package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.SET_EXPIRED_PRODUCT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import java.util.HashMap;
import java.util.Map;

public class VencimientoActivity extends AppCompatActivity {

    Bundle received;
    Button btnVencimientoOption;
    Intent nextIntent;
    Product productReceived;
    RequestQueue requestQueue;
    String usuario, path = INFRA_SERVER_ADDRESS;
    TextInputEditText tvVencimientoCantidad2;
    TextView tvVencimientoCode2, tvVencimientoDetalle2, tvVencimientoResponsable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vencimiento);

        SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        usuario = preferences.getString("name", "");

        tvVencimientoCantidad2 = findViewById(R.id.tvVencimientoCantidad2);
        tvVencimientoCode2 = findViewById(R.id.tvVencimientoCode2);
        tvVencimientoDetalle2 = findViewById(R.id.tvVencimientoDetalle2);
        tvVencimientoResponsable = findViewById(R.id.tvVencimientoResponsable);

        btnVencimientoOption = findViewById(R.id.btnVencimientoOption);

        btnVencimientoOption.setOnClickListener(view -> {
            if (Integer.parseInt(tvVencimientoCantidad2.getText().toString()) > 1) {
                requestQueue = Volley.newRequestQueue(this);
                int und_defect = Integer.parseInt(tvVencimientoCantidad2.getText().toString());
                updateVencProduct(
                        productReceived.getCode(),
                        productReceived.getDetalle(),
                        und_defect,
                        usuario
                );
                nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nextIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Al menos debe Registrar una (01) Unidad", Toast.LENGTH_SHORT).show();
                tvVencimientoCantidad2.requestFocus();
            }
        });

        tvVencimientoResponsable.setText("Responsable: " + usuario);

        received = getIntent().getExtras();

        if (received != null) {
            productReceived = (Product) received.getSerializable("Product");

            tvVencimientoCode2.setText(productReceived.getCode().toString());
            tvVencimientoDetalle2.setText(productReceived.getDetalle());
        }
    }

    private void updateVencProduct(Integer code, String detalle, int und_defect, String responsable) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + SET_EXPIRED_PRODUCT,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("code", String.valueOf(code));
                        params.put("detalle", detalle);
                        params.put("und_defect", String.valueOf(und_defect));
                        params.put("responsable", responsable);
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}