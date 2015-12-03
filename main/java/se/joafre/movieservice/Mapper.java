package se.joafre.movieservice;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by joanne on 03/12/15.
 */
public interface Mapper<T> {

  T map(ResultSet resultSet) throws SQLException;
}
