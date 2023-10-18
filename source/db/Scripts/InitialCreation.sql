DROP TABLE IF EXISTS Product;
CREATE TABLE Product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         vendor VARCHAR(255),
                         url_slug VARCHAR(100) UNIQUE NOT NULL,
                         sku VARCHAR(255) UNIQUE NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         discount_percent INT DEFAULT 0,
                         is_featured BOOLEAN DEFAULT FALSE,
                         is_delete BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS Cart;
CREATE TABLE Cart (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      user_id BIGINT UNIQUE NOT NULL
);

DROP TABLE IF EXISTS CartProduct
CREATE TABLE CartProduct (
                             cart_id INT NOT NULL,
                             product_id INT NOT NULL,
                             PRIMARY KEY (cart_id, product_id),
                             FOREIGN KEY (cart_id) REFERENCES Cart(id),
                             FOREIGN KEY (product_id) REFERENCES Product(id)
);

DROP TABLE IF EXISTS Role
CREATE TABLE Role (
                      role_id int AUTO_INCREMENT PRIMARY KEY,
                      role_name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO Role (role_name) VALUES ('customer'), ('staff');


DROP TABLE IF EXISTS Users
CREATE TABLE Users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       role_id BIGINT,
                       status VARCHAR(255),
                       FOREIGN KEY (role_id) REFERENCES Role(role_id)
);