package com.ExamenClient.Cajero.Controller;

import com.ExamenClient.Cajero.DTO.ConsultarSaldoUsuarioResponse;
import com.ExamenClient.Cajero.ML.Cajero;
import com.ExamenClient.Cajero.ML.Result;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}