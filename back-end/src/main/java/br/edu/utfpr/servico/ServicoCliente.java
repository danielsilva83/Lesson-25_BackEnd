/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.utfpr.servico;

/**
 *
 * @author Daniel
 */

import br.edu.utfpr.dto.ClienteDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.dto.PaisDTO;
import br.edu.utfpr.excecao.NomeClienteMenor5CaracteresException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ServicoCliente {
private List<ClienteDTO> clientes;
private List<PaisDTO> paises;

   public ServicoCliente() {
        paises = Stream.of(
            PaisDTO.builder().id(1).nome("Brasil").sigla("BR").codigoTelefone(55).build(),
           
        ).collect(Collectors.toList());
        
       clientes = Stream.of(
            ClienteDTO.builder().id(1).nome("Daniel").idade(1).limiteCredito(10).telefone("43998989").pais((PaisDTO) paises).build(),
            ClienteDTO.builder().id(1).nome("Jhon Silva").idade(19).limiteCredito(110).telefone("43998555").pais((PaisDTO) paises).build(),
            ClienteDTO.builder().id(1).nome("Bob Goncalves").idade(18).limiteCredito(1110).telefone("43998777").pais((PaisDTO) paises).build()
        ).collect(Collectors.toList());
    }

    @GetMapping ("/servico/cliente")
    public ResponseEntity<List<ClienteDTO>> listar() {
    // public List<PaisDTO> listar() {
        // return paises;
        return ResponseEntity.ok(clientes);
    }

    @GetMapping ("/servico/cliente/{id}")
    public ResponseEntity<ClienteDTO> listarPorId(@PathVariable int id) {
        Optional<ClienteDTO> clienteEncontrado = clientes.stream().filter(p -> p.getId() == id).findAny();

        return ResponseEntity.of(clienteEncontrado);
    }

    @PostMapping ("/servico/cliente")
    public ResponseEntity<ClienteDTO> criar (@RequestBody ClienteDTO cliente) {

        cliente.setId(clientes.size() + 1);
        clientes.add(cliente);

        return ResponseEntity.status(201).body(cliente);
    }

    @DeleteMapping ("/servico/cliente/{id}")
    public ResponseEntity excluir (@PathVariable int id) {
        
        if (clientes.removeIf(cliente -> cliente.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping ("/servico/cliente/{id}")
    public ResponseEntity<ClienteDTO> alterar (@PathVariable int id, @RequestBody ClienteDTO cliente ) {
        Optional<ClienteDTO> clienteExistente = clientes.stream().filter(p -> p.getId() == id).findAny();

        clienteExistente.ifPresent(c -> {
            try {
                c.setNome(cliente.getNome());
            } catch (NomeClienteMenor5CaracteresException ex) {
                Logger.getLogger(ServicoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            c.setIdade(cliente.getIdade());
            c.setTelefone(cliente.getTelefone());
            c.setLimiteCredito(cliente.getLimiteCredito());
            c.setPais(cliente.getPais());
            
        });

        return ResponseEntity.of(clienteExistente);
    }
}
