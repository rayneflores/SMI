package com.ryfsystems.smi.activities;

import static com.ryfsystems.smi.utils.Constants.DELETE_USER;
import static com.ryfsystems.smi.utils.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.utils.Constants.SAVE_USER;
import static com.ryfsystems.smi.utils.Constants.UPDATE_USER;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.models.User;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.utils.HttpsTrustManager;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle received;
    User userReceived;
    Button btnGuardar, btnModificar, btnEliminar;
    CheckBox cbAdmin, cbUser;
    int userId;
    Intent nextIntent;
    RequestQueue queue;
    String role, userRole = "2", path = INFRA_SERVER_ADDRESS;
    TextView tvUsersTitle, tvUserName, tvName, tvPassword1, tvPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SharedPreferences preferences = getSharedPreferences("smiPreferences", Context.MODE_PRIVATE);
        role = preferences.getString("role", "");

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        btnModificar = findViewById(R.id.btnModificar);
        btnModificar.setOnClickListener(this);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(this);

        cbAdmin = findViewById(R.id.cbAdmin);
        cbUser = findViewById(R.id.cbUser);

        tvUsersTitle = findViewById(R.id.tvUsersTitle);
        tvUserName = findViewById(R.id.tvUsername);
        tvName = findViewById(R.id.tvName);
        tvPassword1 = findViewById(R.id.tvPassword1);
        tvPassword2 = findViewById(R.id.tvPassword2);

        queue = Volley.newRequestQueue(getApplicationContext());

        received = getIntent().getExtras();

        if (received != null) {
            userReceived = (User) received.getSerializable("User");
            userId = userReceived.getId();
            tvUserName.setText(userReceived.getUserName());
            tvName.setText(userReceived.getName());
            tvPassword1.setText(userReceived.getPassword());
            tvPassword2.setText(userReceived.getPassword());
            userRole = userReceived.getRol();
            if (userRole.equals("1")) {
                cbAdmin.setEnabled(true);
                cbAdmin.setChecked(true);
                cbUser.setChecked(false);
                cbUser.setEnabled(false);
            } else {
                cbAdmin.setEnabled(false);
                cbAdmin.setChecked(false);
                cbUser.setEnabled(true);
                cbUser.setChecked(true);
            }
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnGuardar.setEnabled(false);
            tvUsersTitle.setText(R.string.editar_borrar);
        } else{
            cbAdmin.setEnabled(false);
            cbUser.setEnabled(true);
            cbUser.setChecked(true);
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(true);
            tvUsersTitle.setText(R.string.nuevo_usuario);
        }

        cbAdmin.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!cbAdmin.isChecked()) {
                userRole = "2";
                cbUser.setChecked(true);
                cbUser.setEnabled(true);
            } else {
                userRole = "1";
                cbUser.setEnabled(false);
            }
        });

        cbUser.setOnCheckedChangeListener(((compoundButton, b) -> {
            if (!cbUser.isChecked()) {
                userRole = "1";
                cbAdmin.setChecked(true);
                cbAdmin.setEnabled(true);
            } else {
                userRole = "2";
                cbAdmin.setEnabled(false);
            }
        }));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), UsersListActivity.class);
        startActivity(nextIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardar:
                btnGuardar.setEnabled(false);
                if (!tvUserName.getText().toString().trim().isEmpty() &&
                    !tvName.getText().toString().trim().isEmpty() &&
                    !tvPassword1.getText().toString().trim().isEmpty() &&
                    !tvPassword2.getText().toString().trim().isEmpty()) {
                    if (tvPassword1.getText().toString().trim().equals(tvPassword2.getText().toString().trim())) {
                        createUser(tvName.getText().toString().trim(), tvUserName.getText().toString().trim(), tvPassword1.getText().toString().trim(), userRole);
                    } else {
                        Toast.makeText(getApplicationContext(), "Los Passwords no coinciden", Toast.LENGTH_SHORT).show();
                        btnGuardar.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                    btnGuardar.setEnabled(true);
                }
                break;
            case R.id.btnModificar:
                if (!tvUserName.getText().toString().trim().isEmpty() &&
                        !tvName.getText().toString().trim().isEmpty() &&
                        !tvPassword1.getText().toString().trim().isEmpty() &&
                        !tvPassword2.getText().toString().trim().isEmpty()) {
                    updateUser(
                            userId,
                            tvName.getText().toString().trim(),
                            tvUserName.getText().toString().trim(),
                            tvPassword1.getText().toString().trim(),
                            userRole
                    );
                }
                break;
            case R.id.btnEliminar:
                deleteUser(userId);
                break;
        }
    }

    private void createUser(String name, String userName, String password, String userRole) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        path + SAVE_USER,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            clearTextViews();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", name);
                        params.put("userName", userName);
                        params.put("password", password);
                        params.put("rol", userRole);
                        return params;
                    }
                };
        queue.add(stringRequest);
    }

    private void updateUser(int userId, String name, String userName, String password, String rol) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        path + UPDATE_USER,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            clearTextViews();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(userId));
                        params.put("name", name);
                        params.put("userName", userName);
                        params.put("password", password);
                        params.put("rol", rol);
                        return params;
                    }
                };
        queue.add(stringRequest);
    }

    private void deleteUser(int userId) {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        path + DELETE_USER,
                        response -> {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            clearTextViews();
                        }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(userId));
                        return params;
                    }
                };
        queue.add(stringRequest);
    }

    private void clearTextViews() {
        tvUsersTitle.setText(R.string.nuevo_usuario);
        tvName.setText(null);
        tvUserName.setText(null);
        tvPassword1.setText(null);
        tvPassword2.setText(null);
        cbUser.setChecked(true);
        cbAdmin.setChecked(false);
        userRole = "2";
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}