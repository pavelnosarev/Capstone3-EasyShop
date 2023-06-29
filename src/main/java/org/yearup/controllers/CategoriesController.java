package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests
@RestController
@RequestMapping("/categories")
@CrossOrigin

public class CategoriesController {

    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryDao.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        return categoryDao.getById(id);
    }

//    @GetMapping("/{categoryId}/products")
//    public List<Product> getProductsById(@PathVariable int categoryId) {
//        return productDao.getProductsByCategoryId(categoryId);
//    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        return categoryDao.create(category);
    }

    @PutMapping("/{categoryId}/products")
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product addProductToCategory(@PathVariable int categoryId, @RequestBody Product product) {
        // Logic to add the product to the specified category
        // Make use of productDao or any other necessary methods to perform the operation
        return productDao.add(product);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category category) {
        categoryDao.update(id, category);
        return ResponseEntity.ok("Update Record: This category has been SUCCESSFULLY updated.");
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int id) {
        categoryDao.delete(id);
    }
}