package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class DenominacionInventario {

    private Integer idInventario;
    private Integer idDenominacion;
    private BigDecimal valor;
    private Integer cantidad;

    public DenominacionInventario() {

    }

    public DenominacionInventario(Integer idInventario, Integer idDenominacion, BigDecimal valor, Integer cantidad) {
        this.idInventario = idInventario;
        this.idDenominacion = idDenominacion;
        this.valor = valor;
        this.cantidad = cantidad;
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
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
