package com.ufcg.psoft.mercadofacil.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;

import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;


@Service
public class LoteService {

	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository produtoRep;

	
	public String addLote(String idProduto, int quantidade) throws LoteNotFoundException{
		Produto prod = this.produtoRep.getProd(idProduto);
		Lote lote = new Lote(prod, quantidade);
		this.loteRep.addLote(lote);
		return lote.getId();
	}
	
	public Lote getLoteById(String id) throws LoteNotFoundException{
		Lote lote = this.loteRep.getLote(id);
		if(lote == null) throw new LoteNotFoundException("Lote: " + id + " não encontrado");
		return(lote);
		
	}
	
	public void updateLote(LoteDTO loteDTO, Lote lote) throws ProductNotFoundException {
		lote.setDataFabricacao(loteDTO.getDataFabricacao());
		lote.setDataValidade(loteDTO.getDataValidade());
		lote.setQuantidade(loteDTO.getQuantidade());

		Produto produto = produtoRep.getProd(loteDTO.getIdProduto());

		if(produto == null) throw new ProductNotFoundException("Produto: " + loteDTO.getIdProduto() + " não encontrado");

		lote.setProduto(produto);

		this.loteRep.editLote(lote);

	}
	
	public void deleteLote(String id) {
		this.loteRep.delLot(id);
		
	}
	
	public List<Lote> listarLotes() {
		return new ArrayList<Lote>(loteRep.getAll());
	}	

	public Lote loteComMenorValidade(List<Lote> lotes){
		Date dataLoteComMenorValidade = lotes.get(0).getDataValidade();
		Lote loteComMenorValidade = lotes.get(0);

		for (Lote lote: lotes) {
			if(lote.getDataValidade().compareTo(dataLoteComMenorValidade) < 0){
				dataLoteComMenorValidade = lote.getDataValidade();
				loteComMenorValidade = lote;
			}

		}
		return loteComMenorValidade;
	}

	public void removerQuantidadeDoLote(Lote lote){
		int qtdLote = lote.getQuantidade() - 1;
		lote.setQuantidade(qtdLote);

	}

	public void adicionarQuantidadeNoLote(Lote lote){
		int qtdLote = lote.getQuantidade() + 1;
		lote.setQuantidade(qtdLote);

	}
}
