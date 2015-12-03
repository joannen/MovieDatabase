package se.joafre.movieservice.service;

import se.joafre.movieservice.model.Movie;
import se.joafre.movieservice.repository.MovieRepository;

import java.util.List;

/**
 * Created by joanne on 02/12/15.
 */
public final class MovieService{

    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public List<Movie> getAll() {
        return movieRepository.getAllMovies();
    }

    public void persist(Movie movie) {
        movieRepository.persistMovie(movie);

    }

    public int update(Movie movie) {
        return movieRepository.updateMovie(movie);
    }

    public int delete(int id) {
        return movieRepository.deleteMovie(id);
    }

    public int addGenre(String genre){
        return movieRepository.addGenre(genre);
    }

    public Movie getMovieById(int id){
        return movieRepository.getMovieById(id);
    }
}
