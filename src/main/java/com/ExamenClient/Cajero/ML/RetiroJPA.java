package com.ExamenClient.Cajero.ML;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class RetiroJPA {

    private int idRetiro;

    @JsonProperty("CuentasJPA")
    public Cuentas cuentas;

    @JsonProperty("CajeroJPA")
    public Cajero cajero;

    private BigDecimal monto;

    private String estatus;

    public RetiroJPA() {
    }

    public RetiroJPA(int idRetiro, Cuentas cuentas, Cajero cajero, BigDecimal monto, String status) {
        this.idRetiro = idRetiro;
        this.cuentas = cuentas;
        this.cajero = cajero;
        this.monto = monto;
        this.estatus = estatus;
    }

    public int getIdRetiro() {
        return idRetiro;
    }

    public void setIdRetiro(int idRetiro) {
        this.idRetiro = idRetiro;
    }

    public Cuentas getCuentas() {
        return cuentas;
    }

    public void setCuentas(Cuentas cuentas) {
        this.cuentas = cuentas;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

}
