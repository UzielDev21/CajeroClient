package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class EjecutarRetiroRequest {

    private Integer idUsuario;
    private Integer idCuenta;
    private Integer idCajero;
    private BigDecimal monto;

    public EjecutarRetiroRequest() {

    }

    public EjecutarRetiroRequest(Integer idUsuario, Integer idCuenta, Integer idCajero, BigDecimal monto) {
        this.idUsuario = idUsuario;
        this.idCuenta = idCuenta;
        this.idCajero = idCajero;
        this.monto = monto;
    }
   

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
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
