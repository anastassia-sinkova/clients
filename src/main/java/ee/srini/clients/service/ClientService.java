package ee.srini.clients.service;

import ee.srini.clients.domain.Client;
import ee.srini.clients.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        Optional<Client> result = clientRepository.findById(id);

        return result.orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }
}
