package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.Interfaces.Pagamento;

public class CartaoDeCredito implements Pagamento {
    private String FORMADEPAGAMENTO = "Cartão de Credito";


    @Override
    public double acrescimosPercentuais(Double valorCompra) {
        return valorCompra * 0.05 ;
    }

    @Override
    public String getDescricao(Double precoTotal) {
        return "Forma de pagamento: " + this.getFORMADEPAGAMENTO() + " TEVE ACRÉSCIMO DE 5% R$" + this.acrescimosPercentuais(precoTotal);
    }

    @Override
    public String getFORMADEPAGAMENTO(){
        return this.FORMADEPAGAMENTO;
    }
}
