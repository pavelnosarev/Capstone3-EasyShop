package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartDao {

    ShoppingCart getShoppingCart(int cartId);

    void saveShoppingCart(ShoppingCart shoppingCart);

    // Additional methods to be implemented

    void deleteShoppingCart(int cartId);

    List<ShoppingCart> getAllShoppingCarts();

    List<ShoppingCart> getShoppingCartsByUserId(int userId);

    void addCartItem(int cartId, ShoppingCartItem item);

    void updateCartItem(int cartId, ShoppingCartItem item);

    void removeCartItem(int cartId, int productId);

    BigDecimal calculateCartTotal(int cartId);

    int getItemCount(int cartId);

    void addProductToCart(int userId, int productId);

    ShoppingCart getByUserId(int userId);

    void clearCart(int userId);

    void updateProductInCart(int userId, int productId, int quantity);
}