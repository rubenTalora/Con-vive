package org.ieseljust.ad.Service;

import org.ieseljust.ad.DTO.PublicacionDTO;
import org.ieseljust.ad.DTO.CategoriaDTO;
import org.ieseljust.ad.Repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    PublicacionRepository PublicacionRepository;
    

    @Override
    public PublicacionDTO getPublicacionById(Long id) {

        Optional<Publicacion> c = PublicacionRepository.findById(id);

        if (c.isPresent()) {
            return PublicacionDTO.convertToDTO(c.get());
        }

        return null;
    }

    @Override
    public List<PublicacionDTO> getPublicacionByLocalitat(String n) {

        List<Publicacion> publicaciones = PublicacionRepository.findByLocalitatContainsIgnoreCase(n);

        List<PublicacionDTO> laspublicacionesDTO = new ArrayList<>();

        //Conversio de Publicacion a PublicacionDTO
        for (Publicacion c : publicaciones)
        {
            laspublicacionesDTO.add(PublicacionDTO.convertToDTO(c));
        }

        return laspublicacionesDTO;
    }

    @Override
    public List<PublicacionDTO> listAllPublicacions() {
        List<Publicacion> publicaciones = PublicacionRepository.findAll();

        List<PublicacionDTO> laspublicacionesDTO = new ArrayList<>();

        //Conversio de Publicacion a PublicacionDTO
        for (Publicacion c : publicaciones)
        {
            laspublicacionesDTO.add(PublicacionDTO.convertToDTO(c));
        }

        return laspublicacionesDTO;
    }

}
