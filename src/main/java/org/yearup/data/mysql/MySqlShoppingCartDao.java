package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }

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


//    @Override
//    public Product add(Product product) {
//        return null;
//    }
//
//    @Override
//    public Product create(Product product) {
//        return null;
//    }
//
//    @Override
//    public List<Category> getAllCategories() {
//        return null;
//    }
//
//    @Override
//    public Category create(Category category) {
//        return null;
//    }
//
//    @Override
//    public ShoppingCart getByUserId(int userId) {
//        String sql = "SELECT * FROM shopping_carts WHERE user_id = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, userId);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                return mapRow(resultSet);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
//
//    @Override
//    public void create(ShoppingCart shoppingCart) {
//        String sql = "INSERT INTO shopping_carts (user_id) VALUES (?)";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, shoppingCart.getUserId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void update(ShoppingCart shoppingCart) {
//        String sql = "UPDATE shopping_carts SET user_id = ? WHERE cart_id = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, shoppingCart.getUserId());
//            statement.setInt(2, shoppingCart.getCartId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void delete(int userId) {
//        String sql = "DELETE FROM shopping_carts WHERE user_id = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, userId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void addProductToCart(int userId, int productId) {
//        // Implement the logic to add a product to the shopping cart
//    }
//
//    @Override
//    public void updateProductInCart(int userId, int productId, int quantity) {
//        // Implement the logic to update the quantity of a product in the shopping cart
//    }
//
//    @Override
//    public void clearCart(int userId) {
//        // Implement the logic to clear the shopping cart
//    }
//
//    // Helper method to map a ResultSet row to a ShoppingCart object
//        int userId = resultSet.getInt("user_id");
//        // Create a new ShoppingCart object based on the retrieved data
//        return new ShoppingCart(cartId, userId);
//    }
}
