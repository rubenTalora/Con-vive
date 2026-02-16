package org.ieseljust.ad.Service;

import java.util.List;

import org.ieseljust.ad.DTO.PublicacionDTO;

public interface PublicacionService {

    PublicacionDTO getPublicacionById(Long id);

    List<PublicacionDTO> getPublicacionByLocalitat(String n);
	
    List<PublicacionDTO> listAllPublicacions();

}
