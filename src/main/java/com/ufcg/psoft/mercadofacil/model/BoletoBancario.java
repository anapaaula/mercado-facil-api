package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.Interfaces.Pagamento;

public class BoletoBancario implements Pagamento {
    private String FORMADEPAGAMENTO = "Boleto Bancario";


    @Override
    public double acrescimosPercentuais(Double valorCompra) {
        return valorCompra;
    }

    @Override
    public String getDescricao(Double precoTotal) {
        return "Forma de pagamento: " + this.getFORMADEPAGAMENTO();
    }
    @Override
    public String getFORMADEPAGAMENTO(){
        return this.FORMADEPAGAMENTO;
    }
}
