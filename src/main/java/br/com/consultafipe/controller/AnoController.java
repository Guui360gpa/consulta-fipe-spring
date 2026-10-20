package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosAnoVeiculo;
import br.com.consultafipe.dto.DadosMarca;
import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/anos")
@RequiredArgsConstructor
public class AnoController {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";

    @GetMapping("/{tipoVeiculo}")
    public Page<DadosAnoVeiculo> listar(
            @PathVariable String tipoVeiculo,
            @RequestParam String codMarca,
            @RequestParam String codModelo,
            @PageableDefault(size = 10,sort = "nome")Pageable pageable
            ){

        String endereco = URI + tipoVeiculo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos";
        String json = consumoApi.obterDados(endereco);

        List<DadosAnoVeiculo> anoVeiculos = conversor.obterListaDeDados(json, DadosAnoVeiculo.class);
        anoVeiculos.sort(Comparator.comparing(DadosAnoVeiculo::nome, String.CASE_INSENSITIVE_ORDER).reversed());

        long offset = pageable.getOffset();

        if (offset >= anoVeiculos.size()) {
            return new PageImpl<>(List.of(), pageable, anoVeiculos.size());
        }

        int inicio = (int) offset;

        int fim = (int) Math.min(offset + pageable.getPageSize(), anoVeiculos.size());

        return new PageImpl<>(anoVeiculos.subList(inicio, fim), pageable, anoVeiculos.size());

    }

    @GetMapping("/{tipoVeiculo}/buscar")
    public List<DadosAnoVeiculo> buscarPorNome(
            @PathVariable String tipoVeiculo,
            @RequestParam String codMarca,
            @RequestParam String codModelo,
            @RequestParam String nome
    ){
        String endereco = URI + tipoVeiculo + "/marcas/" + codMarca + "/modelos/" + codModelo + "/anos";

        String json = consumoApi.obterDados(endereco);

        List<DadosAnoVeiculo> anoVeiculos = conversor.obterListaDeDados(json, DadosAnoVeiculo.class);

        String pesquisa = nome.trim().toLowerCase(Locale.ROOT);

        if (pesquisa.isEmpty()) {
            return List.of();
        }

        return anoVeiculos.stream()
                .filter(ano -> ano.nome()
                        .toLowerCase(Locale.ROOT)
                        .contains(pesquisa))
                .sorted(Comparator.comparing(
                        DadosAnoVeiculo::nome,
                        String.CASE_INSENSITIVE_ORDER
                ).reversed())
                .limit(10)
                .toList();
    }
}
