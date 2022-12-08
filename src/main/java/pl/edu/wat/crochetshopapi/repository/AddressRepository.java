package pl.edu.wat.crochetshopapi.repository;

import org.springframework.data.repository.CrudRepository;
import pl.edu.wat.crochetshopapi.model.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
