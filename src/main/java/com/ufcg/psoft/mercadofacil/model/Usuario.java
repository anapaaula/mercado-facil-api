package com.ufcg.psoft.mercadofacil.model;

public class Usuario {

    private String cpf;
    private String nome;
    private String endereco;
    private String telefone;
    private CarrinhoDeCompras carrinho;
    private String status;

    public Usuario(String cpf, String nome,String endereco, String telefone, String status){
        this.cpf = cpf;
        this.endereco = endereco;
        this.nome = nome;
        this.telefone = telefone;
        this.status = status;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public CarrinhoDeCompras getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(CarrinhoDeCompras carrinho) {
        this.carrinho = carrinho;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int quantidadeDeProdutosNoCarrinho(){
        return this.carrinho.getCarrinhoDeCompras().size();
    }
}
