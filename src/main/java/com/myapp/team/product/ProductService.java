package com.myapp.team.product;

import com.myapp.team.option.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 등록된 전체 상품 리스트
    public List<Product> findAllProducts() {
        return productMapper.findAllProducts();
    }

    public Product findProductById(int productNo) {
        return productMapper.findProductById(productNo);
    }

    @Transactional
    public void insertProduct(Product product) {
        // Product 삽입
        productMapper.insertProduct(product);

        // 삽입된 Product의 productNo가 제대로 설정되었는지 확인
        if (product.getProductNo() == null || product.getProductNo() == 0) {
            throw new RuntimeException("Product ID is not generated correctly.");
        }
        System.out.println("Generated Product ID: " + product.getProductNo());
        // 각 옵션에 productNo 설정 후 삽입
        for (Option option : product.getOptions()) {
            option.setProductNo(product.getProductNo()); // 방금 삽입한 Product의 ID 설정
            System.out.println("Inserting Option with Product ID: " + option.getProductNo());
            productMapper.insertOption(option);
        }
    }

    // findProductByNo
    public Product findProductByNo(int productNo) {
        return productMapper.findProductById(productNo);
    }

    // updateProduct
//    @Transactional
//    public void updateProduct(Product product) {
//        // Product 업데이트
//        productMapper.updateProduct(product);
//
//        // 옵션 업데이트
//        for (Option option : product.getOptions()) {
//            // 옵션의 productNo가 제대로 설정되었는지 확인
////            if (option.getProductNo() == null || option.getProductNo() == 0) {
////                throw new RuntimeException("Product ID is not generated correctly.");
////            }
//            System.out.println("Generated Product ID: " + option.getProductNo());
//            // 각 옵션에 productNo 설정 후 삽입
//            productMapper.insertOption(option);
//        }
//    }

    public void updateProduct(Product product) {
        if (product.getProductNo() == null) {
            throw new IllegalArgumentException("Product number cannot be null");
        }
        productMapper.updateProduct(product);
    }

//    @Transactional
//    public void updateProduct(Product product) {
//        // Product 업데이트
//        productMapper.updateProduct(product);
//
//        // 옵션 업데이트
//        for (Option option : product.getOptions()) {
//            // Check if the product exists
//            Product existingProduct = productMapper.findProductById(product.getProductNo());
//            if (existingProduct == null) {
//                // If the product does not exist, create it
//                productMapper.insertProduct(product);
//            }
//            // Now you can insert the option
//            productMapper.insertOption(option);
//        }
//    }

//    @Transactional
    public void deleteProduct(int productNo) {
        // 해당 제품 번호 옵션 지우기
        productMapper.deleteOptions(productNo);
        // 해당 제품 번호 제품 지우기
        productMapper.deleteProduct(productNo);
    }

    public List<Product> findProductsByCategory(String category) {
        return productMapper.findProductsByCategory(category);
    }

    public Object getAllProducts() {
        return productMapper.findAllProducts();
    }
}
