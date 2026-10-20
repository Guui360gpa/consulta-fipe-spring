package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosMarca;
import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;
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
@RequestMapping("/modelos")
@RequiredArgsConstructor
public class MarcaController {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";


    @GetMapping("/carro")
    public Page<DadosMarca> listarMarcasCarro(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        String tipoVeiculo = "carros/marcas";
        String json = consumoApi.obterDados(URI + tipoVeiculo);

        List<DadosMarca> veiculos = conversor.obterListaDeDados(json, DadosMarca.class);

        veiculos.sort(Comparator.comparing(DadosMarca::nome, String.CASE_INSENSITIVE_ORDER));

        long offset = pageable.getOffset();

        if (offset >= veiculos.size()) {
            return new PageImpl<>(List.of(), pageable, veiculos.size());
        }

        int inicio = (int) offset;

        int fim = (int) Math.min(offset + pageable.getPageSize(), veiculos.size());

        return new PageImpl<>(veiculos.subList(inicio, fim), pageable, veiculos.size());
    }

    @GetMapping("/moto")
    public Page<DadosMarca> listarMarcasMoto(@PageableDefault(size = 10,sort = "nome") Pageable pageable) {

        String tipoVeiculo = "motos/marcas";
        String json = consumoApi.obterDados(URI + tipoVeiculo);

        List<DadosMarca> veiculos = conversor.obterListaDeDados(json, DadosMarca.class);

        veiculos.sort(Comparator.comparing(DadosMarca::nome, String.CASE_INSENSITIVE_ORDER));

        long offset = pageable.getOffset();

        if (offset >= veiculos.size()) {
            return new PageImpl<>(List.of(),pageable,veiculos.size());
        }

        int inicio = (int) offset;

        int fim = (int) Math.min(offset + pageable.getPageSize(), veiculos.size());

        return new PageImpl<>(veiculos.subList(inicio,fim),pageable,veiculos.size());
    }

    @GetMapping("/caminhao")
    public Page<DadosMarca> listarMarcasCaminhao(@PageableDefault(size = 10,sort = "nome") Pageable pageable) {

        String tipoVeiculo = "caminhoes/marcas";
        String json = consumoApi.obterDados(URI + tipoVeiculo);

        List<DadosMarca> veiculos = conversor.obterListaDeDados(json, DadosMarca.class);

        veiculos.sort(Comparator.comparing(DadosMarca::nome, String.CASE_INSENSITIVE_ORDER));

        long offset = pageable.getOffset();

        if (offset >= veiculos.size()) {
            return new PageImpl<>(List.of(),pageable,veiculos.size());
        }

        int inicio = (int) offset;

        int fim = (int) Math.min(offset + pageable.getPageSize(), veiculos.size());

        return new PageImpl<>(veiculos.subList(inicio,fim),pageable,veiculos.size());
    }

    @GetMapping("/{tipoVeiculo}/marcas/buscar")
    public List<DadosMarca> buscarMarcaPorNome(@PathVariable String tipoVeiculo, @RequestParam String nome){
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
