package com.ryfsystems.smi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ryfsystems.smi.Models.Server;
import com.ryfsystems.smi.R;

import java.util.ArrayList;

public class ServerAdapter extends ArrayAdapter<Server> {
    public ServerAdapter(Context context, ArrayList<Server> serverList) {
        super(context, 0, serverList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View serverView, @NonNull ViewGroup parent) {
        return initView(position, serverView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View serverView, @NonNull ViewGroup parent) {
        return initView(position, serverView, parent);
    }

    private View initView(int position, View serverView, ViewGroup parent) {
        if (serverView == null) {
            serverView = LayoutInflater.from(getContext()).inflate(
                    R.layout.server_spinner_layout, parent, false
            );
        }

        ImageView imageViewServer = serverView.findViewById(R.id.ivServer);
        TextView serverName = serverView.findViewById(R.id.tv_server_name);
        //TextView serverAddress  = serverView.findViewById(R.id.tv_server_address);


        Server currentServer = getItem(position);
        imageViewServer.setImageResource(currentServer.getServerImage());
        serverName.setText(currentServer.getServerName());
        //serverAddress.setText(currentServer.getServerAddress());

        return serverView;
    }
}
