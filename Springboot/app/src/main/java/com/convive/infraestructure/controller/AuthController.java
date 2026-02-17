package Springboot.app.src.main.java.com.convive.infraestructure.controller;

import com.convive.dto.LoginRequest;
import com.convive.dto.RegisterRequest;
import com.convive.service.OdooClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OdooClientService odooClientService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(odooClientService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(odooClientService.login(request));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserProfile(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(odooClientService.getUserProfile(token));
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody Object updateRequest) {
        return ResponseEntity.ok(odooClientService.updateUser(token, updateRequest));
    }
}
