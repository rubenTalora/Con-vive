package Springboot.app.src.main.java.com.convive.infraestructure.controller;

import com.convive.service.OdooClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SuscripcionController {

    private final OdooClientService odooClientService;

    @GetMapping("/plans")
    public ResponseEntity<?> getPlans() {
        return ResponseEntity.ok(odooClientService.getPlans());
    }

    @GetMapping("/status")
    public ResponseEntity<?> getSubscriptionStatus(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(odooClientService.getSubscriptionStatus(token));
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(
            @RequestHeader("Authorization") String token,
            @RequestParam Long planId) {
        return ResponseEntity.ok(odooClientService.checkout(token, planId));
    }

    @GetMapping("/invoices")
    public ResponseEntity<?> getInvoices(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(odooClientService.getInvoices(token));
    }
}