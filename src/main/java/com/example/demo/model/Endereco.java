package com.example.demo.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

// Anotações Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
// Anotações JPA
@Entity
@Table(name = "endereco")
// Extendendo o RepresentationModel do HATEOAS para criar os links
public class Endereco extends RepresentationModel<Endereco> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;


    @Column(name = "rua")
    private String rua;


    @Column(name = "bairro")
    private String bairro;


    @Column(name = "numero")
    private int numero;

    @NonNull
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NonNull
    @Column(name = "uf", nullable = false)
    private String uf;

    @Column(name = "cep")
    private String cep;

}
