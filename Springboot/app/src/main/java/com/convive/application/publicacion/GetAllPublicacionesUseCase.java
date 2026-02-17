package Springboot.app.src.main.java.com.convive.application.publicacion;

import com.convive.domain.model.Publicacion;
import com.convive.domain.repository.PublicacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllPublicacionesUseCase {

    private final PublicacionRepository repository;

    public GetAllPublicacionesUseCase(PublicacionRepository repository) {
        this.repository = repository;
    }

    public List<Publicacion> execute() {
        return repository.findAll();
    }
}