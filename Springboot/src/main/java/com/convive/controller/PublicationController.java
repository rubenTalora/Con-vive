
package com.convive.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.convive.entity.Publication;
import com.convive.repository.PublicationRepository;

@RestController
@RequestMapping("/api/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationRepository repository;

    @GetMapping("/")
    public List<Publication> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Publication getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping("/create")
    public Publication create(@RequestBody Publication publication) {
        return repository.save(publication);
    }

    @PutMapping("/{id}")
    public Publication update(@PathVariable Long id, @RequestBody Publication updated) {
        Publication pub = repository.findById(id).orElseThrow();
        pub.setTitle(updated.getTitle());
        pub.setDescription(updated.getDescription());
        pub.setType(updated.getType());
        pub.setPrice(updated.getPrice());
        pub.setCity(updated.getCity());
        return repository.save(pub);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
