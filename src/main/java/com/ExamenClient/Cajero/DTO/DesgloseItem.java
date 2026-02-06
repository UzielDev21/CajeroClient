package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class DesgloseItem {

    private Integer idDenominacion;
    private BigDecimal valor;
    private Integer cantidad;

    public DesgloseItem() {

    }

    public DesgloseItem(Integer idDenominacion, BigDecimal valor, Integer cantidad) {
        this.idDenominacion = idDenominacion;
        this.valor = valor;
        this.cantidad = cantidad;
    }

    public Integer getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(Integer idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
