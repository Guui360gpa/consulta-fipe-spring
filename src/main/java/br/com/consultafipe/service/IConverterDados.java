package br.com.consultafipe.service;

import java.util.List;

public interface IConverterDados {

    <T> List<T> obterListaDeDados(String json, Class<T> classe);

    <T> T obterDados(String json, Class<T> classe);
}
