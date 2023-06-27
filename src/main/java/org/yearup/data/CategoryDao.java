package org.yearup.data;

import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(int id);
    Category add(Category category);
    void update(int id, Category category);
    void delete(int id);
    List<Product> getProductsByCategoryId(int categoryId);
}