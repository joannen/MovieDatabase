package se.joafre.movieservice.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created indirectly by the universe.
 */

public interface Transaction<T> {

    int executeUpdateAndGetKey(Connection conn, StatementBuilder<T> stmntBuilder, T entity) throws SQLException;
}
