package com.ExamenClient.Cajero.Controller;

import com.ExamenClient.Cajero.ML.Cajero;
import com.ExamenClient.Cajero.ML.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
