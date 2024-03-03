package io.softwarebuilding.fusionplex.controller;

import io.softwarebuilding.fusionplex.dto.GenreDto;
import io.softwarebuilding.fusionplex.service.GenreService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@SessionAttributes("genre")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController( final GenreService genreService ) {
        this.genreService = genreService;
    }

    @GetMapping("/genre/manage")
    public String showManageGenrePage( final Model model ) {
        model.addAttribute( "genres", this.genreService.findAll() );

        return "manageGenres";
    }

    @GetMapping("/genre/create")
    public String showCreateGenrePage( final Model model, final HttpSession session ) {

        final GenreDto dto = session.getAttribute( "genre" ) != null ? (GenreDto) session.getAttribute( "genre" ) : new GenreDto();

        this.prepareModelForTitleForm( model, dto, false );

        return "createEditGenre";
    }

    @GetMapping("/genre/edit/{id}")
    public String showEditGenrePage( @PathVariable final UUID id, final Model model ) {
        final GenreDto dto = this.genreService.findById( id );

        this.prepareModelForTitleForm( model, dto, true );

        return "createEditGenre";
    }

    @PostMapping("/genre/edit/{id}")
    public String processEditGenre( @PathVariable final UUID id, final Model model ) {

        final GenreDto dto = this.genreService.findById( id );

        this.prepareModelForTitleForm( model, dto, true );

        return "createEditGenre";
    }

    @PostMapping("/genre/save")
    public String saveGenre( @Valid @ModelAttribute("genre") final GenreDto dto,
                             final BindingResult result,
                             final RedirectAttributes redirectAttributes,
                             final SessionStatus sessionStatus,
                             final HttpSession session ) {

        if ( result.hasErrors() ) {
            session.setAttribute( "errors", true );
            return "createEditGenre";
        }

        this.genreService.saveGenre( dto );

        sessionStatus.setComplete();
        session.removeAttribute( "genre" );
        session.removeAttribute( "errors" );

        redirectAttributes.addFlashAttribute( "alertMessage", "Genre saved successfully" );
        redirectAttributes.addFlashAttribute( "alertType", "success" );

        return "redirect:/genre/manage";
    }

    @PostMapping("/genre/delete/{id}")
    public String deleteMovie( @PathVariable final UUID id, final RedirectAttributes redirectAttributes ) {
        this.genreService.delete( id );

        redirectAttributes.addFlashAttribute( "alertMessage", "Genre deleted successfully" );
        redirectAttributes.addFlashAttribute( "alertType", "success" );


        return "redirect:/genre/manage";
    }

    private void prepareModelForTitleForm( final Model model, final GenreDto dto, final boolean editMode ) {

        final String pageTitle = editMode ? "Edit Genre" : "Add New Genre";

        model.addAttribute( "genre", dto );
        model.addAttribute( "pageTitle", pageTitle );
        model.addAttribute( "formAction", "/genre/save" );
    }

}
