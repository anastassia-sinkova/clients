package ee.srini.clients.repository;

import ee.srini.clients.domain.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Iterable<Client> findByOwner(String owner);

    Optional<Client> findByIdAndOwner(Long id, String currentUserUsername);
}
