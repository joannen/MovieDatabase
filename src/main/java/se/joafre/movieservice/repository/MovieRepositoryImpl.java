package se.joafre.movieservice.repository;

import se.joafre.movieservice.model.Actor;
import se.joafre.movieservice.model.Movie;
import se.joafre.movieservice.utility.Mapper;
import se.joafre.movieservice.utility.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joanne on 02/12/15.
 */
public final class MovieRepositoryImpl implements MovieRepository {

//    private final static String driver = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost/MovieServiceDatabase?user=root&password=hannele1";

    private static final Mapper<Movie> MOVIE_MAPPER = resultSet -> new Movie(resultSet.getInt(1), resultSet.getString(2)
            , resultSet.getInt(3), resultSet.getString(4));

    private static final Mapper<Integer> countMapper = resultSet -> new Integer(resultSet.getInt(1));

    private static final Mapper<Actor> ACTOR_MAPPER = resultSet -> new Actor(resultSet.getInt(1), resultSet.getString(2),
            resultSet.getString(3), resultSet.getString(4));

    public List<Movie> getAllMovies() {
        String sql = "SELECT Movie.id, title, productionYear, name  from Movie JOIN Genre ON Movie.genreId= Genre.id";
        return new Query<Movie>(URL).mapper(MOVIE_MAPPER).query(sql).execute();
    }

    @Override
    public Movie getMovieById(int id) {
        String sql = "SELECT Movie.id, title, productionYear, name FROM Movie JOIN Genre ON Movie.genreId = Genre.id " +
                    "WHERE Movie.id = ?";
        return new Query<Movie>(URL).mapper(MOVIE_MAPPER).query(sql).execute().get(0);
    }

    public boolean persistMovie(Movie movie) {
        int success = 0;
        if(genreExists(movie.getGenre())) {
            String sql = "INSERT INTO Movie (title, productionYear, genreId) VALUES (?,?,?)";
            int genreId = getGenreId(movie.getGenre());
            success = new Query<Movie>(URL).query(sql)
                    .parameter(movie.getTitle())
                    .parameter(movie.getProductionYear())
                    .parameter(genreId)
                    .update();
        }
        return success == 1;
    }


    public boolean updateMovie(Movie movie) {
        String sql = "UPDATE Movie SET title = ?, productionYear = ?, genreId = ? WHERE Movie.id =?";
        int genreId = getGenreId(movie.getGenre());
        return new Query<Movie>(URL).query(sql)
                .parameter(movie.getTitle())
                .parameter(movie.getProductionYear())
                .parameter(genreId)
                .parameter(movie.getId())
                .update() == 1;
    }

    public boolean deleteMovie(int id) {
        String sql = "DELETE FROM Movie where id = ?";
        return new Query<Movie>(URL).query(sql)
                .parameter(id)
                .update() == 1;
    }

    @Override
    public List<Actor> getAllActors() {
        String sql = "SELECT * FROM Actor";

        return new Query<Actor>(URL).query(sql).mapper(ACTOR_MAPPER).execute();

    }

    @Override
    public void persistActor(Actor actor) {

    }

    @Override
    public boolean updateActor(Actor actor) {
        String sql = "UPDATE Actor SET firstName = ?, middleName =?, lastName =? WHERE id=?";
        return new Query<Actor>(URL).query(sql)
                                    .parameter(actor.getFirstName())
                                    .parameter(actor.getMiddleName())
                                    .parameter(actor.getLastName())
                                    .update() ==1;
    }

    @Override
    public boolean deleteActor(String id) {
        return false;

    }

    @Override
    public int addGenre(String genre) {
        try(Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO Genre (name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,genre);

            return statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean genreExists(String genre)
    {
        String sql = "SELECT COUNT(*) FROM Genre WHERE name =?";
        return new Query<Integer>(URL).query(sql).mapper(countMapper).parameter(genre).execute().get(0) == 1;
    }

    private int getGenreId(String genreName) {
        String sql = "select id from Genre where name =?";
        return new Query<Integer>(URL).mapper(countMapper).parameter(genreName).query(sql).execute().get(0);
    }
}
