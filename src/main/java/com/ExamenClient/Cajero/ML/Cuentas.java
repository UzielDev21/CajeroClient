package com.ExamenClient.Cajero.ML;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Cuentas {

    private int idCuenta;

    @JsonProperty("UsuarioJPA")
    public Usuario usuario;

    @JsonProperty("TipoCuentaJPA")
    public TipoCuenta tipoCuenta;

    private BigDecimal saldo;
    private int estado;
    private int noCuenta;
    private int nip;

    public Cuentas() {
    }

    public Cuentas(int idCuenta, Usuario usuario, TipoCuenta tipoCuenta, BigDecimal saldo, int estado, int noCuenta, int nip) {
        this.idCuenta = idCuenta;
        this.usuario = usuario;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.estado = estado;
        this.noCuenta = noCuenta;
        this.nip = nip;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(int noCuenta) {
        this.noCuenta = noCuenta;
    }

    public int getNip() {
        return nip;
    }

    public void setNip(int nip) {
        this.nip = nip;
    }
}
