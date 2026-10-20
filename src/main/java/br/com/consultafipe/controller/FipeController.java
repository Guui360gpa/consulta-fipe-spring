package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosFipe;
import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fipe")
@RequiredArgsConstructor
public class FipeController {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";

    @GetMapping("/{tipoVeiculo}")
    public DadosFipe consultar(
            @PathVariable String tipoVeiculo,
            @RequestParam String codMarca,
            @RequestParam String codModelo,
            @RequestParam String codAno
    ){
        String endereco = URI + tipoVeiculo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos/" + codAno;
        String json = consumoApi.obterDados(endereco);

        return conversor.obterDados(json, DadosFipe.class);
    }
}
