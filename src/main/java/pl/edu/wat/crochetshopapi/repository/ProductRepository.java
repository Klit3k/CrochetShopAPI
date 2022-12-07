package pl.edu.wat.crochetshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.crochetshopapi.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
