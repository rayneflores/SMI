package com.ryfsystems.smi.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.R;
import com.ryfsystems.smi.activities.ProductDetailActivity;
import com.ryfsystems.smi.models.NewProduct;
import com.ryfsystems.smi.viewholders.NewProductCountViewHolder;

import java.util.List;

public class NewProductAdapterCountSelect extends RecyclerView.Adapter<NewProductCountViewHolder> {

    Activity activity;
    List<NewProduct> productList;
    NewProduct productSelected;

    public NewProductAdapterCountSelect(Activity activity,
                                        List<NewProduct> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public NewProductCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_product_count_selection_layout, parent, false);
        NewProductCountViewHolder viewHolder = new NewProductCountViewHolder(itemView);

        viewHolder.setOnClickListener((view, position) -> {
            productSelected = new NewProduct();
            productSelected.setIdProducto(productList.get(position).getIdProducto());
            productSelected.setDetalle(productList.get(position).getDetalle());
            productSelected.setStock_inventario(productList.get(position).getStock_inventario());

            Bundle bundle = new Bundle();
            bundle.putSerializable("newProduct", productSelected);
            Intent intent;
            intent = new Intent(activity, ProductDetailActivity.class);
            intent.putExtras(bundle);
            activity.startActivity(intent);
            activity.finish();
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductCountViewHolder viewHolder, int i) {
        SpannableString code = new SpannableString("Id de Producto: " + productList.get(i).getIdProducto());
        code.setSpan(new StyleSpan(Typeface.BOLD), 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListCountProductId.setText(code);

        SpannableString detalle = new SpannableString("Detalle: " + productList.get(i).getDetalle());
        detalle.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListCountProductDetalle.setText(detalle);

        SpannableString conteo = new SpannableString("Stock: " + productList.get(i).getStock_inventario());
        conteo.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (productList.get(i).getStock_inventario().equals("0")) {
            final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 255, 0));
            conteo.setSpan(fcs, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        viewHolder.tvListCountStockInventario.setText(conteo);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
