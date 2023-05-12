package com.ufcg.psoft.mercadofacil.dto;

import java.util.Date;

public class LoteDTO {
	
	private String idProduto;
	
	private int quantidade;

	private Date dataValidade;

	private Date dataFabricacao;


	public LoteDTO(String idProduto, int quantidade, Date dataValidade, Date dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
		this.dataValidade = dataValidade;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
	}
	
	public String getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Date getDataValidade() {
		return this.dataValidade;
	}

	public Date getDataFabricacao() {
		return this.dataFabricacao;
	}
}
