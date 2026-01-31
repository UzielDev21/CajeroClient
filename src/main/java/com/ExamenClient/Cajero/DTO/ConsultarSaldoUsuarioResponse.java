package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class ConsultarSaldoUsuarioResponse {

    private String username;
    private Integer noCuenta;
    private BigDecimal saldo;

    public ConsultarSaldoUsuarioResponse() {

    }

    public ConsultarSaldoUsuarioResponse(String username, Integer noCuenta, BigDecimal saldo) {
        this.username = username;
        this.noCuenta = noCuenta;
        this.saldo = saldo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(Integer noCuenta) {
        this.noCuenta = noCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

}
