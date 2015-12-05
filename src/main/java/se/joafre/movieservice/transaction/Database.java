package se.joafre.movieservice.transaction;

import se.joafre.movieservice.model.Actor;
import se.joafre.movieservice.transaction.model.Genre;
import se.joafre.movieservice.utility.Mapper;

import java.sql.*;

/**
 * 1. Ett static initieringsblock
 * 2. Typgeneriskt Transaction-interface som använder ett typgeneriskt StatementBuilder-interface
 *    vilket man får implementera själv
 * 3. En implementation av Transaction
 * 4. Separata implementationer av StatementBuilder för diverse modelobjekt object
 * 5. Lambda-implementationer av typgeneriskt Mapper-interface för diverse modelobjekt
 * 6. Överladdade persist()-metoder med modelobjekten som parameter (persist(Movie), persist(Actor), persist(Genre))
 *    vilka returnerar ett objekt av sig själv fast med ett lagrat id hämtat från generatedKeys()
 */
public final class Database {

    // Could not be final due to static block
    private static Connection conn = null;

    static {
        String url = "jdbc:mysql://localhost/MovieDatabase?user=root&password=root";

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mapper-implementations
    private final static Mapper<Movie> MOVIE_MAPPER = rS -> new Movie(rS.getInt("movieId"), rS.getString("title"),
                                                              rS.getInt("productionYear"), rS.getInt("genreId"));

    private final static Mapper<Actor> ACTOR_MAPPER = rS -> new Actor(rS.getInt("actorId"), rS.getString("firstName"),
                                                            rS.getString("middleName"), rS.getString("lastName"));

    private final static Mapper<Genre> GENRE_MAPPER = rS -> new Genre(rS.getInt("genreId"), rS.getString("name"));

    // Could convert to lamba, doesn't change the code alot so I opted not to
    private final static Transaction transaction = new Transaction() {
        @Override
        public int executeUpdateAndGetKey(Connection conn, StatementBuilder stmntBuilder, Object entity) throws SQLException {
            PreparedStatement stmnt = stmntBuilder.getStatement(conn, entity);
            stmnt.executeUpdate();
            ResultSet keySet = stmnt.getGeneratedKeys();
            if (keySet.next()) {
                return keySet.getInt(1);
            }
            return 0;
        }
    };

    // CONSTRUCTOR [COMMENT BECAUSE IT WAS SO SMALL TEXT-WISE RELATIVE TO THE REST OF THE CODE]
    private Database() {}

    private final static StatementBuilder<Movie> movieStatementBuilder = new StatementBuilder<Movie>() {
        @Override
        public String getSQL() {
            return "INSERT INTO Movie (title, productionYear, genreId) VALUES (?, ?, ?)";
        }

        @Override
        public PreparedStatement getStatement(Connection conn, Movie entity) throws SQLException {
            PreparedStatement stmnt = conn.prepareStatement(getSQL(), Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, entity.getTitle());
            stmnt.setInt(2, entity.getProductionYear());
            stmnt.setInt(3, entity.getGenre());
            return stmnt;
        }
    };

    public static Movie persist(Movie m) throws SQLException {
        int generatedKey = transaction.executeUpdateAndGetKey(conn, movieStatementBuilder, m);
        System.out.println("generated movie key: " + generatedKey);
        String sql = "SELECT * FROM Movie WHERE movieId = ?";
        return new Query<Movie>(conn).parameter(generatedKey).query(sql).mapper(MOVIE_MAPPER).execute().get(0);
    }

    private final static StatementBuilder<Actor> actorStatementBuilder = new StatementBuilder<Actor>() {
        @Override
        public String getSQL() {
            return "INSERT INTO Actor (firstName, middleName, lastName) VALUES (?, ?, ?)";
        }

        @Override
        public PreparedStatement getStatement(Connection conn, Actor entity) throws SQLException {
            PreparedStatement stmnt = conn.prepareStatement(getSQL(), Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, entity.getFirstName());
            stmnt.setString(2, entity.getMiddleName());
            stmnt.setString(3, entity.getLastName());
            return stmnt;
        }
    };

    public static Actor persist(Actor a) throws SQLException {
        int generatedKey = transaction.executeUpdateAndGetKey(conn, actorStatementBuilder, a);
        String sql = "SELECT * FROM Actor WHERE actorId = ?";
        return new Query<Actor>(conn).parameter(generatedKey).query(sql).mapper(ACTOR_MAPPER).execute().get(0);
    }

    private final static StatementBuilder<Genre> genreStatementBuilder = new StatementBuilder<Genre>() {
        @Override
        public String getSQL() {
            return "INSERT INTO Genre (name) VALUES (?)";
        }

        @Override
        public PreparedStatement getStatement(Connection conn, Genre entity) throws SQLException {
            PreparedStatement stmnt = conn.prepareStatement(getSQL(), Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, entity.getGenreName());
            return stmnt;
        }
    };

    public static Genre persist(Genre g) throws SQLException {
        int generatedKey = transaction.executeUpdateAndGetKey(conn, genreStatementBuilder, g);
        String sql = "SELECT * FROM Genre WHERE genreId = ?";
        return new Query<Genre>(conn).parameter(generatedKey).query(sql).mapper(GENRE_MAPPER).execute().get(0);
    }
}
