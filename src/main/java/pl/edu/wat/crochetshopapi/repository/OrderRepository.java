package pl.edu.wat.crochetshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wat.crochetshopapi.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
