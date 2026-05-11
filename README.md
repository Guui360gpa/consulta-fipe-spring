# 🚗 Consulta FIPE - Spring Boot

Aplicação de linha de comando desenvolvida com **Java + Spring Boot** que consome a [API FIPE Parallelum](https://parallelum.com.br/fipe/api/v1/) para consultar preços de veículos (carros, motos e caminhões) diretamente no terminal.

## 🛠 Tecnologias

- Java 17+
- Spring Boot 3
- Maven
- Jackson (serialização JSON)
- API REST FIPE (pública)

## ▶️ Como executar

**Pré-requisitos:** Java 17+ e Maven instalados.

```bash
git clone https://github.com/SEU_USUARIO/consulta-fipe-spring.git
cd consulta-fipe-spring
mvn spring-boot:run
```

## 🔍 Como usar

1. Escolha o tipo de veículo: Carro, Moto ou Caminhão
2. Selecione a marca pelo código exibido
3. Filtre o modelo digitando parte do nome
4. Selecione o código do modelo
5. Escolha o ano
6. Veja o resumo com o **valor FIPE, marca, modelo, ano e combustível**

## 📁 Estrutura do projeto

## 📁 Estrutura do projeto

- **ConsultafipeApplication.java** — inicialização da aplicação
- **Main.java** — menu interativo e fluxo principal
- **model/** — records para mapear os dados da API (modelo, valor, veículo)
- **service/** — consumo da API e conversão de JSON
