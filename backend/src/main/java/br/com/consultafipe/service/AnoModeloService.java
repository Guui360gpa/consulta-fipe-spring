package br.com.consultafipe.service;

import br.com.consultafipe.dto.DadosAnoVeiculo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AnoModeloService {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";

    public Page<DadosAnoVeiculo> listarAnosModeloPorOrdemDecrescente(String tipoVeiculo, String codMarca, String codModelo, Pageable pageable){

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

    public List<DadosAnoVeiculo> buscarAnoPorNome(String tipoVeiculo, String codMarca, String codModelo, String nome){
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
