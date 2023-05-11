package com.ryfsystems.smi.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.R;

import java.io.IOException;

public class NewProductCountViewHolder extends RecyclerView.ViewHolder {

    public TextView tvListCountProductId, tvListCountProductDetalle, tvListCountStockInventario;
    View mView;

    private NewProductCountViewHolder.ClickListener mClickListener;

    public NewProductCountViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(v -> {
            try {
                mClickListener.onItemClick(v, getAdapterPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tvListCountProductId = mView.findViewById(R.id.tvListCountProductId);
        tvListCountProductDetalle = mView.findViewById(R.id.tvListCountProductDetalle);
        tvListCountStockInventario = mView.findViewById(R.id.tvListCountStockInventario);
    }

    public void setOnClickListener(NewProductCountViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position) throws IOException;
    }
}
