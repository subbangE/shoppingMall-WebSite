package com.myapp.team.option;

import com.myapp.team.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Option {

    private int optionNo;  // 옵션 번호 (한 상품에 여러 옵션 가능)
    private String optionName; // 옵션 이름 (예: 색상, 사이즈)
    private String optionValue;  // 옵션 값 (예: 빨강, 파랑, M, L)
    private int optionCount;  // 옵션 상품 수량

    private int productNo;  // 옵션 상품이 속한 상품


    public void setProduct(Product product) {
    }

    public void setProductId(int id) {
    }

}

