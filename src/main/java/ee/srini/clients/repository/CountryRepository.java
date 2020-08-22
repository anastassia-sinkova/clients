package ee.srini.clients.repository;

import ee.srini.clients.domain.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, String> {
}
