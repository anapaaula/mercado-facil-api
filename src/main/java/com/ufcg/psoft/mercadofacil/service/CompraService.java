package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.BoletoBancario;
import com.ufcg.psoft.mercadofacil.model.CartaoDeCredito;
import com.ufcg.psoft.mercadofacil.Interfaces.Pagamento;
import com.ufcg.psoft.mercadofacil.model.PayPal;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CompraService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProdutoService produtoService;


    public String compra(String cpf_cliente, int opcaoDePagamento) throws UsuarioNotFoundException {
        Usuario cliente = this.usuarioService.getUsuarioByCpf(cpf_cliente);
        Compra compra = new Compra(cliente.getCarrinho(), cliente,this.verificaFormaDePagamento(opcaoDePagamento),possivelDesconto(cliente));
        String descricaoCompra = this.compraRepository.addCompra(compra, cliente.getCpf());
        cliente.setCarrinho(null);
        this.usuarioService.editarUser(cliente);
        return descricaoCompra;
    }

    public void cancelarcompra(String cpf_cliente) throws UsuarioNotFoundException {
        this.produtoService.retornarProdutosProLote(this.compraRepository.getCompra(cpf_cliente).getCarrinho());
        Usuario usuario = this.usuarioService.getUsuarioByCpf(cpf_cliente);
        usuario.setCarrinho(null);
        this.usuarioService.editarUser(usuario);
        this.compraRepository.delCompra(cpf_cliente);
    }

    public List<Compra> listarCompras(String cpf_cliente){
        Map<String,Compra> compras = this.compraRepository.mapCompras();
        List<Compra> comprasList = new ArrayList<>();
        for (Map.Entry<String,Compra> com : compras.entrySet()) {
            if(com.getKey().equals(cpf_cliente)){
                comprasList.add(com.getValue());
            }

        }
        return comprasList;
    }


    public String descricaoCompra(String id_compra) {
        Map<String,Compra> compras = this.compraRepository.mapCompras();

        for (Map.Entry<String,Compra> com : compras.entrySet()) {
            if(com.getValue().getId().equals(id_compra)){
                return com.getValue().descricaoDaCompra();
            }
        }
        return "Não tem nenhuma compra com esse id!";
    }

    public Pagamento verificaFormaDePagamento(int opcaoPagamento){
        Pagamento formaDePagamento = new BoletoBancario();
        if(opcaoPagamento == 2){
            formaDePagamento = new CartaoDeCredito();

        }else if(opcaoPagamento == 3){
            formaDePagamento = new PayPal();
        }

        return formaDePagamento;

    }

    public double possivelDesconto(Usuario cliente){
        if(cliente.getStatus().equals("ESPECIAL") && cliente.quantidadeDeProdutosNoCarrinho() > 10){
            return 0.1;
        }else if(cliente.getStatus().equals("PREMIUM") && cliente.quantidadeDeProdutosNoCarrinho() > 5){
            return 0.1;
        }
        return 0;
    }
}
