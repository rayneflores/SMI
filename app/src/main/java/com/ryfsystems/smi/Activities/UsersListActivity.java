package com.ryfsystems.smi.Activities;

import static com.ryfsystems.smi.Utils.Constants.GET_ALL_USERS;
import static com.ryfsystems.smi.Utils.Constants.INFRA_SERVER_ADDRESS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ryfsystems.smi.Adapters.UserAdapter;
import com.ryfsystems.smi.Models.User;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.Utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    Button btnNuevo;
    Intent nextIntent;
    JsonObjectRequest jsonObjectRequest;
    List<User> userList = new ArrayList<>();
    ProgressDialog progressDialog;
    RecyclerView rvUsers;
    RecyclerView.LayoutManager layoutManager;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        btnNuevo = findViewById(R.id.btnNuevo);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere...");
        progressDialog.setMessage("Listando Usuarios...");
        progressDialog.setCanceledOnTouchOutside(false);

        rvUsers = findViewById(R.id.rvUsers);
        rvUsers.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvUsers.setLayoutManager(layoutManager);

        btnNuevo.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(intent);
            finish();
        });

        listUsers(INFRA_SERVER_ADDRESS + GET_ALL_USERS);
    }

    private void listUsers(String path) {

        HttpsTrustManager.allowAllSSL();
        progressDialog.setMessage("Listando Usuarios...");
        progressDialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, response -> {

            try {
                userList.clear();
                JSONArray jsonArray = response.getJSONArray("Users");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    User user = new User(
                            jsonObject.getInt("id"),
                            jsonObject.getString("username"),
                            null,
                            jsonObject.getString("name"),
                            jsonObject.getString("rol")
                    );
                    userList.add(user);
                }
                userAdapter = new UserAdapter(UsersListActivity.this, userList);
                rvUsers.setAdapter(userAdapter);
                progressDialog.dismiss();
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), "Sql: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "Error de Conexion", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            onBackPressed();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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