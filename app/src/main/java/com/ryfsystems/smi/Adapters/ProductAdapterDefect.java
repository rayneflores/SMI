package com.ryfsystems.smi.Adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.ViewHolders.ProductViewHolder;

import java.util.List;

public class ProductAdapterDefect extends RecyclerView.Adapter<ProductViewHolder> {

    Activity activity;
    List<Product> productList;

    public ProductAdapterDefect(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_defect_layout, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder viewHolder, int i) {

        SpannableString code = new SpannableString("Code: " + productList.get(i).getCode());
        code.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductCode.setText(code);

        SpannableString detalle = new SpannableString("Detalle: " + productList.get(i).getDetalle());
        detalle.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductDetalle.setText(detalle);

        SpannableString und_defect = new SpannableString("Unidades Defectuosas: " + productList.get(i).getUnd_defect());
        und_defect.setSpan(new StyleSpan(Typeface.BOLD), 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductUndDefect.setText(und_defect);

        SpannableString responsable = new SpannableString("Responsable: " + productList.get(i).getResponsable());
        responsable.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductResponsable.setText(responsable);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
