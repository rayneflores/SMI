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

import com.ryfsystems.smi.models.Product;
import com.ryfsystems.smi.R;
import com.ryfsystems.smi.viewholders.ProductViewHolder;

import java.util.List;

public class ProductAdapterRequest extends RecyclerView.Adapter<ProductViewHolder> {

    Activity activity;
    List<Product> productList;

    public ProductAdapterRequest(Activity activity, List<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_request_layout, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder viewHolder, int i) {

        SpannableString code = new SpannableString("Code: " + productList.get(i).getCode());
        code.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductCode.setText(code);

        SpannableString codLocal = new SpannableString("CodLocal: " + productList.get(i).getCodlocal());
        codLocal.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductCodLocal.setText(codLocal);

        SpannableString detalle = new SpannableString("Detalle: " + productList.get(i).getDetalle());
        detalle.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductDetalle.setText(detalle);

        SpannableString ean_13 = new SpannableString("Ean_13: " + productList.get(i).getEan_13());
        ean_13.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductEan_13.setText(ean_13);

        SpannableString sucursal = new SpannableString("Sucursal: " + productList.get(i).getSucursal());
        sucursal.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductSucursal.setText(sucursal);

        SpannableString stock = new SpannableString("Stock: " + productList.get(i).getStock_());
        stock.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductStock.setText(stock);

        SpannableString pventa = new SpannableString("Precio de Venta: " + productList.get(i).getPventa());
        pventa.setSpan(new StyleSpan(Typeface.BOLD), 0, 15, 0);
        if (productList.get(i).getPoferta() > 0) {
            pventa.setSpan(new StrikethroughSpan(), 15, 21, 0);
        }
        viewHolder.tvListProductPventa.setText(pventa);

        SpannableString poferta = new SpannableString("Precio Oferta: " + productList.get(i).getPoferta());
        poferta.setSpan(new StyleSpan(Typeface.BOLD), 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductpOferta.setText(poferta);

        SpannableString avgProm = new SpannableString("Avg_Prom: " + productList.get(i).getAvg_pro());
        avgProm.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductAvgProm.setText(avgProm);

        SpannableString costoProm = new SpannableString("Costo Prom: " + productList.get(i).getCosto_prom());
        costoProm.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductCostoProm.setText(costoProm);

        SpannableString codBarra = new SpannableString("Codigo de Barras: " + productList.get(i).getCodBarra());
        codBarra.setSpan(new StyleSpan(Typeface.BOLD), 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductCodBarra.setText(codBarra);

        SpannableString pcadena = new SpannableString("Precio Cadena: " + productList.get(i).getPcadena());
        pcadena.setSpan(new StyleSpan(Typeface.BOLD), 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductPcadena.setText(pcadena);

        SpannableString pedido = new SpannableString("Pedido: " + productList.get(i).getPedido());
        pedido.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvListProductPedido.setText(pedido);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
