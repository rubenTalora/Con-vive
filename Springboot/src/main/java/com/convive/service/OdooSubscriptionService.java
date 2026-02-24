package com.convive.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Servicio que consulta a Odoo para verificar y consumir el cupo
 * de publicaciones de la suscripción activa del usuario.
 */
@Service
public class OdooSubscriptionService {

    @Value("${odoo.base-url}")
    private String odooBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean consumePublication(String bearerToken) {
        if (bearerToken == null || bearerToken.isBlank()) {
            // Sin token → usuario no autenticado, Spring dejará pasar o bloqueará por otro filtro
            return true;
        }

        String url = odooBaseUrl + "/api/convive/subscriptions/consume-publication";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            // 403 → límite alcanzado o sin suscripción activa
            return false;
        } catch (Exception e) {
            // Si Odoo no está disponible, se permite la creación para no bloquear el servicio
            return true;
        }
    }
}
