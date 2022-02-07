package com.example.demo.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

// Anotações Lombok;
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
// Anotações JPA
@Entity
@Table(name = "cliente")
// Extendendo o RepresentationModel do HATEOAS para criar os links
public class Cliente extends RepresentationModel<Cliente> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @NonNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "telefone")
    private String telefone;

    @OneToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    @Column(name = "data_cadastro")
    private Date data_cadastro;

}
