package com.ryfsystems.smi.Models;

import java.io.Serializable;

public class Product implements Serializable {

    private Integer codlocal;
    private String sucursal;
    private Integer activado;
    private String dep;
    private String ean_13;
    private Integer linea;
    private Integer code;
    private String detalle;

    public Product() {
    }

    public Product(Integer codlocal, String sucursal, Integer activado, String dep, String ean_13, Integer linea, Integer code, String detalle) {
        this.codlocal = codlocal;
        this.sucursal = sucursal;
        this.activado = activado;
        this.dep = dep;
        this.ean_13 = ean_13;
        this.linea = linea;
        this.code = code;
        this.detalle = detalle;
    }

    public Integer getCodlocal() {
        return codlocal;
    }

    public void setCodlocal(Integer codlocal) {
        this.codlocal = codlocal;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getActivado() {
        return activado;
    }

    public void setActivado(Integer activado) {
        this.activado = activado;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getEan_13() {
        return ean_13;
    }

    public void setEan_13(String ean_13) {
        this.ean_13 = ean_13;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "Product{" +
                "codlocal=" + codlocal +
                ", sucursal='" + sucursal + '\'' +
                ", activado=" + activado +
                ", dep='" + dep + '\'' +
                ", ean_13='" + ean_13 + '\'' +
                ", linea=" + linea +
                ", code=" + code +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
