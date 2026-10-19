package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosVeiculo;
import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";


    @GetMapping("/cars")
    public Page<DadosVeiculo> listCarModels(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        String tipoVeiculo = "carros/marcas";
        String json = consumoApi.obterDados(URI + tipoVeiculo);

        List<DadosVeiculo> veiculos =
                conversor.obterListaDeDados(
                        json, DadosVeiculo.class
                );

        veiculos.sort(Comparator.comparing(
                DadosVeiculo::nome,
                String.CASE_INSENSITIVE_ORDER
        ));

        long offset = pageable.getOffset();

        if (offset >= veiculos.size()) {
            return new PageImpl<>(
                    List.of(), pageable, veiculos.size()
            );
        }

        int inicio = (int) offset;
        int fim = (int) Math.min(
                offset + pageable.getPageSize(),
                veiculos.size()
        );

        return new PageImpl<>(
                veiculos.subList(inicio, fim),
                pageable,
                veiculos.size()
        );
    }

/*    @GetMapping("/motorcycles")
    public Page<DadosVeiculo> listMotorcycleModels(@PageableDefault(size = 10,sort = "nome") Pageable pageable) {

    }*/


}
