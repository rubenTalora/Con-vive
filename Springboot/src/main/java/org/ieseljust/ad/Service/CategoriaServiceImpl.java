package org.ieseljust.ad.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ieseljust.ad.DTO.CategoriaDTO;
import org.ieseljust.ad.Model.Categoria;
import org.ieseljust.ad.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;


    @Override
    public void saveCategoria(CategoriaDTO categoriaDTO) {
        categoriaRepository.save(CategoriaDTO.convertToEntity(categoriaDTO));
    }

    @Override
    public CategoriaDTO getCategoriaById(Long id) {

        Optional<Categoria> c = categoriaRepository.findById(id);

        if (c.isPresent()) {
            return CategoriaDTO.convertToDTO(c.get());
        }

        return null;
    }

    @Override
    public CategoriaDTO getCategoriaByNom(String n) {

        Optional<Categoria> c = categoriaRepository.findByNom(n);

        if (c.isPresent()) {
            return CategoriaDTO.convertToDTO(c.get());
        }

        return null;
    }

    @Override
    public List<CategoriaDTO> listAllCategorias() {
        List<Categoria> categories = categoriaRepository.findAll();

        List<CategoriaDTO> lasCategoriesDTO = new ArrayList<>();

        //Conversio de Categoria a CategoriaDTO
        for (Categoria c : categories)
        {
            lasCategoriesDTO.add(CategoriaDTO.convertToDTO(c));
        }

        return lasCategoriesDTO;
    }

    @Override
    public void deleteCategoria(Long id) {

    }
}
