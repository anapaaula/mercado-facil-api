package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.exception.NotFoundProductInCart;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrinhoDeComprasService {

    @Autowired
    private UsuarioService userService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LoteService loteService;



    public void addItemNoCarrinho(ItemCompraDTO itemCompraDTO) throws UsuarioNotFoundException, ProductNotFoundException {
        Usuario user = this.userService.getUsuarioByCpf(itemCompraDTO.getCpfCliente());

        Produto prod = this.produtoService.getProdutoById(itemCompraDTO.getIdProduto());
        Lote lote = this.loteService.loteComMenorValidade(this.produtoService.lotesDoProduto(prod.getId()));

        ItemCompra itemCompra = new ItemCompra(prod, lote);

        this.loteService.removerQuantidadeDoLote(lote);

        if(this.userService.getCarrinho(itemCompraDTO.getCpfCliente()) == null) {
            CarrinhoDeCompras carrinho = new CarrinhoDeCompras();
            carrinho.addItemNoCarrinho(itemCompra);
            user.setCarrinho(carrinho);
            this.userService.editarUser(user);
        }else{
            this.userService.getCarrinho(itemCompraDTO.getCpfCliente()).addItemNoCarrinho(itemCompra);
        }
    }

    public void removerItemDoCarrinho(ItemCompraDTO itemCompraDTO) throws NotFoundProductInCart, ProductNotFoundException, UsuarioNotFoundException {
        Usuario user = this.userService.getUsuarioByCpf(itemCompraDTO.getCpfCliente());
        Produto prod = this.produtoService.getProdutoById(itemCompraDTO.getIdProduto());

        CarrinhoDeCompras carrinho = this.userService.getCarrinho(itemCompraDTO.getCpfCliente());

        List<ItemCompra> carrinhoDeCompras = carrinho.getCarrinhoDeCompras();

        if(!verificaSeOProdutoTaNoCarrinho(carrinhoDeCompras,prod)) throw new NotFoundProductInCart("Produto não está no carrinho!");

        if(carrinhoDeCompras.size() == 1){
            user.setCarrinho(null);
            this.userService.editarUser(user);
            this.loteService.adicionarQuantidadeNoLote(this.produtoService.loteDoProduto(prod.getId()));
        }else {
            carrinhoDeCompras.remove(prod);
            carrinho.setCarrinhoDeCompras(carrinhoDeCompras);
            user.setCarrinho(carrinho);
            this.userService.editarUser(user);
            this.loteService.adicionarQuantidadeNoLote(this.produtoService.loteDoProduto(prod.getId()));
        }
    }

    public boolean verificaSeOProdutoTaNoCarrinho(List<ItemCompra> carrinho, Produto prod){
        for (ItemCompra itemCompra: carrinho) {
            if(prod.getId().equals(itemCompra.getProduto().getId())){
                return true;
            }

        }
        return false;

    }




}
