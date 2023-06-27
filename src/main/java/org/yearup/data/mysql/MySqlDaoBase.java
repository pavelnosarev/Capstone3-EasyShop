package org.yearup.data.mysql;

import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class MySqlDaoBase
{
    private DataSource dataSource;

    public MySqlDaoBase(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public abstract List<Product> listByCategoryId(int categoryId);

    public abstract Product add(Product product);

    public abstract Product create(Product product);

    public abstract List<Category> getAllCategories();

    public abstract Category create(Category category);


}
