package io.softwarebuilding.fusionplex.converter;

import io.softwarebuilding.fusionplex.dto.GenreDto;
import io.softwarebuilding.fusionplex.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToGenreDtoConverter implements Converter<String, GenreDto> {

    private final GenreService genreService;

    @Autowired
    public StringToGenreDtoConverter( final GenreService genreService ) {
        this.genreService = genreService;
    }

    @Override
    public GenreDto convert( @NonNull final String id ) {
        final UUID genreId = UUID.fromString( id );

        return this.genreService.findById( genreId );
    }

}
