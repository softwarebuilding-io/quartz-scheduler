package io.softwarebuilding.fusionplex.client;

import io.softwarebuilding.fusionplex.client.models.error.ErrorResponse;
import io.softwarebuilding.fusionplex.client.models.genre.Genre;
import io.softwarebuilding.fusionplex.client.models.genre.GenrePage;
import io.softwarebuilding.fusionplex.client.models.movie.Movie;
import io.softwarebuilding.fusionplex.client.models.movie.MoviesPage;
import io.softwarebuilding.fusionplex.client.util.ApiUtil;
import io.softwarebuilding.fusionplex.error.FusionPlexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClientApiService {

    private final ClientApi clientApi;

    @Autowired
    public ClientApiService(
            final Retrofit retrofit) {
        this.clientApi = retrofit.create(ClientApi.class);}

    public List<Movie> getLatestMovies() {
        try {
            Response<MoviesPage> response = this.clientApi.getLatestMovies().execute();

            if (response.isSuccessful()) {
                return response.body() != null ? response.body().getMovies() : new ArrayList<>();
            } else {
                ErrorResponse errorResponse = ApiUtil.parseError(response);
                throw new FusionPlexException("Failed to fetch latest movies: " + errorResponse.getStatusMessage());
            }
        } catch (final IOException exception) {

            throw new FusionPlexException("Error getting latest movies", exception);
        }
    }

    public List<Genre> getGenres() {
        try {
            Response<GenrePage> response = this.clientApi.getMovieGenres().execute();

            if (response.isSuccessful()) {
                return response.body() != null ? response.body().getGenres() : new ArrayList<>();
            } else {
                ErrorResponse errorResponse = ApiUtil.parseError(response);
                throw new FusionPlexException("Failed to fetch genres: " + errorResponse.getStatusMessage());
            }

        } catch (final IOException exception) {

            throw new FusionPlexException("Error getting movie genres", exception);
        }
    }
}
