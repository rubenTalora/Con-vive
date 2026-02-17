package Springboot.app.src.main.java.com.convive.infraestructure.controller;

import com.convive.model.Publicacion;
import com.convive.service.PublicacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(publicacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(publicacionService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody Publicacion publicacion,
            @RequestAttribute("userId") Long userId) {

        return ResponseEntity.ok(publicacionService.create(publicacion, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody Publicacion publicacion,
            @RequestAttribute("userId") Long userId) {

        return ResponseEntity.ok(publicacionService.update(id, publicacion, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id,
            @RequestAttribute("userId") Long userId,
            @RequestAttribute("role") String role) {

        boolean isAdmin = role.equals("ADMIN");
        publicacionService.delete(id, userId, isAdmin);
        return ResponseEntity.ok("Publicación eliminada correctamente");
    }
}