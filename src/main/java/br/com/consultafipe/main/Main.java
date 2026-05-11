package br.com.consultafipe.main;

import br.com.consultafipe.model.DadosModelo;
import br.com.consultafipe.model.DadosValor;
import br.com.consultafipe.model.DadosVeiculo;
import br.com.consultafipe.service.ConsumoApi;
import br.com.consultafipe.service.ConverterDados;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private Scanner read = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();
    private final String URI = "https://parallelum.com.br/fipe/api/v1/";
    private List<DadosVeiculo> dados;
    private String json;

    public void exibir(){

        System.out.println("\nEscolha o tipo do veículo:");
        System.out.println("""
                (1) Carros
                (2) Motos
                (3) Caminhões
                """);
        var tipoVeiculo = read.nextLine();

        if(tipoVeiculo.equals("1")){
            tipoVeiculo = "carros/marcas";
            json = consumoApi.obterDados(URI + tipoVeiculo);
        } else if (tipoVeiculo.equals("2")) {
            tipoVeiculo = "motos/marcas";
            json = consumoApi.obterDados(URI + tipoVeiculo);
        } else if (tipoVeiculo.equals("3")) {
            tipoVeiculo = "caminhoes/marcas";
            json = consumoApi.obterDados(URI + tipoVeiculo);
        }else {
            System.out.println("!ERRO! Digite alguma das opções listadas");
        }

        dados = conversor.obterListaDeDados(json, DadosVeiculo.class);

        dados.forEach(v -> System.out.printf("[%s]  %s\n",v.codigo(),v.nome()));


        System.out.println("\nSelecione a marca pelo código:");
        String cod = read.nextLine();

        json = consumoApi.obterDados(URI + tipoVeiculo + "/" + cod + "/modelos");
        DadosModelo dadosModelo = conversor.obterDados(json, DadosModelo.class);

        dadosModelo.modelos().forEach(v -> System.out.printf("[%s] %s\n",v.codigo(),v.nome()));


        System.out.println("\nDigite o modelo:");
        String modelo = read.nextLine();

        List<DadosVeiculo> modelosFiltrados = dadosModelo.modelos().stream()
                .filter(m -> m.nome()
                        .toLowerCase()
                        .contains(modelo.toLowerCase()))
                .toList();


        modelosFiltrados.forEach(m -> System.out.printf("[%s] %s\n",m.codigo(),m.nome()));

        System.out.println("Digite o código do modelo:");
        String codModelo = read.nextLine();

        json = consumoApi.obterDados(URI + tipoVeiculo + "/" + cod + "/modelos/" + codModelo + "/anos");
        dados = conversor.obterListaDeDados(json,DadosVeiculo.class);

        dados.forEach(m -> System.out.printf("[%s] %s\n",m.codigo(),m.nome()));

        System.out.println("Digite o ano:");
        String ano = read.nextLine();

        json = consumoApi.obterDados(URI + tipoVeiculo + "/" + cod + "/modelos/" + codModelo + "/anos/" + ano);
        DadosValor dadosValor = conversor.obterDados(json,DadosValor.class);

        System.out.printf("""
                RESUMO
                
                Marca: %s
                Modelo: %s
                Ano: %d
                FIPE: %s
                Combustível: %s
                """,dadosValor.marca(),
                dadosValor.modelo(),
                dadosValor.ano(),
                dadosValor.valorFipe(),
                dadosValor.combustivel());




    }
}
