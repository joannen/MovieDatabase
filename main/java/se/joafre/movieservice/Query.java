package se.joafre.movieservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by joanne on 03/12/15.
 */
public final class Query<T> {

    private final String connectionString;
    private Mapper<T> mapper;
    private String sql;
    private final List<Object> parameters;

    public Query(String connectionString) {
        this.connectionString = connectionString;
        this.parameters = new ArrayList<>();
    }

    public Query<T> query(String query){
        this.sql = query;
        return this;
    }

    public Query<T> parameter(Object parameter){
        parameters.add(parameter);
        return this;
    }

    public Query<T> mapper(Mapper mapper){
        this.mapper = mapper;
        return  this;
    }

    public List<T> execute(){

        List<T> result = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement statement = createStatement(connection);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                result.add(mapper.map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private PreparedStatement createStatement(Connection connection) throws SQLException {
       PreparedStatement statement = connection.prepareStatement(sql);

        for (int i = 0; i < parameters.size() ; i++) {
            statement.setObject(i+1, parameters.get(i));
        }

        return statement;
    }
}
