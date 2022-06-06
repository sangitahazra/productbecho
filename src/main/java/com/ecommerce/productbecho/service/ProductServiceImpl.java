package com.ecommerce.productbecho.service;

import com.ecommerce.productbecho.dto.ProductDTO;
import com.ecommerce.productbecho.entity.*;
import com.ecommerce.productbecho.repository.*;
import form.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    VariantProductRepository variantProductRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    HttpSession httpSession;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AbstractOrderRepository abstractOrderRepository;

    @Autowired
    AbstractOrderEntryRepository abstractOrderEntryRepository;

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

    public String populateOrderDetails() {
        String orderCode = (String) httpSession.getAttribute("orderCode");
        String addressDetails = "";
        StringBuilder orderDetails = new StringBuilder();
        Order order = orderRepository.findByCode(orderCode);
        if(order != null) {
            AbstractOrder abstractOrder =order.getAbstractOrder();
            Address address = abstractOrder.getAddress();
            addressDetails = address.getName() + ", " + address.getLine1() + " " + address.getLine2()
                    + " " + address.getCity() + " " + address.getState() + " PIN: " + address.getZip() +
                    " Contact: "+ address.getPhone();
            for(AbstractOrderEntry abstractOrderEntry : abstractOrder.getAbstractOrderEntries()) {
                orderDetails.append("Product code: " + abstractOrderEntry.getVariantProduct().getCode());
                orderDetails.append("\n  Quantity: " + abstractOrderEntry.getQuantity());
                orderDetails.append("\n \n");
            }
            return orderDetails.toString() + "\n \n \n" + "Address: " + addressDetails;
        }
        return orderDetails.toString() + "\n \n \n" + "Address: " + addressDetails;
    }



}
