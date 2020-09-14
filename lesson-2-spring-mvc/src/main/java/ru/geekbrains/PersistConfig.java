package ru.geekbrains;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.geekbrains.persistance.ProductRepo;
import ru.geekbrains.persistance.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
public class PersistConfig {

    @Value("${database.driver.class}")
    private String driverClassName;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database2.url}")
    private String database2Url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Bean
    public UserRepository userRepository(DataSource dataSource) throws SQLException {
        return new UserRepository(dataSource);
    }

    @Bean
    public ProductRepo productRepo(DataSource dataSource) throws SQLException {
        return new ProductRepo(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(database2Url);
        return ds;
    }
}
