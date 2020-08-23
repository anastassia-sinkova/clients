package ee.srini.clients.service;

import ee.srini.clients.domain.Client;
import ee.srini.clients.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;

    public Iterable<Client> getAllClients() {
        return clientRepository.findByOwner(getCurrentUserUsername());
    }

    public Client getClientById(Long id) {
        String currentUser = getCurrentUserUsername();

        Optional<Client> result = clientRepository.findByIdAndOwner(id, currentUser);

        return result.orElseThrow(() ->
                new RuntimeException("Client with id " + id + " not found for user " + currentUser));
    }

    public Client upsertClient(Client client) {
        String currentUser = getCurrentUserUsername();
        Long clientId = client.getId();

        if (clientId != null) {
            Optional<Client> result = clientRepository.findByIdAndOwner(clientId, currentUser);

            if (result.isEmpty()) {
                throw new RuntimeException("User " + currentUser + " tried to modify client " + clientId);
            }
        }

        client.setOwner(currentUser);

        return clientRepository.save(client);
    }

    private String getCurrentUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ((User) principal).getUsername();
    }
}
