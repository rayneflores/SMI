package com.ryfsystems.smi.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.R;

import java.io.IOException;

public class UserViewHolder extends RecyclerView.ViewHolder{

    public TextView tvListUserId, tvListUserName, tvListUserUserName, tvListUserRol;
    View mView;

    private UserViewHolder.ClickListener mClickListener;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(v -> {
            try {
                mClickListener.onItemClick(v, getAdapterPosition());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tvListUserId = mView.findViewById(R.id.tvListUserId);
        tvListUserUserName = mView.findViewById(R.id.tvListUserUserName);
        tvListUserName = mView.findViewById(R.id.tvListUserName);
        tvListUserRol = mView.findViewById(R.id.tvListUserRol);
    }

    public void setOnClickListener(UserViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position) throws IOException;
    }
}
