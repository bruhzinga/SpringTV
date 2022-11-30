package by.zvor.springtv.Config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUser {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:oracle:thin:@//localhost:1521/SpringTV");
        config.setUsername("SpringTVUser");
        config.setPassword("qwerty");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(3);
        ds = new HikariDataSource(config);
    }

    private DataSourceUser() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
