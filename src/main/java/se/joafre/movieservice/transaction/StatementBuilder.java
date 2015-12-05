package se.joafre.movieservice.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created indirectly by the universe.
 */
public interface StatementBuilder<T> {

    String getSQL();

    PreparedStatement getStatement(Connection conn, T entity) throws SQLException;
}
