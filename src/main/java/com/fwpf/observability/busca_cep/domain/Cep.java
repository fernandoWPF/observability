package com.fwpf.observability.busca_cep.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CEP")
public class Cep {

    @Id
    @GeneratedValue
    private Long id;
    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String regiao;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

}
