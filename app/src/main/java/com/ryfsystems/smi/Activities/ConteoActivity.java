package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.NEW_SET_LABEL_PRODUCT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.ryfsystems.smi.Models.NewProduct;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import java.util.HashMap;
import java.util.Map;

public class ConteoActivity extends AppCompatActivity {

    Bundle received;
    Intent intent;
    Button btnConteoContar;
    int module;
    int serverId;
    Intent nextIntent;
    NewProduct productReceived;
    String rol;
    String serverAddress;
    String usuario;
    String query;
    SharedPreferences preferences;
    String path = INFRA_SERVER_ADDRESS;
    TextInputEditText tvConteoPventa2;
    TextView tvConteoTitle;
    TextView tvConteoId;
    TextView tvConteoId2;
    TextView tvConteoBarCode;
    TextView tvConteoBarCode2;
    TextView tvConteoDetalle;
    TextView tvConteoDetalle2;
    TextView tvConteoPventa;
    TextView tvConteoPoferta;
    TextView tvConteoPoferta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_conteo);

        btnConteoContar = findViewById(R.id.btnConteoContar);

        recuperarPreferencias();

        tvConteoTitle = findViewById(R.id.tvConteoTitle);
        tvConteoId = findViewById(R.id.tvConteoId);
        tvConteoId2 = findViewById(R.id.tvConteoId2);
        tvConteoBarCode = findViewById(R.id.tvConteoBarCode);
        tvConteoBarCode2 = findViewById(R.id.tvConteoBarCode2);
        tvConteoDetalle = findViewById(R.id.tvConteoDetalle);
        tvConteoDetalle2 = findViewById(R.id.tvConteoDetalle2);
        tvConteoPventa = findViewById(R.id.tvConteoPventa);
        tvConteoPventa2 = findViewById(R.id.tvConteoPventa2);
        tvConteoPoferta = findViewById(R.id.tvConteoPoferta);
        tvConteoPoferta2 = findViewById(R.id.tvConteoPoferta2);

        intent = getIntent();

        //Metodo Nuevo para Generar la Informacion a partir de la consulta
        if (intent.getExtras().containsKey("newProduct")) {
            productReceived = (NewProduct) intent.getSerializableExtra("newProduct");

            module = intent.getIntExtra("module", 0);
            switch (module) {
                case 2:
                    tvConteoTitle.setText(R.string.generacion_etiqueta);
                    tvConteoId2.setText(productReceived.getIdProducto().toString());
                    tvConteoBarCode2.setText(productReceived.getCodigoBarras());
                    tvConteoDetalle2.setText(productReceived.getDetalle());
                    if (productReceived.getPrecioOferta() > 0) {
                        SpannableString pventa = new SpannableString(productReceived.getPrecioVenta());
                        pventa.setSpan(new StrikethroughSpan(), 0, 4, 0);
                        tvConteoPventa2.setText(pventa);
                    } else {
                        tvConteoPventa2.setText(productReceived.getPrecioVenta());
                    }
                    tvConteoPoferta2.setText(productReceived.getPrecioOferta().toString());
                    btnConteoContar.setText(R.string.guardar_etiqueta);
                    break;
            }
        }

        //Tambien hay Cambio de Comportamiento para el boton y en el metodo privado
        btnConteoContar.setOnClickListener(
                view -> updateLabelProduct(
                        String.valueOf(productReceived.getIdProducto()),
                        productReceived.getCodigoBarras(),
                        productReceived.getDetalle(),
                        productReceived.getPrecioVenta(),
                        String.valueOf(productReceived.getPrecioOferta())
                )
        );
    }

    private void updateLabelProduct(String idProducto, String codigoBarras, String detalle, String precioVenta, String precioOferta) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_SET_LABEL_PRODUCT;
        String url = path + query;
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        url,
                        response -> Toast
                                .makeText(
                                        getApplicationContext(),
                                        response,
                                        Toast.LENGTH_SHORT)
                                .show(),
                        error -> Toast
                                .makeText(
                                        getApplicationContext(),
                                        "Error_Det: " + error.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show()
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_producto", idProducto);
                        params.put("codigo_barras", codigoBarras);
                        params.put("detalle", detalle);
                        params.put("precio_venta", precioVenta);
                        params.put("precio_oferta", precioOferta);
                        return params;
                    }
                };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }

    private void recuperarPreferencias() {
        preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");
        serverAddress = preferences.getString("serverAddress", "");
        serverId = preferences.getInt("serverId", 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}