package com.ufcg.psoft.mercadofacil.dto;

public class UsuarioDTO {
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;

    public UsuarioDTO(String cpf,String nome, String endereco, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }
    public String getTelefone() {
        return telefone;
    }

}
