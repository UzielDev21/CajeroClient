package com.ExamenClient.Cajero.ML;

public class TipoCuenta {

    private int idTipoCuenta;
    private String tipoCuenta;

    public TipoCuenta() {
    }

    public TipoCuenta(int idTipoCuenta, String tipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
        this.tipoCuenta = tipoCuenta;
    }

    public int getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(int idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}
