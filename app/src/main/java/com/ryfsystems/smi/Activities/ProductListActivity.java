package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_ALL_PRODUCTS;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.SEND_DATA;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Adapters.ProductAdapter;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        btnEnviar.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ProductListActivity.this);
            dialog.setMessage("Esta seguro que desea eniviar los Datos? ESTA ACCION NO SE PUEDE DESHACER")
                    .setPositiveButton("Si", (d, which) -> {
                        d.dismiss();
                        processCSV(view);
                        enviarDatos(INFRA_SERVER_ADDRESS + SEND_DATA);
                        btnEnviar.setEnabled(false);
                    })
                    .setNegativeButton("No", (d, which) -> {
                        d.dismiss();
                    })
                    .setIcon(R.drawable.inventario)
                    .setTitle(" ");
            dialog.show();
        });

        listProducts(INFRA_SERVER_ADDRESS + GET_ALL_PRODUCTS);
    }

    private void enviarDatos(String path) {

        /*StringRequest stringRequest =
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
        queue.add(stringRequest);*/
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
                            jsonObject.getLong("pventa"),
                            jsonObject.getLong("p_oferta"),
                            jsonObject.getDouble("avg_pro"),
                            jsonObject.getLong("costo_prom"),
                            jsonObject.getString("codBarra"),
                            jsonObject.getDouble("pcadena"),
                            jsonObject.getInt("pedido"),
                            jsonObject.getInt("und_defect"));
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

    public void processCSV(View view) {
        try {
            boolean writePermissionStatus = checkStoragePermission(false);
            //Check for permission
            if (!writePermissionStatus) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return;
            } else {
                boolean writePermissionStatusAgain = checkStoragePermission(true);
                if (!writePermissionStatusAgain) {
                    Toast.makeText(this, "No ha otorgado el Permiso", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    //Permission Granted. Export
                    exportDataToCSV();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportDataToCSV() throws IOException {
        String csvData = "";
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            String[] data = product.toString().split(",");
            csvData += toCSV(data) + "\n";
        }
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String uniqueFileName = "Data.csv";
        File file = new File(directory, uniqueFileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(csvData);
        fileWriter.flush();
        fileWriter.close();
        Toast.makeText(ProductListActivity.this, "Archivo Exportado Satisfactoriamente", Toast.LENGTH_SHORT).show();
    }

    public static String toCSV(String[] array) {
        String result = "";
        if (array.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("codlocal, sucursal, activado, dep, ean_13, linea, code, detalle, stock, pventa, poferta, avg_pro, costo_prom, codBarra, pcadena, pedido, und_defect \n");
            for (String s : array) {
                sb.append(s.trim()).append(",");
            }
            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }

    private boolean checkStoragePermission(boolean showNotification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                if (showNotification) showNotificationAlertToAllowPermission();
                return false;
            }
        } else {
            return true;
        }
    }

    private void showNotificationAlertToAllowPermission() {
        new AlertDialog.Builder(this)
                .setMessage("Please allow Storage Read/Write permission for this app to function properly.")
                .setPositiveButton("Open Settings", (paramDialogInterface, paramInt) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }).setNegativeButton("Cancel", null).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}