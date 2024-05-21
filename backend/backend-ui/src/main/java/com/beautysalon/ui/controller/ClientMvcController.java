package com.beautysalon.ui.controller;

import com.beautysalon.domain.entities.Client;
import com.beautysalon.domain.services.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "noBlocked", required = false, defaultValue = "false") boolean noBlocked,
            Model model,
            HttpServletRequest request
    ) {
        final List<Client> clients = clientService.getAll(!noBlocked);
        model.addAttribute("clients", clients);
        model.addAttribute("currentUri", request.getRequestURI());
        return "pages/clients";
    }

    @GetMapping("/clients/block")
    public String clientsBlockGet(@RequestParam("id") Long id) {
        clientService.setBlock(id, true);
        return "redirect:/clients";
    }

    @GetMapping("/clients/unblock")
    public String clientsUnblockGet(@RequestParam("id") Long id) {
        clientService.setBlock(id, false);
        return "redirect:/clients";
    }

    @DeleteMapping("/clients")
    public String clientsDelete(@RequestParam("id") Long id) {
        clientService.deleteById(id);
        return "redirect:/clients";
    }
}
