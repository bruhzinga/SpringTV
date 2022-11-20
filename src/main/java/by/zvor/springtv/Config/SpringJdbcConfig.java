package by.zvor.springtv.Config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SpringJdbcConfig {
    @Bean
    public DataSource OracleDataSourceAdmin() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        dataSource.setUsername("SpringTVAdmin");
        dataSource.setPassword("9");
        return dataSource;
    }

    @Bean
    public JdbcTemplate AdminJdbcTemplate(@Qualifier("OracleDataSourceAdmin") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

   /* //TODO
    @Bean
    public JdbcTemplate UserJdbcTemplate(@Qualifier("topicsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }*/


}
