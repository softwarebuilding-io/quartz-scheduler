package io.softwarebuilding.fusionplex.service;

import io.softwarebuilding.fusionplex.client.ClientApiService;
import io.softwarebuilding.fusionplex.client.models.genre.Genre;
import io.softwarebuilding.fusionplex.dto.TitleDto;
import io.softwarebuilding.fusionplex.enums.ElementType;
import io.softwarebuilding.fusionplex.util.FusionPlexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientApiService clientApiService;

    private final TitleService titleService;

    private final GenreService genreService;

    @Autowired
    public ClientService(
            final ClientApiService clientApiService, final TitleService titleService, final GenreService genreService) {
        this.clientApiService = clientApiService;
        this.titleService = titleService;
        this.genreService = genreService;
    }

    public void updateLatestPlayingMovies() {
        this.clientApiService.getLatestMovies().forEach(movie -> {
            final TitleDto titleDto = this.titleService.findByName(movie.getTitle());
            titleDto.setName(movie.getTitle());
            titleDto.setDescription(movie.getOverview());
            titleDto.setType(ElementType.MOVIE);
            titleDto.setReleaseYear(
                    FusionPlexUtil.convertStringToLocalDate(movie.getReleaseDate(), "yyyy-MM-dd").getYear());

            final List<Genre> genres = this.clientApiService.getGenres().stream()
                    .filter(genre -> movie.getGenreIds().contains(genre.getId())).toList();

            titleDto.getGenres().addAll(genres
                    .stream().map(genre -> this.genreService.findByName(genre.getName())).toList());

            this.titleService.saveTitle(titleDto);
        });
    }

}
