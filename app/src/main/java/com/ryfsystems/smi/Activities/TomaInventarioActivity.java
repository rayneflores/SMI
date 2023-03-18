package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.NEW_SEARCH_PRODUCT_BY_CODE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Models.NewProduct;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import org.json.JSONObject;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class TomaInventarioActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    Bundle extras;
    Bundle received;
    int module;
    int serverId;
    Intent nextIntent;
    Intent intent;
    private static final String TAG = "ScannerLog";
    String path = INFRA_SERVER_ADDRESS;
    String rol;
    String usuario;
    String query;
    SharedPreferences preferences;
    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
        received = getIntent().getExtras();
        if (received != null) {
            module = received.getInt("module");
        }
        extras = new Bundle();
        extras.putInt("module", module);

        recuperarPreferencias();

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

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        final String code = rawResult.getContents();

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
            buscarPorBarCode(code);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    private void buscarPorBarCode(String code) {
        HttpsTrustManager.allowAllSSL();
        query = NEW_SEARCH_PRODUCT_BY_CODE;
        JsonObjectRequest jObjReq = new JsonObjectRequest(
                Request.Method.GET,
                path + query + code,
                null,
                response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject("Product");
                        NewProduct newProduct = new NewProduct();
                        newProduct.setIdProducto(jsonObject.getInt("id_producto"));
                        newProduct.setCodigoBarras(jsonObject.getString("codigo_barras"));
                        newProduct.setDetalle(jsonObject.getString("detalle"));
                        newProduct.setPrecioVenta(jsonObject.getString("precio_venta"));
                        newProduct.setPrecioOferta(jsonObject.getInt("precio_oferta"));
                        extras.putSerializable("newProduct", newProduct);
                        intent = new Intent(getApplicationContext(), ConteoActivity.class);
                        intent.putExtras(extras);
                        startActivity(intent);
                        finish();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(
                    getApplicationContext(),
                    "Fallo en la Lectura, Reintente!!!",
                    Toast.LENGTH_SHORT)
                    .show();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jObjReq);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}