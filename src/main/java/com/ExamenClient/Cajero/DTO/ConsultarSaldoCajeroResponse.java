package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;

public class ConsultarSaldoCajeroResponse {

    private String ubicacion;
    private BigDecimal total;
    private String isService;

    public ConsultarSaldoCajeroResponse() {

    }

    public ConsultarSaldoCajeroResponse(String ubicacion, BigDecimal total, String isService) {
        this.ubicacion = ubicacion;
        this.total = total;
        this.isService = isService;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getIsService() {
        return isService;
    }

    public void setIsService(String isService) {
        this.isService = isService;
    }
    
    

}
