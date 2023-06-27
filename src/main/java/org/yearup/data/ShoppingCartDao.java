package org.yearup.data;
import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);
    void create(ShoppingCart shoppingCart);
    void update(ShoppingCart shoppingCart);
    void delete(int userId);
    void addProductToCart(int userId, int productId);
    void updateProductInCart(int userId, int productId, int quantity);
    void clearCart(int userId);
}