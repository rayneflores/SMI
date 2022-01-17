package com.ryfsystems.smi.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.Activities.UserActivity;
import com.ryfsystems.smi.Models.User;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.ViewHolders.UserViewHolder;

import java.io.IOException;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Activity activity;
    List<User> userList;

    User userSelected;

    public UserAdapter(Activity activity, List<User> userList) {
        this.activity = activity;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_layout, viewGroup, false);
        UserViewHolder viewHolder = new UserViewHolder(itemView);

        viewHolder.setOnClickListener((view, position) -> {
            userSelected = new User();
            userSelected.setId(userList.get(position).getId());
            userSelected.setUserName(userList.get(position).getUserName());
            userSelected.setPassword(userList.get(position).getPassword());
            userSelected.setName(userList.get(position).getName());
            userSelected.setRol(userList.get(position).getRol());

            Bundle bundle = new Bundle();
            bundle.putSerializable("User", userSelected);
            Intent intent = new Intent(activity, UserActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
            activity.finish();
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder viewHolder, int i) {
        SpannableString id = new SpannableString("Id de Usuario: " + userList.get(i).getId());
        id.setSpan(new StyleSpan(Typeface.BOLD), 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListUserId.setText(id);

        SpannableString userName = new SpannableString("Usuario: " + userList.get(i).getUserName());
        userName.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListUserUserName.setText(userName);

        SpannableString name = new SpannableString("Nombre: " + userList.get(i).getName());
        name.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListUserName.setText(name);

        if (userList.get(i).getRol().equals("1")) {
            SpannableString rol = new SpannableString("Rol: Administrador");
            rol.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tvListUserRol.setText(rol);
        } else {
            SpannableString rol = new SpannableString("Rol: Usuario");
            rol.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tvListUserRol.setText(rol);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
