package br.com.consultafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosValor(@JsonAlias("Valor") String valorFipe,
                         @JsonAlias("Marca") String marca,
                         @JsonAlias("Modelo") String modelo,
                         @JsonAlias("AnoModelo") int ano,
                         @JsonAlias("Combustivel") String combustivel) {
}
