package ee.srini.clients.controller.rest;

import ee.srini.clients.domain.Country;
import ee.srini.clients.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("countries")
@AllArgsConstructor
public class CountryRestController {

    private CountryService countryService;

    @GetMapping
    public Iterable<Country> getAll() {
        return countryService.getAllCountries();
    }
}
