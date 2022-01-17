package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Constants.INFRA_SERVER_ADDRESS;
import static com.ryfsystems.smi.Constants.SAVE_USER_OPERATION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Models.User;
import com.ryfsystems.smi.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    Bundle received;
    User userReceived;
    Button btnGuardar, btnModificar, btnEliminar;
    CheckBox cbAdmin, cbUser;
    Intent nextIntent;
    RequestQueue queue;
    String rol = "2";
    TextView tvUsersTitle, tvUserName, tvName, tvPassword1, tvPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);

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
            tvUserName.setText(userReceived.getUserName());
            tvName.setText(userReceived.getName());
            tvPassword1.setText(userReceived.getPassword());
            tvPassword2.setText(userReceived.getPassword());
            if (userReceived.getRol().equals("1")) {
                cbAdmin.setEnabled(true);
                cbAdmin.setChecked(true);
                cbUser.setChecked(false);
            } else {
                cbAdmin.setEnabled(false);
                cbUser.setEnabled(true);
                cbUser.setChecked(true);
            }
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnGuardar.setEnabled(false);
            tvUsersTitle.setText("Editar / Borrar");
        } else{
            cbAdmin.setEnabled(false);
            cbUser.setEnabled(true);
            cbUser.setChecked(true);
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(true);
            tvUsersTitle.setText("Nuevo Usuario");
        }

        cbAdmin.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!cbAdmin.isChecked()) {
                rol = "2";
                cbUser.setChecked(true);
                cbUser.setEnabled(true);
            } else {
                rol = "1";
                cbUser.setEnabled(false);
            }
        });

        cbUser.setOnCheckedChangeListener(((compoundButton, b) -> {
            if (!cbUser.isChecked()) {
                rol = "1";
                cbAdmin.setChecked(true);
                cbAdmin.setEnabled(true);
            } else {
                rol = "2";
                cbAdmin.setEnabled(false);
            }
        }));

        /*btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGuardar.setEnabled(false);
                if (tvPassword1.getText().toString().trim().equals(tvPassword2.getText().toString().trim())) {
                    createUser(tvName.getText().toString().trim(), tvUserName.getText().toString().trim(), tvPassword1.getText().toString().trim(), rol);
                } else {
                    Toast.makeText(getApplicationContext(), "Los Passwords no coinciden", Toast.LENGTH_LONG).show();
                    btnGuardar.setEnabled(true);
                }
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        nextIntent = new Intent(getApplicationContext(), UsersListActivity.class);
        startActivity(nextIntent);
        finish();
    }

    private void createUser(String name, String userName, String password, String rol) {
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        INFRA_SERVER_ADDRESS + SAVE_USER_OPERATION,
                response -> {
                    Toast.makeText(getApplicationContext(), "Usuario Agregado", Toast.LENGTH_SHORT).show();
                    clearTextViews();
                }, error -> {
                    Toast.makeText(getApplicationContext(), "Error_Det: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("userName", userName);
                params.put("password", password);
                params.put("rol", rol);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void clearTextViews() {
        tvName.setText(null);
        tvUserName.setText(null);
        tvPassword1.setText(null);
        tvPassword2.setText(null);
        btnGuardar.setEnabled(true);
        cbUser.setChecked(true);
        cbAdmin.setChecked(false);
        rol = "2";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardar:
                /*btnGuardar.setEnabled(false);
                if (tvPassword1.getText().toString().trim().equals(tvPassword2.getText().toString().trim())) {
                    createUser(tvName.getText().toString().trim(), tvUserName.getText().toString().trim(), tvPassword1.getText().toString().trim(), rol);
                } else {
                    Toast.makeText(getApplicationContext(), "Los Passwords no coinciden", Toast.LENGTH_LONG).show();
                    btnGuardar.setEnabled(true);
                }*/
                Toast.makeText(getApplicationContext(), "Guardar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnModificar:
                Toast.makeText(UserActivity.this, "Modificar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnEliminar:
                Toast.makeText(UserActivity.this, "Eliminar", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}