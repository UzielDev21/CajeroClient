package com.ExamenClient.Cajero.Controller;

import com.ExamenClient.Cajero.DTO.AccesoCuentaRequest;
import com.ExamenClient.Cajero.DTO.AccesoCuentaResponse;
import com.ExamenClient.Cajero.ML.Cajero;
import com.ExamenClient.Cajero.ML.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class AccesoCuentaController {

    private static final String urlBase = "http://localhost:8080";

    @GetMapping("/acceso-cuenta/{idCajero}")
    public String viewAccesoCuenta(@PathVariable int idCajero,
            Model model, HttpSession session) {

        String token = (String) session.getAttribute("jwtToken");

        if (token == null) {
            return "redirect:/auth/login";
        }
        
        session.setAttribute("idCajeroSeleccionado", idCajero);

        String user = (String) session.getAttribute("loggedUsername");
        model.addAttribute("UsuarioLogueado", user);

        model.addAttribute("accesoCuentaRequest", new AccesoCuentaRequest());

        return "accesoCuenta";
    }

    @PostMapping("/acceso-cuenta")
    public String ejecutarAccesoCuenta(
            @ModelAttribute("accesoCuentaRequest") AccesoCuentaRequest accesoCuentaForm,
            Model model, HttpSession session) {

        String token = (String) session.getAttribute("jwtToken");
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
        Integer idCajero = (Integer) session.getAttribute("idCajeroSeleccionado");

        if (token == null || idUsuario == null) {
            return "redirect:/auth/login";
        }

        accesoCuentaForm.setIdUsuario(idUsuario);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<AccesoCuentaRequest> entity = new HttpEntity<>(accesoCuentaForm, headers);

        RestTemplate restTemplate = new RestTemplate();
        Result<AccesoCuentaResponse> result;

        try {

            ResponseEntity<Result<AccesoCuentaResponse>> accesoCuentaResponse = restTemplate.exchange(
                    urlBase + "/api/acceso-cuenta",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Result<AccesoCuentaResponse>>() {
            });
            result = accesoCuentaResponse.getBody();

        } catch (Exception ex) {

            model.addAttribute(
                    "msgError",
                    "Error de comunicación con el servidor");
            model.addAttribute(
                    "UsuarioLogueado",
                    session.getAttribute("loggedUsername"));
            return "accesoCuenta";
        }

        model.addAttribute(
                "UsuarioLogueado",
                session.getAttribute("loggedUsername"));

        if (result != null && result.correct) {

            if (result.object != null) {
                session.setAttribute("idCuenta", result.object.getIdCuenta());
            }

            model.addAttribute(
                    "mensajeOk",
                    result.object != null ? result.object.getMensaje() : "Bienvenido");
            
            return "redirect:/cajeros/" + idCajero;

        } else {
            String mensaje = (result != null && result.errorMessage != null)
                    ? result.errorMessage : "No fue posible acceder a la cuenta";
            model.addAttribute("msgError", mensaje);
            return "accesoCuenta";
        }
    }

}
