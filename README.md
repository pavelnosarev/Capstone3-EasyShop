# Easyshop - Capstone 3
This repository contains a Java Spring Boot application with several controllers that handle various operations related to authentication, user profiles, categories, products, and the shopping cart.

### Overview
The EasyShop application is a shopping platform that allows users to browse and purchase products. It provides features such as user authentication, managing user profiles, managing product categories, searching for products, and maintaining a shopping cart.

### Getting Started
To run the application, follow these steps:

Make sure you have the Java Development Kit (JDK) installed on your system.
Ensure that you have the required dependencies and libraries as mentioned in the project documentation.
Configure the application properties, such as the server port and database connection details, in the application.properties file or through environment variables.
Build the project using your preferred build tool (e.g., Maven or Gradle).
Execute the main() method in the EasyshopApplication class to start the application.
Once the application starts successfully, you should see log messages indicating that the server is running.
Controllers
The EasyShop application includes several controllers to handle different functionalities. Here's an overview of each controller and its endpoints:

### AuthenticationController
The AuthenticationController handles authentication-related operations such as user login and registration. It provides the following endpoints:

POST /auth/login: Authenticates a user with the provided username and password and generates a JWT token.
POST /auth/register: Registers a new user with the provided username, password, and role.
CategoriesController
The CategoriesController manages operations related to product categories. It provides the following endpoints:

GET /categories: Retrieves all categories.
GET /categories/{id}: Retrieves a category by its ID.
POST /categories: Creates a new category.
PUT /categories/{categoryId}/products: Adds a product to the specified category.
PUT /categories/{id}: Updates a category.
DELETE /categories/{id}: Deletes a category.


### ProductsController
The ProductsController handles operations related to products. It provides the following endpoints:

GET /products: Searches for products based on optional query parameters such as category, minimum price, maximum price, and color.
GET /products/{id}: Retrieves a product by its ID.
POST /products: Creates a new product.
PUT /products/{id}: Updates a product.
DELETE /products/{id}: Deletes a product.

### ProfileController
The ProfileController manages user profiles. It provides the following endpoints:

GET /profile: Retrieves the profile of the currently authenticated user.
PUT /profile: Updates the profile of the currently authenticated user.

### ShoppingCartController
The ShoppingCartController handles operations related to the shopping cart. It provides the following endpoints:

GET /cart/{id}: Retrieves the shopping cart for the currently authenticated user.
POST /cart/products/{productId}: Adds a product to the shopping cart.
PUT /cart/products/{productId}: Updates the quantity of a product in the shopping cart.
DELETE /cart: Clears the shopping cart.
Please note that some endpoints may require specific user roles or authentication for access, as indicated by the commented-out @PreAuthorize annotations.

Each controller is annotated with the appropriate Spring annotations such as @RestController, @RequestMapping, and @CrossOrigin to handle HTTP requests and enable cross-origin resource sharing.

Feel free to explore each controller to understand the implemented functionality in detail.

 ### DAO Interfaces
This section provides an overview of the DAO (Data Access Object) interfaces used in the EasyShop application. Each DAO interface represents a specific data entity and defines methods to interact with the corresponding data in the database.

#### ProductDao
The ProductDao interface handles operations related to products. It allows you to search for products based on optional parameters such as category, price range, and color. Additionally, you can list products by category, retrieve a product by ID, create a new product, update an existing product, delete a product, retrieve products associated with a specific category, add a product, and retrieve a product by ID.

#### ProfileDao
The ProfileDao interface is responsible for managing user profiles. It provides methods to create a new profile, retrieve a profile by user ID, and update an existing profile.

#### ShoppingCartDao
The ShoppingCartDao interface manages shopping cart operations. It provides methods to retrieve a shopping cart by ID, save a shopping cart, delete a shopping cart, retrieve all shopping carts, retrieve shopping carts by user ID, add a cart item, update a cart item, remove a cart item, calculate the total price of a cart, get the item count in a cart, add a product to a cart, retrieve a shopping cart by user ID, clear a cart, and update the quantity of a product in a cart.

#### UserDao
The UserDao interface handles user-related operations. It allows you to retrieve all users, get a user by ID, get a user by username, retrieve a user's ID by username, create a new user, and check if a username exists.

### Database Configuration
The DatabaseConfig class is responsible for configuring the database connection for the EasyShop application using the Apache Commons DBCP2 library. Here's how to set it up:

Update the values in the application.properties file or provide them through environment variables for the following properties:
datasource.url: The URL of your database.
datasource.username: The username to access the database.
datasource.password: The password to access the database.
The DatabaseConfig class reads these values and sets up a BasicDataSource bean, which provides the necessary connection details for the database.
The BasicDataSource bean is then used by other components of the application to establish database connections.
Make sure to configure the database connection before running the application.
### Maven Dependencies
The project's dependencies are managed using Maven. Here's the relevant information from the pom.xml file:

xml
Copy code
<!-- Parent -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.3</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<!-- Project Info -->
<groupId>org.yearup</groupId>
<artifactId>easyshop-capstone-starter</artifactId>
<version>0.0.1-SNAPSHOT</version>

<!-- Dependencies -->
<dependencies>
    <!-- Spring Boot Starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Spring JDBC -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>6.0.6</version>
    </dependency>
    
    <!-- JSON Web Token (JWT) -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.1</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.1</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.1</version>
    </dependency>
    
    <!-- Spring Boot DevTools -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    
    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    
    <!-- Apache Commons DBCP2 -->
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-dbcp2</artifactId>
        <version>2.9.0</version>
    </dependency>
    
    <!-- Spring Boot Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- MyBatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.4.5</version>
    </dependency>
</dependencies>

<!-- Build Configuration -->
<build>
    <plugins>
        <!-- Spring Boot Maven Plugin -->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
The pom.xml file defines the project's dependencies, including Spring Boot, Spring Security, Spring Web, Spring Validation, Spring JDBC, JSON Web Token (JWT), MySQL Connector, Apache Commons DBCP2, Spring Boot DevTools, Spring Boot Test, and MyBatis.

## Conclusion
That's it! You're now familiar with the EasyShop application's structure, controllers, DAO interfaces, database configuration, and Maven dependencies. Feel free to explore the source code further to understand the implementation details.




