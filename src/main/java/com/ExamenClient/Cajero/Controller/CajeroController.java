package com.ExamenClient.Cajero.Controller;

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
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        
        Map<String, Object> validarCajeroRequest = new HashMap<>();
        validarCajeroRequest.put("idCajero", idCajero);
        validarCajeroRequest.put("monto", 1);
        
        //terminar de revisar aqui, no esta terminado 
        
        ResponseEntity<Result<Cajero>> responseEntityCajero = restTemplate.exchange(
                urlBase + "/api/cajeros/" + idCajero,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Result<Cajero>>() {
        });

        if (responseEntityCajero.getStatusCode().value() == 200) {

            Result resultCajero = responseEntityCajero.getBody();
            model.addAttribute(
                    "Cajero",
                    resultCajero.object);

            String user = (String) session.getAttribute("loggedUsername");
            model.addAttribute("UsuarioLogueado", user);

            return "cajero";
        } else {
            return "error";
        }
    }
    
    
    
}
