package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosItemModelo;
import br.com.consultafipe.service.ModeloService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloService modeloService;

    @GetMapping("/{tipoVeiculo}/{codMarca}")
    public Page<DadosItemModelo> listar(@PathVariable String tipoVeiculo,
                                    @PathVariable String codMarca,
                                    @PageableDefault(size = 10, sort="nome")Pageable pageable){

      return modeloService.listarModelosEmOrdemAlfabetica(tipoVeiculo,codMarca,pageable);
    }

    @GetMapping("/{tipoVeiculo}/buscar")
    public List<DadosItemModelo> buscarPorNome(@PathVariable String tipoVeiculo,
                                               @RequestParam String cod,
                                               @RequestParam String nome){


        return modeloService.buscarModeloPorNome(tipoVeiculo,cod,nome);
    }

}
