package com.elektrodevs.elektromart.service;

import com.elektrodevs.elektromart.dao.ProductDao;
import com.elektrodevs.elektromart.dao.ProductRepository;
import com.elektrodevs.elektromart.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> getProducts() {
        return productDao.getProducts();
    }

    public Product createProduct(Product newProduct) {
        return productDao.createProduct(newProduct);
    }

    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }

    public Product editProduct(Product updatedProduct) {
        return productDao.editProduct(updatedProduct);
    }
}
