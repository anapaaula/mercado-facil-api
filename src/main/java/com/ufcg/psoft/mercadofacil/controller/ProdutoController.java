package com.ufcg.psoft.mercadofacil.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder ucBuilder) {
		String prodID = produtoService.addProduto(produtoDTO);
		return new ResponseEntity<>("Produto cadastrado com ID:" + prodID, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") String id) {
		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
			
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutos(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "with-lote", required = false) String lote) {
		List<Produto> produtos;

		if(nome == null && lote == null){
			produtos = this.produtoService.listarProdutos();
		}else if(nome == null && lote != null){
			produtos = this.produtoService.prodsWithLote();
		}else if(nome != null && lote != null) {
			produtos = this.produtoService.prodLoteByName(nome);
		}else if(nome != null && lote == null){
			produtos = this.produtoService.prodByName(nome);
		}else{
			produtos = new ArrayList<>();
		}

		return new ResponseEntity<>(produtos, HttpStatus.OK);

	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> uptadeProduto(@RequestBody ProdutoDTO produtoDTO, @PathVariable("id") String id){
		Produto produto;
		try {
			produto = this.produtoService.getProdutoById(id);
			this.produtoService.updateProduto(produtoDTO,produto);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>("Produto atualizado com sucesso!", HttpStatus.OK);

	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduto(@PathVariable("id") String id) {
		try{
		produtoService.deleteProduto(id);
		return new ResponseEntity<String>("Produto Removido com sucesso", HttpStatus.OK);
		}catch (ProductNotFoundException e){
			return new ResponseEntity<String>("Produto inexistente", HttpStatus.NO_CONTENT);
		}

	}
}
