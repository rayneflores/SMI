package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_USER;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Utils.Constants.LOGIN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Models.User;
import com.ryfsystems.smi.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText tvUsuario, tvPassword;
    Intent nextIntent;
    RequestQueue queue;
    String path = INFRA_SERVER_ADDRESS;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvPassword = findViewById(R.id.tvPassword);

        queue = Volley.newRequestQueue(getApplicationContext());

        recuperarPreferencias();

        btnLogin.setOnClickListener(view -> {
            btnLogin.setEnabled(false);
            if (!tvUsuario.getText().toString().trim().isEmpty() && !tvPassword.getText().toString().trim().isEmpty()) {
                login(tvUsuario.getText().toString().trim(), tvPassword.getText().toString().trim());
            } else {
                btnLogin.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String userName, String password) {
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        path + LOGIN,
                        response -> {
                            if (!response.equals("401")) {
                                buscarDatosUsuario(response);
                                nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(nextIntent);
                                finish();
                            } else {
                                btnLogin.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Combinacion Usuario/Password Incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }, error -> {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", userName);
                        params.put("password", password);
                        return params;
                    }
                };
        queue.add(stringRequest);
    }

    private void buscarDatosUsuario(String userId) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path + GET_USER + userId, null, response -> {

            try {
                JSONObject object = response.getJSONObject("User");
                user = new User(
                        object.getString("name"),
                        object.getString("rol")
                );
                guardarPreferencias();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void recuperarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        tvUsuario.setText(preferences.getString("usuario", ""));
        tvPassword.setText(preferences.getString("password", ""));
    }

    private void guardarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", user.getName());
        editor.putString("role", user.getRol());
        editor.putBoolean("sesion", true);
        editor.apply();
    }
}