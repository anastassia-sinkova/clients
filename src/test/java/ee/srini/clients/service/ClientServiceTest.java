package ee.srini.clients.service;

import ee.srini.clients.domain.Client;
import ee.srini.clients.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class ClientServiceTest {
    @InjectMocks
    @Spy
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private static final long CLIENT_ID = 1L;

    private static final String RIGHTFUL_OWNER = "user1";
    private static final String OTHER_PERSON = "user2";

    private static final String ADDRESS = "non empty address";

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void upsertClient_existingClientSameOwner() {
        Client input = Client.builder()
                .id(CLIENT_ID)
                .owner(RIGHTFUL_OWNER)
                .address(ADDRESS)
                .build();

        Client existingClient = Client.builder()
                .id(CLIENT_ID)
                .owner(RIGHTFUL_OWNER)
                .build();

        when(clientRepository.findByIdAndOwner(CLIENT_ID, RIGHTFUL_OWNER)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(input)).thenReturn(input);

        doReturn(RIGHTFUL_OWNER).when(clientService).getCurrentUserUsername();

        Client actual = clientService.upsertClient(input);

        Client expected = Client.builder()
                .id(CLIENT_ID)
                .owner(RIGHTFUL_OWNER)
                .address(ADDRESS)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void upsertClient_existingClientOtherOwner() {
        Client input = Client.builder()
                .id(CLIENT_ID)
                .owner(RIGHTFUL_OWNER)
                .address(ADDRESS)
                .build();

        when(clientRepository.findByIdAndOwner(CLIENT_ID, OTHER_PERSON)).thenReturn(Optional.empty());

        doReturn(OTHER_PERSON).when(clientService).getCurrentUserUsername();

        try {
            clientService.upsertClient(input);
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
            assertEquals("Client with id " + CLIENT_ID + " can not be modified", e.getMessage());
        }
    }

    @Test
    public void upsertClient_newClient() {
        Client input = Client.builder()
                .owner(RIGHTFUL_OWNER)
                .address(ADDRESS)
                .build();

        when(clientRepository.save(input)).thenReturn(input);

        doReturn(RIGHTFUL_OWNER).when(clientService).getCurrentUserUsername();

        Client actual = clientService.upsertClient(input);

        Client expected = Client.builder()
                .owner(RIGHTFUL_OWNER)
                .address(ADDRESS)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void getClientById_existingClient() {
        Client existingClient = Client.builder()
                .owner(RIGHTFUL_OWNER)
                .address(ADDRESS)
                .build();

        when(clientRepository.findByIdAndOwner(CLIENT_ID, RIGHTFUL_OWNER)).thenReturn(Optional.of(existingClient));

        doReturn(RIGHTFUL_OWNER).when(clientService).getCurrentUserUsername();

        Client actual = clientService.getClientById(CLIENT_ID);

        assertEquals(existingClient, actual);
    }

    @Test
    public void getClientById_clientNotFound() {
        when(clientRepository.findByIdAndOwner(CLIENT_ID, RIGHTFUL_OWNER)).thenReturn(Optional.empty());

        doReturn(RIGHTFUL_OWNER).when(clientService).getCurrentUserUsername();

        try {
            clientService.getClientById(CLIENT_ID);
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
            assertEquals("Client with id " + CLIENT_ID + " not found", e.getMessage());
        }
    }
}