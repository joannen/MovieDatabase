package se.joafre.movieservice;

import se.joafre.movieservice.model.Movie;
import se.joafre.movieservice.repository.MovieRepository;
import se.joafre.movieservice.repository.MovieRepositoryImpl;
import se.joafre.movieservice.service.MovieService;

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

        final String connectionString = "jdbc:mysql://localhost/MovieServiceDatabase?user=root&password=hannele1";
        final String sql = "SELECT Movie.id, title, productionYear, name  from Movie JOIN Genre" +
                            " ON Movie.genreId= Genre.id";
        List<Movie> movieList = new Query<Movie>(connectionString).query(sql).mapper(movieMapper).execute();

        for(Movie movie: movieList){
            System.out.println(movie.toString());
        }



//        MovieRepository movieRepository = new MovieRepositoryImpl();
//        MovieService movieService = new MovieService(movieRepository);
//        Movie movie = new Movie("Back to the Future", 1985, "action");
//        Movie movie1 = new Movie(1, "Back to the Past", 1985, "action");
////        movieService.persist(movie);
////        movieService.getAll();
//        movieService.update(movie1);
////        movieService.getAll();
//        movieService.delete(1);
////        movieService.getAll();
////        movieService.addGenre("comedy");
//        Movie movie2 = movieService.getMovieById(2);
//        System.out.println(movie2.toString());




    }
}
