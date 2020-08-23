package ee.srini.clients.controller.rest;

import ee.srini.clients.domain.Client;
import ee.srini.clients.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "clients")
//@RequestMapping(value = "clients", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClientRestController {

    private ClientService clientService;

    @GetMapping
    public Iterable<Client> getAll() {
        return clientService.getAllClients(); //TODO: logger
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public Client addClient(@Valid @RequestBody Client client) {
        return clientService.upsertClient(client);
    }
}
