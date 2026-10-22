package br.com.consultafipe.controller;

import br.com.consultafipe.dto.DadosFipe;
import br.com.consultafipe.service.FipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fipe")
@RequiredArgsConstructor
public class FipeController {

    private final FipeService fipeService;

    @GetMapping("/{tipoVeiculo}")
    public DadosFipe consultar(
            @PathVariable String tipoVeiculo,
            @RequestParam String codMarca,
            @RequestParam String codModelo,
            @RequestParam String codAno
    ){
        return fipeService.consultarFipe(tipoVeiculo,codMarca,codModelo,codAno);
    }
}
