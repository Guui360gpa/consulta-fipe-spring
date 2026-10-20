package br.com.consultafipe.service;

import br.com.consultafipe.dto.DadosItemModelo;
import br.com.consultafipe.dto.DadosModelo;
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
public class ModeloService {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";

    public Page<DadosItemModelo> listarModelosEmOrdemAlfabetica(String tipoVeiculo, String cod, Pageable pageable){
        String endereco = URI + tipoVeiculo + "/marcas" + "/" + cod + "/modelos";
        String json = consumoApi.obterDados(endereco);
        DadosModelo dadosModelo = conversor.obterDados(json, DadosModelo.class);

        List<DadosItemModelo> modelos = dadosModelo.modelos().stream()
                .sorted(Comparator.comparing(DadosItemModelo::nome, String.CASE_INSENSITIVE_ORDER))
                .toList();

        long offset = pageable.getOffset();

        if (offset >= modelos.size()){
            return new PageImpl<>(List.of(),pageable,modelos.size());
        }

        int inicio = (int) offset;

        int fim = (int) Math.min(offset + pageable.getPageSize(), modelos.size());

        List<DadosItemModelo> pagina = modelos.subList(inicio,fim);

        return new PageImpl<>(pagina,pageable, modelos.size());
    }

    public List<DadosItemModelo> buscarModeloPorNome(String tipoVeiculo, String cod, String nome){
        String endereco = URI + tipoVeiculo + "/marcas" + "/" + cod + "/modelos";
        String json = consumoApi.obterDados(endereco);

        DadosModelo dadosModelo = conversor.obterDados(json, DadosModelo.class);
        List<DadosItemModelo> modelos = dadosModelo.modelos();

        String pesquisa = nome.trim().toLowerCase(Locale.ROOT);

        if (pesquisa.isEmpty()) {
            return List.of();
        }

        return modelos.stream()
                .filter(modelo -> modelo.nome()
                        .toLowerCase(Locale.ROOT)
                        .contains(pesquisa))
                .sorted(Comparator.comparing(
                        DadosItemModelo::nome,
                        String.CASE_INSENSITIVE_ORDER
                ))
                .limit(10)
                .toList();
    }
}
