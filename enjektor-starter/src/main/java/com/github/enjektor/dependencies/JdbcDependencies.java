package com.github.enjektor.dependencies;

import com.github.enesusta.jdbc.datasource.HikariJdbcDataSource;
import com.github.enesusta.jdbc.datasource.JdbcConfiguration;
import com.github.enesusta.jdbc.datasource.JdbcDataSource;
import com.github.enesusta.jdbc.datasource.JdbcOption;
import com.github.enesusta.jdbc.datasource.enums.ConnectionOptions;
import com.github.enesusta.jdbc.datasource.enums.DatabaseType;
import com.github.enjektor.core.annotations.Dependencies;
import com.github.enjektor.core.annotations.Dependency;

import javax.sql.DataSource;
import java.util.Arrays;

@Dependencies
public class JdbcDependencies {

    @Dependency
    public DataSource dataSource() {
        final JdbcConfiguration configuration = new JdbcConfiguration.JdbcConfigurationBuilder()
            .username("docker")
            .password("123456")
            .type(DatabaseType.POSTGRE)
            .host("localhost")
            .selectedDatabase("docker")
            .options(
                Arrays.asList(new JdbcOption(ConnectionOptions.CHARACTER_ENCODING, "utf8"))
            )
            .build();

        final JdbcDataSource jdbcDataSource = new HikariJdbcDataSource(configuration);
        return jdbcDataSource.getDataSource();
    }

}
