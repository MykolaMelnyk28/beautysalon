package com.beautysalon.ui.controller;

import com.beautysalon.domain.entities.Client;
import com.beautysalon.domain.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/panel")
public class ClientMvcController {

    private final ClientService clientService;

    public ClientMvcController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String clientsGet(
            Model model,
            HttpServletRequest request
    ) {
        final List<Client> clients = clientService.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/clients";
    }
}
