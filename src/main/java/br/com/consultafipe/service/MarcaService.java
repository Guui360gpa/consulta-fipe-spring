package br.com.consultafipe.service;

import br.com.consultafipe.dto.DadosMarca;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";

    public Page<DadosMarca> listarMarcasEmOrdemAlfabetica(String tipoVeiculo, Pageable pageable){
        if (!List.of("carros, motos, caminhoes").contains(tipoVeiculo)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipo de veículo inválido");
        }

        String endereco = URI + tipoVeiculo + "/marcas";
        String json = consumoApi.obterDados(endereco);

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
}
