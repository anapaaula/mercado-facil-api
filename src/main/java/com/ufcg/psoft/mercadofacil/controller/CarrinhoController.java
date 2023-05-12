package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.NotFoundProductInCart;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.*;
import com.ufcg.psoft.mercadofacil.service.CarrinhoDeComprasService;
import com.ufcg.psoft.mercadofacil.service.CompraService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoController {

    @Autowired
    private CarrinhoDeComprasService cartComprasService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioService usuarioService;


    //Recebe um DTO
    // {
    // "cpf":
    // "idProduto":
    //}
    @RequestMapping(value = "/carrinho/", method = RequestMethod.POST)
    public ResponseEntity<?> addProdutoNoCarrinho(@RequestBody ItemCompraDTO itemCompraDTO) throws ProductNotFoundException, UsuarioNotFoundException {
        try {
           this.produtoService.produtoWithLote(itemCompraDTO.getIdProduto());
       }catch (ProductNotFoundException e){
         return new ResponseEntity<>("Produto indisponível!", HttpStatus.NO_CONTENT);
       }
        try{
            this.usuarioService.getUsuarioByCpf(itemCompraDTO.getCpfCliente());
        }catch (UsuarioNotFoundException e){
            return new ResponseEntity<>("Usuário inexistente!", HttpStatus.NO_CONTENT);

        }
        this.cartComprasService.addItemNoCarrinho(itemCompraDTO);
        return new ResponseEntity<>("Produto adicionado no carrinho!", HttpStatus.OK);

    }
    @RequestMapping(value = "/carrinho/", method = RequestMethod.PUT)
    public ResponseEntity<?> removerProdutoDoCarrinho(@RequestBody ItemCompraDTO itemCompraDTO) {
      try {
          this.cartComprasService.removerItemDoCarrinho(itemCompraDTO);
          return new ResponseEntity<>("Produto removido do carrinho!", HttpStatus.OK);
      }catch (NotFoundProductInCart | ProductNotFoundException | UsuarioNotFoundException e){
          return new ResponseEntity<>("Produto não foi removido, pois não está no carrinho!", HttpStatus.NO_CONTENT);

      }
    }


}
