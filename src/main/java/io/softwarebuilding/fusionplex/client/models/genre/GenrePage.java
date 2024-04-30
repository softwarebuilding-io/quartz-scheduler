package io.softwarebuilding.fusionplex.client.models.genre;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class GenrePage implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -1520727297638710220L;

    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
