package io.softwarebuilding.fusionplex.client;

import io.softwarebuilding.fusionplex.client.models.genre.GenrePage;
import io.softwarebuilding.fusionplex.client.models.movie.MoviesPage;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientApi {

    @GET("movie/now_playing")
    Call<MoviesPage> getLatestMovies();

    @GET("genre/movie/list")
    Call<GenrePage> getMovieGenres();
}
