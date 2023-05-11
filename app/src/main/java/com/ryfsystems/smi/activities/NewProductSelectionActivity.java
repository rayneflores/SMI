package com.ryfsystems.smi.activities;

import static com.ryfsystems.smi.utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.utils.Constants.NEW_GET_QUERY_PRODUCT;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.adapters.NewProductAdapterSelection;
import com.ryfsystems.smi.models.NewProduct;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProductSelectionActivity extends AppCompatActivity {

    Bundle received;
    int module, serverId, userId;
    Intent nextIntent;
    JsonObjectRequest jsonObjectRequest;
    List<NewProduct> productList = new ArrayList<>();
    ProgressDialog progressDialog;
    NewProductAdapterSelection productAdapterSelection;
    RecyclerView rvQueryProducts;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences preferences;
    String rol, usuario, query, detalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_selection);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere...");
        progressDialog.setMessage("Listando Productos...");
        progressDialog.setCanceledOnTouchOutside(false);

        rvQueryProducts = findViewById(R.id.rvQueryProducts);
        rvQueryProducts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvQueryProducts.setLayoutManager(layoutManager);

        recuperarPreferencias();
        received = getIntent().getExtras();
        module = (int) received.get("module");
        detalle = (String) received.get("detalle");

        query = NEW_GET_QUERY_PRODUCT + detalle + "&id_suc=" + serverId;

        listProducts(INFRA_SERVER_ADDRESS + query);
    }

    private void listProducts(String path) {

        HttpsTrustManager.allowAllSSL();
        progressDialog.setMessage("Listando Productos...");
        progressDialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, response -> {
            try {
                productList.clear();
                JSONArray jsonArray = response.getJSONArray("Products");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    NewProduct newProduct = new NewProduct();
                    newProduct.setIdProducto(jsonObject.getInt("id_producto"));
                    newProduct.setCodigoBarras(jsonObject.getString("codigo_barras"));
                    newProduct.setDetalle(jsonObject.getString("detalle"));
                    newProduct.setPrecioVenta(jsonObject.getString("precio_venta"));
                    newProduct.setPrecioOferta(jsonObject.getInt("precio_oferta"));
                    productList.add(newProduct);
                }
                productAdapterSelection = new NewProductAdapterSelection(NewProductSelectionActivity.this, productList, module);
                rvQueryProducts.setAdapter(productAdapterSelection);
                progressDialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Sql: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "Fallo en la Lectura, Reintente!!!", Toast.LENGTH_SHORT).show();
            rvQueryProducts.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();
            onBackPressed();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void recuperarPreferencias() {
        preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");
        serverId = preferences.getInt("serverId", 1);
        userId = preferences.getInt("userId",1);
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