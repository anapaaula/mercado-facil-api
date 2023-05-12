package com.ufcg.psoft.mercadofacil.model;

public class ItemCompra {
    private Produto produto;
    private Lote lote;


    public ItemCompra(Produto produto, Lote lote) {
        this.produto = produto;
        this.lote = lote;

    }

    public Produto getProduto() {
        return produto;
    }

    public Lote getLote() {
        return lote;
    }

}
