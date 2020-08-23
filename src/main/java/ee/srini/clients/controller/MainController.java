package ee.srini.clients.controller;

import ee.srini.clients.domain.Client;
import ee.srini.clients.service.ClientService;
import ee.srini.clients.service.CountryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@Slf4j
public class MainController {

    private CountryService countryService;

    private ClientService clientService;

    @GetMapping("/")
    public String index(@RequestParam(required = false) String loggedInUserId, Model model) {
        log.debug("Listing all clients");

        model.addAttribute("clients", clientService.getAllClients());

        return "index";
    }

    @GetMapping("/client")
    public String client(@RequestParam(required = false) Long clientId, Model model) {
        log.debug("Showing client with id {}", clientId);

        Client client = clientId != null
                ? clientService.getClientById(clientId)
                : new Client();

        model.addAttribute("client", client);
        model.addAttribute("countries", countryService.getAllCountries());

        return "client";
    }

    @PostMapping("/")
    public String upsertClient(@ModelAttribute @Valid Client client, BindingResult bindingResult, Model model) {
        log.debug("Posting client with id {}", client.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("countries", countryService.getAllCountries());

            return "client";
        }

        String message = client.getId() == null
                ? "Client was successfully created"
                : "Client was successfully updated";

        clientService.upsertClient(client);

        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("message", message);

        return "index";
    }
}
