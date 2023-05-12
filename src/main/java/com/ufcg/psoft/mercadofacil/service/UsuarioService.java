package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.UsuarioDTO;
import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.CarrinhoDeCompras;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String addUsuario(UsuarioDTO usuarioDTO, int status){
        Usuario usuario = new Usuario(usuarioDTO.getCpf(), usuarioDTO.getNome(), usuarioDTO.getEndereco(), usuarioDTO.getTelefone(), this.tipoUsuario(status));
        this.usuarioRepository.addUsuario(usuario);

        return usuario.getCpf();

    }

    public void updateUsuario(UsuarioDTO usuarioDTO, Usuario usuario, int status) {
        usuario.setEndereco(usuarioDTO.getEndereco());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setStatus(this.tipoUsuario(status));

        usuarioRepository.editUsuario(usuario);

    }

    public Usuario getUsuarioByCpf(String cpf) throws UsuarioNotFoundException {
        Usuario usuario = this.usuarioRepository.getUsuario(cpf);
        if(usuario == null) throw new UsuarioNotFoundException("Usuario: " + cpf + " não encontrado");

        return usuario;

    }

    public void deleteUsuario(String id) throws UsuarioNotFoundException {
        this.usuarioRepository.delUsuario(id);
    }

    public List<String> getUsuariosByNameAndCpf() {
       List<Usuario> usuarios = this.listarUsuarios();
       return usuarios.stream().map(usuario -> usuario.getNome() + " - " + usuario.getNome()).collect(Collectors.toList());
    }

    public List<Usuario> listarUsuarios() {return new ArrayList<Usuario>(this.usuarioRepository.getAll());}


    public CarrinhoDeCompras getCarrinho(String cpf) throws UsuarioNotFoundException {
        return this.usuarioRepository.getUsuario(cpf).getCarrinho();
    }


    public void editarUser(Usuario user){
        this.usuarioRepository.editUsuario(user);
    }

    public String tipoUsuario(int status){
        String str = "NORMAL";
        if(status == 2){
            str = "ESPECIAL";
        }else if(status == 3){
            str = "PREMIUM";
        }
        return str;

    }


}
