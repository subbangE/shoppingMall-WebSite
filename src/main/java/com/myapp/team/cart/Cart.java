package com.myapp.team.cart;

import com.myapp.team.option.Option;
import com.myapp.team.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class Cart {

    private int cartNo;  // 장바구니 번호
    private int userNo;  // 사용자 번호
    private int productNo;  // 상품 번호
    private int optionNo;  // 옵션 번호

    private Product product; // 상품 정보
    private Option option;   // 옵션 정보

    private String productName; // 상품명
    private int productPrice;   // 상품 가격
//    private String options;     // 옵션명

    private int cartCount;  // 상품 수량 (내가 살 수량임 = default 1)
    private int cartPrice;  // 상품 가격 * 수량 (결제 금액)

    public void setUserNo(int userNo) {
    }

    public void setProductName(String productName) {
    }

    public void setOptions(String options) {
    }

    public void setProductPrice(int productPrice) {
    }

    public void setCartCount(int i) {

    }
}


