package se.joafre.movieservice;

import se.joafre.movieservice.model.Actor;
import se.joafre.movieservice.model.Movie;
import se.joafre.movieservice.repository.MovieRepository;
import se.joafre.movieservice.repository.MovieRepositoryImpl;
import se.joafre.movieservice.service.MovieService;
import se.joafre.movieservice.utility.Mapper;
import se.joafre.movieservice.utility.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by joanne on 02/12/15.
 */
public final class Runner {



    public static void main(String[] args) {

        MovieRepository movieRepository = new MovieRepositoryImpl();
        MovieService ms = new MovieService(movieRepository);

        ms.persist(new Movie("wat", 1999, "Action"));

        for (Movie movie : ms.getAllMovies()) {
            System.out.println(movie);
        }

        Movie m = new Movie("wat", 1999, "action");

        System.out.println(ms.getAllMovies().contains(m));



    }
}
