package com.ryfsystems.smi.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.R;

import java.io.IOException;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView tvListProductActivado, tvListProductCode, tvListProductCodLocal, tvListProductDetalle, tvListProductDep, tvListProductEan_13, tvListProductLinea,
            tvListProductSucursal, tvListProductStock, tvListProductpOferta, tvListProductPventa, tvListProductAvgProm, tvListProductCostoProm, tvListProductCodBarra,
            tvListProductPcadena, tvListProductPedido, tvListProductUndDefect, tvListProductResponsable, tvListProductCodSuc;
    View mView;

    private ProductViewHolder.ClickListener mClickListener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(v -> {
            try {
                mClickListener.onItemClick(v, getAdapterPosition());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tvListProductActivado = mView.findViewById(R.id.tvListProductActivado);
        tvListProductCode = mView.findViewById(R.id.tvListProductCode);
        tvListProductCodLocal = mView.findViewById(R.id.tvListProductCodLocal);
        tvListProductDetalle = mView.findViewById(R.id.tvListProductDetalle);
        tvListProductDep = mView.findViewById(R.id.tvListProductDep);
        tvListProductEan_13 = mView.findViewById(R.id.tvListProductEan_13);
        tvListProductLinea = mView.findViewById(R.id.tvListProductLinea);
        tvListProductSucursal = mView.findViewById(R.id.tvListProductSucursal);
        tvListProductStock = mView.findViewById(R.id.tvListProductStock);
        tvListProductpOferta = mView.findViewById(R.id.tvListProductpOferta);
        tvListProductPventa = mView.findViewById(R.id.tvListProductPventa);
        tvListProductAvgProm = mView.findViewById(R.id.tvListProductAvgProm);
        tvListProductCostoProm = mView.findViewById(R.id.tvListProductCostoProm);
        tvListProductCodBarra = mView.findViewById(R.id.tvListProductCodBarra);
        tvListProductPcadena = mView.findViewById(R.id.tvListProductPcadena);
        tvListProductPedido = mView.findViewById(R.id.tvListProductPedido);
        tvListProductUndDefect = mView.findViewById(R.id.tvListProductUndDefect);
        tvListProductResponsable = mView.findViewById(R.id.tvListProductResponsable);
        tvListProductCodSuc = mView.findViewById(R.id.tvListProductCodSuc);
    }

    public void setOnClickListener(ProductViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position) throws IOException;
    }
}
