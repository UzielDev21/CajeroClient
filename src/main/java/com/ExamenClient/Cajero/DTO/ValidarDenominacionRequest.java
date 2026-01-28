package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class ValidarDenominacionRequest {

    private int idCajero;
    private BigDecimal monto;

    public ValidarDenominacionRequest() {

    }

    public ValidarDenominacionRequest(int idCajero, BigDecimal monto) {
        this.idCajero = idCajero;
        this.monto = monto;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
