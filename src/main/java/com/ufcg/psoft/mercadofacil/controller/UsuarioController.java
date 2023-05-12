package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.UsuarioDTO;
import com.ufcg.psoft.mercadofacil.exception.UsuarioNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Usuario;
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
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/usuario/", method = RequestMethod.POST)
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDTO usuarioDTO, @RequestParam(value = "status", defaultValue = "1") String status, UriComponentsBuilder ucBuilder) {
        if(status.equals("1") || status.equals("2") || status.equals("3")){
        try {
            this.usuarioService.getUsuarioByCpf(usuarioDTO.getCpf());
            return new ResponseEntity<>("Usuário já cadastrado!", HttpStatus.NO_CONTENT);
        } catch (UsuarioNotFoundException e) {
            String cpfUsuario = this.usuarioService.addUsuario(usuarioDTO, Integer.parseInt(status));
            return new ResponseEntity<>("Usuário com o cpf " + cpfUsuario + ", cadastrado com sucesso!", HttpStatus.CREATED);

        }
        }else{
            return new ResponseEntity<>("Status de usuario inválido!", HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "/usuarios/{cpf}", method = RequestMethod.PUT)
    public ResponseEntity<?> uptadeUsuario(@RequestBody UsuarioDTO usuarioDTO,@RequestParam(value = "status", defaultValue = "1") String status, @PathVariable("cpf") String cpf){
        if(status.equals("1") || status.equals("2") || status.equals("3")){
            Usuario usuario;
            try {
                usuario = this.usuarioService.getUsuarioByCpf(cpf);
            } catch (UsuarioNotFoundException e) {
                return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NO_CONTENT);
            }

            this.usuarioService.updateUsuario(usuarioDTO,usuario, Integer.parseInt(status));

            return new ResponseEntity<>("Usuário atualizado com sucesso!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Status de usuario inválido!", HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/usuario/{cpf}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduto(@PathVariable("cpf") String cpf){
        try {
            this.usuarioService.deleteUsuario(cpf);
            return new ResponseEntity<>("Usuário deletado!", HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NO_CONTENT);
        }

    }

    @RequestMapping(value = "/usuario/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarUsuario(@PathVariable("cpf") String cpf) {
        Usuario usuario;
        try {
            usuario = usuarioService.getUsuarioByCpf(cpf);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public ResponseEntity<?> listarUsuarios() {
        List<String> usuarios = this.usuarioService.getUsuariosByNameAndCpf();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

}
