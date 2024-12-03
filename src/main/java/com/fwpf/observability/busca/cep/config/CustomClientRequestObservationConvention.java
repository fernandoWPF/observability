package com.fwpf.observability.busca.cep.config;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.observation.ClientRequestObservationContext;
import org.springframework.http.client.observation.DefaultClientRequestObservationConvention;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomClientRequestObservationConvention extends DefaultClientRequestObservationConvention {

    @Override
    public String getContextualName(ClientRequestObservationContext context) {
        ClientHttpRequest request = context.getCarrier();
        return (request != null ? "http " + request.getMethod().name().toLowerCase(Locale.ROOT) + " " + request.getURI() : null);
    }
}
