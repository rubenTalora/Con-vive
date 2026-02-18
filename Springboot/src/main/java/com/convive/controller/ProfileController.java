package com.convive.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.convive.entity.Profile;
import com.convive.repository.ProfileRepository;

@RestController
@RequestMapping("/api/profiles") // Ruta para todas las perfiles
@RequiredArgsConstructor
public class ProfileController {

    // Inyección automática del repository
    private final ProfileRepository repository;


    // OBTENER TODAS LAS PERFILES

    @GetMapping
    public List<Profile> getAll() {
        return repository.findAll();
    }

    // BUSCAR PERFIL POR ID

    @GetMapping("/{id}")
    public Profile getById(@PathVariable Long id) {
        // Si no existe lanza excepción
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrada"));
    }

    // CREAR Profile

    @PostMapping
    public Profile create(@RequestBody Profile profile) {

        // Que no sea in id manual
        profile.setId(null);

        return repository.save(profile);
    }

    // ACTUALIZAR Profile

    @PutMapping("/{id}")
    public Profile update(@PathVariable Long id, 
                            @RequestBody Profile updated) {

        // Buscar la perfil  existente
        Profile pub = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        // Actualizamos los campos que queremos modificar
        pub.setName(updated.getName());
        pub.setDescription(updated.getDescription());
        pub.setCity(updated.getCity());
        pub.setPiso(updated.getPiso());
        pub.setPrice(updated.getPrice());
        pub.setSex(updated.getSex());
        pub.setVisible(updated.getVisible());
        pub.setAge(updated.getAge());


        // Guardar cambios
        return repository.save(pub);
    }

    // BORRAR PERFIL

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        // Verificamos que exista antes de borrar
        if (!repository.existsById(id)) {
            throw new RuntimeException("Perfil no encontrado");
        }

        repository.deleteById(id);
    }
}
