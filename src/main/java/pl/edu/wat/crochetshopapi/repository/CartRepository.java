package pl.edu.wat.crochetshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.crochetshopapi.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
}
