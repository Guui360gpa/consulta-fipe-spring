package br.com.consultafipe.service;

import br.com.consultafipe.dto.DadosFipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FipeService {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";

    public DadosFipe consultarFipe(String tipoVeiculo, String codMarca, String codModelo, String codAno){
        String endereco = URI + tipoVeiculo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos/" + codAno;
        String json = consumoApi.obterDados(endereco);

        return conversor.obterDados(json, DadosFipe.class);
    }
}
