package com.example.demo.repository;

import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    // Criando métodos para consultas específicas com anotação @Query
    @Query(value = "SELECT * FROM cliente WHERE nome = ?1", nativeQuery = true)
    List<Cliente> findByName(String name);


    // Retornar a quantidade de cliente
    @Query(value = "SELECT COUNT(*) FROM cliente", nativeQuery = true)
    int quantidadeCliente();

}
