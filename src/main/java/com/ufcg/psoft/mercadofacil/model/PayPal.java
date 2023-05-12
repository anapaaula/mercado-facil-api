package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.Interfaces.Pagamento;

public class PayPal implements Pagamento {
    private String FORMADEPAGAMENTO = "PAYPAL";

    @Override
    public double acrescimosPercentuais(Double valorCompra) {
        return valorCompra * 0.02;
    }

    @Override
    public String getDescricao(Double precoTotal) {
        return "FORMA DE PAGAMENTO: " + this.getFORMADEPAGAMENTO() + " TEVE ACRÉSCIMO DE 2% R$" + (this.acrescimosPercentuais(precoTotal));
    }

    @Override
    public String getFORMADEPAGAMENTO(){
        return this.FORMADEPAGAMENTO;
    }
}
