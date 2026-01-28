package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class ValidarCajeroRequest {

    private Integer idCajero;
    private BigDecimal monto;

    public ValidarCajeroRequest() {
    }

    public ValidarCajeroRequest(Integer idcajero, BigDecimal monto) {
        this.idCajero = idcajero;
        this.monto = monto;
    }

    public Integer getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(Integer idCajero) {
        this.idCajero = idCajero;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
