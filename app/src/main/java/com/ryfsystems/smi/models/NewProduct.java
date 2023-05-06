package com.ryfsystems.smi.models;

import java.io.Serializable;

public class NewProduct implements Serializable {
    private Integer idProducto;
    private String codigoBarras;
    private String detalle;
    private String precioVenta;
    private Integer precioOferta;
    private String stock_inicial;
    private String stock_inventario;
    private String cantidad_diferencia;

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

    public String getStock_inicial() {
        return stock_inicial;
    }

    public void setStock_inicial(String stock_inicial) {
        this.stock_inicial = stock_inicial;
    }

    public String getStock_inventario() {
        return stock_inventario;
    }

    public void setStock_inventario(String stock_inventario) {
        this.stock_inventario = stock_inventario;
    }

    public String getCantidad_diferencia() {
        return cantidad_diferencia;
    }

    public void setCantidad_diferencia(String cantidad_diferencia) {
        this.cantidad_diferencia = cantidad_diferencia;
    }

    public NewProduct() {
    }

    public NewProduct(Integer idProducto, String codigoBarras, String detalle,
                      String precioVenta, Integer precioOferta, String stock_inicial,
                      String stock_inventario, String cantidad_diferencia) {
        this.idProducto = idProducto;
        this.codigoBarras = codigoBarras;
        this.detalle = detalle;
        this.precioVenta = precioVenta;
        this.precioOferta = precioOferta;
        this.stock_inicial = stock_inicial;
        this.stock_inventario = stock_inventario;
        this.cantidad_diferencia = cantidad_diferencia;
    }

    @Override
    public String toString() {
        return "NewProduct{" +
                "idProducto=" + idProducto +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", detalle='" + detalle + '\'' +
                ", precioVenta='" + precioVenta + '\'' +
                ", precioOferta=" + precioOferta +
                ", stock_inicial='" + stock_inicial + '\'' +
                ", stock_inventario='" + stock_inventario + '\'' +
                ", cantidad_diferencia='" + cantidad_diferencia + '\'' +
                '}';
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
