package se.joafre.movieservice.repository;

import se.joafre.movieservice.model.Actor;
import se.joafre.movieservice.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joanne on 02/12/15.
 */
public final class MovieRepositoryImpl implements MovieRepository {

//    private final static String driver = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost/MovieServiceDatabase";
    private final static  String USERNAME = "root";
    private final static String PASSWORD = "hannele1";
    private final List<Movie> movies = new ArrayList<>();
    private final List<Long> movieKeys = new ArrayList<>();


    public List<Movie> getAllMovies() {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD))
        {
            Statement statement = connection.createStatement();
            String sql = "SELECT Movie.id, title, productionYear, name  from Movie JOIN Genre ON Movie.genreId= Genre.id";
            try(ResultSet resultSet = statement.executeQuery(sql))
            {
                while(resultSet.next())
                {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    int productionYear = resultSet.getInt("productionYear");
                    String genre = resultSet.getString("name");

                    Movie movie = new Movie(id, title, productionYear, genre);
                    movies.add(movie);
                    System.out.println(movie.toString());

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movie getMovieById(int id) {
        try(Connection connection =DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            String sql = "SELECT Movie.id, title, productionYear, name FROM Movie JOIN Genre ON Movie.genreId = Genre.id " +
                    "WHERE Movie.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){

                int movieId = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int productionYear = resultSet.getInt("productionYear");
                String genre = resultSet.getString("name");
                return new Movie(movieId, title, productionYear, genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void persistMovie(Movie movie) {
        if(genreExists(movie.getGenre())) {

            String sql = "INSERT INTO Movie (title, productionYear, genreId) VALUES (?,?,?)";
            int genreId = getGenreId(movie.getGenre());
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

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


        }
    }


    public int updateMovie(Movie movie) {
        String sql = "UPDATE Movie SET title = ?, productionYear = ?, genreId = ? WHERE Movie.id =?";
        int success =0;
        int genreId = getGenreId(movie.getGenre());

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
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
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
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
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
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
        int count = 0;
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD))
        {
            String sql = "SELECT COUNT(*) FROM Genre WHERE name =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, genre);
            try(ResultSet resultSet = statement.executeQuery(sql))
            {
                while (resultSet.next())
                {
                    count = resultSet.getInt(1);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return count > 0;

    }

    private int getGenreId(String genreName) {
        int genreId = 0;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "select id from Genre where name =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, genreName);
            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    genreId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genreId;

    }
}
