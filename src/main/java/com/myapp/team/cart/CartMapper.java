package com.myapp.team.cart;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    List<Cart> findCartsByUserNo(@Param("userNo") int userNo);

    void insertCart(Cart cart);

    void updateCart(Cart cart);

    void deleteCart(@Param("cartNo") int cartNo);

    List<Cart> findAllCartItem(int userNo);

    Cart findCartByProductNo(int productNo, int userNo);



    // 글벗 Mapper
//    void updateQuantityInCart(String userNo, int cartCount, int optionNo);
//    void deleteItemFromCart(String userNo, int optionNo);

}