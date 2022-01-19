package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Constants.GET_PRODUCT;
import static com.ryfsystems.smi.Constants.INFRA_SERVER_ADDRESS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;

import org.json.JSONObject;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class TomaInventarioActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    Intent nextIntent;
    private static final String TAG = "ScannerLog";
    String path = INFRA_SERVER_ADDRESS;
    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
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
            /*Creacion de la Busqueda de Productos*/
            buscarDatosUsuario(code);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    public void buscarDatosUsuario(String code) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + GET_PRODUCT + code, null, response -> {
            try {
                JSONObject jsonObject = response.getJSONObject("Product");
                Product product = new Product();
                product.setActivado(jsonObject.getInt("activado"));
                product.setCode(jsonObject.getInt("code"));
                product.setCodlocal(jsonObject.getInt("codlocal"));
                product.setDetalle(jsonObject.getString("detalle"));
                product.setDep(jsonObject.getString("dep"));
                product.setEan_13(jsonObject.getString("ean_13"));
                product.setLinea(jsonObject.getInt("linea"));
                product.setSucursal(jsonObject.getString("sucursal"));

                //Toast.makeText(getApplicationContext(), product.toString(), Toast.LENGTH_LONG).show();

                Bundle extras = new Bundle();
                extras.putSerializable("Product", product);
                Intent intent = new Intent(getApplicationContext(), ConteoActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            onResume();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

}