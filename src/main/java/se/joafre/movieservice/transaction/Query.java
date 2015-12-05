package se.joafre.movieservice.transaction;

import se.joafre.movieservice.utility.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created indirectly by the universe.
 */

public final class Query<T> {

    private final Connection conn;
    private final List<Object> parameters;
    private String sql;
    private Mapper<T> mapper;


    public Query(Connection conn) {
        this.conn = conn;
        this.parameters = new ArrayList<>();
    }

    public Query<T> query(String sql) {
        this.sql = sql;
        return this;
    }

    public Query<T> parameter(Object o) {
        parameters.add(o);
        return this;
    }

    public Query<T> mapper(Mapper<T> mapper) {
        this.mapper = mapper;
        return this;
    }

    public List<T> execute() throws SQLException {
        final List<T> result = new ArrayList<>();
        ResultSet rs = getStatement().executeQuery();

        while(rs.next()) {
            result.add(mapper.map(rs));
        }

        return result;
    }

    private PreparedStatement getStatement() throws SQLException {
        PreparedStatement stmnt = conn.prepareStatement(sql);
        for (int i = 0; i < parameters.size(); i++) {
            stmnt.setObject(i+1, parameters.get(i));
        }

        return stmnt;
    }
}
