package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_COUNT_EXISTENCE;
import static com.ryfsystems.smi.Utils.Constants.GET_REAL_EXISTENCE;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.SET_COUNT_PRODUCT;
import static com.ryfsystems.smi.Utils.Constants.SET_LABEL_PRODUCT;
import static com.ryfsystems.smi.Utils.Constants.SET_SEGUI_PRODUCT;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConteoActivity extends AppCompatActivity {

    Bundle received;
    Button btnConteoContar;
    int module;
    Intent nextIntent;
    Long prevStock;
    Product productReceived;
    RequestQueue requestQueue;
    String path = INFRA_SERVER_ADDRESS;
    TextInputEditText tvConteoCantidad2, tvConteoPventa2;
    TextView tvConteoTitle, tvConteoPrevio, tvConteoCode2, tvConteoActivado2, tvConteoCodLocal2, tvConteoDetalle2, tvConteoDep2, tvConteoEan132, tvConteoLinea2, tvConteoSucursal2, tvConteoPrevio2, tvConteoPventa, tvConteoPrecioOferta, tvConteoPrecioOferta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo);

        btnConteoContar = findViewById(R.id.btnConteoContar);

        tvConteoTitle = findViewById(R.id.tvConteoTitle);
        tvConteoPrevio = findViewById(R.id.tvConteoPrevio);
        tvConteoCode2 = findViewById(R.id.tvConteoCode2);
        tvConteoActivado2 = findViewById(R.id.tvConteoActivado2);
        tvConteoCodLocal2 = findViewById(R.id.tvConteoCodLocal2);
        tvConteoDetalle2 = findViewById(R.id.tvConteoDetalle2);
        tvConteoDep2 = findViewById(R.id.tvConteoDep2);
        tvConteoEan132 = findViewById(R.id.tvConteoEan132);
        tvConteoLinea2 = findViewById(R.id.tvConteoLinea2);
        tvConteoSucursal2 = findViewById(R.id.tvConteoSucursal2);
        tvConteoCantidad2 = findViewById(R.id.tvConteoCantidad2);
        tvConteoPrevio2 = findViewById(R.id.tvConteoPrevio2);
        tvConteoPventa = findViewById(R.id.tvConteoPventa);
        tvConteoPventa2 = findViewById(R.id.tvConteoPventa2);
        tvConteoPrecioOferta = findViewById(R.id.tvConteoPrecioOferta);
        tvConteoPrecioOferta2 = findViewById(R.id.tvConteoPrecioOferta2);

        received = getIntent().getExtras();

        if (received != null) {
            productReceived = (Product) received.getSerializable("Product");

            module = (int) received.get("module");

            switch (module) {
                case 1:
                    tvConteoTitle.setText("Conteo de Articulo");
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
                    tvConteoPrecioOferta.setVisibility(View.INVISIBLE);
                    tvConteoPrecioOferta2.setVisibility(View.INVISIBLE);
                    tvConteoPventa2.setText(productReceived.getPventa().toString());
                    btnConteoContar.setText("Guardar Conteo");
                    break;
                case 2:
                    tvConteoPrevio2.setVisibility(View.INVISIBLE);
                    tvConteoTitle.setText("Generacion de Etiqueta");
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
                    tvConteoPrecioOferta2.setText(productReceived.getPoferta().toString());
                    tvConteoPrevio.setVisibility(View.INVISIBLE);
                    tvConteoCantidad2.setEnabled(false);
                    tvConteoPventa2.setEnabled(false);
                    btnConteoContar.setText("Guardar Etiqueta");
                    break;
                case 3:
                    tvConteoPrevio2.setVisibility(View.INVISIBLE);
                    tvConteoTitle.setText("Seguimiento de Productos");
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
                    tvConteoPrecioOferta2.setText(productReceived.getPoferta().toString());
                    tvConteoPrevio.setVisibility(View.INVISIBLE);
                    tvConteoCantidad2.setEnabled(false);
                    btnConteoContar.setText("Guardar Producto");
                    break;
            }
        }

        btnConteoContar.setOnClickListener(view -> {

            if (Integer.parseInt(tvConteoCantidad2.getText().toString()) <= 20000) {
                requestQueue = Volley.newRequestQueue(this);
                Long total = Long.parseLong(tvConteoCantidad2.getText().toString()) + Long.parseLong(tvConteoPrevio2.getText().toString());
                Double pventa = Double.parseDouble(tvConteoPventa2.getText().toString());
                Double poferta = Double.parseDouble(tvConteoPventa2.getText().toString());

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
        });
    }

    public void getStock(String ean_13) {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + GET_COUNT_EXISTENCE + ean_13, null, response -> {
            try {
                JSONObject jsonObject = response.getJSONObject("Stock");
                prevStock = jsonObject.getLong("stock_");
                tvConteoPrevio2.setText(prevStock != 0 ? prevStock.toString() : "0");
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getStock2(String ean_13) {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + GET_REAL_EXISTENCE + ean_13, null, response -> {
            try {
                JSONObject jsonObject = response.getJSONObject("Stock");
                prevStock = jsonObject.getLong("stock_");
                tvConteoCantidad2.setText(prevStock.toString());
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void updateCountProduct(int activado, int code, int codLocal, String detalle, String dep, String ean_13, int linea, String sucursal, Long stock) {
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
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }

    private void updateLabelProduct(int activado, int code, int codLocal, String detalle, String dep, String ean_13, int linea, String sucursal, Long stock, Long pventa, Long poferta) {
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + SET_LABEL_PRODUCT,
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
    }

    private void updateFollowProduct(Integer activado, Integer code, Integer codLocal, String detalle, String dep, String ean_13, Integer linea, String sucursal, Long total, Double pventa, Double poferta) {
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
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextIntent);
        finish();
    }
}