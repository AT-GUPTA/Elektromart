package com.elektromart.service;

import com.elektromart.dao.ProductDao;
import com.elektromart.domain.Product;

import java.util.List;

public class ProductService {

    private final ProductDao productDao = new ProductDao();

    public List<Product> getProducts() {
        return productDao.getProducts();
    }
}

