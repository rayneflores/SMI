package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Constants.GET_ALL_PRODUCTS;
import static com.ryfsystems.smi.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Constants.SEND_DATA;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Adapters.ProductAdapter;
import com.ryfsystems.smi.Adapters.UserAdapter;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.Models.User;
import com.ryfsystems.smi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;

public class ProductListActivity extends AppCompatActivity {

    Button btnEnviar;
    Intent nextIntent;
    JsonObjectRequest jsonObjectRequest;
    List<Product> productList = new ArrayList<>();
    ProgressDialog progressDialog;
    RecyclerView rvProducts;
    RecyclerView.LayoutManager layoutManager;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere...");
        progressDialog.setMessage("Listando Productos...");
        progressDialog.setCanceledOnTouchOutside(false);

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvProducts.setLayoutManager(layoutManager);

        btnEnviar = findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProductListActivity.this);
                dialog.setMessage("Esta seguro que desea eniviar los Datos? ESTA ACCION NO SE PUEDE DESHACER")
                        .setPositiveButton("Si", (d, which) -> {
                            d.dismiss();
                            enviarDatos(INFRA_SERVER_ADDRESS + SEND_DATA);
                            btnEnviar.setEnabled(false);
                        })
                        .setNegativeButton("No", (d, which) -> {
                            d.dismiss();
                        })
                        .setIcon(R.drawable.inventario)
                        .setTitle(" ");
                dialog.show();
            }
        });

        listProducts(INFRA_SERVER_ADDRESS + GET_ALL_PRODUCTS);
    }

    private void enviarDatos(String path) {
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        path,
                        response -> {
                            if (response.equals("200")){
                                Toast.makeText(getApplicationContext(), "Datos Enviados!!!", Toast.LENGTH_SHORT).show();
                                productList.clear();
                                onBackPressed();
                            } else {
                                Toast.makeText(getApplicationContext(), "Ocurrio un Error al Enviar!!!", Toast.LENGTH_SHORT).show();
                                btnEnviar.setEnabled(true);
                            }
                        }, error -> {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    btnEnviar.setEnabled(true);
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void listProducts(String path) {

        progressDialog.setMessage("Listando Productos...");
        progressDialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, response -> {

            try {
                productList.clear();
                JSONArray jsonArray = response.getJSONArray("Products");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Product product = new Product(
                            jsonObject.getInt("codlocal"),
                            jsonObject.getString("sucursal"),
                            jsonObject.getInt("activado"),
                            jsonObject.getString("dep"),
                            jsonObject.getString("ean_13"),
                            jsonObject.getInt("linea"),
                            jsonObject.getInt("code"),
                            jsonObject.getString("detalle"),
                            jsonObject.getLong("stock_"),
                            jsonObject.getLong("pventa")
                    );
                    productList.add(product);
                }
                productAdapter = new ProductAdapter(ProductListActivity.this, productList);
                rvProducts.setAdapter(productAdapter);
                progressDialog.dismiss();
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), "Sql: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "No Hay datos!!!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            onBackPressed();
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