package com.myapp.team.cart;

import com.myapp.team.user.model.User;
import com.myapp.team.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    private final CartMapper cartMapper;

    @Autowired
    public CartService(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }


    @Transactional
    public void addToCart(Cart cart) {
        // 장바구니에 이미 있는 상품인지 확인
        Cart existingCart = cartMapper.findCartByProductNo(cart.getProductNo(), cart.getUserNo());
        if (existingCart != null) {
            // 이미 있는 상품이면 수량만 증가
            existingCart.setCartCount(existingCart.getCartCount() + 1);
            cartMapper.updateCart(existingCart);
        } else {
            // 없는 상품이면 새로 추가
            cart.setCartCount(1); // 기본 값 설정
            cartMapper.insertCart(cart);
        }
    }

    // 사용자 번호로 장바구니 찾기
    @Transactional
    public List<Cart> findCartsByUserNo(int userNo) {
        return cartMapper.findCartsByUserNo(userNo);
    }

    @Transactional
    public List<Cart> findAllCartItem(int userNo) {
        return cartMapper.findAllCartItem(userNo);
    }

    // 장바구니 추가
    @Transactional
    public void insertCart(Cart cart) {
//        cart.setCartCount(1); // 기본 값 설정
        cartMapper.insertCart(cart);
    }

    // 장바구니 수정 (체크박스)
    public void updateCart(Cart cart) {
        cartMapper.updateCart(cart);
    }

    // 장바구니 삭제 (체크박스)
    public void deleteCart(int cartNo) {
        cartMapper.deleteCart(cartNo);
    }
}