package com.ufcg.psoft.mercadofacil.model;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeCompras {
    private List<ItemCompra> carrinhoDeCompras;

    public CarrinhoDeCompras() {
        this.carrinhoDeCompras = new ArrayList<ItemCompra>();
    }

    public void addItemNoCarrinho(ItemCompra itemCompra){
        this.carrinhoDeCompras.add(itemCompra);
    }

    public List<ItemCompra> getCarrinhoDeCompras() {
        return carrinhoDeCompras;
    }

    public void setCarrinhoDeCompras(List<ItemCompra> carrinhoDeCompras) {
        this.carrinhoDeCompras = carrinhoDeCompras;
    }
}


