package br.com.consultafipe.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IConverterDados {

    <T> List<T> obterListaDeDados(String json, Class<T> classe);

    <T> T obterDados(String json, Class<T> classe);
}
