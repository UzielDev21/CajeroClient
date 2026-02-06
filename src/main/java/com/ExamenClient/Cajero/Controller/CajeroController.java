package com.ExamenClient.Cajero.Controller;

import com.ExamenClient.Cajero.DTO.ConsultarSaldoCajeroResponse;
import com.ExamenClient.Cajero.DTO.ConsultarSaldoUsuarioResponse;
import com.ExamenClient.Cajero.DTO.EjecutarRetiroRequest;
import com.ExamenClient.Cajero.DTO.EjecutarRetiroResponse;
import com.ExamenClient.Cajero.DTO.ValidarDenominacionRequest;
import com.ExamenClient.Cajero.DTO.ValidarDenominacionResponse;
import com.ExamenClient.Cajero.DTO.ValidarSaldoCuentaRequest;
import com.ExamenClient.Cajero.DTO.ValidarSaldoCuentaResponse;
import com.ExamenClient.Cajero.ML.Cajero;
import com.ExamenClient.Cajero.ML.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("cajeros")
public class CajeroController {

    private static final String urlBase = "http://localhost:8080";

    @GetMapping("/menu")
    public String Cajeros(Model model, HttpSession session) {

        String token = (String) session.getAttribute("jwtToken");

        if (token == null) {
            return "redirect:/auth/login";
        }

        String rol = (String) session.getAttribute("rol");
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (rol == null) {
            return "redirect:/auth/login";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplateCajero = new RestTemplate();
        ResponseEntity<Result<Cajero>> responseEntityCajero = restTemplateCajero.exchange(
                urlBase + "/api/cajeros",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Result<Cajero>>() {
        });

        if (responseEntityCajero.getStatusCode().value() == 200) {

            Result resultCajero = responseEntityCajero.getBody();
            model.addAttribute(
                    "Cajeros",
                    resultCajero.objects);

            String user = (String) session.getAttribute("loggedUsername");
            model.addAttribute(
                    "UsuarioLogueado",
                    user);
            return "cajeros";
        } else {
            return "error";
        }
    }

    @GetMapping("/{idCajero}")
    public String Cajero(
            @PathVariable int idCajero,
            Model model, HttpSession session) {

        String token = (String) session.getAttribute("jwtToken");
        String rol = (String) session.getAttribute("rol");

        if (token == null || rol == null) {
            return "redirect:/auth/login";
        }

        Integer idCuenta = (Integer) session.getAttribute("idCuenta");

        if (idCuenta == null) {
            return "redirect:/cajeros/menu";
        }

        session.setAttribute(("idCajeroSeleccionado"), idCajero);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();

        //aqui hago la validación del cajero
        Map<String, Object> validarCajeroRequest = new HashMap<>();
        validarCajeroRequest.put("idCajero", idCajero);

        /*
            con este put mando la comprobación del saldo del cajero
            mando el minimo para que se pueda comprobar el saldo del cajero
         */
        validarCajeroRequest.put("monto", 1);

        HttpEntity<Map<String, Object>> validarEntity = new HttpEntity<>(validarCajeroRequest, headers);
        ResponseEntity<Result> validarResponse = restTemplate.exchange(
                urlBase + "/api/validar-cajero",
                HttpMethod.POST,
                validarEntity,
                new ParameterizedTypeReference<Result>() {
        });

        boolean cajeroDisponible = false;
        String mensajeCajero = null;

        if (validarResponse.getStatusCode().value() == 200) {

            Result validarResult = validarResponse.getBody();
            cajeroDisponible = validarResult != null && validarResult.correct;
            mensajeCajero = validarResult != null ? validarResult.errorMessage : null;
        } else {
            mensajeCajero = "no se pudo validar el cajero";
        }

        model.addAttribute(
                "cajeroDisponible",
                cajeroDisponible);
        model.addAttribute(
                "mensajeCajero",
                mensajeCajero);

        HttpEntity<?> SaldoEntity = new HttpEntity<>(headers);

        ResponseEntity<Result<ConsultarSaldoUsuarioResponse>> saldoResponse = restTemplate.exchange(
                urlBase + "/api/consultar-saldo-usuario/" + idCuenta,
                HttpMethod.GET,
                SaldoEntity,
                new ParameterizedTypeReference<Result<ConsultarSaldoUsuarioResponse>>() {
        });

        if (saldoResponse.getStatusCode().value() == 200) {
            Result<ConsultarSaldoUsuarioResponse> resultSaldo = saldoResponse.getBody();

            if (resultSaldo != null && resultSaldo.correct) {
                model.addAttribute(
                        "saldoUsuario",
                        resultSaldo.object);
            } else {
                model.addAttribute(
                        "mensajeSaldo",
                        resultSaldo != null ? resultSaldo.errorMessage : "Error al consultar el saldo");
            }
        } else {
            model.addAttribute(
                    "mensajeSaldo",
                    "no fue posible obtener el saldo");
        }

        model.addAttribute(
                "esAdmin",
                "Administrador".equalsIgnoreCase(rol));

        model.addAttribute(
                "esCliente",
                "Cliente".equalsIgnoreCase(rol));

        String user = (String) session.getAttribute("loggedUsername");
        model.addAttribute("UsuarioLogueado", user);

        return "cajero";
    }

    @GetMapping("/retirar")
    public String ViewRetirar(Model model, HttpSession session) {

        String token = (String) session.getAttribute("jwtToken");
        String rol = (String) session.getAttribute("rol");

        if (token == null || rol == null) {
            return "redirect:/auth/login";
        }

        Integer idCuenta = (Integer) session.getAttribute("idCuenta");
        Integer idCajero = (Integer) session.getAttribute("idCajeroSeleccionado");

        if (idCuenta == null || idCajero == null) {
            return "redirect:/cajeros/menu";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> saldoEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {

            ResponseEntity<Result<ConsultarSaldoUsuarioResponse>> saldoResponse = restTemplate.exchange(
                    urlBase + "/api/consultar-saldo-usuario/" + idCuenta,
                    HttpMethod.GET,
                    saldoEntity,
                    new ParameterizedTypeReference<Result<ConsultarSaldoUsuarioResponse>>() {
            });

            if (saldoResponse.getStatusCode().value() == 200) {
                Result<ConsultarSaldoUsuarioResponse> result = saldoResponse.getBody();

                if (result != null && result.correct) {

                    model.addAttribute(
                            "saldoUsuario",
                            result.object);
                } else {
                    String errorMsg = (result != null) ? result.errorMessage : "Error desconocido al consultar el saldo";
                    model.addAttribute(
                            "mensajeError",
                            errorMsg);
                }
            } else {
                model.addAttribute(
                        "mensajeError",
                        "no se pudo obtener el saldo actual");
            }

        } catch (Exception ex) {
            System.out.println("Error en ViewRetirar: " + ex.getLocalizedMessage());
            model.addAttribute("mensajeError", "Ocurrio un error al obtener los datos");
        }

        String user = (String) session.getAttribute("loggedUsername");
        model.addAttribute(
                "UsuarioLogueado",
                user);

        model.addAttribute(
                "idCajero",
                idCajero);

        return "retiro";
    }

    @PostMapping("/retirar")
    public String EjecutarRetiro(
            @RequestParam("monto") BigDecimal monto,
            Model model, HttpSession session) {

        String token = (String) session.getAttribute("jwtToken");
        String rol = (String) session.getAttribute("rol");
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        Integer idCuenta = (Integer) session.getAttribute("idCuenta");
        Integer idCajero = (Integer) session.getAttribute("idCajeroSeleccionado");

        if (token == null || rol == null || idUsuario == null) {
            return "redirect:/auth/login";
        }

        if (idCuenta == null || idCajero == null) {
            return "redirect:/cajeros/menu";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        RestTemplate restTemplate = new RestTemplate();

        String user = (String) session.getAttribute("loggedUsername");
        model.addAttribute("UsuarioLogueado", user);
        model.addAttribute("idCajero", idCajero);

        try {
            ValidarSaldoCuentaRequest saldoCuentaRequest = new ValidarSaldoCuentaRequest(idCuenta, monto);
            HttpEntity<ValidarSaldoCuentaRequest> entitySaldo = new HttpEntity<>(saldoCuentaRequest, headers);

            ResponseEntity<Result> responseSaldo = restTemplate.exchange(
                    urlBase + "/api/validar-saldoCuenta",
                    HttpMethod.POST,
                    entitySaldo,
                    Result.class);

            if (responseSaldo.getStatusCode().value() == 200) {

                Result resultSaldo = responseSaldo.getBody();

                if (resultSaldo == null || !resultSaldo.correct) {
                    String msg = (resultSaldo != null) ? resultSaldo.errorMessage : "Saldo insuficiente";
                    model.addAttribute("mensajeError", msg);

                    recargarSaldoParaVista(model, idCuenta, headers, restTemplate);
                    return "retiro";
                }
            } else {
                model.addAttribute("mensajeError", "Error al conectar con el servicio de validación del saldo");
                recargarSaldoParaVista(model, idCuenta, headers, restTemplate);
                return "retiro";
            }

            ValidarDenominacionRequest denoRequest = new ValidarDenominacionRequest(idCajero, monto);
            HttpEntity<ValidarDenominacionRequest> entityDeno = new HttpEntity<>(denoRequest, headers);

            ResponseEntity<Result> responseDeno = restTemplate.exchange(
                    urlBase + "/api/validar-denominacion",
                    HttpMethod.POST,
                    entityDeno,
                    Result.class);

            if (responseDeno.getStatusCode().value() == 200) {
                Result resultDeno = responseDeno.getBody();

                if (resultDeno == null || !resultDeno.correct) {
                    model.addAttribute(
                            "mensajeError",
                            resultDeno != null ? resultDeno.errorMessage : "El cajero no puede dispensar ese monto"
                    );
                    recargarSaldoParaVista(model, idCuenta, headers, restTemplate);
                    return "retiro";
                }
            } else {
                model.addAttribute("mensajeError", "Error al verificar disponibilidad de denominacion");
                recargarSaldoParaVista(model, idCuenta, headers, restTemplate);
                return "retiro";
            }

            EjecutarRetiroRequest retiroRequest = new EjecutarRetiroRequest(idUsuario, idCuenta, idCajero, monto);
            HttpEntity<EjecutarRetiroRequest> entityRetiro = new HttpEntity<>(retiroRequest, headers);

            ResponseEntity<Result> responseRetiro = restTemplate.exchange(
                    urlBase + "/api/retiro",
                    HttpMethod.POST,
                    entityRetiro,
                    Result.class);

            if (responseRetiro.getStatusCode().value() == 200) {
                Result resultRetiro = responseRetiro.getBody();

                if (resultRetiro != null && resultRetiro.correct) {

                    ObjectMapper mapper = new ObjectMapper();
                    EjecutarRetiroResponse datosRetiro = mapper.convertValue(resultRetiro.object, EjecutarRetiroResponse.class);

                    model.addAttribute(
                            "ListaDesglose",
                            datosRetiro.getDesglose());
                    model.addAttribute(
                            "mensajeExito",
                            "Retiro realizado con exito");
                } else {
                    model.addAttribute(
                            "mensajeError",
                            resultRetiro != null ? resultRetiro.errorMessage : "Error al procesar el retiro"
                    );
                }
            } else {
                model.addAttribute("mensajeError", "Error de comunicacion");
            }
            recargarSaldoParaVista(model, idCuenta, headers, restTemplate);

        } catch (Exception ex) {
            System.out.println("Error critico: " + ex.getLocalizedMessage());
            model.addAttribute("mensajeError", "ocurrio un error en el sistema");
        }
        return "retiro";
    }

    @GetMapping("/estatus")
    public String ViewEstatus(Model model, HttpSession session) {

        String token = (String) session.getAttribute("jwtToken");
        String rol = (String) session.getAttribute("rol");

        if (token == null || rol == null || rol.equalsIgnoreCase("Cliente")) {
            return "redirect:/auth/login";
        }

        return "estatus";
    }

    private void recargarSaldoParaVista(Model model, Integer idCuenta, HttpHeaders headers, RestTemplate restTemplate) {
        try {
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Result<ConsultarSaldoUsuarioResponse>> response = restTemplate.exchange(
                    urlBase + "/api/consultar-saldo-usuario/" + idCuenta,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Result<ConsultarSaldoUsuarioResponse>>() {
            }
            );
            if (response.getBody() != null && response.getBody().correct) {
                model.addAttribute("saldoUsuario", response.getBody().object);
            }
        } catch (Exception ex) {
            System.out.println("No se pudo recargar el saldo en el POST: " + ex.getMessage());
        }
    }

}
