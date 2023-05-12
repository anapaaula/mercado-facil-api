package com.ufcg.psoft.mercadofacil.repository;

import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UsuarioRepository {

    private Map<String, Usuario> usuarios;

    public UsuarioRepository() {this.usuarios = new HashMap<String,Usuario>();}

    public String addUsuario(Usuario usuario) {
        this.usuarios.put(usuario.getCpf(), usuario);
        return usuario.getCpf();
    }

    public Collection<Usuario> getAll() {return usuarios.values();}

    public Usuario getUsuario(String id) {return this.usuarios.get(id);}

    public void delUsuario(String id) {
        this.usuarios.remove(id);
    }

    public void editUsuario(Usuario usuario) {this.usuarios.replace(usuario.getCpf(), usuario);}

}
