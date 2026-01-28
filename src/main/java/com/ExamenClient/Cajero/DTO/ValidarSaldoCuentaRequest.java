package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class ValidarSaldoCuentaRequest {

    private int idCuenta;
    private BigDecimal monto;

    public ValidarSaldoCuentaRequest() {

    }

    public ValidarSaldoCuentaRequest(int idCuenta, BigDecimal monto) {
        this.idCuenta = idCuenta;
        this.monto = monto;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}
