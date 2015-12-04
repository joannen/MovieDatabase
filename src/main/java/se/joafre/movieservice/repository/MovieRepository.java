package se.joafre.movieservice.repository;

import se.joafre.movieservice.model.Actor;
import se.joafre.movieservice.model.Movie;

import java.util.List;

/**
 * Created by joanne on 02/12/15.
 */
public interface MovieRepository {

    List<Movie> getAllMovies();

    Movie getMovieById(int id);

    boolean persistMovie(Movie movie);

    boolean updateMovie(Movie movie);

    boolean deleteMovie(int id);

    List<Actor> getAllActors();

    void persistActor(Actor actor);

    boolean updateActor(Actor actor);

    boolean deleteActor(String id);

    int addGenre(String genre);

}
