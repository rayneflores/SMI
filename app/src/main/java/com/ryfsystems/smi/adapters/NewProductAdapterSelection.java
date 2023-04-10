package com.ryfsystems.smi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.activities.ConteoActivity;
import com.ryfsystems.smi.activities.ProductDetailActivity;
import com.ryfsystems.smi.activities.VencimientoActivity;
import com.ryfsystems.smi.models.NewProduct;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.viewholders.NewProductViewHolder;

import java.util.List;

public class NewProductAdapterSelection extends RecyclerView.Adapter<NewProductViewHolder> {

    Activity activity;
    List<NewProduct> productList;
    int module;

    NewProduct productSelected;

    public NewProductAdapterSelection(Activity activity, List<NewProduct> productList, int module) {
        this.activity = activity;
        this.productList = productList;
        this.module = module;
    }

    @NonNull
    @Override
    public NewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_product_selection_layout, parent, false);
        NewProductViewHolder viewHolder = new NewProductViewHolder(itemView);

        viewHolder.setOnClickListener((view, position) -> {
            productSelected = new NewProduct();
            productSelected.setIdProducto(productList.get(position).getIdProducto());
            productSelected.setCodigoBarras(productList.get(position).getCodigoBarras());
            productSelected.setDetalle(productList.get(position).getDetalle());
            productSelected.setPrecioVenta(productList.get(position).getPrecioVenta());
            productSelected.setPrecioOferta(productList.get(position).getPrecioOferta());

            Bundle bundle = new Bundle();
            bundle.putSerializable("newProduct", productSelected);
            bundle.putInt("module", module);
            Intent intent;
            switch (module) {
                case 1:
                case 2:
                case 3:
                    intent = new Intent(activity, ConteoActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    activity.finish();
                    break;
                case 4:
                case 5:
                    intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    activity.finish();
                    break;
                case 6:
                    intent = new Intent(activity, VencimientoActivity.class);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                    activity.finish();
                    break;
            }
        });

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
