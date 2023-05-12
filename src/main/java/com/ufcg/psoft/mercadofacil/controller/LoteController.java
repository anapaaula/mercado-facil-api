package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.service.LoteService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {
	
	@Autowired
	private LoteService loteService;
	@Autowired
	private ProdutoService produtoService;

	// RECEBE UMA STRING QUE É O ID DO PRODUTO, E UM INTEIRO QUE É A QUANTIDADE
	@RequestMapping(value = "/lote/", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@RequestParam(value = "idProduto", required = false) String idProduto, @RequestParam(value = "quantidade", required = false) int quantidade) throws LoteNotFoundException {
		try {
			this.produtoService.getProdutoById(idProduto);
		}catch (ProductNotFoundException e) {
			return new ResponseEntity<>("Lote não cadastrado, porque produto não existe!" , HttpStatus.NO_CONTENT);
		}

		String loteID = this.loteService.addLote(idProduto,quantidade);
		return new ResponseEntity<>("Lote cadastrado com ID:" +  loteID, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarLote(@PathVariable("id") String id) {
		Lote lote;
		try {
			lote = loteService.getLoteById(id);
		} catch (LoteNotFoundException e) {
			return new ResponseEntity<>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}
			
		return new ResponseEntity<>(lote, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lotes/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> uptadeLote(@RequestBody LoteDTO loteDTO, @PathVariable("id") String id){
		Lote lote;
		try {
			lote = this.loteService.getLoteById(id);
		} catch (LoteNotFoundException e) {
			return new ResponseEntity<>("Lote não encontrado", HttpStatus.NO_CONTENT);
		}

		try{
			this.loteService.updateLote(loteDTO, lote);
			return new ResponseEntity<>("Lote atualizado com sucesso!", HttpStatus.OK);
		}catch (ProductNotFoundException e){
			return new ResponseEntity<>("Produto inexistente!", HttpStatus.NO_CONTENT);
		}
	}


	@RequestMapping(value = "/lote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLote(@PathVariable("id") String id){
		this.loteService.deleteLote(id);

		return new ResponseEntity<>("Lote deletado!", HttpStatus.OK);

	}
	
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotes() {
		List<Lote> lotes = this.loteService.listarLotes();
		
		return new ResponseEntity<>(lotes, HttpStatus.OK);
		
	}

}
