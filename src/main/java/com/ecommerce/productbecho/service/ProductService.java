package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.dto.ProductDTO;
import form.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface ProductService {
    void uploadProduct(Product product, MultipartFile file) throws IOException;
    Set<ProductDTO> getAllProducts();

    Set<ProductDTO> findByKey(String key);
}
