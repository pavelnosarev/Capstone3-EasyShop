package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public List<Category> getAllCategories() {
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
        String sql = "SELECT * FROM categories WHERE category_id=?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setInt(1, categoryId);

            try(
                    ResultSet resultSet = preparedStatement.executeQuery();

            ){
                if(resultSet.next()){
                    return mapRow(resultSet);
                } else {
                    System.out.println("Category not found.");
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Category create(Category category) {
        String sql = "INSERT INTO categories(description, name) VALUES(?, ?);";
        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)

        ){
            preparedStatement.setString(1, category.getDescription());
            preparedStatement.setString(2, category.getName());

            preparedStatement.executeUpdate();

            try (
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            ){
                if (generatedKeys.next()) {
                    int category_id = generatedKeys.getInt(1);
                    category.setCategoryId(category_id);
                    return category;
                } else {
                    System.out.println("Category has been created successfully.");
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(int categoryId, Category category) {
        String sql = "Update categories SET description=?, name=? WHERE category_id=?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, category.getDescription());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setInt(3, category.getCategoryId());

            preparedStatement.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void delete(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id=?;";

        try(
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        String sql = "SELECT product_id, name, description, price FROM products WHERE category_id=?";
        List<Product> products = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, categoryId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("product_id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
//                    product.setPrice(resultSet.getDouble("price"));

                    products.add(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
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