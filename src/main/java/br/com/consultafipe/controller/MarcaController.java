package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosMarca;
import br.com.consultafipe.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping("/{tipoVeiculo}")
    public Page<DadosMarca> listar(@PathVariable String tipoVeiculo,@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return marcaService.listarMarcasEmOrdemAlfabetica(tipoVeiculo,pageable);
    }

    @GetMapping("/{tipoVeiculo}/marcas/buscar")
    public List<DadosMarca> buscarPorNome(@PathVariable String tipoVeiculo, @RequestParam String nome){
        return marcaService.buscarMarcasPorNome(tipoVeiculo,nome);
    }

}
