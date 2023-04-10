package com.ryfsystems.smi.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.models.NewProduct;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.viewholders.NewProductViewHolder;

import java.util.List;

public class NewProductAdapterLabel extends RecyclerView.Adapter<NewProductViewHolder> {

    Activity activity;
    List<NewProduct> productList;

    public NewProductAdapterLabel(Activity activity, List<NewProduct> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public NewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_product_label_layout, parent, false);
        NewProductViewHolder viewHolder = new NewProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductViewHolder viewHolder, int i) {

        SpannableString code = new SpannableString("Id de Producto: " + productList.get(i).getIdProducto());
        code.setSpan(new StyleSpan(Typeface.BOLD), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductId.setText(code);

        SpannableString barCode = new SpannableString("Codigo de Barras: " + productList.get(i).getCodigoBarras());
        barCode.setSpan(new StyleSpan(Typeface.BOLD), 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductBarCode.setText(barCode);

        SpannableString detalle = new SpannableString("Detalle: " + productList.get(i).getDetalle());
        detalle.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductDetalle.setText(detalle);

        SpannableString pventa = new SpannableString("Precio de Venta: " + productList.get(i).getPrecioVenta());
        pventa.setSpan(new StyleSpan(Typeface.BOLD), 0, 15, 0);
        if (productList.get(i).getPrecioOferta() > 0) {
            pventa.setSpan(new StrikethroughSpan(), 15, 21, 0);
        }
        viewHolder.tvListProductPventa.setText(pventa);

        SpannableString poferta = new SpannableString("Precio Oferta: " + productList.get(i).getPrecioOferta());
        poferta.setSpan(new StyleSpan(Typeface.BOLD), 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductpOferta.setText(poferta);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
