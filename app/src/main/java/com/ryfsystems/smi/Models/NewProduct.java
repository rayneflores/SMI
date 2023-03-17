package com.ryfsystems.smi.Models;

import java.io.Serializable;

public class NewProduct implements Serializable {
    private Integer id_producto;
    private String codigo_barras;
    private String detalle;
    private String precio_venta;
    private Integer precio_oferta;

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(String precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Integer getPrecio_oferta() {
        return precio_oferta;
    }

    public void setPrecio_oferta(Integer precio_oferta) {
        this.precio_oferta = precio_oferta;
    }

    public String toStringLabel() {
        return
                id_producto +
                        ", " + codigo_barras +
                        ", " + detalle +
                        ", " + precio_venta +
                        ", " + precio_oferta;
    }
}
