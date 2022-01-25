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
    private Long stock_;
    private Long pventa;
    private Long poferta;
    private Double avg_pro;
    private Long costo_prom;
    private String codBarra;
    private Double pcadena;
    private int pedido;
    private int und_defect;

    public Product() {
    }

    public Product(Integer codlocal,
                   String sucursal,
                   Integer activado,
                   String dep,
                   String ean_13,
                   Integer linea,
                   Integer code,
                   String detalle,
                   Long stock_,
                   Long pventa,
                   Long poferta,
                   Double avg_pro,
                   Long costo_prom,
                   String codBarra,
                   Double pcadena,
                   int pedido,
                   int und_defect) {
        this.codlocal = codlocal;
        this.sucursal = sucursal;
        this.activado = activado;
        this.dep = dep;
        this.ean_13 = ean_13;
        this.linea = linea;
        this.code = code;
        this.detalle = detalle;
        this.stock_ = stock_;
        this.pventa = pventa;
        this.poferta = poferta;
        this.avg_pro = avg_pro;
        this.costo_prom = costo_prom;
        this.codBarra = codBarra;
        this.pcadena = pcadena;
        this.pedido = pedido;
        this.und_defect = und_defect;
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

    public Long getStock_() {
        return stock_;
    }

    public void setStock_(Long stock_) {
        this.stock_ = stock_;
    }

    public Long getPventa() {
        return pventa;
    }

    public void setPventa(Long pventa) {
        this.pventa = pventa;
    }

    public Long getPoferta() {
        return poferta;
    }

    public void setPoferta(Long poferta) {
        this.poferta = poferta;
    }

    public Double getAvg_pro() {
        return avg_pro;
    }

    public void setAvg_pro(Double avg_pro) {
        this.avg_pro = avg_pro;
    }

    public Long getCosto_prom() {
        return costo_prom;
    }

    public void setCosto_prom(Long costo_prom) {
        this.costo_prom = costo_prom;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }


    public Double getPcadena() {
        return pcadena;
    }

    public void setPcadena(Double pcadena) {
        this.pcadena = pcadena;
    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public int getUnd_defect() {
        return und_defect;
    }

    public void setUnd_defect(int und_defect) {
        this.und_defect = und_defect;
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
                ", stock_=" + stock_ +
                ", pventa=" + pventa +
                ", p_oferta=" + poferta +
                ", avg_pro=" + avg_pro +
                ", costo_prom=" + costo_prom +
                ", codBarra='" + codBarra + '\'' +
                ", pcadena=" + pcadena +
                ", pedido=" + pedido +
                ", und_defect=" + und_defect +
                '}';
    }
}
