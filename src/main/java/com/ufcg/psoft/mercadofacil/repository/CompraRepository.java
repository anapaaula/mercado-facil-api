package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CompraRepository {
    private Map<String, Compra> compras;

    public CompraRepository(){this.compras = new HashMap<String,Compra>();}

    public String addCompra(Compra compra, String cpfCliente){
        this.compras.put(cpfCliente, compra);
        return(compra.descricaoDaCompra());
    }

    public Collection<Compra> getAll() {
        return compras.values();
    }

    public Compra getCompra(String id) {
        return this.compras.get(id);
    }

    public void delCompra(String id) {
        this.compras.remove(id);
    }

    public Map<String,Compra> mapCompras(){return this.compras;}



}
