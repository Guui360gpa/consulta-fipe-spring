package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosMarca;
import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;
import br.com.consultafipe.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;
    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";


    @GetMapping("/{tipoVeiculo}")
    public Page<DadosMarca> listar(@PathVariable String tipoVeiculo,@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return marcaService.listarMarcasEmOrdemAlfabetica(tipoVeiculo,pageable);
    }

    @GetMapping("/{tipoVeiculo}/marcas/buscar")
    public List<DadosMarca> buscarPorNome(@PathVariable String tipoVeiculo, @RequestParam String nome){
        if (!List.of("carros, motos, caminhoes").contains(tipoVeiculo)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipo de veículo inválido");
        }

        String endereco = URI + tipoVeiculo + "/marcas";

        String json = consumoApi.obterDados(endereco);

        List<DadosMarca> marcas =
                conversor.obterListaDeDados(
                        json, DadosMarca.class
                );

        String pesquisa = nome.trim().toLowerCase(
                Locale.ROOT
        );

        if (pesquisa.isEmpty()) {
            return List.of();
        }

        return marcas.stream()
                .filter(marca -> marca.nome()
                        .toLowerCase(Locale.ROOT)
                        .contains(pesquisa))
                .sorted(Comparator.comparing(
                        DadosMarca::nome,
                        String.CASE_INSENSITIVE_ORDER
                ))
                .limit(10)
                .toList();
    }

}
