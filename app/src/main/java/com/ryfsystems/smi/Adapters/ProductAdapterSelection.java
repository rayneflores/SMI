package com.ryfsystems.smi.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryfsystems.smi.Activities.ConteoActivity;
import com.ryfsystems.smi.Activities.ProductDetailActivity;
import com.ryfsystems.smi.Activities.VencimientoActivity;
import com.ryfsystems.smi.Models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.ViewHolders.ProductViewHolder;

import java.util.List;

public class ProductAdapterSelection extends RecyclerView.Adapter<ProductViewHolder> {

    Activity activity;
    List<Product> productList;
    int module;

    Product productSelected;

    public ProductAdapterSelection(Activity activity, List<Product> productList, int module) {
        this.activity = activity;
        this.productList = productList;
        this.module = module;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_selection_layout, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);

        viewHolder.setOnClickListener((view, position) -> {
            productSelected = new Product();
            productSelected.setActivado(productList.get(position).getActivado());
            productSelected.setAvg_pro(productList.get(position).getAvg_pro());
            productSelected.setCode(productList.get(position).getCode());
            productSelected.setCodBarra(productList.get(position).getCodBarra());
            productSelected.setCodlocal(productList.get(position).getCodlocal());
            productSelected.setCodSucursal(productList.get(position).getCodSucursal());
            productSelected.setCosto_prom(productList.get(position).getCosto_prom());
            productSelected.setDetalle(productList.get(position).getDetalle());
            productSelected.setDep(productList.get(position).getDep());
            productSelected.setEan_13(productList.get(position).getEan_13());
            productSelected.setLinea(productList.get(position).getLinea());
            productSelected.setPcadena(productList.get(position).getPcadena());
            productSelected.setPoferta(productList.get(position).getPoferta());
            productSelected.setPventa(productList.get(position).getPventa());
            productSelected.setStock_(productList.get(position).getStock_());
            productSelected.setSucursal(productList.get(position).getSucursal());

            Bundle bundle = new Bundle();
            bundle.putSerializable("Product", productSelected);
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
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
