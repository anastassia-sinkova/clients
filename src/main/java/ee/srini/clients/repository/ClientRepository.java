package ee.srini.clients.repository;

import ee.srini.clients.domain.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
