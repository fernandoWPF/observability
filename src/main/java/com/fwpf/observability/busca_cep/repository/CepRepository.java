package com.fwpf.observability.busca_cep.repository;

import com.fwpf.observability.busca_cep.domain.Cep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CepRepository extends JpaRepository<Cep, Long> {

    Optional<Cep> findByCep(String codigo);

}
