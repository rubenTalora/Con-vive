package org.ieseljust.ad.Controller;

import java.util.List;

import org.ieseljust.ad.DTO.PublicacionDTO;
import org.ieseljust.ad.Service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class PublicacionController {
	
	@Autowired
	PublicacionService PublicacionService;
    
	@GetMapping("/Publicacions")
    public ResponseEntity<List<PublicacionDTO>> getAllCategories() {

        List<PublicacionDTO> laspublicaciones = PublicacionService.listAllPublicacions();

        if (laspublicaciones != null)
            return new ResponseEntity<>(laspublicaciones, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

	@GetMapping("/Publicacion/id/{idPublicacion}")
    public ResponseEntity<PublicacionDTO> getPublicacion(@PathVariable Long idPublicacion) {
        PublicacionDTO cdto = PublicacionService.getPublicacionById(idPublicacion);

        if (cdto != null)
            return new ResponseEntity<>(cdto, HttpStatus.OK);
		else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/Publicacions/localitat/{name}")
    public ResponseEntity<List<PublicacionDTO>> getPublicacion(@PathVariable String name) {
        List<PublicacionDTO> laspublicaciones = PublicacionService.getPublicacionByLocalitat(name);

        if (laspublicaciones != null)
            return new ResponseEntity<>(laspublicaciones, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleError(MethodArgumentTypeMismatchException e) {
        //myLog.warn("Method Argument Type Mismatch", e);
        String message = String.format("El format de l'argument no és correcte: %s", e.getName());
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
