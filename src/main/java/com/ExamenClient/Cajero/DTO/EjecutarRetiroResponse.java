package com.ExamenClient.Cajero.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class EjecutarRetiroResponse {

    @JsonIgnore
    private int codigo;

    private String mensaje;
    private List<DesgloseItem> desglose;

    public EjecutarRetiroResponse() {

    }

    public EjecutarRetiroResponse(int codigo, String mensaje, List<DesgloseItem> desglose) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.desglose = desglose;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<DesgloseItem> getDesglose() {
        return desglose;
    }

    public void setDesglose(List<DesgloseItem> desglose) {
        this.desglose = desglose;
    }

}
