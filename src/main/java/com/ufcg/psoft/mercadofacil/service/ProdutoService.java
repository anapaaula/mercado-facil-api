package com.ufcg.psoft.mercadofacil.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ufcg.psoft.mercadofacil.model.CarrinhoDeCompras;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository prodRep;

	@Autowired
	private LoteService loteService;

	public List<Produto> listarProdutos() {
		return new ArrayList<Produto>(prodRep.getAll());
	}
	
	public List<Produto> listarProdsLoteByName(String nome) {
		List<Produto> prods = getProdsWithLote();
		return getProdsByName(nome, prods);
	}

	public List<Produto> listarProdsByName(String nome) {
		return getProdsByName(nome, this.prodRep.getAll());
	}

	private List<Produto> getProdsByName(String nome, Collection<Produto> prods) {
		List<Produto> prodsResult = new ArrayList<Produto>();
		for (Produto produto : prods) {
			if(produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
				prodsResult.add(produto);
			}
		}	
		return(prodsResult);
	}
	
	private List<Produto> getProdsWithLote() {
		List<Produto> prods = new ArrayList<Produto>();
		for (Lote lote : this.loteRep.getAll()) {
			prods.add(lote.getProduto());
		}
		return(prods);
	}

	public String addProduto(ProdutoDTO prodDTO) {
		Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		
		this.prodRep.addProduto(produto);
		
		return produto.getId();
	}
	
	public Produto getProdutoById(String id) throws ProductNotFoundException {
		Produto prod = this.prodRep.getProd(id);
		if(prod == null) throw new ProductNotFoundException("Produto: " + id + " não encontrado");
		
		return(prod);
	}

	public void updateProduto(ProdutoDTO prodDTO, Produto prod){
		prod.setNome(prodDTO.getNome());
		prod.setFabricante(prodDTO.getFabricante());
		prod.setPreco(prodDTO.getPreco());

		this.prodRep.editProd(prod);

	}

	public void deleteProduto(String id) throws ProductNotFoundException{
		if(this.produtoWithLote(id)) {
			Lote lote = this.loteDoProduto(id);
			this.loteRep.delLot(lote.getId());
		}
		this.prodRep.delProd(id);
	}
	
	public boolean produtoWithLote(String id) throws ProductNotFoundException {
		List<Produto> produtosLote = this.getProdsWithLote();
		Produto produto = this.getProdutoById(id);
		
		return produtosLote.contains(produto);
		
	}
	
	public Lote loteDoProduto(String idProduto) {
		List<Lote> lotes = new ArrayList<Lote>(this.loteRep.getAll());

		for (Lote lote : lotes) {
			if(lote.getProduto().getId().equals(idProduto)) {
				return lote;
			}
			
		}
		return null;
	}

	public List<Produto> prodsWithLote(){
		return this.getProdsWithLote();
	}

	public List<Produto> prodByName(String nome){
		return this.listarProdsByName(nome);

	}

	public  List<Produto> prodLoteByName(String nome){
		return this.listarProdsLoteByName(nome);
	}

	public List<Lote> lotesDoProduto(String idProduto) {
		List<Lote> lotes = new ArrayList<Lote>(this.loteRep.getAll());
		List<Lote> lotesDoProduto = new ArrayList<Lote>();

		for (Lote lote : lotes) {
			if(lote.getProduto().getId().equals(idProduto)) {
				lotesDoProduto.add(lote);
			}

		}
		return lotesDoProduto;
	}

    public void retornarProdutosProLote(CarrinhoDeCompras carrinho) {
		List<ItemCompra> carr = carrinho.getCarrinhoDeCompras();
		for (ItemCompra itemCompra: carr) {
			this.loteService.adicionarQuantidadeNoLote(this.loteDoProduto(itemCompra.getProduto().getId()));
		}
    }
}
