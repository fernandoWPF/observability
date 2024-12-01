package com.fwpf.observability.busca_cep.controller;

import com.fwpf.observability.busca_cep.domain.Cep;
import com.fwpf.observability.busca_cep.service.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/cep")
public class CepController {

    @Autowired
    private CepService service;

    @GetMapping("/{codigo}")
    public ResponseEntity<Cep> buscaCep(@PathVariable String codigo) {
        Optional<Cep> cep = service.buscarCep(codigo);
        return ResponseEntity.of(cep);
    }

}
