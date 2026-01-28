package com.ExamenClient.Cajero.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ValidarDenominacionResponse {

    @JsonIgnore
    private int codigo;
    
    private String mensaje;
    private String desglose;
    
    public ValidarDenominacionResponse(){
        
    }
    
    public ValidarDenominacionResponse(int codigo, String mensaje, String desglose){
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

    public String getDesglose() {
        return desglose;
    }

    public void setDesglose(String desglose) {
        this.desglose = desglose;
    }
    
    
    
}
