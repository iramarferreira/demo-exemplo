package com.example.demo.service;


import com.example.demo.model.Endereco;
import com.example.demo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {
    // Injetando dependência
    @Autowired
    EnderecoRepository enderecoRepository;

    // Retornar todos os endereços
    public List<Endereco> findAll(){
        return enderecoRepository.findAll();
    }

    // Retornar pelo id
    public Endereco findById(Long id){
        // Verificar se existe um endereço com id
        if(enderecoRepository.findById(id).isPresent()){
            return enderecoRepository.findById(id).get();
        }else{
            return null;
        }
    }

    // Salvar endereço
    public Endereco save(Endereco endereco){
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return enderecoSalvo;
    }

    // Atualizar endereço
    public Endereco update(Endereco endereco){
            return enderecoRepository.save(endereco);

    }

    // Delete endereço
    public void deleteById(Long id){
            enderecoRepository.deleteById(id);

    }
}
