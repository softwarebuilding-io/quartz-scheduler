package io.softwarebuilding.fusionplex.dto;

import io.softwarebuilding.fusionplex.enums.ElementType;
import io.softwarebuilding.fusionplex.enums.EnumValue;
import io.softwarebuilding.fusionplex.validator.annotation.NotEmptySet;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TitleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -3421755938506696353L;

    private UUID id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotNull(message = "Type is required")
    private ElementType type;

    @NotNull(message = "Release Year is required")
    private Long releaseYear;

    @NotEmptySet(message = "You must select at least one genre.")
    private Set<GenreDto> genres = new HashSet<>();

    public UUID getId() {
        return this.id;
    }

    public void setId( final UUID id ) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName( final String name ) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( final String description ) {
        this.description = description;
    }


    public Set<GenreDto> getGenres() {
        return this.genres;
    }

    public void setGenres( final Set<GenreDto> genres ) {
        this.genres = genres;
    }

    public String genresValues() {
        final StringBuilder sb = new StringBuilder();
        final AtomicInteger count = new AtomicInteger( 0 );

        this.genres.forEach( genre -> {
            sb.append( genre.getName() );

            if ( count.incrementAndGet() < this.genres.size() ) {
                sb.append( ", " );
            }
        } );

        return sb.toString();
    }

    public ElementType getType() {
        return this.type;
    }

    public void setType( final ElementType type ) {
        this.type = type;
    }

    public EnumValue getTypeValue() {
        return this.type != null ? new EnumValue( this.type.getId(), this.type.getDescription() ) : new EnumValue( 0L, "NONE" );
    }

    public Long getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear( final Long releaseYear ) {
        this.releaseYear = releaseYear;
    }

}
