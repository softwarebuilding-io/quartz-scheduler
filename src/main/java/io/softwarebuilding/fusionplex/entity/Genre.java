package io.softwarebuilding.fusionplex.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
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

    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
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

    public Set<Title> getTitles() {
        return this.titles;
    }

    public void setTitles( final Set<Title> titles ) {
        this.titles = titles;
    }

    public void map( final Genre genre ) {
        if ( this.id == null ) {
            throw new IllegalArgumentException( "ID cannot be null" );
        }

        if ( genre.getName() != null || !genre.getName().equalsIgnoreCase( this.name ) ) {
            this.name = genre.getName();
        }

        if ( genre.getTitles() != null || !genre.getTitles().equals( this.titles ) ) {
            this.addAllTitle(genre.getTitles());
        }
    }

    public void addTitle(final Title title) {
        this.titles.add( title );
        title.addGenre(this);
    }

    public void addAllTitle(final Set<Title> titles) {
        titles.forEach(title -> title.addGenre(this));
        this.titles.addAll(titles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
