package io.softwarebuilding.fusionplex.converter;

import io.softwarebuilding.fusionplex.enums.ElementType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ElementTypeConverter implements Converter<String, ElementType> {

    @Override
    public ElementType convert( @NonNull final String source ) {
        try {
            final Long id = Long.parseLong( source );

            return ElementType.valueOf( id );
        } catch ( final NumberFormatException exception ) {
            return null;
        }
    }

}
