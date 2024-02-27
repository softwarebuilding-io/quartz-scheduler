package io.softwarebuilding.fusionplex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "genre")
public class Genre extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -375008015950096305L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Title> titles = new HashSet<>();

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

    public Set<Title> getMovies() {
        return this.titles;
    }

    public void setMovies( final Set<Title> titles ) {
        this.titles = titles;
    }

    public void map( final Genre genre ) {
        if ( this.id == null ) {
            throw new IllegalArgumentException( "ID cannot be null" );
        }

        if ( genre.getName() != null || !genre.getName().equalsIgnoreCase( this.name ) ) {
            this.name = genre.getName();
        }
    }

}
