package br.com.consultafipe.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosFipe(@JsonAlias("Valor") String valorFipe,
                        @JsonAlias("Marca") String marca,
                        @JsonAlias("Modelo") String modelo,
                        @JsonAlias("AnoModelo") int ano,
                        @JsonAlias("Combustivel") String combustivel) {
}
