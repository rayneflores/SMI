package com.ryfsystems.smi.adapters;

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

import com.ryfsystems.smi.models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.viewholders.ProductViewHolder;

import java.util.List;

public class ProductAdapterCount extends RecyclerView.Adapter<ProductViewHolder> {

    Activity activity;
    List<Product> productList;

    public ProductAdapterCount(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_count_layout, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder viewHolder, int i) {

        if (productList.get(i).getActivado() != 0) {
            SpannableString activado = new SpannableString("Activado: Si");
            activado.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tvListProductActivado.setText(activado);
        } else {
            SpannableString activado = new SpannableString("Activado: No");
            activado.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tvListProductActivado.setText(activado);
        }

        SpannableString code = new SpannableString("Code: " + productList.get(i).getCode());
        code.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductCode.setText(code);

        SpannableString codLocal = new SpannableString("CodLocal: " + productList.get(i).getCodlocal());
        codLocal.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductCodLocal.setText(codLocal);

        SpannableString detalle = new SpannableString("Detalle: " + productList.get(i).getDetalle());
        detalle.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductDetalle.setText(detalle);

        SpannableString Dep = new SpannableString("Dep: " + productList.get(i).getDep());
        Dep.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductDep.setText(Dep);

        SpannableString ean_13 = new SpannableString("Ean_13: " + productList.get(i).getEan_13());
        ean_13.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductEan_13.setText(ean_13);

        SpannableString linea = new SpannableString("Linea: " + productList.get(i).getLinea());
        linea.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductLinea.setText(linea);

        SpannableString sucursal = new SpannableString("Sucursal: " + productList.get(i).getSucursal());
        sucursal.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductSucursal.setText(sucursal);

        SpannableString stock = new SpannableString("Stock: " + productList.get(i).getStock_());
        stock.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductStock.setText(stock);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
