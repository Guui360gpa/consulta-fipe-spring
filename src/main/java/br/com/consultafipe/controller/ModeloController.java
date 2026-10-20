package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosItemModelo;
import br.com.consultafipe.dto.DadosMarca;
import br.com.consultafipe.dto.DadosModelo;
import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;
import br.com.consultafipe.service.ModeloService;
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

    private final ModeloService modeloService;

    @GetMapping("/{tipoVeiculo}")
    public Page<DadosItemModelo> listar(@PathVariable String tipoVeiculo,
                                    @RequestParam String cod,
                                    @PageableDefault(size = 10, sort="nome")Pageable pageable){

      return modeloService.listarModelosEmOrdemAlfabetica(tipoVeiculo,cod,pageable);
    }

    @GetMapping("/{tipoVeiculo}/modelos/buscar")
    public List<DadosItemModelo> buscarPorNome(@PathVariable String tipoVeiculo,
                                               @RequestParam String cod,
                                               @RequestParam String nome){


        return modeloService.buscarModeloPorNome(tipoVeiculo,cod,nome);
    }

}
