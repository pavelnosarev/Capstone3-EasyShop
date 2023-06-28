package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
//import org.yearup.data.MySqlDaoBase;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Product> listByCategoryId(int categoryId) {
        return null;
    }

    @Override
    public Product add(Product product) {
        return null;
    }

    @Override
    public Product create(Product product) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }

    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Category category = mapRow(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        // Implement logic to retrieve a category by its ID
        return null;
    }

    @Override
    public Category add(Category category) {
        // Implement logic to add a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category) {
        // Implement logic to update a category
    }

    @Override
    public void delete(int categoryId) {
        // Implement logic to delete a category
    }

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        // Implement logic to retrieve products by category ID
        return null;
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName(name);
        category.setDescription(description);

        return category;
    }
}