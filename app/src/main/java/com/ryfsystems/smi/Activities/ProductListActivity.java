package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_COUNT;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_DEFECT;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_FOLLOW;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_LABEL;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_REQUEST;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.SEND_DATA;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Adapters.ProductAdapterCount;
import com.ryfsystems.smi.Adapters.ProductAdapterLabel;
import com.ryfsystems.smi.Adapters.ProductAdapterRequest;
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
    CheckBox cbConteo, cbEtiquetas, cbSeguimiento, cbPedido, cbVencimiento;
    int module;
    Intent nextIntent;
    JsonObjectRequest jsonObjectRequest;
    List<Product> productList = new ArrayList<>();
    ProgressDialog progressDialog;
    RecyclerView rvProducts;
    RecyclerView.LayoutManager layoutManager;
    ProductAdapterCount productAdapterCount;
    ProductAdapterLabel productAdapterLabel;
    ProductAdapterRequest productAdapterRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere...");
        progressDialog.setMessage("Listando Productos...");
        progressDialog.setCanceledOnTouchOutside(false);

        cbConteo = findViewById(R.id.cbConteo);
        cbEtiquetas = findViewById(R.id.cbEtiquetas);
        cbSeguimiento = findViewById(R.id.cbSeguimiento);
        cbPedido = findViewById(R.id.cbPedido);
        cbVencimiento = findViewById(R.id.cbVencimiento);

        module = 1;

        listProducts(INFRA_SERVER_ADDRESS + GET_PRODUCTS_COUNT, module);

        cbConteo.setChecked(true);

        cbConteo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbConteo.isChecked()){
                module = 1;
                cbEtiquetas.setChecked(false);
                cbSeguimiento.setChecked(false);
                cbPedido.setChecked(false);
                cbVencimiento.setChecked(false);
                listProducts(INFRA_SERVER_ADDRESS + GET_PRODUCTS_COUNT, module);
            } else {
                if (!cbEtiquetas.isChecked() && !cbSeguimiento.isChecked() && !cbPedido.isChecked() && !cbVencimiento.isChecked()){
                    cbConteo.setChecked(true);
                }
            }
        });

        cbEtiquetas.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbEtiquetas.isChecked()){
                module = 2;
                cbConteo.setChecked(false);
                cbSeguimiento.setChecked(false);
                cbPedido.setChecked(false);
                cbVencimiento.setChecked(false);
                listProducts(INFRA_SERVER_ADDRESS + GET_PRODUCTS_LABEL, module);
            } else {
                if (!cbConteo.isChecked() && !cbSeguimiento.isChecked() && !cbPedido.isChecked() && !cbVencimiento.isChecked()){
                    cbEtiquetas.setChecked(true);
                }
            }
        });

        cbSeguimiento.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbSeguimiento.isChecked()){
                module = 3;
                cbConteo.setChecked(false);
                cbEtiquetas.setChecked(false);
                cbPedido.setChecked(false);
                cbVencimiento.setChecked(false);
                listProducts(INFRA_SERVER_ADDRESS + GET_PRODUCTS_FOLLOW, module);
            } else {
                if (!cbConteo.isChecked() && !cbEtiquetas.isChecked() && !cbPedido.isChecked() && !cbVencimiento.isChecked()){
                    cbSeguimiento.setChecked(true);
                }
            }
        });

        cbPedido.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbPedido.isChecked()){
                module = 4;
                cbConteo.setChecked(false);
                cbEtiquetas.setChecked(false);
                cbSeguimiento.setChecked(false);
                cbVencimiento.setChecked(false);
                listProducts(INFRA_SERVER_ADDRESS + GET_PRODUCTS_REQUEST, module);
            } else {
                if (!cbConteo.isChecked() && !cbEtiquetas.isChecked() && !cbSeguimiento.isChecked() && !cbVencimiento.isChecked()){
                    cbPedido.setChecked(true);
                }
            }
        });

        cbVencimiento.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbVencimiento.isChecked()){
                module = 5;
                cbConteo.setChecked(false);
                cbEtiquetas.setChecked(false);
                cbSeguimiento.setChecked(false);
                cbPedido.setChecked(false);
                listProducts(INFRA_SERVER_ADDRESS + GET_PRODUCTS_DEFECT, module);
            } else {
                if (!cbConteo.isChecked() && !cbEtiquetas.isChecked() && !cbSeguimiento.isChecked() && !cbPedido.isChecked()){
                    cbVencimiento.setChecked(true);
                }
            }
        });

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
                        btnEnviar.setEnabled(false);
                    })
                    .setNegativeButton("No", (d, which) -> {
                        d.dismiss();
                    })
                    .setIcon(R.drawable.magallean)
                    .setTitle(" ");
            dialog.show();
        });
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

    private void listProducts(String path, int mod) {

        progressDialog.setMessage("Listando Productos...");
        progressDialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, response -> {

            try {
                JSONArray jsonArray = response.getJSONArray("Products");
                switch (mod) {
                    case 1:
                        productList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setCode(jsonObject.getInt("code"));
                            product.setCodlocal(jsonObject.getInt("codlocal"));
                            product.setActivado(jsonObject.getInt("activado"));
                            product.setDep(jsonObject.getString("dep"));
                            product.setEan_13(jsonObject.getString("ean_13"));
                            product.setLinea(jsonObject.getInt("linea"));
                            product.setDetalle(jsonObject.getString("detalle"));
                            product.setSucursal(jsonObject.getString("sucursal"));
                            product.setStock_(jsonObject.getLong("stock_"));
                            productList.add(product);
                        }
                        productAdapterCount = new ProductAdapterCount(ProductListActivity.this, productList);
                        rvProducts.setAdapter(productAdapterCount);
                        break;
                    case 2:
                    case 3:
                        productList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setCode(jsonObject.getInt("code"));
                            product.setCodlocal(jsonObject.getInt("codlocal"));
                            product.setActivado(jsonObject.getInt("activado"));
                            product.setDep(jsonObject.getString("dep"));
                            product.setEan_13(jsonObject.getString("ean_13"));
                            product.setLinea(jsonObject.getInt("linea"));
                            product.setDetalle(jsonObject.getString("detalle"));
                            product.setSucursal(jsonObject.getString("sucursal"));
                            product.setStock_(jsonObject.getLong("stock_"));
                            product.setPventa(jsonObject.getLong("pventa"));
                            product.setPoferta(jsonObject.getLong("p_oferta"));
                            productList.add(product);
                        }
                        productAdapterLabel = new ProductAdapterLabel(ProductListActivity.this, productList);
                        rvProducts.setAdapter(productAdapterLabel);
                        break;
                    case 4:
                        productList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setCode(jsonObject.getInt("code"));
                            product.setCodlocal(jsonObject.getInt("codlocal"));
                            product.setDetalle(jsonObject.getString("detalle"));
                            product.setEan_13(jsonObject.getString("ean_13"));
                            product.setSucursal(jsonObject.getString("sucursal"));
                            product.setStock_(jsonObject.getLong("stock_"));
                            product.setPventa(jsonObject.getLong("pventa"));
                            product.setPoferta(jsonObject.getLong("p_oferta"));
                            product.setAvg_pro(jsonObject.getDouble("avg_pro"));
                            product.setCodBarra(jsonObject.getString("codBarra"));
                            product.setPcadena(jsonObject.getDouble("pcadena"));
                            product.setPedido(jsonObject.getInt("pedido"));
                            productList.add(product);
                        }
                        productAdapterRequest = new ProductAdapterRequest(ProductListActivity.this, productList);
                        rvProducts.setAdapter(productAdapterRequest);
                        break;
                    case 5:
                        productList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Product product = new Product();
                            product.setCode(jsonObject.getInt("code"));
                            product.setDetalle(jsonObject.getString("detalle"));
                            product.setUnd_defect(jsonObject.getInt("und_defect"));
                            product.setResponsable(jsonObject.getString("responsable"));
                            productList.add(product);
                        }
                        productAdapterRequest = new ProductAdapterRequest(ProductListActivity.this, productList);
                        rvProducts.setAdapter(productAdapterRequest);
                        break;
                }
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
        String uniqueFileName = "reporte_magallEAN.csv";
        File file = new File(directory, uniqueFileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(csvData);
        fileWriter.flush();
        fileWriter.close();
        Toast.makeText(ProductListActivity.this, "Archivo Exportado Satisfactoriamente", Toast.LENGTH_SHORT).show();
        enviarDatos(INFRA_SERVER_ADDRESS + SEND_DATA);
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