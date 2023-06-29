package org.yearup.data;

import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao
{
    List<Product> search(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String color);
    List<Product> listByCategoryId(int categoryId);

    List<Product> getByCategoryId(int categoryId);

    Product getById(int productId);
    Product create(Product product);
    void update(int productId, Product product);
    void delete(int productId);

    List<Product> getProductsByCategoryId(int categoryId);

    Product add(Product product);
}
