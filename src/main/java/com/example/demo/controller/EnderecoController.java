package com.example.demo.controller;


import com.example.demo.model.Endereco;
import com.example.demo.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@ResponseBody // As respotas dos métodos serão apresentados na web (json)
//@RestController
@RequestMapping(path = "/api")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping(path = "/enderecos")
    public ResponseEntity<List<Endereco>> listarEnderecos(){
        List<Endereco> enderecoList = enderecoService.findAll();

        // Criar um link para cada modelo
        for(Endereco endereco : enderecoList){
            // pegar o id
            Long id = endereco.getId();
            endereco.add(linkTo(methodOn(EnderecoController.class).findById(id)).withSelfRel());
        }
        return ResponseEntity.status(HttpStatus.OK).body(enderecoList);
    }

    @GetMapping(path = "/endereco/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id){
        Endereco endereco = enderecoService.findById(id);
        if(endereco != null){
            // Criar o link para endereços
            endereco.add(linkTo(methodOn(EnderecoController.class).listarEnderecos()).withRel("Lista de endereços"));
            return ResponseEntity.status(HttpStatus.OK).body(endereco);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/endereco")
    public ResponseEntity<Endereco> save(@RequestBody Endereco endereco){
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.save(endereco));
    }

    @PutMapping(path = "/endereco")
    public ResponseEntity<Endereco> update(@RequestBody Endereco endereco){
        // Verificar se existe o endereço
        if(enderecoService.findById(endereco.getId()) != null){
            return ResponseEntity.status(HttpStatus.OK).body(enderecoService.update(endereco));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @DeleteMapping(path = "/endereco/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        // Verificar se existe o endereço
        if(enderecoService.findById(id) != null){
            enderecoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deletado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }

    }
}
