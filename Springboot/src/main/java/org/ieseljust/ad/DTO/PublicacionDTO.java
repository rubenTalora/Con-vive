package org.ieseljust.ad.DTO;

import lombok.Data;
import org.ieseljust.ad.Model.Publicacion;

@Data
public class PublicacionDTO {

    private Long id;
    
    private String nom;
    
    private String localitat;
    
    private int distancia;

    private int num_participants;

    public static PublicacionDTO convertToDTO(Publicacion Publicacion) {

        PublicacionDTO PublicacionDTO = new PublicacionDTO();

        PublicacionDTO.setId(Publicacion.getId());
        PublicacionDTO.setNom(Publicacion.getNom());
        PublicacionDTO.setLocalitat(Publicacion.getLocalitat());
        PublicacionDTO.setDistancia(Publicacion.getDistancia());
        PublicacionDTO.setNum_participants(Publicacion.getNum_participants());

        return PublicacionDTO;
    }
}
