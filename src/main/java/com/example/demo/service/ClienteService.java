package com.example.demo.service;


import com.example.demo.model.Cliente;
import com.example.demo.model.Endereco;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClienteService {

    // injetar dependência
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    // Retornar todos os clientes
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }


    // Retornar todos os clientes com paginação
    public Page<Cliente> findAllPaginado(Pageable pageable){
        return clienteRepository.findAll(pageable);
    }

    // Cliente pelo Id
    public Cliente findById(Long id){
        if(clienteRepository.findById(id).isPresent()){
            return clienteRepository.findById(id).get();
        }else{
//            return Cliente.builder()
//                    .nome("")
//                    .telefone("")
//                    .build(); // Retornando um endereço vazio
            return null;
        }
        //return clienteRepository.findById(id).get();

    }

    // Clientes pelo nome
    public List<Cliente> findByName(String nome){
        return clienteRepository.findByName(nome);

    }

    // Retornar a quantidade de cliente
    public int qtdClientes(){
        return clienteRepository.quantidadeCliente();
    }

    // Salvar o cliente
    public Cliente save(Cliente cliente){
        // Para salvar um cliente eu preciso salvar
        // o endereço primeiro

        Endereco enderecoSalvo = enderecoRepository.save(cliente.getEndereco());
        cliente.setEndereco(enderecoSalvo);
        // Cadastro da hora
        cliente.setData_cadastro(new Date());
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteSalvo;
    }

    // Atualizar um cliente
    public Cliente update(Cliente cliente){
        cliente.getEndereco().setId(cliente.getId());
        // Atualizando o endereco primeiro
        Endereco enderedoSalvo = enderecoRepository.save(cliente.getEndereco());
        cliente.setEndereco(enderedoSalvo);
        return clienteRepository.save(cliente);

    }

    // Deletar pelo id
    public void deleteById(Long id){
        clienteRepository.deleteById(id);
        enderecoRepository.deleteById(id);
    }


}
