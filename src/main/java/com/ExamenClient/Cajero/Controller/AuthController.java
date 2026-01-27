package com.ExamenClient.Cajero.Controller;

import com.ExamenClient.Cajero.ML.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("auth")
public class AuthController {

    private static final String urlBase = "http://localhost:8080";

    @GetMapping("/login")
    public String login() {
        return "login";
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
                    return "redirect:/cajeros/menu";
                }

                model.addAttribute(
                        "loginErrorMensaje",
                        "No tienes permisos para continuar");
                return "login";
            }

            model.addAttribute(
                    "loginErrorMessage",
                    "Error al iniciar sesión, Por favor intentalo nuevamente"
            );
            return "login";

        } catch (HttpClientErrorException ex) {

            int status = ex.getStatusCode().value();
            String body = ex.getResponseBodyAsString();

            if (status == 403) {
                model.addAttribute(
                        "loginErrorMessage",
                        "Acceso denegado");
                return "login";
            }

            if (status == 401) {

                if (body != null && !body.isBlank()) {

                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        Result<?> result = mapper.readValue(body, Result.class);

                        if (result != null
                                && result.errorMessage != null
                                && !result.errorMessage.isBlank()) {

                            model.addAttribute(
                                    "loginErrorMessage",
                                    result.errorMessage);
                            return "login";
                        }
                    } catch (Exception ignored) {
                    }
                }
                model.addAttribute(
                        "loginErrorMessage",
                        "Credenciales incorrectas. Verifica el usuario y contraseña");
                return "login";
            }

            model.addAttribute(
                    "loginErrorMessage",
                    "Error al iniciar sesion (" + status + ")");
            return "login";

        } catch (HttpServerErrorException ex) {
            model.addAttribute(
                    "loginErrorMessage",
                    "El servcio no esta disponible, intentalo mas tarde");
            return "login";

        } catch (ResourceAccessException ex) {
            model.addAttribute(
                    "loginErrorMessage",
                    "No se pudo conectar con el servidor de autenticación"
            );
            return "login";

        } catch (Exception ex) {
            model.addAttribute(
                    "loginErrorMessage",
                    "Error inesperado al iniciar sesión"
            );
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {

        String token = (String) session.getAttribute("jwtToken");

        if (token == null) {
            redirectAttributes.addFlashAttribute("error", "No hay sesion activa");
            return "redirect:/auht/login";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {

            ResponseEntity<Result<String>> responseEntity = restTemplate.exchange(
                    urlBase + "/api/logout",
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Result<String>>() {
            });

            if (responseEntity.getStatusCode().value() == 200) {

                Result<String> result = responseEntity.getBody();

                if (result != null && Boolean.TRUE.equals(result.correct)) {

                    session.invalidate();;
                    redirectAttributes.addFlashAttribute(
                            "msgLogout",
                            "Sesion Cerrada");
                    return "redirect:/auth/login";
                }
            }

            redirectAttributes.addFlashAttribute(
                    "msgError",
                    "No se puede cerrar sesión");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(
                    "msgError",
                    "Error Logout: " + ex.getLocalizedMessage());
        }
        return "redirect:/auth/login";
    }

    private Map<String, Object> decodeJwt(String jwt) {

        Result result = new Result();

        try {
            String[] parts = jwt.split("\\.");

            if (parts.length < 2) {
                return (Map<String, Object>) result;
            }

            String payload = new String(
                    java.util.Base64.getUrlDecoder().decode(parts[1]),
                    java.nio.charset.StandardCharsets.UTF_8
            );
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(payload, new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            return null;
        }
    }

}
