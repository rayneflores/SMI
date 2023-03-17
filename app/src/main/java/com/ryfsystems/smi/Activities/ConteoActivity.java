package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.NEW_SET_LABEL_PRODUCT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.ryfsystems.smi.Models.NewProduct;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import java.util.HashMap;
import java.util.Map;

public class ConteoActivity extends AppCompatActivity {

    Bundle received;
    Button btnConteoContar;
    int module;
    int serverId;
    Intent nextIntent;
    Long prevStock = 0L;
    NewProduct productReceived;
    RequestQueue requestQueue;
    String rol;
    String serverAddress;
    String usuario;
    String query;
    SharedPreferences preferences;
    String path = INFRA_SERVER_ADDRESS;
    TextInputEditText tvConteoPventa2;
    TextView tvConteoTitle;
    TextView tvConteoId;
    TextView tvConteoId2;
    TextView tvConteoBarCode;
    TextView tvConteoBarCode2;
    TextView tvConteoDetalle;
    TextView tvConteoDetalle2;
    TextView tvConteoPventa;
    TextView tvConteoPOferta;
    TextView tvConteoPOferta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_conteo);

        btnConteoContar = findViewById(R.id.btnConteoContar);

        recuperarPreferencias();

        tvConteoTitle = findViewById(R.id.tvConteoTitle);
        tvConteoId = findViewById(R.id.tvConteoId);
        tvConteoId2 = findViewById(R.id.tvConteoId2);
        tvConteoBarCode = findViewById(R.id.tvConteoBarCode);
        tvConteoBarCode2 = findViewById(R.id.tvConteoBarCode2);
        tvConteoDetalle = findViewById(R.id.tvConteoDetalle);
        tvConteoDetalle2 = findViewById(R.id.tvConteoDetalle2);
        tvConteoPventa = findViewById(R.id.tvConteoPventa);
        tvConteoPventa2 = findViewById(R.id.tvConteoPventa2);
        tvConteoPOferta = findViewById(R.id.tvConteoPrecioOferta);
        tvConteoPOferta2 = findViewById(R.id.tvConteoPrecioOferta2);

        received = getIntent().getExtras();

        //Metodo Nuevo para Generar la Informacion a partir de la consulta
        if (received != null) {
            productReceived = (NewProduct) received.getSerializable("newProduct");
            tvConteoTitle.setText(R.string.generacion_etiqueta);
            tvConteoId2.setText(productReceived.getId_producto());
            tvConteoBarCode2.setText(productReceived.getCodigo_barras());
            tvConteoDetalle2.setText(productReceived.getDetalle());
            if (productReceived.getPrecio_oferta() > 0) {
                SpannableString pventa = new SpannableString(productReceived.getPrecio_venta());
                pventa.setSpan(new StrikethroughSpan(), 0, 4, 0);
                tvConteoPventa2.setText(pventa);
            } else {
                tvConteoPventa2.setText(productReceived.getPrecio_venta());
            }
            tvConteoPOferta2.setText(productReceived.getPrecio_oferta().toString());
            btnConteoContar.setText(R.string.guardar_etiqueta);
        }

        /*if (received != null) {
            productReceived = (Product) received.getSerializable("Product");

            module = (int) received.get("module");

            switch (module) {
                case 1:
                    tvConteoTitle.setText(R.string.conteo_articulo);
                    getStock(productReceived.getEan_13());
                    tvConteoActivado2.setText(productReceived.getActivado() == 1 ? "Si" : "No");
                    tvConteoCode2.setText(productReceived.getCode().toString());
                    tvConteoCodLocal2.setText(productReceived.getCodlocal().toString());
                    tvConteoDetalle2.setText(productReceived.getDetalle());
                    tvConteoDep2.setText(productReceived.getDep());
                    tvConteoEan132.setText(productReceived.getEan_13());
                    tvConteoLinea2.setText(productReceived.getLinea().toString());
                    tvConteoSucursal2.setText(productReceived.getSucursal());
                    tvConteoCantidad2.setText("0");
                    tvConteoPventa.setVisibility(View.INVISIBLE);
                    tvConteoPventa2.setVisibility(View.INVISIBLE);
                    tvConteoPOferta.setVisibility(View.INVISIBLE);
                    tvConteoPOferta2.setVisibility(View.INVISIBLE);
                    tvConteoPventa2.setText(productReceived.getPventa().toString());
                    btnConteoContar.setText("Guardar Conteo");
                    break;
                case 2:
                    tvConteoPrevio2.setVisibility(View.INVISIBLE);
                    tvConteoTitle.setText(R.string.generacion_etiqueta);
                    tvConteoActivado2.setText(productReceived.getActivado() == 1 ? "Si" : "No");
                    getStock2(productReceived.getCodBarra());
                    tvConteoCode2.setText(productReceived.getCode().toString());
                    tvConteoCodLocal2.setText(productReceived.getCodlocal().toString());
                    tvConteoDetalle2.setText(productReceived.getDetalle());
                    tvConteoDep2.setText(productReceived.getDep());
                    tvConteoEan132.setText(productReceived.getEan_13());
                    tvConteoLinea2.setText(productReceived.getLinea().toString());
                    tvConteoSucursal2.setText(productReceived.getSucursal());
                    if (productReceived.getPoferta() > 0) {
                        SpannableString pventa = new SpannableString(productReceived.getPventa().toString());
                        pventa.setSpan(new StrikethroughSpan(), 0, 4, 0);
                        tvConteoPventa2.setText(pventa);
                    } else {
                        tvConteoPventa2.setText(productReceived.getPventa().toString());
                    }
                    tvConteoPOferta2.setText(productReceived.getPoferta().toString());
                    tvConteoPrevio.setVisibility(View.INVISIBLE);
                    tvConteoCantidad2.setEnabled(false);
                    tvConteoPventa2.setEnabled(false);
                    btnConteoContar.setText(R.string.guardar_etiqueta);
                    break;
                case 3:
                    tvConteoPrevio2.setVisibility(View.INVISIBLE);
                    tvConteoTitle.setText(R.string.seguimiento_productos);
                    getStock2(productReceived.getEan_13());
                    tvConteoCode2.setText(productReceived.getCode().toString());
                    tvConteoCodLocal2.setText(productReceived.getCodlocal().toString());
                    tvConteoDetalle2.setText(productReceived.getDetalle());
                    tvConteoDep2.setText(productReceived.getDep());
                    tvConteoEan132.setText(productReceived.getEan_13());
                    tvConteoLinea2.setText(productReceived.getLinea().toString());
                    tvConteoSucursal2.setText(productReceived.getSucursal());
                    if (productReceived.getPoferta() > 0) {
                        SpannableString pventa = new SpannableString(productReceived.getPventa().toString());
                        pventa.setSpan(new StrikethroughSpan(), 0, 4, 0);
                        tvConteoPventa2.setText(pventa);
                    }
                    tvConteoPOferta2.setText(productReceived.getPoferta().toString());
                    tvConteoPrevio.setVisibility(View.INVISIBLE);
                    tvConteoCantidad2.setEnabled(false);
                    btnConteoContar.setText(R.string.guardar_producto);
                    break;
            }
        }*/

        //Tambien hay Cambio de Comportamiento para el boton y en el metodo privado
        btnConteoContar.setOnClickListener(
                view -> updateLabelProduct(
                        productReceived.getId_producto(),
                        productReceived.getCodigo_barras(),
                        productReceived.getDetalle(),
                        productReceived.getPrecio_venta(),
                        productReceived.getPrecio_oferta()
                )
        );

        /*btnConteoContar.setOnClickListener(view -> {

            if (Integer.parseInt(tvConteoCantidad2.getText().toString()) <= 20000) {
                double pventa;
                double poferta;
                requestQueue = Volley.newRequestQueue(this);
                Long total = Long.parseLong(tvConteoCantidad2.getText().toString()) + Long.parseLong(tvConteoPrevio2.getText().toString());
                if (!tvConteoPventa2.getText().toString().equals("") && !tvConteoPventa2.getText().toString().isEmpty()) {
                    pventa = Double.parseDouble(tvConteoPventa2.getText().toString());
                } else {
                    pventa = 0.00;
                }
                if (!tvConteoPventa2.getText().toString().equals("") && !tvConteoPventa2.getText().toString().isEmpty()) {
                    poferta = Double.parseDouble(tvConteoPventa2.getText().toString());
                } else {
                    poferta = 0.00;
                }


                if (total < 0) {
                    Toast.makeText(getApplicationContext(), "No puede Actualizar un Producto con Existencia Negativa", Toast.LENGTH_SHORT).show();
                } else {
                    switch (module) {
                        case 1:
                            updateCountProduct(
                                    productReceived.getActivado(),
                                    productReceived.getCode(),
                                    productReceived.getCodlocal(),
                                    productReceived.getDetalle(),
                                    productReceived.getDep(),
                                    String.valueOf(productReceived.getEan_13()),
                                    productReceived.getLinea(),
                                    productReceived.getSucursal(),
                                    total
                            );
                            break;
                        case 2:
                            updateLabelProduct(
                                    productReceived.getActivado(),
                                    productReceived.getCode(),
                                    productReceived.getCodlocal(),
                                    productReceived.getDetalle(),
                                    productReceived.getDep(),
                                    String.valueOf(productReceived.getEan_13()),
                                    productReceived.getLinea(),
                                    productReceived.getSucursal(),
                                    total,
                                    productReceived.getPventa(),
                                    productReceived.getPoferta()
                            );
                            break;
                        case 3:
                            updateFollowProduct(
                                    productReceived.getActivado(),
                                    productReceived.getCode(),
                                    productReceived.getCodlocal(),
                                    productReceived.getDetalle(),
                                    productReceived.getDep(),
                                    String.valueOf(productReceived.getEan_13()),
                                    productReceived.getLinea(),
                                    productReceived.getSucursal(),
                                    total,
                                    pventa,
                                    poferta
                            );
                            break;
                    }
                    nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(nextIntent);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "CANTIDAD SUPERIOR A 20000", Toast.LENGTH_SHORT).show();
                tvConteoCantidad2.requestFocus();
            }
        });*/
    }

    private void updateLabelProduct(
            Integer id_producto,
            String codigo_barras,
            String detalle,
            String precio_venta,
            Integer precio_oferta) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_SET_LABEL_PRODUCT;
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + query,
                        response -> Toast
                                .makeText(
                                        getApplicationContext(),
                                        response,
                                        Toast.LENGTH_SHORT)
                                .show(),
                        error -> Toast
                                .makeText(
                                        getApplicationContext(),
                                        "Error_Det: " + error.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show()
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_producto", String.valueOf(id_producto));
                        params.put("codigo_barras", codigo_barras);
                        params.put("detalle", detalle);
                        params.put("precio_venta", precio_venta);
                        params.put("precio_oferta", precio_oferta.toString());
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }

    private void recuperarPreferencias() {
        preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        rol = preferences.getString("role", "");
        usuario = preferences.getString("name", "");
        serverAddress = preferences.getString("serverAddress", "");
        serverId = preferences.getInt("serverId", 1);
    }

    /*public void getStock(String ean_13) {
        HttpsTrustManager.allowAllSSL();
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + GET_COUNT_EXISTENCE + ean_13 + "&codSucursal=" + serverId, null, response -> {
            try {
                JSONObject jsonObject = response.getJSONObject("Stock");
                prevStock = jsonObject.getLong("stock_");
                tvConteoPrevio2.setText(prevStock != 0 ? prevStock.toString() : "0");
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(jsonObjectRequest);
    }*/

    /*private void getStock2(String ean_13) {
        HttpsTrustManager.allowAllSSL();
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + GET_REAL_EXISTENCE + ean_13 + "&codSucursal=" + serverId, null, response -> {
            try {
                JSONObject jsonObject = response.getJSONObject("Stock");
                if (!jsonObject.isNull("st_actual")) {
                    prevStock = jsonObject.getLong("st_actual");
                }
                tvConteoCantidad2.setText(prevStock.toString());
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(jsonObjectRequest);
    }*/

    /*private void updateCountProduct(int activado, int code, int codLocal, String detalle, String dep, String ean_13, int linea, String sucursal, Long stock) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + SET_COUNT_PRODUCT,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("activado", String.valueOf(activado));
                        params.put("code", String.valueOf(code));
                        params.put("codlocal", String.valueOf(codLocal));
                        params.put("detalle", detalle);
                        params.put("dep", dep);
                        params.put("ean_13", ean_13);
                        params.put("linea", String.valueOf(linea));
                        params.put("sucursal", sucursal);
                        params.put("stock_", String.valueOf(stock));
                        params.put("codSucursal", String.valueOf(serverId));
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }*/

    /*private void updateLabelProduct(int activado, int code, int codLocal, String detalle, String dep, String ean_13, int linea, String sucursal, Long stock, Long pventa, Long poferta) {

        HttpsTrustManager.allowAllSSL();

        switch (serverId) {
            case 1:
                query = SET_LABEL_PRODUCT;
                break;
            case 2:
                query = SET_LABEL_PRODUCT2;
                break;
            case 3:
                query = SET_LABEL_PRODUCT3;
                break;
        }

        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + query,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("activado", String.valueOf(activado));
                        params.put("code", String.valueOf(code));
                        params.put("codlocal", String.valueOf(codLocal));
                        params.put("detalle", detalle);
                        params.put("dep", dep);
                        params.put("ean_13", ean_13);
                        params.put("linea", String.valueOf(linea));
                        params.put("sucursal", sucursal);
                        params.put("stock_", String.valueOf(stock));
                        params.put("pventa", pventa.toString());
                        params.put("poferta", poferta.toString());
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }*/

    /*private void updateFollowProduct(Integer activado, Integer code, Integer codLocal, String detalle, String dep, String ean_13, Integer linea, String sucursal, Long total, Double pventa, Double poferta) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + SET_SEGUI_PRODUCT,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("activado", String.valueOf(activado));
                        params.put("code", String.valueOf(code));
                        params.put("codlocal", String.valueOf(codLocal));
                        params.put("detalle", detalle);
                        params.put("dep", dep);
                        params.put("ean_13", ean_13);
                        params.put("linea", String.valueOf(linea));
                        params.put("sucursal", sucursal);
                        params.put("stock_", String.valueOf(total));
                        params.put("pventa", pventa.toString());
                        params.put("poferta", poferta.toString());
                        params.put("codSucursal", String.valueOf(serverId));
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}