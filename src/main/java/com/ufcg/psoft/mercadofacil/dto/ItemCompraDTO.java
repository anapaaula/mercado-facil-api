package com.ufcg.psoft.mercadofacil.dto;

public class ItemCompraDTO {
    private String cpfCliente;
    private String idProduto;


    public ItemCompraDTO(String cpfCliente, String idProduto){
        this.cpfCliente = cpfCliente;
        this.idProduto = idProduto;

    }
    public String getCpfCliente() {
        return this.cpfCliente;
    }

    public String getIdProduto() {
        return this.idProduto;
    }

}
