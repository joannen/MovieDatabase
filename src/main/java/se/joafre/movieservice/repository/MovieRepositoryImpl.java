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
    private final static String URL = "jdbc:mysql://localhost/MovieServiceDatabase?user=root&password=root";
    private final List<Movie> movies = new ArrayList<>();
    private final List<Long> movieKeys = new ArrayList<>();

    private static final Mapper<Movie> MOVIE_MAPPER = new Mapper<Movie>() {
        @Override
        public Movie map(ResultSet resultSet) throws SQLException {
            return new Movie(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4));
        }
    };

    final Mapper<Integer> countMapper = new Mapper<Integer>() {
        @Override
        public Integer map(ResultSet resultSet) throws SQLException {
            return new Integer(resultSet.getInt(1));
        }
    };

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

    public void persistMovie(Movie movie) {
        if(genreExists(movie.getGenre())) {
            String sql = "INSERT INTO Movie (title, productionYear, genreId) VALUES (?,?,?)";
            int genreId = getGenreId(movie.getGenre());
            new Query<Object>(URL).query(sql).parameter(movie.getTitle()).parameter(movie.getProductionYear())
                    .parameter(genreId).update();
            /* try (Connection connection = DriverManager.getConnection(URL)) {

                connection.setAutoCommit(false);

                try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, movie.getTitle());
                    statement.setInt(2, movie.getProductionYear());
                    statement.setInt(3, genreId);

                    statement.executeUpdate(sql);


                    ResultSet keys = statement.getGeneratedKeys();

                    while (keys.next()) {
                        movieKeys.add(keys.getLong(1));
                        System.out.println(keys.getLong(1));
                    }
                    connection.commit();


            } catch (SQLException e) {
                connection.rollback();
                    throw e;
            }


        } catch (SQLException e) {
                e.printStackTrace();
            }


        }*/
        }
    }


    public int updateMovie(Movie movie) {
        String sql = "UPDATE Movie SET title = ?, productionYear = ?, genreId = ? WHERE Movie.id =?";
        int success =0;
        int genreId = getGenreId(movie.getGenre());

        try (Connection connection = DriverManager.getConnection(URL)) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {


                statement.setString(1, movie.getTitle());
                statement.setInt(2, movie.getProductionYear());
                statement.setInt(3, genreId);
                statement.setInt(4, movie.getId());
                success = statement.executeUpdate(sql);
                connection.commit();
                return success;


            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public int deleteMovie(int id) {
        try(Connection connection = DriverManager.getConnection(URL)) {
            String sql = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            return statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Actor> getAllActors() {
        return null;
    }

    @Override
    public void persistActor(Actor actor) {

    }

    @Override
    public int updateActor(Actor actor) {
        return 0;
    }

    @Override
    public int deleteActor(String id) {
        return 0;
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


        return new Query<Integer>(URL).query(sql).mapper(countMapper).parameter(genre).execute().get(0) > 0;
    }

    private int getGenreId(String genreName) {
        int genreId;
        String sql = "select id from Genre where name =?";

        genreId = new Query<Integer>(URL).mapper(countMapper).parameter(genreName).query(sql).execute().get(0);

        /* try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, genreName);
            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    genreId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } */

        return genreId;

    }
}