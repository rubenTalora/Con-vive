package org.ieseljust.ad.DTO;

import org.ieseljust.ad.Model.Categoria;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
public class CategoriaDTO {

    private Long id;

    private String nom;

    private String descripcio;

    @JsonManagedReference
    public static CategoriaDTO convertToDTO(Categoria categoria) {

        CategoriaDTO categoriaDTO = new CategoriaDTO();

        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNom(categoria.getNom());
        categoriaDTO.setDescripcio(categoria.getDescripcio());

        return categoriaDTO;

    }

    public static Categoria convertToEntity(CategoriaDTO categoriaDTO) {

        Categoria categoria = new Categoria();

        categoria.setId(categoriaDTO.getId());
        categoria.setNom(categoriaDTO.getNom());
        categoria.setDescripcio(categoriaDTO.getDescripcio());

        return categoria;

    }

}
