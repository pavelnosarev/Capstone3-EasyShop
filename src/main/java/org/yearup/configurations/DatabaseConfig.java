package org.yearup.configurations;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.yearup.data.ProfileDao;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DatabaseConfig {

    private final BasicDataSource basicDataSource;

    @Autowired
    public DatabaseConfig(@Value("${datasource.url}") String url,
                          @Value("${datasource.username}") String username,
                          @Value("${datasource.password}") String password) {
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
    }

    @Bean
    public DataSource dataSource() {
        return basicDataSource;
    }

    @Bean
    public ProfileDao profileDao(DataSource dataSource) {
        return new ProfileDao() {
            @Override
            public Profile create(Profile profile) {
                return null;
            }
        };
    }

    @Bean
    public ProductDao productDao(DataSource dataSource) {
        return new ProductDao() {
            @Override
            public List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color) {
                return null;
            }

            @Override
            public List<Product> getByCategoryId(int categoryId) {
                return null;
            }

            @Override
            public Product getById(int productId) {
                return null;
            }

            @Override
            public Product create(Product product) {
                return null;
            }

            @Override
            public void update(int productId, Product product) {

            }

            @Override
            public void delete(int productId) {

            }

            @Override
            public List<Product> getProductsByCategoryId(int categoryId) {
                return null;
            }
        };
    }

    @Bean
    public ShoppingCartDao shoppingCartDao(DataSource dataSource) {
        return new ShoppingCartDao() {
            @Override
            public ShoppingCart getByUserId(int userId) {
                return null;
            }

            @Override
            public void create(ShoppingCart shoppingCart) {

            }

            @Override
            public void update(ShoppingCart shoppingCart) {

            }

            @Override
            public void delete(int userId) {

            }

            @Override
            public void addProductToCart(int userId, int productId) {

            }

            @Override
            public void updateProductInCart(int userId, int productId, int quantity) {

            }

            @Override
            public void clearCart(int userId) {

            }
        };
    }
}