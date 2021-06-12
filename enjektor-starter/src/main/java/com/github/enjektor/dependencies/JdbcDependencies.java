package com.github.enjektor.dependencies;

import com.github.enjektor.core.annotations.Dependencies;

@Dependencies
public class JdbcDependencies {

//    @Dependency
//    public DataSource dataSource() {
//        final JdbcConfiguration configuration = new JdbcConfiguration.JdbcConfigurationBuilder()
//            .username("docker")
//            .password("123456")
//            .type(DatabaseType.POSTGRE)
//            .host("localhost")
//            .selectedDatabase("docker")
//            .options(
//                Arrays.asList(new JdbcOption(ConnectionOptions.CHARACTER_ENCODING, "utf8"))
//            )
//            .build();
//
//        final JdbcDataSource jdbcDataSource = new HikariJdbcDataSource(configuration);
//        return jdbcDataSource.getDataSource();
//    }

}
