package com.ryfsystems.smi.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.R;

import java.io.IOException;

public class NewProductViewHolder extends RecyclerView.ViewHolder {

    public TextView tvListProductId, tvListProductBarCode, tvListProductDetalle,
            tvListProductpOferta, tvListProductPventa;
    View mView;

    private NewProductViewHolder.ClickListener mClickListener;

    public NewProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(v -> {
            try {
                mClickListener.onItemClick(v, getAdapterPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tvListProductId = mView.findViewById(R.id.tvListProductId);
        tvListProductBarCode = mView.findViewById(R.id.tvListProductBarCode);
        tvListProductDetalle = mView.findViewById(R.id.tvListProductDetalle);
        tvListProductpOferta = mView.findViewById(R.id.tvListProductpOferta);
        tvListProductPventa = mView.findViewById(R.id.tvListProductPventa);
    }

    public void setOnClickListener(NewProductViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position) throws IOException;
    }
}
