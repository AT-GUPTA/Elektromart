package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.ProductDao;
import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getProducts() {
        List<Product> products = productDao.getProducts();
        log.debug("getProducts: Retrieved {} products.", products.size());
        return products;
    }
    public Product createProduct(Product newProduct) {
        Product createdProduct = productDao.createProduct(newProduct);
        if (createdProduct != null) {
            log.debug("createProduct: Created product with ID {}.", createdProduct.getId());
        } else {
            log.error("createProduct: Failed to create a new product.");
        }
        return createdProduct;
    }
    public Product getProductById(int productId) {
        Product product = productDao.getProductById(productId);
        if (product != null) {
            log.debug("getProductById: Retrieved product with ID {}.", productId);
        } else {
            log.error("getProductById: Product with ID {} not found.", productId);
        }
        return product;
    }
    public Product editProduct(Product updatedProduct) {
        Product editedProduct = productDao.editProduct(updatedProduct);
        if (editedProduct != null) {
            log.debug("editProduct: Updated product with ID {}.", updatedProduct.getId());
        } else {
            log.error("editProduct: Failed to update product with ID {}.", updatedProduct.getId());
        }
        return editedProduct;
    }
    public Product getProductBySlug(String urlSlug) {
        Product product = productDao.getProductBySlug(urlSlug);
        if (product != null) {
            log.debug("getProductBySlug: Retrieved product with URL Slug '{}'.", urlSlug);
        } else {
            log.error("getProductBySlug: Product with URL Slug '{}' not found.", urlSlug);
        }
        return product;
    }
}
