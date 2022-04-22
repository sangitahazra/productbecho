package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.entity.Stock;
import com.ecommerce.productbecho.entity.VariantProduct;
import com.ecommerce.productbecho.repository.StockRepository;
import com.ecommerce.productbecho.repository.VariantProductRepository;
import form.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService{

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
}
