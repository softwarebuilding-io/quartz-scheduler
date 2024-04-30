package io.softwarebuilding.fusionplex.service;

import io.softwarebuilding.fusionplex.dto.GenreDto;
import io.softwarebuilding.fusionplex.entity.Genre;
import io.softwarebuilding.fusionplex.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService( final GenreRepository genreRepository ) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> findAll() {
        return this.genreRepository.findAll().stream()
                .map( category -> this.getModelMapper().map( category, GenreDto.class ) ).toList();
    }

    public GenreDto findById( final UUID id ) {
        return this.genreRepository.findById( id ).map( genre -> this.getModelMapper().map( genre, GenreDto.class ) )
                .orElseThrow( () -> new ResponseStatusException( HttpStatus.NOT_FOUND, "Genre not Found!" ) );
    }

    public GenreDto findByName(final String name) {
        return this.genreRepository.findByName(name).map(genre -> this.getModelMapper().map(genre, GenreDto.class))
                .orElseGet(() -> {
                    final GenreDto dto = new GenreDto();
                    dto.setName(name);

                    return dto;
                });
    }

    public void saveGenre(final GenreDto dto) {

        this.genreRepository.findByName(dto.getName()).ifPresentOrElse(genre -> {
            genre.map(this.getModelMapper().map(dto, Genre.class));
            this.genreRepository.save(genre);
        }, () -> this.genreRepository.save(this.getModelMapper().map(dto, Genre.class)));
    }

    public Genre saveGenreEntity(final Genre model) {
        return this.genreRepository.findByName(model.getName()).map(genre -> {
            genre.map(model);
            return this.genreRepository.saveAndFlush(genre);
        }).orElseGet(() -> this.genreRepository.saveAndFlush(model));
    }

    public void delete( final UUID id ) {
        this.genreRepository.delete( this.getModelMapper().map( this.findById( id ), Genre.class ) );
    }

    private ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
