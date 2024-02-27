package io.softwarebuilding.fusionplex.controller;

import io.softwarebuilding.fusionplex.dto.GenreDto;
import io.softwarebuilding.fusionplex.dto.TitleDto;
import io.softwarebuilding.fusionplex.enums.ElementType;
import io.softwarebuilding.fusionplex.enums.EnumValue;
import io.softwarebuilding.fusionplex.service.GenreService;
import io.softwarebuilding.fusionplex.service.TitleService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("title")
public class TitleController {

    private final TitleService titleService;

    private final GenreService genreService;

    @Autowired
    public TitleController( final TitleService titleService, final GenreService genreService ) {
        this.titleService = titleService;
        this.genreService = genreService;
    }

    @GetMapping("/title/manage")
    public String showManageTitlePage( final Model model ) {
        final List<TitleDto> titles = this.titleService.findAll();

        model.addAttribute( "titles", titles );

        return "manageTitles";
    }

    @GetMapping("/title/create")
    public String showCreateTitlePage( final Model model, final HttpSession session ) {

        final TitleDto dto = session.getAttribute( "title" ) != null
                ? (TitleDto) session.getAttribute( "title" ) : new TitleDto();

        this.prepareModelForTitleForm( model, dto, false );

        return "createEditTitle";
    }

    @GetMapping("/title/edit/{id}")
    public String showEditTitlePage(
            @PathVariable final UUID id, final Model model ) {
        final TitleDto dto = this.titleService.findById( id );

        this.prepareModelForTitleForm( model, dto, true );

        return "createEditTitle";
    }

    @PostMapping("/title/edit/{id}")
    public String processEditTitle(
            @PathVariable final UUID id, final Model model ) {

        final TitleDto dto = this.titleService.findById( id );

        this.prepareModelForTitleForm( model, dto, true );

        return "createEditTitle";
    }

    @PostMapping("/title/save")
    public String saveTitle( @Valid @ModelAttribute("title") final TitleDto dto,
                             final Model model, final BindingResult result,
                             final RedirectAttributes redirectAttributes,
                             final SessionStatus sessionStatus,
                             final HttpSession session ) {

        final boolean editMode = dto.getId() != null;

        if ( result.hasErrors() ) {
            this.prepareModelForTitleForm( model, dto, editMode );

            return "createEditTitle";
        }

        this.titleService.saveTitle( dto );

        redirectAttributes.addFlashAttribute( "alertMessage", "Movie saved successfully" );
        redirectAttributes.addFlashAttribute( "alertType", "success" );

        sessionStatus.setComplete();
        session.invalidate();

        return "redirect:/title/manage";
    }

    @PostMapping("/title/delete/{id}")
    public String deleteMovie( @PathVariable final UUID id, final RedirectAttributes redirectAttributes ) {
        this.titleService.delete( id );

        redirectAttributes.addFlashAttribute( "alertMessage", "Movie deleted successfully" );
        redirectAttributes.addFlashAttribute( "alertType", "success" );


        return "redirect:/title/manage";
    }

    private List<GenreDto> findAllGenres() {
        return this.genreService.findAll();
    }

    private void prepareModelForTitleForm( final Model model, final TitleDto dto, final boolean editMode ) {
        final List<GenreDto> genres = this.findAllGenres();

        final List<UUID> selectedGenreIds = dto.getGenres() != null
                ? dto.getGenres().stream().map( GenreDto::getId ).collect( Collectors.toList() ) : new ArrayList<>();

        final EnumValue selectedType = dto.getTypeValue();

        final String pageTitle = editMode ? "Edit Title" : "Add New Title";

        model.addAttribute( "selectedGenreIds", selectedGenreIds );
        model.addAttribute( "types", ElementType.getValues() );
        model.addAttribute( "selectedType", selectedType );
        model.addAttribute( "title", dto );
        model.addAttribute( "genres", genres );
        model.addAttribute( "pageTitle", pageTitle );
        model.addAttribute( "formAction", "/title/save" );
    }

}