package com.example.demo.controller;


import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Spring HATEOAS agora fornece um WebMvcLinkBuilderque permite criar links
// apontando para classes de controlador

@Log4j2
@Controller
@ResponseBody
@CrossOrigin(origins = "*") // Requisições de qualquer origem
//@RestController
@RequestMapping(path = "/api")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    // Listar clientes
    @GetMapping(path = "/clientes")
    public ResponseEntity<List<Cliente>> findAll(){
        List<Cliente> clienteList = clienteService.findAll();
        // Criar os links de  cada cliente
        for(Cliente cliente : clienteList){
            // pegar o id
            Long id = cliente.getId();
            // Adicionando o link para cada cliente
            cliente.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
            // Adicionar os links de cada endereço
            cliente.getEndereco().add(linkTo(methodOn(EnderecoController.class)
                    .listarEnderecos()).withRel("Lista de enderecos"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteList);
    }

    // Listar clientes
    @GetMapping(path = "/clientes/paginacao")
    // Tem que passar como parâmetro um pageable e passar para o método do service
    public ResponseEntity<Page<Cliente>> findAllPaginado(Pageable pageable){
        Page<Cliente> clienteList = clienteService.findAllPaginado(pageable);
        // Criar os links de  cada cliente
        for(Cliente cliente : clienteList){
            // pegar o id
            Long id = cliente.getId();
            // Adicionando o link para cada cliente
            cliente.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
            // Adicionar os links de cada endereço
            cliente.getEndereco().add(linkTo(methodOn(EnderecoController.class)
                    .listarEnderecos()).withRel("Lista de enderecos"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteList);
    }

    // Listar um cliente
    @GetMapping(path = "/cliente/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        // Verificar se existe um cliente com este id
        Cliente cliente = clienteService.findById(id);
        if(cliente != null){
            // Adicionando o link ao cliente
            cliente.add(linkTo(methodOn(ClienteController.class).findAll()).withRel("Lista de clientes"));
            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

      // Retornar a quantidade de clientes
    @GetMapping(path = "/clientes/quantidade")
    public ResponseEntity<?> quantidadeClientes(){
        int qtdClientes = clienteService.qtdClientes();
        return ResponseEntity.status(HttpStatus.OK).body(qtdClientes);
    }

    // Buscar um cliente pelo nome passado por parâmetro na requisição
    @GetMapping(path = "/cliente")
    public ResponseEntity<List<Cliente>> buscarClienteNome(@RequestParam(value = "nome") String nome){
        List<Cliente> clienteList = clienteService.findByName(nome);
        // Criar um link para cada cliente
        for(Cliente cliente : clienteList){
            // Pegar o id de cada cliente
            Long id = cliente.getId();
            // Adicionando o link
            cliente.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
            // Adicionar os links de cada endereço
            cliente.getEndereco().add(linkTo(methodOn(EnderecoController.class)
                    .listarEnderecos()).withRel("Lista de enderecos"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteList);
    }

    // Cadastrar um cliente
    @PostMapping(path = "/cliente")
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
    }

    // Atualizar um cliente
    @PutMapping(path = "/cliente")
    public ResponseEntity<Cliente> update(@RequestBody Cliente cliente){
        // Verificar se existe um cliente com este id
        if(clienteService.findById(cliente.getId()) != null){
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(cliente));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    // Deletar um cliente
    @DeleteMapping(path = "/cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        // Verificar se existe um cliente com este id
        if(clienteService.findById(id) != null){
            clienteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

    }

}
