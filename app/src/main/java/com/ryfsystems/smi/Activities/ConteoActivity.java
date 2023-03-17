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
import com.google.android.material.textfield.TextInputEditText;
import com.ryfsystems.smi.Models.NewProduct;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import java.util.HashMap;
import java.util.Map;

public class ConteoActivity extends AppCompatActivity {

    Bundle received;
    Button btnConteoContar;
    int module;
    int serverId;
    Intent nextIntent;
    NewProduct productReceived;
    RequestQueue requestQueue;
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
    TextView tvConteoPOferta;
    TextView tvConteoPOferta2;

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
        tvConteoPOferta = findViewById(R.id.tvConteoPrecioOferta);
        tvConteoPOferta2 = findViewById(R.id.tvConteoPrecioOferta2);

        received = getIntent().getExtras();

        //Metodo Nuevo para Generar la Informacion a partir de la consulta
        if (received != null) {
            productReceived = (NewProduct) received.getSerializable("NewProduct");
            tvConteoTitle.setText(R.string.generacion_etiqueta);
            tvConteoId2.setText(productReceived.getId_producto());
            tvConteoBarCode2.setText(productReceived.getCodigo_barras());
            tvConteoDetalle2.setText(productReceived.getDetalle());
            if (productReceived.getPrecio_oferta() > 0) {
                SpannableString pventa = new SpannableString(productReceived.getPrecio_venta());
                pventa.setSpan(new StrikethroughSpan(), 0, 4, 0);
                tvConteoPventa2.setText(pventa);
            } else {
                tvConteoPventa2.setText(productReceived.getPrecio_venta());
            }
            tvConteoPOferta2.setText(productReceived.getPrecio_oferta().toString());
            btnConteoContar.setText(R.string.guardar_etiqueta);
        }

        //Tambien hay Cambio de Comportamiento para el boton y en el metodo privado
        btnConteoContar.setOnClickListener(
                view -> updateLabelProduct(
                        productReceived.getId_producto(),
                        productReceived.getCodigo_barras(),
                        productReceived.getDetalle(),
                        productReceived.getPrecio_venta(),
                        productReceived.getPrecio_oferta()
                )
        );
    }

    private void updateLabelProduct(
            Integer id_producto,
            String codigo_barras,
            String detalle,
            String precio_venta,
            Integer precio_oferta) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_SET_LABEL_PRODUCT;
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + query,
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
                        params.put("id_producto", String.valueOf(id_producto));
                        params.put("codigo_barras", codigo_barras);
                        params.put("detalle", detalle);
                        params.put("precio_venta", precio_venta);
                        params.put("precio_oferta", precio_oferta.toString());
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
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