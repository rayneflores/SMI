package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_QUERY_PRODUCT1;
import static com.ryfsystems.smi.Utils.Constants.GET_QUERY_PRODUCT2;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Adapters.ProductAdapterSelection;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductSelectionActivity extends AppCompatActivity {

    Bundle received;
    int module, serverId;
    Intent nextIntent;
    JsonObjectRequest jsonObjectRequest;
    List<Product> productList = new ArrayList<>();
    ProgressDialog progressDialog;
    ProductAdapterSelection productAdapterSelection;
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

        switch (serverId) {
            case 1:
                query = GET_QUERY_PRODUCT1 + detalle;
                break;
            case 2:
                query = GET_QUERY_PRODUCT2 + detalle;
                break;
        }

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
                    Product product = new Product();
                    product.setCode(jsonObject.getInt("code"));
                    product.setCodlocal(jsonObject.getInt("codlocal"));
                    product.setActivado(jsonObject.getInt("activado"));
                    product.setCodBarra(jsonObject.getString("cbarra"));
                    product.setDep(jsonObject.getString("dep"));
                    product.setEan_13(jsonObject.getString("ean_13"));
                    product.setLinea(jsonObject.getInt("linea"));
                    product.setDetalle(jsonObject.getString("detalle"));
                    product.setSucursal(jsonObject.getString("sucursal"));
                    product.setStock_(jsonObject.getLong("stock_"));
                    product.setPventa(jsonObject.getLong("pventa"));
                    product.setPoferta(jsonObject.getLong("p_oferta"));
                    product.setAvg_pro(jsonObject.getDouble("avg_pro"));
                    product.setCosto_prom(jsonObject.getLong("costo_prom"));
                    product.setPcadena(jsonObject.getDouble("pcadena"));
                    productList.add(product);
                }
                productAdapterSelection = new ProductAdapterSelection(ProductSelectionActivity.this, productList, module);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}