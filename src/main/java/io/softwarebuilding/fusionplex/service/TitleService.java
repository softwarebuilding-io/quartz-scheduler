package io.softwarebuilding.fusionplex.service;

import io.softwarebuilding.fusionplex.dto.TitleDto;
import io.softwarebuilding.fusionplex.entity.Genre;
import io.softwarebuilding.fusionplex.entity.Title;
import io.softwarebuilding.fusionplex.repository.TitleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TitleService {

    private final TitleRepository titleRepository;

    private final GenreService genreService;

    @Autowired
    public TitleService( final TitleRepository titleRepository, final GenreService genreService ) {
        this.titleRepository = titleRepository;
        this.genreService = genreService;
    }

    public List<TitleDto> findAll() {
        return this.titleRepository.findAll().stream()
                .map( title -> this.getModelMapper().map( title, TitleDto.class ) ).toList();
    }

    public TitleDto findById( final UUID id ) {
        return this.titleRepository.findById( id ).map( title -> this.getModelMapper().map( title, TitleDto.class ) )
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND, "Movie not Found!" ) );
    }

    public TitleDto findByName(final String name) {
        return this.titleRepository.findByNameContainingIgnoreCase(name)
                .map(title -> this.getModelMapper().map(title, TitleDto.class))
                .orElse(new TitleDto());
    }

    public void saveTitle( final TitleDto dto ) {

        final Title title;

        if ( dto.getId() != null ) {
            title = this.titleRepository.findById( dto.getId() ).map( existingTitle -> {
                existingTitle.map( this.getModelMapper().map( dto, Title.class ) );
                return existingTitle;
            } ).orElseGet( () -> this.getModelMapper().map( dto, Title.class ) );

        } else {
            title = this.getModelMapper().map( dto, Title.class );
        }

        final Set<Genre> genres = title.getGenres().stream()
                .map(this.genreService::saveGenreEntity).collect(Collectors.toSet());

        title.addAllGenres(genres);

        this.titleRepository.save( title );
    }

    public void delete( final UUID id ) {
        this.titleRepository.delete( this.getModelMapper().map( this.findById( id ), Title.class ) );
    }

    private ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
