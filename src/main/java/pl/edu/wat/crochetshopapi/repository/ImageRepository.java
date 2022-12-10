package pl.edu.wat.crochetshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wat.crochetshopapi.model.Image;

import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image, Long> {
    Optional<Image> findByName(String name);
}
