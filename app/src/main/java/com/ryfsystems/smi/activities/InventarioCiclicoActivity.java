package com.ryfsystems.smi.activities;

import static com.ryfsystems.smi.utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.utils.Constants.NEW_INVENTARIO_CICLICO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.adapters.NewProductAdapterCountSelect;
import com.ryfsystems.smi.models.NewProduct;
import com.ryfsystems.smi.utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InventarioCiclicoActivity extends AppCompatActivity {

    Integer serverId;
    Integer userId;
    Intent nextIntent;
    List<NewProduct> productList = new ArrayList<>();
    NewProductAdapterCountSelect productAdapterCountSelect;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    String path = INFRA_SERVER_ADDRESS;
    String query;
    String rol;
    String serverAddress;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario_ciclico);
        recuperarPreferencias();
        callInventarioCiclico(serverId);
        /*Toast.makeText(
                        this,
                        "Estoy en la Sucursal: " + serverId,
                        Toast.LENGTH_SHORT)
                .show();*/
    }

    private void callInventarioCiclico(Integer serverId) {
        HttpsTrustManager.allowAllSSL();
        progressDialog.setMessage("Listando Productos...");
        progressDialog.show();
        query = NEW_INVENTARIO_CICLICO;
        JsonObjectRequest jObjReq = new JsonObjectRequest(
                Request.Method.GET,
                path + query + serverId,
                null,
                response -> {
                    try {
                        productList.clear();
                        JSONArray jsonArray = response.getJSONArray("Products");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            NewProduct newProduct = new NewProduct();
                            newProduct.setDetalle(jsonObject.getString("detalle"));
                            productList.add(newProduct);
                        }
                        productAdapterCountSelect = new NewProductAdapterCountSelect(InventarioCiclicoActivity.this, productList);
                    }catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(
                            getApplicationContext(),
                            "No se Encontraron Registros!!!",
                            Toast.LENGTH_SHORT)
                    .show();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jObjReq);
    }

    private void recuperarPreferencias() {
        preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");
        serverAddress = preferences.getString("serverAddress", "");
        userId = preferences.getInt("userId", 1);
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