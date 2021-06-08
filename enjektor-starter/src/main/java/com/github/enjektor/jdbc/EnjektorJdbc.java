package com.github.enjektor.jdbc;

import com.github.enjektor.core.annotations.Dependency;
import com.github.enjektor.core.annotations.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Dependency
public class EnjektorJdbc {

//    @Inject("jdbc.query")
    private String query;

    @Inject
    private DataSource dataSource;

    public void print() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(6));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
