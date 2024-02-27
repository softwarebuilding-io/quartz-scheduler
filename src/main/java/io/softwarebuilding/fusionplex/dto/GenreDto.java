package io.softwarebuilding.fusionplex.dto;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class GenreDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7968351250055759882L;

    private UUID id;

    @NotEmpty(message = "Name is required")
    private String name;

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

}
