package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Constants.SET_PRODUCT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;

import java.util.HashMap;
import java.util.Map;

public class ConteoActivity extends AppCompatActivity {

    Bundle received;
    Button btnConteoContar;
    Intent nextIntent;
    Product productReceived;
    RequestQueue requestQueue;
    String path = INFRA_SERVER_ADDRESS;
    TextView tvConteoCode2, tvConteoActivado2, tvConteoCodLocal2, tvConteoDetalle2, tvConteoDep2, tvConteoEan132, tvConteoLinea2, tvConteoSucursal2;
    TextInputEditText tvConteoCantidad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);

        btnConteoContar = findViewById(R.id.btnConteoContar);

        tvConteoCode2 = findViewById(R.id.tvConteoCode2);
        tvConteoActivado2 = findViewById(R.id.tvConteoActivado2);
        tvConteoCodLocal2 = findViewById(R.id.tvConteoCodLocal2);
        tvConteoDetalle2 = findViewById(R.id.tvConteoDetalle2);
        tvConteoDep2 = findViewById(R.id.tvConteoDep2);
        tvConteoEan132 = findViewById(R.id.tvConteoEan132);
        tvConteoLinea2 = findViewById(R.id.tvConteoLinea2);
        tvConteoSucursal2 = findViewById(R.id.tvConteoSucursal2);
        tvConteoCantidad2 = findViewById(R.id.tvConteoCantidad2);

        received = getIntent().getExtras();

        if (received != null) {
            productReceived = (Product) received.getSerializable("Product");
            tvConteoActivado2.setText(productReceived.getActivado() == 1 ? "Si": "No");
            tvConteoCode2.setText(productReceived.getCode().toString());
            tvConteoCodLocal2.setText(productReceived.getCodlocal().toString());
            tvConteoDetalle2.setText(productReceived.getDetalle());
            tvConteoDep2.setText(productReceived.getDep());
            tvConteoEan132.setText(productReceived.getEan_13());
            tvConteoLinea2.setText(productReceived.getLinea().toString());
            tvConteoSucursal2.setText(productReceived.getSucursal());
            tvConteoCantidad2.setText("0");
        }

        requestQueue = Volley.newRequestQueue(this);

        btnConteoContar.setOnClickListener(view -> {
            if (Integer.parseInt(tvConteoCantidad2.getText().toString()) <= 20000) {
                updateProduct(
                        productReceived.getActivado(),
                        productReceived.getCode(),
                        productReceived.getCodlocal(),
                        productReceived.getDetalle(),
                        productReceived.getDep(),
                        productReceived.getEan_13(),
                        productReceived.getLinea(),
                        productReceived.getSucursal(),
                        Integer.parseInt(tvConteoCantidad2.getText().toString())
                );
                nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nextIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "CANTIDAD SUPERIOR A 20000", Toast.LENGTH_SHORT).show();
                tvConteoCantidad2.requestFocus();
            }
        });
    }

    private void updateProduct(int activado, int code, int codLocal, String detalle, String dep, String ean_13, int linea, String sucursal, int stock) {
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + SET_PRODUCT,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Log.e("culo", "Error_Det: " + error.getMessage());
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("activado", String.valueOf(activado));
                        params.put("code", String.valueOf(code));
                        params.put("codlocal", String.valueOf(codLocal));
                        params.put("detalle", detalle);
                        params.put("dep", dep);
                        params.put("ean_13", ean_13);
                        params.put("linea", String.valueOf(linea));
                        params.put("sucursal", sucursal);
                        params.put("stock_", String.valueOf(stock));
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