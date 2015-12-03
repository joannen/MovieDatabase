package se.joafre.movieservice;

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

    public final static Mapper<Movie> movieMapper = new Mapper<Movie>() {
        @Override
        public Movie map(ResultSet resultSet) throws SQLException {

            return new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),resultSet.getString(4));

        }
    };

    public static void main(String[] args) {

        final String connectionString = "jdbc:mysql://localhost/MovieServiceDatabase?user=root&password=root";
        final String sql = "SELECT Movie.id, title, productionYear, name  from Movie JOIN Genre" +
                            " ON Movie.genreId= Genre.id";
        List<Movie> movieList = new Query<Movie>(connectionString).query(sql).mapper(movieMapper).execute();

        /* for(Movie movie: movieList){
            System.out.println(movie.toString());
        } */



        MovieRepository movieRepository = new MovieRepositoryImpl();
        MovieService movieService = new MovieService(movieRepository);

        for (Movie movie : movieService.getAllMovies()) {
            System.out.println(movie);
        }
        Movie movie = new Movie("Back to the Future", 1985, "action");

        movieService.persist(movie);
        movieService.persist(movie);
        movieService.persist(movie);

        for (Movie movieOne : movieService.getAllMovies()) {
            System.out.println(movieOne);
        }
//        Movie movie1 = new Movie(1, "Back to the Past", 1985, "action");
////        movieService.persist(movie);
////        movieService.getAllMovies();
//        movieService.update(movie1);
////        movieService.getAllMovies();
//        movieService.delete(1);
////        movieService.getAllMovies();
////        movieService.addGenre("comedy");
//        Movie movie2 = movieService.getMovieById(2);
//        System.out.println(movie2.toString());




    }
}
