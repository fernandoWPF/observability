package com.fwpf.observability.busca.cep.service;

import com.fwpf.observability.busca.cep.domain.Cep;
import com.fwpf.observability.busca.cep.repository.CepRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CepService {

    private final ObservationRegistry observationRegistry;
    private final CepRepository repository;
    private final RestTemplate restTemplate;

    public Optional<Cep> buscarCep(String codigo) {
        log.info("Buscando cep pelo codigo {}", codigo);
        Optional<Cep> entity = buscaCepNaBaseLocal(codigo);

        if (entity.isPresent()) {
            log.info("Cep encontrado em base de dados local.");
            return entity;
        }

        log.info("Cep nao encontrado em base de dados local.");

        Cep cepWs = buscaCepWs(codigo);

        if (cepWs != null) {
            Cep cepBaseLocal = salvarCepBaseLocal(cepWs);
            return Optional.of(cepBaseLocal);
        }

        return Optional.empty();

    }

    private Optional<Cep> buscaCepNaBaseLocal(String codigo) {
        return Observation.createNotStarted("busca-cep-local", observationRegistry)
                .highCardinalityKeyValue("cep", codigo)
                .observe(() -> repository.findByCep(codigo));
    }

    private Cep buscaCepWs(String codigo) {
        ResponseEntity<Cep> response = restTemplate
                .getForEntity(String.format("https://viacep.com.br/ws/%s/json/", codigo), Cep.class);

        return Observation.createNotStarted("processa-response", observationRegistry)
                .observe(() -> {

                    if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                        Cep body = response.getBody();
                        if (body != null && body.getCep() != null) {
                            log.info("Cep encontrado no WS.");
                            body.setCep(body.getCep().replace("-", ""));

                            return body;
                        }
                    }

                    log.info("Cep inexistente {}", codigo);
                    return null;
                });
    }

    private Cep salvarCepBaseLocal(Cep cepWs) {
        return Observation.createNotStarted("salva-cep-local", observationRegistry)
                .observe(() -> {
                    log.info("Salvando cep na base local");
                    return repository.save(cepWs);
                });
    }

}
