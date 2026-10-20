package br.com.consultafipe.controller;

import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fipe")
@RequiredArgsConstructor
public class FipeController {

    private final ConsumoApi consumoApi;
    private final ConverterDados conversor;
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";
}
