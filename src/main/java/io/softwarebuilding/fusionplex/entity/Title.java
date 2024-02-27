package io.softwarebuilding.fusionplex.entity;

import io.softwarebuilding.fusionplex.enums.ElementType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "title")
public class Title extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1847803474984964052L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "release_year", nullable = false)
    private Long releaseYear;

    @Column(name = "type", nullable = false)
    private ElementType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "title_genre",
            joinColumns = @JoinColumn(name = "title_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_title_genre")),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_genre_title")))
    private Set<Genre> genres = new HashSet<>();

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

    public Long getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear( final Long releaseYear ) {
        this.releaseYear = releaseYear;
    }

    public ElementType getType() {
        return this.type;
    }

    public void setType( final ElementType type ) {
        this.type = type;
    }

    public Set<Genre> getGenres() {
        return this.genres;
    }

    public void setGenres( final Set<Genre> categories ) {
        this.genres = categories;
    }

    public void map( final Title title ) {
        if ( this.id == null ) {
            throw new IllegalArgumentException( "ID cannot be null" );
        }

        if ( title.getName() != null || !title.getName().equalsIgnoreCase( this.name ) ) {
            this.name = title.getName();
        }

        if ( title.getDescription() != null || !title.getDescription().equalsIgnoreCase( this.description ) ) {
            this.description = title.getDescription();
        }

        if ( title.getReleaseYear() != null || !Objects.equals( title.getReleaseYear(), this.releaseYear ) ) {
            this.releaseYear = title.getReleaseYear();
        }

        if ( title.getType() != null || !title.getType().equals( this.type ) ) {
            this.type = title.getType();
        }

        if ( title.getGenres() != null || !title.getGenres().isEmpty() ) {
            this.genres = title.getGenres();
        }

    }

}
