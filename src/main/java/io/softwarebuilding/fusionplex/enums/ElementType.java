package io.softwarebuilding.fusionplex.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ElementType {

    TV_SHOW( 1L, "TV Show" ),
    MOVIE( 2L, "Movie" );

    private final Long id;

    private final String description;

    ElementType( final Long id, final String description ) {
        this.id = id;
        this.description = description;
    }

    public static ElementType valueOf( final Long id ) {

        for ( final ElementType value : ElementType.values() ) {
            if ( value.getId().equals( id ) ) return value;
        }

        return null;
    }

    public static List<EnumValue> getValues() {
        return Arrays.stream( ElementType.values() ).map( type -> new EnumValue( type.getId(), type.getDescription() ) )
                .collect( Collectors.toList() );
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }
}
