package org.ieseljust.ad.Service;

import org.ieseljust.ad.DTO.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
	
	void saveCategoria(CategoriaDTO categoriaDTO);

    CategoriaDTO getCategoriaById(Long id);

    CategoriaDTO getCategoriaByNom(String n);
	
    List<CategoriaDTO> listAllCategorias();

    
    void deleteCategoria(Long id);

}
