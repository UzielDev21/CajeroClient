package com.ExamenClient.Cajero.DTO;

public class AccesoCuentaRequest {

    private Integer idUsuario;
    private Integer noCuenta;
    private Integer nip;

    public AccesoCuentaRequest() {
    }

    public AccesoCuentaRequest(Integer idCuenta, Integer codigo, Integer nip) {
        this.idUsuario = idCuenta;
        this.noCuenta = codigo;
        this.nip = nip;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(Integer noCuenta) {
        this.noCuenta = noCuenta;
    }

    public Integer getNip() {
        return nip;
    }

    public void setNip(Integer nip) {
        this.nip = nip;
    }
}
