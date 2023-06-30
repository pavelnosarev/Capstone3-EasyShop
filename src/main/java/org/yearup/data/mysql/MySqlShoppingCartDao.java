package org.yearup.data.mysql;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class MySqlShoppingCartDao implements ShoppingCartDao {
    private final JdbcTemplate jdbcTemplate;
    private final ProductDao productDao;

    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart getShoppingCart(int cartId) {
        String sql = "SELECT * FROM shopping_carts WHERE cart_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{cartId}, (resultSet, rowNum) -> {
            int userId = resultSet.getInt("user_id");
            ShoppingCart shoppingCart = new ShoppingCart(cartId, userId);

            // Fetch cart items
            Map<Integer, ShoppingCartItem> items = (Map<Integer, ShoppingCartItem>) getCartItems(cartId);
            shoppingCart.setItems(items);

            return shoppingCart;
        });
    }

    @Override
    public void saveShoppingCart(ShoppingCart shoppingCart) {
        // Save or update the shopping cart in the database
        String sql = "INSERT INTO shopping_carts (cart_id, user_id) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE user_id = ?";
        jdbcTemplate.update(sql, shoppingCart.getCartId(), shoppingCart.getUserId(), shoppingCart.getUserId());

        // Save or update the cart items
        for (ShoppingCartItem item : shoppingCart.getItems().values()) {
            saveOrUpdateCartItem(shoppingCart.getCartId(), item);
        }
    }

    @Override
    public void deleteShoppingCart(int cartId) {
        String deleteCartItemsSql = "DELETE FROM cart_items WHERE cart_id = ?";
        jdbcTemplate.update(deleteCartItemsSql, cartId);

        String deleteCartSql = "DELETE FROM shopping_carts WHERE cart_id = ?";
        jdbcTemplate.update(deleteCartSql, cartId);
    }

    @Override
    public List<ShoppingCart> getAllShoppingCarts() {
        String sql = "SELECT * FROM shopping_carts";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ShoppingCart.class));
    }

    @Override
    public List<ShoppingCart> getShoppingCartsByUserId(int userId) {
        String sql = "SELECT * FROM shopping_carts WHERE user_id = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ShoppingCart.class), userId);
    }

    @Override
    public void addCartItem(int cartId, ShoppingCartItem item) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, cartId, item.getProductId(), item.getQuantity(), item.getPrice());
    }

    @Override
    public void updateCartItem(int cartId, ShoppingCartItem item) {
        String sql = "UPDATE cart_items SET quantity = ?, price = ? WHERE cart_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, item.getQuantity(), item.getPrice(), cartId, item.getProductId());
    }

    @Override
    public void removeCartItem(int cartId, int productId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, cartId, productId);
    }

    @Override
    public BigDecimal calculateCartTotal(int cartId) {
        String sql = "SELECT SUM(price * quantity) FROM cart_items WHERE cart_id = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, cartId);
    }

    @Override
    public int getItemCount(int cartId) {
        String sql = "SELECT SUM(quantity) FROM cart_items WHERE cart_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, cartId);
    }

    public void addProductToCart(int userId, int productId) {
        // Check if the user has an existing cart
        ShoppingCart cart = getByUserId(userId);
        if (cart == null) {
            // If the user doesn't have a cart, create a new cart
            cart = new ShoppingCart(userId);
            saveShoppingCart(cart);
        }

        // Check if the product already exists in the cart
        ShoppingCartItem existingItem = cart.getItems().get(productId);
        if (existingItem != null) {
            // If the product already exists, increment the quantity by 1
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            updateCartItem(cart.getCartId(), existingItem);
        } else {
            // If the product doesn't exist, fetch the product details
            Product product = productDao.getProductById(productId);
            if (product != null) {
                // Create a new cart item with quantity 1
                ShoppingCartItem newItem = new ShoppingCartItem(product, 1, product.getPrice());
                cart.getItems().put(productId, newItem);
                addCartItem(cart.getCartId(), newItem);
            } else {
                throw new IllegalArgumentException("Product does not exist");
            }
        }
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT * FROM shopping_carts WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (resultSet, rowNum) -> {
            int cartId = resultSet.getInt("cart_id");
            ShoppingCart shoppingCart = new ShoppingCart(cartId, userId);

            // Fetch cart items
            Map<Integer, ShoppingCartItem> items = (Map<Integer, ShoppingCartItem>) getCartItems(cartId);
            shoppingCart.setItems(items);

            return shoppingCart;
        });
    }

    @Override
    public void clearCart(int userId) {
        // Get the user's cart
        ShoppingCart cart = getByUserId(userId);
        if (cart != null) {
            // Clear the cart items
            cart.getItems().clear();
            // Update the cart in the database
            saveShoppingCart(cart);
        }
    }

    private List<Map.Entry<Integer, ShoppingCartItem>> getCartItems(int cartId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id = ?";
        return jdbcTemplate.query(sql, new Object[]{cartId}, (resultSet, rowNum) -> {
            int productId = resultSet.getInt("product_id");
            int quantity = resultSet.getInt("quantity");
            BigDecimal price = resultSet.getBigDecimal("price");

            // Fetch product details using ProductDao
            Product product = productDao.getProductById(productId);

            ShoppingCartItem cartItem = new ShoppingCartItem(product, quantity, price);
            return Map.entry(productId, cartItem);
        });
    }

    private void saveOrUpdateCartItem(int cartId, ShoppingCartItem item) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = ?, price = ?";
        jdbcTemplate.update(sql, cartId, item.getProductId(), item.getQuantity(), item.getPrice(),
                item.getQuantity(), item.getPrice());
    }

    @Override
    public void updateProductInCart(int userId, int productId, int quantity) {
        // Get the user's cart
        ShoppingCart cart = getByUserId(userId);
        if (cart != null) {
            // Check if the product exists in the cart
            ShoppingCartItem item = cart.getItems().get(productId);
            if (item != null) {
                if (quantity > 0) {
                    // Update the quantity of the cart item
                    item.setQuantity(quantity);
                    updateCartItem(cart.getCartId(), item);
                } else {
                    // If quantity is 0, remove the item from the cart
                    removeCartItem(cart.getCartId(), productId);
                    cart.getItems().remove(productId);
                }
                // Update the cart in the database
                saveShoppingCart(cart);
            }
        }
    }

    // Implement other methods of the ShoppingCartDao interface as needed
}
