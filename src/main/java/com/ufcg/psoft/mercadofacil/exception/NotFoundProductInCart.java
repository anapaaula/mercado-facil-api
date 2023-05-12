package com.ufcg.psoft.mercadofacil.exception;

public class NotFoundProductInCart extends Exception {
    private static final long serialVersionUID = 1L;

	public NotFoundProductInCart(String msg) {
        super(msg);
    }

}
