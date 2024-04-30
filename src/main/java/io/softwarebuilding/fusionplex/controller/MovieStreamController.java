package io.softwarebuilding.fusionplex.controller;

import io.softwarebuilding.fusionplex.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MovieStreamController {

    private final ClientService clientService;

    @Autowired
    public MovieStreamController(final ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/movie/updateLatestPlaying")
    public String updatedLatestPlayingMovies(final Model model) {
        this.clientService.updateLatestPlayingMovies();

        model.addAttribute("alertMessage", "Latest's playing movies updated");
        model.addAttribute( "alertType", "success" );

        return "redirect:/title/manage";
    }


}
