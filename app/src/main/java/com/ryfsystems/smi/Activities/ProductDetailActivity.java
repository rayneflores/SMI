package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_REAL_EXISTENCE;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.SET_PRODUCT_FULL;

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
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    Bundle received;
    Button btnDetailOption;
    Double margen, stock, vtaSem;
    int module;
    Intent nextIntent;
    Long poferta, pventa, costoProm;
    Product productReceived;
    RequestQueue requestQueue;
    String path = INFRA_SERVER_ADDRESS;
    TextInputEditText tvDetailCantidad2;
    TextView tvDetailCode2, tvDetailBarCode2, tvDetailDetalle2, tvDetailMargen2, tvDetailStock2, tvDetailAvgPro2, tvDetailAvgProSem2, tvDetailPcadena2, tvDetailCantidad, tvDetailPVenta2, tvDetailPOferta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvDetailCode2 = findViewById(R.id.tvDetailCode2);
        tvDetailBarCode2 = findViewById(R.id.tvDetailBarCode2);
        tvDetailDetalle2 = findViewById(R.id.tvDetailDetalle2);
        tvDetailPVenta2 = findViewById(R.id.tvDetailPVenta2);
        tvDetailPOferta2 = findViewById(R.id.tvDetailPOferta2);
        tvDetailMargen2 = findViewById(R.id.tvDetailMargen2);
        tvDetailStock2 = findViewById(R.id.tvDetailStock2);
        tvDetailAvgPro2 = findViewById(R.id.tvDetailAvgPro2);
        tvDetailAvgProSem2 = findViewById(R.id.tvDetailAvgProSem2);
        tvDetailPcadena2 = findViewById(R.id.tvDetailPcadena2);
        tvDetailCantidad2 = findViewById(R.id.tvDetailCantidad2);
        tvDetailCantidad = findViewById(R.id.tvDetailCantidad);
        btnDetailOption = findViewById(R.id.btnDetailOption);

        btnDetailOption.setOnClickListener(view -> {
            if (Integer.parseInt(tvDetailCantidad2.getText().toString()) > 0) {
                requestQueue = Volley.newRequestQueue(this);
                int pedido = Integer.parseInt(tvDetailCantidad2.getText().toString());
                updateReqProduct(
                        productReceived.getCode(),
                        productReceived.getCodlocal(),
                        productReceived.getDetalle(),
                        productReceived.getEan_13(),
                        productReceived.getSucursal(),
                        productReceived.getStock_(),
                        productReceived.getPventa(),
                        productReceived.getPoferta(),
                        productReceived.getAvg_pro(),
                        productReceived.getCodBarra(),
                        productReceived.getPcadena(),
                        pedido
                );
                nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(nextIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Al menos debe Pedir una (01) Unidad", Toast.LENGTH_SHORT).show();
                tvDetailCantidad2.requestFocus();
            }
        });

        received = getIntent().getExtras();

        if (received != null) {

            module = (int) received.get("module");

            DecimalFormat df = new DecimalFormat("0.00%");
            DecimalFormat df2 = new DecimalFormat("0.00");
            String fMargin, fVtaSem;
            switch (module) {
                case 4:
                    productReceived = (Product) received.getSerializable("Product");
                    getStock2(productReceived.getCodBarra());
                    poferta = productReceived.getPoferta();
                    pventa = productReceived.getPventa();
                    costoProm = productReceived.getCosto_prom();

                    if (poferta > 0) {
                        margen = ((poferta / 1.19) - costoProm)/(poferta / 1.19);
                        SpannableString pventa = new SpannableString(productReceived.getPventa().toString());
                        pventa.setSpan(new StrikethroughSpan(), 0,4,0);
                        tvDetailPVenta2.setText(pventa);
                    } else {
                        margen = ((pventa / 1.19) - costoProm)/(pventa / 1.19);
                        tvDetailPVenta2.setText(productReceived.getPventa().toString());
                    }

                    fMargin = df.format(margen);
                    vtaSem = (productReceived.getAvg_pro() / 4);
                    fVtaSem = df2.format(vtaSem);

                    tvDetailCode2.setText(productReceived.getCode().toString());
                    tvDetailBarCode2.setText(productReceived.getEan_13());
                    tvDetailDetalle2.setText(productReceived.getDetalle());
                    tvDetailPOferta2.setText(poferta.toString());
                    tvDetailMargen2.setText(fMargin);
                    tvDetailAvgPro2.setText(productReceived.getAvg_pro().toString());
                    tvDetailAvgProSem2.setText(fVtaSem);
                    tvDetailPcadena2.setText(productReceived.getPcadena().toString());
                    tvDetailCantidad.setVisibility(View.INVISIBLE);
                    tvDetailCantidad2.setVisibility(View.INVISIBLE);
                    btnDetailOption.setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    productReceived = (Product) received.getSerializable("Product");
                    getStock2(productReceived.getCodBarra());
                    poferta = productReceived.getPoferta();
                    pventa = productReceived.getPventa();
                    costoProm = productReceived.getCosto_prom();

                    if (poferta > 0) {
                        margen = ((poferta / 1.19) - costoProm)/(poferta / 1.19) ;
                        SpannableString pventa = new SpannableString(productReceived.getPventa().toString());
                        pventa.setSpan(new StrikethroughSpan(), 0,4,0);
                        tvDetailPVenta2.setText(pventa);
                    } else {
                        margen = ((pventa / 1.19) - costoProm)/(pventa / 1.19);
                        tvDetailPVenta2.setText(pventa.toString());
                    }

                    fMargin = df.format(margen);
                    vtaSem = (productReceived.getAvg_pro() / 4);
                    fVtaSem = df2.format(vtaSem);

                    tvDetailPOferta2.setText(productReceived.getPoferta().toString());
                    tvDetailCode2.setText(productReceived.getCode().toString());
                    tvDetailBarCode2.setText(productReceived.getEan_13());
                    tvDetailDetalle2.setText(productReceived.getDetalle());
                    tvDetailMargen2.setText(fMargin);
                    tvDetailAvgPro2.setText(productReceived.getAvg_pro().toString());
                    tvDetailAvgProSem2.setText(fVtaSem);
                    tvDetailPcadena2.setText(productReceived.getPcadena().toString());
                    tvDetailCantidad.requestFocus();
                    break;
            }
        }
    }

    private void updateReqProduct(
            Integer code,
            Integer codLocal,
            String detalle,
            String ean_13,
            String sucursal,
            Long stock_,
            Long pventa,
            Long p_oferta,
            Double avg_pro,
            String codBarra,
            Double pcadena,
            int pedido) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        path + SET_PRODUCT_FULL,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("code", String.valueOf(code));
                        params.put("codlocal", String.valueOf(codLocal));
                        params.put("detalle", detalle);
                        params.put("ean_13", ean_13);
                        params.put("sucursal", sucursal);
                        params.put("stock_", String.valueOf(stock_));
                        params.put("pventa", String.valueOf(pventa));
                        params.put("p_oferta", String.valueOf(p_oferta));
                        params.put("avg_pro", String.valueOf(avg_pro));
                        params.put("codBarra", codBarra);
                        params.put("pcadena", String.valueOf(pcadena));
                        params.put("pedido", String.valueOf(pedido));
                        return params;
                    }
                };
        requestQueue.add(stringRequest);
    }

    private void getStock2(String codBarra) {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + GET_REAL_EXISTENCE + codBarra, null, response -> {
            try {

                JSONObject jsonObject =  response.getJSONObject("Stock");
                stock = jsonObject.getDouble("st_actual");
                tvDetailStock2.setText(stock.toString());
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
        });
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