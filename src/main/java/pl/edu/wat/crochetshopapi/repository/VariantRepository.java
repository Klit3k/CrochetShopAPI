package pl.edu.wat.crochetshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wat.crochetshopapi.model.Variant;

import java.util.Optional;

public interface VariantRepository extends CrudRepository<Variant, Long> {
    Optional<Variant> findByName(String name);
}
