package ee.srini.clients.service;

import ee.srini.clients.domain.Client;
import ee.srini.clients.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private ClientRepository clientRepository;

    public Iterable<Client> getAllClients() {
        return clientRepository.findByOwner(getCurrentUserUsername());
    }

    public Client getClientById(Long clientId) {
        String currentUser = getCurrentUserUsername();

        Optional<Client> client = clientRepository.findByIdAndOwner(clientId, currentUser);

        if (client.isEmpty()) {
            log.info("User {} tried to access client with id {}", currentUser, clientId);
            throw new RuntimeException("Client with id " + clientId + " not found");
        }

        return client.get();
    }

    public Client upsertClient(Client client) {
        String currentUser = getCurrentUserUsername();
        Long clientId = client.getId();

        if (clientId != null) {
            Optional<Client> existingClient = clientRepository.findByIdAndOwner(clientId, currentUser);

            if (existingClient.isEmpty()) {
                log.warn("User {} tried to modify client with id {}", currentUser, clientId);
                throw new RuntimeException("Client with id " + clientId + "can not be modified");
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
