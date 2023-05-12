package com.ufcg.psoft.mercadofacil.model;

import com.ufcg.psoft.mercadofacil.Interfaces.Pagamento;

import java.util.UUID;

public class Compra {
    private CarrinhoDeCompras carrinho;
    private Double preco;
    private Usuario cliente;
    private String id;
    private Pagamento formaDePagamento;
    private double descontoCliente;


    public Compra(CarrinhoDeCompras carrinho, Usuario cliente, Pagamento formaDePagamento, double descontoCliente){
        this.id = UUID.randomUUID().toString();
        this.carrinho = carrinho;
        this.cliente = cliente;
        this.formaDePagamento = formaDePagamento;
        this.descontoCliente = descontoCliente;
        this.preco = this.getPreco();

    }

    public Double getPreco(){
        double preco = 0;
        for (ItemCompra itemCompra: this.carrinho.getCarrinhoDeCompras()) {
            preco += itemCompra.getProduto().getPreco();
        }
        return preco;
    }

    public Double getPrecoTotal(){
        return (this.preco - (this.preco * this.descontoCliente)) + this.formaDePagamento.acrescimosPercentuais(this.preco);
    }

    public String descricaoDaCompra(){
        String descricaoCompra = "";
        for (ItemCompra itemCompra: this.carrinho.getCarrinhoDeCompras()) {
            descricaoCompra += " PRODUTO: " + itemCompra.getProduto().getNome() + " ID: " + itemCompra.getProduto().getId() + " PREÇO: " + itemCompra.getProduto().getPreco() + " FABRICANTE: " + itemCompra.getProduto().getFabricante() + "\n";
        }


        if(this.descontoCliente == 0.1){
            descricaoCompra += "ID DA COMPRA: " + this.getId() + "\nCLIENTE: " + this.getCliente().getNome() +
                    "\nPREÇO TOTAL DA COMPRA: R$" + this.getPrecoTotal() + ". RECEBEU 10% DE DESCONTO POR SER CLIENTE: "
                    + this.getCliente().getStatus() + ". PORTANTO TEVE UMA REDUÇÃO NO VALOR DE R$" + (this.getPreco() * this.descontoCliente)
                    + " " + this.formaDePagamento.getDescricao(this.getPreco());
        }else{
            descricaoCompra += "ID DA COMPRA: " + this.getId() + "\nCLIENTE: " + this.getCliente().getNome() +
                    "\nPREÇO TOTAL DA COMPRA: R$" + this.getPrecoTotal() + " " + this.formaDePagamento.getDescricao(this.getPreco());
        }

        return descricaoCompra;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public CarrinhoDeCompras getCarrinho() {
        return carrinho;
    }

    public String getId() {
        return id;
    }
}
