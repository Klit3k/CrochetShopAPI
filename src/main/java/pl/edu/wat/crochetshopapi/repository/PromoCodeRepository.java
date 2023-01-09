package pl.edu.wat.crochetshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wat.crochetshopapi.model.PromoCode;

import java.util.Optional;

public interface PromoCodeRepository extends CrudRepository<PromoCode, Long> {
    Optional<PromoCode> findByCode(String code);
}
