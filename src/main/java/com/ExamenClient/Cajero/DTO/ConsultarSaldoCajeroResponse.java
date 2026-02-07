package com.ExamenClient.Cajero.DTO;

import java.math.BigDecimal;
import java.util.List;

public class ConsultarSaldoCajeroResponse {

    private String ubicacion;
    private BigDecimal total;
    private String isService;
    private List<DenominacionInventario> denominaciones;

    public ConsultarSaldoCajeroResponse() {

    }

    public ConsultarSaldoCajeroResponse(String ubicacion, BigDecimal total, String isService, List<DenominacionInventario> denominaciones) {
        this.ubicacion = ubicacion;
        this.total = total;
        this.isService = isService;
        this.denominaciones = denominaciones;
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

    public List<DenominacionInventario> getDenominaciones() {
        return denominaciones;
    }

    public void setDenominaciones(List<DenominacionInventario> denominaciones) {
        this.denominaciones = denominaciones;
    }
}
