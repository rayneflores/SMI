package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCT;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCT2;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import org.json.JSONObject;

import java.util.Objects;

public class BusquedaManualActivity extends AppCompatActivity {

    Bundle extras;
    Button btnSearch;
    CheckBox cbConteo1, cbEtiquetas1, cbSeguimiento1, cbConsulta1, cbPedido1, cbVencimiento1;
    Integer module, serverId;
    Intent nextIntent;
    String path = INFRA_SERVER_ADDRESS;
    String rol, usuario, serverAddress, query;
    SharedPreferences preferences;
    TextInputLayout etSearchText;
    TextInputEditText titCodigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_manual);

        btnSearch = findViewById(R.id.btnSearch);
        cbConteo1 = findViewById(R.id.cbConteo1);
        cbEtiquetas1 = findViewById(R.id.cbEtiquetas1);
        cbSeguimiento1 = findViewById(R.id.cbSeguimiento1);
        cbConsulta1 = findViewById(R.id.cbConsulta);
        cbPedido1 = findViewById(R.id.cbPedido1);
        cbVencimiento1 = findViewById(R.id.cbVencimiento1);
        etSearchText = findViewById(R.id.etSearchText);
        titCodigo = findViewById(R.id.titCodigo);

        recuperarPreferencias();

        extras = new Bundle();

        cbConteo1.setChecked(true);
        btnSearch.setEnabled(false);
        module = 1;

        cbConteo1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbConteo1.isChecked()) {
                cbEtiquetas1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 1;
            } else {
                if (!cbEtiquetas1.isChecked() && !cbSeguimiento1.isChecked() && !cbConsulta1.isChecked() && !cbPedido1.isChecked() && !cbVencimiento1.isChecked()) {
                    cbConteo1.setChecked(true);
                    module = 1;
                }
            }
        });

        cbEtiquetas1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbEtiquetas1.isChecked()) {
                cbConteo1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 2;
            } else {
                if (!cbConteo1.isChecked() && !cbSeguimiento1.isChecked() && !cbConsulta1.isChecked() && !cbPedido1.isChecked() && !cbVencimiento1.isChecked()){
                    cbEtiquetas1.setChecked(true);
                }
            }
        });

        cbSeguimiento1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbSeguimiento1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 3;
            } else {
                if (!cbConteo1.isChecked() && !cbEtiquetas1.isChecked() && !cbConsulta1.isChecked() && !cbPedido1.isChecked() && !cbVencimiento1.isChecked()){
                    cbSeguimiento1.setChecked(true);
                }
            }
        });

        cbConsulta1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbConsulta1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 4;
            } else {
                if (!cbConsulta1.isChecked() && !cbPedido1.isChecked() && !cbSeguimiento1.isChecked() && !cbEtiquetas1.isChecked() && !cbVencimiento1.isChecked()) {
                    cbConsulta1.setChecked(true);
                }
            }
        });

        cbPedido1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbPedido1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 5;
            } else {
                if (!cbConteo1.isChecked() && !cbEtiquetas1.isChecked() && !cbConsulta1.isChecked() && !cbSeguimiento1.isChecked() && !cbVencimiento1.isChecked()){
                    cbPedido1.setChecked(true);
                }
            }
        });

        cbVencimiento1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbVencimiento1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbPedido1.setChecked(false);
                module = 6;
            } else {
                if (!cbConteo1.isChecked() && !cbEtiquetas1.isChecked() && !cbConsulta1.isChecked() && !cbSeguimiento1.isChecked() && !cbPedido1.isChecked()){
                    cbVencimiento1.setChecked(true);
                }
            }
        });

        titCodigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSearch.setEnabled(i2 > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnSearch.setEnabled(editable.toString().length() > 0);
            }
        });

        btnSearch.setOnClickListener(view -> {
            btnSearch.setEnabled(false);
            String code = Objects.requireNonNull(titCodigo.getText()).toString();
            buscarDatosProducto(code);
        });
    }

    private void recuperarPreferencias() {
        preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");
        serverAddress = preferences.getString("serverAddress", "");
        serverId = preferences.getInt("serverId", 1);
    }

    public void buscarDatosProducto(String code) {
        HttpsTrustManager.allowAllSSL();

        switch (serverId) {
            case 1:
                query = GET_PRODUCT;
                break;
            case 2:
                query = GET_PRODUCT2;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + query + code, null, response -> {
            try {
                JSONObject jsonObject = response.getJSONObject("Product");
                Product product = new Product();
                product.setActivado(jsonObject.getInt("activado"));
                product.setCode(jsonObject.getInt("code"));
                product.setCodlocal(jsonObject.getInt("codlocal"));
                product.setDetalle(jsonObject.getString("detalle"));
                product.setDep(jsonObject.getString("dep"));
                product.setEan_13(jsonObject.getString("ean_13"));
                product.setLinea(jsonObject.getInt("linea"));
                product.setSucursal(jsonObject.getString("sucursal"));
                product.setStock_(jsonObject.getLong("stock_"));
                product.setPventa(jsonObject.getLong("pventa"));
                product.setPoferta(jsonObject.getLong("p_oferta"));
                product.setAvg_pro(jsonObject.getDouble("avg_pro"));
                product.setCosto_prom(jsonObject.getLong("costo_prom"));
                product.setCodBarra(code);
                product.setPcadena(jsonObject.getDouble("pcadena"));

                extras.putInt("module", module);

                switch (module) {
                    case 1:
                    case 2:
                    case 3:
                        extras.putSerializable("Product", product);
                        nextIntent = new Intent(getApplicationContext(), ConteoActivity.class);
                        nextIntent.putExtras(extras);
                        startActivity(nextIntent);
                        finish();
                        break;
                    case 4:
                    case 5:
                        extras.putSerializable("Product", product);
                        nextIntent = new Intent(getApplicationContext(), ProductDetailActivity.class);
                        nextIntent.putExtras(extras);
                        startActivity(nextIntent);
                        finish();
                        break;
                    case 6:
                        extras.putSerializable("Product", product);
                        nextIntent = new Intent(getApplicationContext(), VencimientoActivity.class);
                        nextIntent.putExtras(extras);
                        startActivity(nextIntent);
                        finish();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "Fallo en la Lectura, Reintente!!!", Toast.LENGTH_SHORT).show();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}