package com.ryfsystems.smi.activities;

import static com.ryfsystems.smi.utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.utils.Constants.NEW_SEARCH_PRODUCT_BY_CODE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ryfsystems.smi.models.NewProduct;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.utils.HttpsTrustManager;

import org.json.JSONObject;

import java.util.Objects;

public class BusquedaManualActivity extends AppCompatActivity {

    Bundle extras;
    Button btnSearch;
    CheckBox cbConteo1;
    CheckBox cbEtiquetas1;
    CheckBox cbSeguimiento1;
    CheckBox cbConsulta1;
    CheckBox cbPedido1;
    CheckBox cbVencimiento1;
    CheckBox cbQueryMode1;
    CheckBox cbQueryMode2;
    Integer module;
    Integer serverId;
    Integer userId;
    Intent intent;
    Intent nextIntent;
    String path = INFRA_SERVER_ADDRESS;
    String rol;
    String usuario;
    String serverAddress;
    String query;
    SharedPreferences preferences;
    TextInputLayout etSearchText;
    TextInputEditText titCodigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_manual);

        defineUIElements();
        generateUIChanges();
        recuperarPreferencias();

        extras = new Bundle();

        cbEtiquetas1.setChecked(true);
        cbQueryMode1.setChecked(true);
        btnSearch.setEnabled(false);

        module = 2;

        cbQueryMode1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cbQueryMode2.setChecked(false);
                etSearchText.setHint("Codigo");
                titCodigo.setText("");
            } else {
                cbQueryMode2.setChecked(true);
                etSearchText.setHint("Descripcion");
                titCodigo.setText("");
            }
        });
        cbQueryMode2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cbQueryMode1.setChecked(false);
                etSearchText.setHint("Descripcion");
                titCodigo.setText("");
            } else {
                cbQueryMode1.setChecked(true);
                etSearchText.setHint("Codigo");
                titCodigo.setText("");
            }
        });

        validateConteo();
        validateEtiquetas();
        validateSeguimiento();
        validateConsulta();
        validatePedido();
        validateVencimiento();
        onWriteValidation();
        validateSearchButton();
    }

    private void validateSearchButton() {
        btnSearch.setOnClickListener(view -> {
            btnSearch.setEnabled(false);
            String code = Objects.requireNonNull(titCodigo.getText()).toString();

            if (cbQueryMode1.isChecked()) {
                buscarPorBarCode(code);
            } else {
                buscarPorDetalle(code);
            }
        });
    }

    private void onWriteValidation() {
        titCodigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do Nothing because not have any text
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
    }

    private void validateVencimiento() {
        cbVencimiento1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbVencimiento1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbPedido1.setChecked(false);
                module = 6;
            } else {
                if (!cbConteo1.isChecked() &&
                        !cbEtiquetas1.isChecked() &&
                        !cbConsulta1.isChecked() &&
                        !cbSeguimiento1.isChecked() &&
                        !cbPedido1.isChecked()) {
                    cbVencimiento1.setChecked(true);
                }
            }
        });
    }

    private void validatePedido() {
        cbPedido1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbPedido1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 5;
            } else {
                if (!cbConteo1.isChecked()
                        && !cbEtiquetas1.isChecked()
                        && !cbConsulta1.isChecked()
                        && !cbSeguimiento1.isChecked()
                        && !cbVencimiento1.isChecked()) {
                    cbPedido1.setChecked(true);
                }
            }
        });
    }

    private void validateConsulta() {
        cbConsulta1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbConsulta1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 4;
            } else {
                if (!cbConteo1.isChecked() &&
                        !cbPedido1.isChecked() &&
                        !cbSeguimiento1.isChecked() &&
                        !cbEtiquetas1.isChecked() &&
                        !cbVencimiento1.isChecked()) {
                    cbConsulta1.setChecked(true);
                }
            }
        });
    }

    private void validateSeguimiento() {
        cbSeguimiento1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbSeguimiento1.isChecked()) {
                cbConteo1.setChecked(false);
                cbEtiquetas1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 3;
            } else {
                if (!cbConteo1.isChecked() &&
                        !cbEtiquetas1.isChecked() &&
                        !cbConsulta1.isChecked() &&
                        !cbPedido1.isChecked() &&
                        !cbVencimiento1.isChecked()) {
                    cbSeguimiento1.setChecked(true);
                }
            }
        });
    }

    private void validateEtiquetas() {
        cbEtiquetas1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbEtiquetas1.isChecked()) {
                cbConteo1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 2;
            } else {
                if (!cbConteo1.isChecked() &&
                        !cbSeguimiento1.isChecked() &&
                        !cbConsulta1.isChecked() &&
                        !cbPedido1.isChecked() &&
                        !cbVencimiento1.isChecked()) {
                    cbEtiquetas1.setChecked(true);
                }
            }
        });
    }

    private void validateConteo() {
        cbConteo1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbConteo1.isChecked()) {
                cbEtiquetas1.setChecked(false);
                cbSeguimiento1.setChecked(false);
                cbConsulta1.setChecked(false);
                cbPedido1.setChecked(false);
                cbVencimiento1.setChecked(false);
                module = 1;
            } else {
                if (!cbEtiquetas1.isChecked() &&
                        !cbSeguimiento1.isChecked() &&
                        !cbConsulta1.isChecked() &&
                        !cbPedido1.isChecked() &&
                        !cbVencimiento1.isChecked()) {
                    cbConteo1.setChecked(true);
                    module = 1;
                }
            }
        });
    }

    private void generateUIChanges() {
        cbConteo1.setAlpha(0.3f);
        cbConteo1.setEnabled(false);
        cbSeguimiento1.setAlpha(0.3f);
        cbSeguimiento1.setEnabled(false);
        cbConsulta1.setAlpha(0.3f);
        cbConsulta1.setEnabled(false);
        cbPedido1.setAlpha(0.3f);
        cbPedido1.setEnabled(false);
        cbVencimiento1.setAlpha(0.3f);
        cbVencimiento1.setEnabled(false);
    }

    private void defineUIElements() {
        btnSearch = findViewById(R.id.btnSearch);
        cbConteo1 = findViewById(R.id.cbConteo1);
        cbEtiquetas1 = findViewById(R.id.cbEtiquetas1);
        cbSeguimiento1 = findViewById(R.id.cbSeguimiento1);
        cbConsulta1 = findViewById(R.id.cbConsulta);
        cbPedido1 = findViewById(R.id.cbPedido1);
        cbVencimiento1 = findViewById(R.id.cbVencimiento1);
        cbQueryMode1 = findViewById(R.id.cbQueryMode1);
        cbQueryMode2 = findViewById(R.id.cbQueryMode2);
        etSearchText = findViewById(R.id.etSearchText);
        titCodigo = findViewById(R.id.titCodigo);
    }

    public void buscarPorBarCode(String code) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_SEARCH_PRODUCT_BY_CODE;
        JsonObjectRequest jObjReq = new JsonObjectRequest(
                Request.Method.GET,
                path + query + code,
                null,
                response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject("Product");
                        NewProduct newProduct = new NewProduct();
                        newProduct.setIdProducto(jsonObject.getInt("id_producto"));
                        newProduct.setCodigoBarras(jsonObject.getString("codigo_barras"));
                        newProduct.setDetalle(jsonObject.getString("detalle"));
                        newProduct.setPrecioVenta(jsonObject.getString("precio_venta"));
                        newProduct.setPrecioOferta(jsonObject.getInt("precio_oferta"));
                        extras.putSerializable("newProduct", newProduct);
                        intent = new Intent(getApplicationContext(), ConteoActivity.class);
                        extras.putInt("module", module);
                        intent.putExtras(extras);
                        startActivity(intent);
                        finish();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(
                            getApplicationContext(),
                            "Fallo en la Lectura, Reintente!!!",
                            Toast.LENGTH_SHORT)
                    .show();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jObjReq);
    }

    public void buscarPorDetalle(String description) {
        HttpsTrustManager.allowAllSSL();

        nextIntent = new Intent(getApplicationContext(), NewProductSelectionActivity.class);
        extras.putInt("module", module);
        extras.putString("detalle", description);
        nextIntent.putExtras(extras);
        startActivity(nextIntent);
        finish();
    }

    private void recuperarPreferencias() {
        preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");
        serverAddress = preferences.getString("serverAddress", "");
        userId = preferences.getInt("userId",1);
        serverId = preferences.getInt("serverId", 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}