package com.ecommerce.productbecho.service;

import form.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    void uploadProduct(Product product, MultipartFile file) throws IOException;
}
