package br.com.consultafipe.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAnoVeiculo(
        @JsonAlias("codigo") String codigo,
        @JsonAlias("nome") String nome
) {
}
