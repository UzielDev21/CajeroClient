package com.ExamenClient.Cajero.Controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("auth")
public class AuthController {

    private static final String urlBase = "http://localhost:8080";

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    private Map<String, Object> decodeJwt(String jwt){
        
        return null;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model, HttpSession session) {

        Map<String, String> datos = new HashMap<>();
        datos.put("username", username);
        datos.put("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(datos, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {

        } catch (Exception ex) {
            ResponseEntity<Result<String>> responseEntity
                    = restTemplate.exchange(
                            urlBase + "/api/login",
                            HttpMethod.POST,
                            requestEntity,
                            new ParameterizedTypeReference<Result<String>>() {
                    });
            
            Result<String> result = responseEntity.getBody();
            
            if (result != null && Boolean.TRUE.equals(result.correct)) {
                
                String jwt = result.object;
                
                session.setAttribute("jwtToken", jwt);
                session.setAttribute("loggedUsername", username);
                
                Map<String, Object> claims = decodeJwt(jwt);
                if (claims == null) {
                    model.addAttribute(
                            "loginErrorMessage", 
                            "No se pudo procesar el token de autenticación");
                    return "login";
                }
                
                String rol = (String) claims.get("rol");
                Object idObject = claims.get("idUsuario");
                Integer idUsuario = null;
                
                if (idObject instanceof Number) {
                    idUsuario = ((Number) idObject).intValue();
                }
                
                session.setAttribute("rol", rol);
                session.setAttribute("idUsuario", idUsuario);
                
                if ("Administrador".equalsIgnoreCase(rol)
                        || "Cliente".equalsIgnoreCase(rol)) {
                    return "redirect:/cajeros";
                }
                
            }
            
        }
        return null;
    }

}
