package com.bookforest.project_bookforest_intj.cart.entity;

import com.bookforest.project_bookforest_intj.book.entity.Book;
import lombok.Data;

@Data
public class CartItem {

    private Long cartItemId;

    private Cart cart;

    private Book book;

    private int quantity;

    public static CartItem createCartItem(Cart cart, Book book, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
