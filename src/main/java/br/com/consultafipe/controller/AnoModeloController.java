package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosAnoVeiculo;
import br.com.consultafipe.service.AnoModeloService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anos")
@RequiredArgsConstructor
public class AnoModeloController {

    private final AnoModeloService anoModeloService;

    @GetMapping("/{tipoVeiculo}")
    public Page<DadosAnoVeiculo> listar(
            @PathVariable String tipoVeiculo,
            @RequestParam String codMarca,
            @RequestParam String codModelo,
            @PageableDefault(size = 10,sort = "nome")Pageable pageable
            ){

        return anoModeloService.listarAnosModeloPorOrdemDecrescente(tipoVeiculo,codMarca,codModelo,pageable);
    }

    @GetMapping("/{tipoVeiculo}/buscar")
    public List<DadosAnoVeiculo> buscarPorNome(
            @PathVariable String tipoVeiculo,
            @RequestParam String codMarca,
            @RequestParam String codModelo,
            @RequestParam String nome
    ){

        return anoModeloService.buscarAnoPorNome(tipoVeiculo,codMarca,codModelo,nome);
    }
}
