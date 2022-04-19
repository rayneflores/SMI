package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_COUNT;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_DEFECT;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_FOLLOW;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_LABEL;
import static com.ryfsystems.smi.Utils.Constants.GET_PRODUCTS_REQUEST;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.SEND_COUNT_DATA;
import static com.ryfsystems.smi.Utils.Constants.SEND_DEFECT_DATA;
import static com.ryfsystems.smi.Utils.Constants.SEND_FOLLOW_DATA;
import static com.ryfsystems.smi.Utils.Constants.SEND_LABEL_DATA;
import static com.ryfsystems.smi.Utils.Constants.SEND_REQUEST_DATA;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.ryfsystems.smi.Adapters.ProductAdapterDefect;
import com.ryfsystems.smi.Adapters.ProductAdapterLabel;
import com.ryfsystems.smi.Adapters.ProductAdapterRequest;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

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
    ProductAdapterDefect productAdapterDefect;

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
            if (cbConteo.isChecked()) {
                btnEnviar.setEnabled(true);
                module = 1;
                cbEtiquetas.setChecked(false);
                cbSeguimiento.setChecked(false);
                cbPedido.setChecked(false);
                cbVencimiento.setChecked(false);
                listProducts(INFRA_SERVER_ADDRESS + GET_PRODUCTS_COUNT, module);
            } else {
                if (!cbEtiquetas.isChecked() && !cbSeguimiento.isChecked() && !cbPedido.isChecked() && !cbVencimiento.isChecked()) {
                    cbConteo.setChecked(true);
                }
            }
        });

        cbEtiquetas.setOnCheckedChangeListener((compoundButton, b) -> {
            if (cbEtiquetas.isChecked()){
                btnEnviar.setEnabled(true);
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
                btnEnviar.setEnabled(true);
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
                btnEnviar.setEnabled(true);
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
                btnEnviar.setEnabled(true);
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
            dialog.setMessage("Esta seguro que desea enviar los Datos? ESTA ACCION NO SE PUEDE DESHACER")
                    .setPositiveButton("Si", (d, which) -> {
                        d.dismiss();
                        processCSV(view, module);
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

    private void enviarDatos(String path, int module) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest;
        RequestQueue queue;
        switch (module) {
            case 1:
                stringRequest =
                        new StringRequest(
                                Request.Method.POST,
                                path + SEND_COUNT_DATA,
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
                queue = Volley.newRequestQueue(this);
                queue.add(stringRequest);
                break;
            case 2:
                stringRequest =
                        new StringRequest(
                                Request.Method.POST,
                                path + SEND_LABEL_DATA,
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
                queue = Volley.newRequestQueue(this);
                queue.add(stringRequest);
                break;
            case 3:
                stringRequest =
                        new StringRequest(
                                Request.Method.POST,
                                path + SEND_FOLLOW_DATA,
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
                queue = Volley.newRequestQueue(this);
                queue.add(stringRequest);
                break;
            case 4:
                stringRequest =
                        new StringRequest(
                                Request.Method.POST,
                                path + SEND_REQUEST_DATA,
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
                queue = Volley.newRequestQueue(this);
                queue.add(stringRequest);
                break;
            case 5:
                stringRequest =
                        new StringRequest(
                                Request.Method.POST,
                                path + SEND_DEFECT_DATA,
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
                queue = Volley.newRequestQueue(this);
                queue.add(stringRequest);
                break;
        }
    }

    private void listProducts(String path, int mod) {

        HttpsTrustManager.allowAllSSL();
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
                            product.setCosto_prom(jsonObject.getLong("costo_prom"));
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
                        productAdapterDefect = new ProductAdapterDefect(ProductListActivity.this, productList);
                        rvProducts.setAdapter(productAdapterDefect);
                        break;
                }
                rvProducts.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), "Sql: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "No Hay datos!!!", Toast.LENGTH_SHORT).show();
            rvProducts.setVisibility(View.INVISIBLE);
            progressDialog.dismiss();
            btnEnviar.setEnabled(false);
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public void processCSV(View view, int module) {
        try {
            boolean writePermissionStatus = checkStoragePermission(false);
            //Check for permission
            if (!writePermissionStatus) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return;
            } else {
                boolean writePermissionStatusAgain = checkStoragePermission(true);
                if (!writePermissionStatusAgain) {
                    Toast.makeText(this, "No ha otorgado el Permiso", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //Permission Granted. Export
                    exportDataToCSV(module);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportDataToCSV(int module) throws IOException {
        File directory;
        File file;
        FileWriter fileWriter;
        String csvData = "";
        String uniqueFileName;

        switch (module) {
            case 1:
                csvData = "codlocal, sucursal, activado, dep, ean_13, linea, code, detalle, stock \n";
                for (int i = 0; i < productList.size(); i++) {
                    Product product = new Product();
                    product.setActivado(productList.get(i).getActivado());
                    product.setCode(productList.get(i).getCode());
                    product.setCodlocal(productList.get(i).getCodlocal());
                    product.setDetalle(productList.get(i).getDetalle());
                    product.setDep(productList.get(i).getDep());
                    product.setEan_13(productList.get(i).getEan_13());
                    product.setLinea(productList.get(i).getLinea());
                    product.setSucursal(productList.get(i).getSucursal());
                    product.setStock_(productList.get(i).getStock_());
                    String[] data = product.toStringCount().split(",");
                    csvData += toCSV(data, module) + "\n";
                }
                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                uniqueFileName = "reporte_conteo_magallEAN.csv";
                file = new File(directory, uniqueFileName);
                fileWriter = new FileWriter(file);
                fileWriter.write(csvData);
                fileWriter.flush();
                fileWriter.close();
                Toast.makeText(ProductListActivity.this, "Archivo Exportado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                enviarDatos(INFRA_SERVER_ADDRESS, module);
                break;
            case 2:
                csvData = "codlocal, sucursal, activado, dep, ean_13, linea, code, detalle, stock, pventa, poferta \n";
                for (int i = 0; i < productList.size(); i++) {
                    Product product = new Product();
                    product.setActivado(productList.get(i).getActivado());
                    product.setCode(productList.get(i).getCode());
                    product.setCodlocal(productList.get(i).getCodlocal());
                    product.setDetalle(productList.get(i).getDetalle());
                    product.setDep(productList.get(i).getDep());
                    product.setEan_13(productList.get(i).getEan_13());
                    product.setLinea(productList.get(i).getLinea());
                    product.setSucursal(productList.get(i).getSucursal());
                    product.setStock_(productList.get(i).getStock_());
                    product.setPventa(productList.get(i).getPventa());
                    product.setPoferta(productList.get(i).getPoferta());
                    String[] data = product.toStringLabel().split(",");
                    csvData += toCSV(data, module) + "\n";
                }
                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                uniqueFileName = "reporte_etiquetas_magallEAN.csv";
                file = new File(directory, uniqueFileName);
                fileWriter = new FileWriter(file);
                fileWriter.write(csvData);
                fileWriter.flush();
                fileWriter.close();
                Toast.makeText(ProductListActivity.this, "Archivo Exportado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                enviarDatos(INFRA_SERVER_ADDRESS, module);
                break;
            case 3:
                csvData = "codlocal, sucursal, activado, dep, ean_13, linea, code, detalle, stock, pventa, poferta \n";
                for (int i = 0; i < productList.size(); i++) {
                    Product product = new Product();
                    product.setActivado(productList.get(i).getActivado());
                    product.setCode(productList.get(i).getCode());
                    product.setCodlocal(productList.get(i).getCodlocal());
                    product.setDetalle(productList.get(i).getDetalle());
                    product.setDep(productList.get(i).getDep());
                    product.setEan_13(productList.get(i).getEan_13());
                    product.setLinea(productList.get(i).getLinea());
                    product.setSucursal(productList.get(i).getSucursal());
                    product.setStock_(productList.get(i).getStock_());
                    product.setPventa(productList.get(i).getPventa());
                    product.setPoferta(productList.get(i).getPoferta());
                    String[] data = product.toStringLabel().split(",");
                    csvData += toCSV(data, module) + "\n";
                }
                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                uniqueFileName = "reporte_seguimiento_magallEAN.csv";
                file = new File(directory, uniqueFileName);
                fileWriter = new FileWriter(file);
                fileWriter.write(csvData);
                fileWriter.flush();
                fileWriter.close();
                Toast.makeText(ProductListActivity.this, "Archivo Exportado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                enviarDatos(INFRA_SERVER_ADDRESS, module);
                break;
            case 4:
                csvData = "codlocal, sucursal, ean_13, linea, code, detalle, stock, pventa, poferta, avg_pro, codBarra, pcadena, pedido \n";
                for (int i = 0; i < productList.size(); i++) {
                    Product product = new Product();
                    product.setCode(productList.get(i).getCode());
                    product.setCodlocal(productList.get(i).getCodlocal());
                    product.setDetalle(productList.get(i).getDetalle());
                    product.setEan_13(productList.get(i).getEan_13());
                    product.setLinea(productList.get(i).getLinea());
                    product.setSucursal(productList.get(i).getSucursal());
                    product.setStock_(productList.get(i).getStock_());
                    product.setPventa(productList.get(i).getPventa());
                    product.setPoferta(productList.get(i).getPoferta());
                    product.setAvg_pro(productList.get(i).getAvg_pro());
                    product.setCodBarra(productList.get(i).getCodBarra());
                    product.setPcadena(productList.get(i).getPcadena());
                    product.setPedido(productList.get(i).getPedido());
                    String[] data = product.toStringRequest().split(",");
                    csvData += toCSV(data, module) + "\n";
                }
                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                uniqueFileName = "reporte_pedido_magallEAN.csv";
                file = new File(directory, uniqueFileName);
                fileWriter = new FileWriter(file);
                fileWriter.write(csvData);
                fileWriter.flush();
                fileWriter.close();
                Toast.makeText(ProductListActivity.this, "Archivo Exportado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                enviarDatos(INFRA_SERVER_ADDRESS, module);
                break;
            case 5:
                csvData = "code, detalle, und_defect, responsable \n";
                for (int i = 0; i < productList.size(); i++) {
                    Product product = new Product();
                    product.setCode(productList.get(i).getCode());
                    product.setDetalle(productList.get(i).getDetalle());
                    product.setUnd_defect(productList.get(i).getUnd_defect());
                    product.setResponsable(productList.get(i).getResponsable());
                    String[] data = product.toStringVence().split(",");
                    csvData += toCSV(data, module) + "\n";
                }
                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                uniqueFileName = "reporte_vencimiento_magallEAN.csv";
                file = new File(directory, uniqueFileName);
                fileWriter = new FileWriter(file);
                fileWriter.write(csvData);
                fileWriter.flush();
                fileWriter.close();
                Toast.makeText(ProductListActivity.this, "Archivo Exportado Satisfactoriamente", Toast.LENGTH_SHORT).show();
                enviarDatos(INFRA_SERVER_ADDRESS, module);
                break;
        }
    }

    public static String toCSV(String[] array, int module) {
        String result = "";
        if (array.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : array) {
                sb.append(s.trim()).append(",");
            }
            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }

    private boolean checkStoragePermission(boolean showNotification) {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (showNotification) showNotificationAlertToAllowPermission();
            return false;
        }
    }

    private void showNotificationAlertToAllowPermission() {
        new AlertDialog.Builder(this)
                .setMessage("Otorgue Permisos de lectura/escritura a la aplicacion, para que funcione apropiadamente.")
                .setPositiveButton("Configuracion", (paramDialogInterface, paramInt) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }).setNegativeButton("Cancelar", null).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}