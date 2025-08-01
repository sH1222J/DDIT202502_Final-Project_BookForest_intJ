package com.bookforest.project_bookforest_intj.cart.entity;

import com.bookforest.project_bookforest_intj.user.vo.UserVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {


    private Long cartId;

    private UserVO user;

    private List<CartItem> cartItems = new ArrayList<>();

    public static Cart createCart(UserVO user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cart;
    }
}
