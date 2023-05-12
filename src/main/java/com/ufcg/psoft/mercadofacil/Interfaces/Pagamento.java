package com.ufcg.psoft.mercadofacil.Interfaces;

public interface Pagamento {

    double acrescimosPercentuais(Double valorCompra);

    String getDescricao(Double precoTotal);

    String getFORMADEPAGAMENTO();

}
