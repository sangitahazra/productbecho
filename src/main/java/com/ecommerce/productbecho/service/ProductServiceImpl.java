package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.dto.ProductDTO;
import com.ecommerce.productbecho.entity.Stock;
import com.ecommerce.productbecho.entity.VariantProduct;
import com.ecommerce.productbecho.repository.StockRepository;
import com.ecommerce.productbecho.repository.VariantProductRepository;
import form.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    VariantProductRepository variantProductRepository;

    @Autowired
    StockRepository stockRepository;

    @Override
    public void uploadProduct(Product product, MultipartFile file) throws IOException {
        VariantProduct variantProduct = new VariantProduct();
        variantProduct.setCode(product.getCode());
        variantProduct.setDescription(product.getDescription());
        variantProduct.setImage(file.getBytes());
        variantProduct.setName(product.getName());
        variantProduct.setPrice(product.getPrice());
        variantProductRepository.save(variantProduct);

        Stock stock = new Stock();
        stock.setQuantity(product.getQuantity());
        stock.setWarehouseCode(product.getWarehouse());
        stock.setVariantProduct(variantProduct);
        stockRepository.save(stock);
    }

    @Override
    public Set<ProductDTO> getAllProducts() {
        List<VariantProduct> variantProductList = variantProductRepository.findAll();
        Set<ProductDTO> products = new HashSet<>();
        variantProductList.forEach(populateProductDetails(products));
        return products;
    }

    @Override
    public Set<ProductDTO> findByKey(String key) {
        Set<VariantProduct> variantProductSet = variantProductRepository.findByKey(key, key, key);
        Set<ProductDTO> products = new HashSet<>();
        variantProductSet.forEach(populateProductDetails(products));
        return products;
    }

    private Consumer<VariantProduct> populateProductDetails(Set<ProductDTO> products) {
        return variantProduct -> {
            String image = Base64.getEncoder().encodeToString(variantProduct.getImage());
            ProductDTO productDTO = new ProductDTO();
            productDTO.setCode(variantProduct.getCode());
            productDTO.setImage(image);
            productDTO.setName(variantProduct.getName());
            productDTO.setPrice(variantProduct.getPrice());
            productDTO.setDescription(variantProduct.getDescription());
            products.add(productDTO);
        };
    }
}
