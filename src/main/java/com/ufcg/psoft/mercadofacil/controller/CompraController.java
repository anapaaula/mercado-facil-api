package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.service.CompraService;
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
public class CompraController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CompraService compraService;


    @RequestMapping(value = "/carrinho/compra", method = RequestMethod.POST)
    public ResponseEntity<?> comprar(@RequestParam(value = "cpf_cliente") String cpf_cliente, @RequestParam(value = "opcaoDePagamento",defaultValue = "1") String opcaoDePagamento, UriComponentsBuilder ucBuilder) {
       if(opcaoDePagamento.equals("1") || opcaoDePagamento.equals("2")|| opcaoDePagamento.equals("3")){
        try {
            String descricaoCompra = this.compraService.compra(cpf_cliente, Integer.parseInt(opcaoDePagamento));
            return new ResponseEntity<>("Compra realizada com sucesso" + descricaoCompra, HttpStatus.OK);
        }catch (UsuarioNotFoundException e){
            return new ResponseEntity<>("Compra  não realizada" , HttpStatus.NO_CONTENT);
        }
       } else{
            return new ResponseEntity<>("Forma de pagamento invalida!", HttpStatus.NO_CONTENT);
            }
    }

    @RequestMapping(value = "/carrinho/compra", method = RequestMethod.DELETE)
    public ResponseEntity<?> cancelarCompra(@RequestParam("cpf_cliente") String cpf_cliente) throws UsuarioNotFoundException {
        try {
            this.usuarioService.getUsuarioByCpf(cpf_cliente);
        }catch (UsuarioNotFoundException e){
            return new ResponseEntity<>("Usuário inexistente!", HttpStatus.NO_CONTENT);
        }
        this.compraService.cancelarcompra(cpf_cliente);
        return new ResponseEntity<>("Compra cancelada!", HttpStatus.OK);
    }

    @RequestMapping(value = "/carrinho/compra", method = RequestMethod.GET)
    public ResponseEntity<?> listarCompras(@RequestParam("cpf_cliente") String cpf_cliente) {
        try{
            this.usuarioService.getUsuarioByCpf(cpf_cliente);
        }catch (UsuarioNotFoundException e){
            return new ResponseEntity<>("Usuário inexistente!", HttpStatus.NO_CONTENT);
        }
        List<Compra> compras = this.compraService.listarCompras(cpf_cliente);
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @RequestMapping(value = "/compra/{id_compra}", method = RequestMethod.GET)
    public ResponseEntity<?> verCompra(@PathVariable("id_compra") String id_compra) {
        String descricaoCompra = this.compraService.descricaoCompra(id_compra);
        return new ResponseEntity<>(descricaoCompra, HttpStatus.OK);
    }


    @RequestMapping(value = "/pagamento/", method = RequestMethod.GET)
    public ResponseEntity<?> verMetodosDePagamento(){
        return new ResponseEntity<>("Opção 1 - Boleto Bancario\nOpção 2 - Cartão de Credito\nOpção 3 - PayPal", HttpStatus.OK);

    }
}
