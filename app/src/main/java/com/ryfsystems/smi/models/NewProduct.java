package com.ryfsystems.smi.models;

import java.io.Serializable;

public class NewProduct implements Serializable {
    private Integer idProducto;
    private String codigoBarras;
    private String detalle;
    private String precioVenta;
    private Integer precioOferta;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(Integer precioOferta) {
        this.precioOferta = precioOferta;
    }

    public NewProduct() {
    }

    public NewProduct(Integer idProducto, String codigoBarras, String detalle,
                      String precioVenta, Integer precioOferta) {
        this.idProducto = idProducto;
        this.codigoBarras = codigoBarras;
        this.detalle = detalle;
        this.precioVenta = precioVenta;
        this.precioOferta = precioOferta;
    }

    @Override
    public String toString() {
        return  idProducto +
                ", " + codigoBarras +
                ", " + detalle +
                ", " + precioVenta +
                ", " + precioOferta;
    }

    public String toStringLabel() {
        return
                idProducto +
                        ", " + codigoBarras +
                        ", " + detalle +
                        ", " + precioVenta +
                        ", " + precioOferta;
    }
}
