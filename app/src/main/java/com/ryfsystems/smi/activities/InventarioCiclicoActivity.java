package com.ryfsystems.smi.activities;

import static com.ryfsystems.smi.utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.utils.Constants.NEW_GET_CHECK_IC;
import static com.ryfsystems.smi.utils.Constants.NEW_GET_LAST_IC;
import static com.ryfsystems.smi.utils.Constants.NEW_INVENTARIO_CICLICO;
import static com.ryfsystems.smi.utils.Constants.NEW_UPLOAD_IC;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.adapters.NewProductAdapterCountSelect;
import com.ryfsystems.smi.models.NewProduct;
import com.ryfsystems.smi.utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InventarioCiclicoActivity extends AppCompatActivity {

    FloatingActionButton fabDownload;
    Integer serverId;
    Integer userId;
    Intent nextIntent;
    List<NewProduct> productList = new ArrayList<>();
    NewProductAdapterCountSelect productAdapterCountSelect;
    ProgressDialog progressDialog;
    RecyclerView rvCountQueryICProducts;
    RecyclerView.LayoutManager layoutManager;
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
        callLastCyclicInv(serverId);
        fabDownload = findViewById(R.id.fabDownload);
        fabDownload.setEnabled(false);
        fabDownload.setOnClickListener(v -> callUploadIC(serverId));

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere...");
        progressDialog.setMessage("Listando Productos...");
        progressDialog.setCanceledOnTouchOutside(false);

        /*rvCountQueryICProducts = findViewById(R.id.rvCountQueryICProducts);
        rvCountQueryICProducts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvCountQueryICProducts.setLayoutManager(layoutManager);
        callInventarioCiclico(serverId);*/

    }

    private void callUploadIC(Integer serverId) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_UPLOAD_IC;
        JsonObjectRequest jObjReq = new JsonObjectRequest(
                Request.Method.GET,
                path + query + serverId,
                null,
                response -> {
                    try {
                        Toast.makeText(getApplicationContext(),
                                response.getString("msg"),
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(
                            getApplicationContext(),
                            "No se Encontraron Registros, Cargue Inventario!!!",
                            Toast.LENGTH_LONG)
                    .show();
            progressDialog.dismiss();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jObjReq);
    }


    private void callLastCyclicInv(Integer serverId) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_GET_LAST_IC;
        JsonObjectRequest jObjReq = new JsonObjectRequest(
                Request.Method.GET,
                path + query + serverId,
                null,
                response -> {
                    try {
                        if (response.getInt("id_inventario_ciclico") != 0) {
                            checkInvCicSMI(response.getInt("id_inventario_ciclico"));
                        } else {
                            Toast
                                    .makeText(getApplicationContext(),
                                            "No hay Inventario Ciclico Disponible!!!",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        progressDialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(
                            getApplicationContext(),
                            "No se Encontraron Registros, Cargue Inventario!!!",
                            Toast.LENGTH_LONG)
                    .show();
            progressDialog.dismiss();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jObjReq);
    }

    private void checkInvCicSMI(int inv) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_GET_CHECK_IC;
        JsonObjectRequest jObjReq = new JsonObjectRequest(
                Request.Method.GET,
                path + query + serverId + "&id_inv=" + inv,
                null,
                response -> {
                    try {
                        if (response.getInt("id_inventario_ciclico") != 0) {
                            /*checkInvCicSMI(response.getInt("id_inventario_ciclico"));*/
                        } else {
                            Toast
                                    .makeText(getApplicationContext(),
                                            "No esta Disponible, cargue Inventario!!!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            fabDownload.setEnabled(true);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        progressDialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(
                            getApplicationContext(),
                            "No se Encontraron Registros!!!",
                            Toast.LENGTH_LONG)
                    .show();
            progressDialog.dismiss();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jObjReq);
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
                            newProduct.setIdProducto(jsonObject.getInt("id_producto"));
                            newProduct.setDetalle(jsonObject.getString("detalle"));
                            newProduct.setStock_inventario(jsonObject.getString("stock_inventario"));
                            productList.add(newProduct);
                        }
                        productAdapterCountSelect = new NewProductAdapterCountSelect(InventarioCiclicoActivity.this, productList);
                        rvCountQueryICProducts.setAdapter(productAdapterCountSelect);
                        progressDialog.dismiss();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        progressDialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(
                            getApplicationContext(),
                            "No se Encontraron Registros!!!",
                            Toast.LENGTH_SHORT)
                    .show();
            progressDialog.dismiss();
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}