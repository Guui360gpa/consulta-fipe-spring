package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosItemModelo;
import br.com.consultafipe.dto.DadosMarca;
import br.com.consultafipe.dto.DadosModelo;
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
@RequestMapping("/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";

    @GetMapping("/{tipoVeiculo}")
    public Page<DadosItemModelo> listar(@PathVariable String tipoVeiculo,
                                    @RequestParam String cod,
                                    @PageableDefault(size = 10, sort="nome")Pageable pageable){

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

    @GetMapping("/{tipoVeiculo}/modelos/buscar")
    public List<DadosItemModelo> buscarPorNome(@PathVariable String tipoVeiculo,
                                               @RequestParam String cod,
                                               @RequestParam String nome){


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
