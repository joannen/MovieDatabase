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

    void persistMovie(Movie movie);

    int updateMovie(Movie movie);

    int deleteMovie(int id);

    List<Actor> getAllActors();

    void persistActor(Actor actor);

    int updateActor(Actor actor);

    int deleteActor(String id);

    int addGenre(String genre);

}
