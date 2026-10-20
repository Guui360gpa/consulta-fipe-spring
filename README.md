# 🚗 Consulta FIPE — API REST com Spring Boot

API REST desenvolvida com **Java e Spring Boot** que consome a [API FIPE Parallelum](https://parallelum.com.br/fipe/api/v1/) para consultar informações e preços de veículos, incluindo **carros, motos e caminhões**.

O projeto foi inicialmente desenvolvido como uma aplicação de linha de comando, com interação pelo terminal. Posteriormente, passou por uma **refatoração para uma arquitetura REST**, permitindo que as consultas sejam realizadas por meio de requisições HTTP.

A aplicação disponibiliza endpoints para consultar marcas, modelos, anos e valores FIPE, além de oferecer **paginação, ordenação alfabética e busca por nome**, facilitando sua futura integração com uma interface web.

## 🛠️ Tecnologias utilizadas

- **Java 17+** — linguagem de programação
- **Spring Boot** — desenvolvimento da API REST
- **Spring Web** — criação dos controllers e endpoints HTTP
- **Spring Data Commons** — paginação dos resultados
- **Maven** — gerenciamento de dependências
- **Jackson** — serialização e desserialização de JSON
- **Lombok** — redução de código repetitivo
- **API FIPE Parallelum** — fonte externa dos dados de veículos

## ⚙️ Funcionalidades

A API permite realizar as seguintes operações:

### 🚘 Marcas
- Listar marcas de carros, motos e caminhões.
- Paginar os resultados.
- Ordenar as marcas alfabeticamente.
- Buscar marcas por nome, com correspondência parcial.
- Limitar os resultados da busca para utilização em autocomplete.

### 🚙 Modelos
- Listar modelos de uma marca específica.
- Paginar os modelos disponíveis.
- Ordenar os resultados alfabeticamente.
- Buscar modelos por nome dentro da marca selecionada.
- Retornar até 10 sugestões por pesquisa.

### 📅 Anos
- Listar os anos disponíveis para determinado modelo.
- Paginar os resultados.
- Buscar anos pelo nome, incluindo informações de combustível.
- Retornar até 10 sugestões por pesquisa.

### 💰 Consulta FIPE
- Consultar o valor FIPE de um veículo.
- Retornar a marca e o modelo selecionados.
- Informar o ano do veículo.
- Exibir o tipo de combustível.
- Retornar os dados em formato JSON.

## 📁 Estrutura do projeto

A aplicação está organizada em pacotes com responsabilidades específicas:

```text
src/main/java/br/com/consultafipe/
│
├── ConsultafipeApplication.java
│
├── controller/
│   ├── MarcaController.java
│   ├── ModeloController.java
│   ├── AnoController.java
│   └── FipeController.java
│
├── dto/
│   ├── DadosMarca.java
│   ├── DadosModelo.java
│   ├── DadosItemModelo.java
│   ├── DadosAnoVeiculo.java
│   └── DadosFipe.java
│
└── service/
    ├── ConsumoApi.java
    └── ConverterDados.java
```

**Controller:** responsável por receber as requisições HTTP, processar os parâmetros e retornar as respostas.

**DTO:** utiliza records Java para representar os dados recebidos da API FIPE e enviados aos consumidores da aplicação.

**Service:** contém os componentes responsáveis pelo consumo da API externa e pela conversão dos dados JSON em objetos Java.

A aplicação não utiliza banco de dados, pois as informações são obtidas diretamente da API FIPE.

## 🌐 Endpoints da API

Os exemplos abaixo consideram a aplicação executando em `http://localhost:8080`.

### 1. Listar marcas

```http
GET /marcas/{tipoVeiculo}
```

Retorna as marcas disponíveis para o tipo de veículo informado, com paginação.

**Exemplo:**

```http
GET http://localhost:8080/marcas/carros?page=0&size=10
```

### 2. Buscar marcas por nome

```http
GET /marcas/{tipoVeiculo}/buscar?nome={nome}
```

Realiza uma busca parcial pelo nome da marca.

**Exemplo:**

```http
GET http://localhost:8080/marcas/carros/buscar?nome=vol
```

### 3. Listar modelos

```http
GET /modelos/{tipoVeiculo}/{codMarca}
```

Retorna os modelos disponíveis para uma marca específica, com paginação.

**Exemplo:**

```http
GET http://localhost:8080/modelos/carros/59?page=0&size=10
```

### 4. Buscar modelos por nome

```http
GET /modelos/{tipoVeiculo}/buscar?cod={codMarca}&nome={nome}
```

Busca modelos pelo nome, considerando a marca selecionada.

**Exemplo:**

```http
GET http://localhost:8080/modelos/carros/buscar?cod=59&nome=gol
```

### 5. Listar anos

```http
GET /anos/{tipoVeiculo}?codMarca={codMarca}&codModelo={codModelo}
```

Retorna os anos disponíveis para um modelo específico.

**Exemplo:**

```http
GET http://localhost:8080/anos/carros?codMarca=59&codModelo=5940&page=0&size=10
```

### 6. Buscar anos por nome

```http
GET /anos/{tipoVeiculo}/buscar?codMarca={codMarca}&codModelo={codModelo}&nome={nome}
```

Permite pesquisar os anos disponíveis para determinado modelo.

**Exemplo:**

```http
GET http://localhost:8080/anos/carros/buscar?codMarca=59&codModelo=5940&nome=2020
```

### 7. Consultar valor FIPE

```http
GET /fipe/{tipoVeiculo}?codMarca={codMarca}&codModelo={codModelo}&codAno={codAno}
```

Retorna as informações e o valor FIPE do veículo selecionado.

**Exemplo:**

```http
GET http://localhost:8080/fipe/carros?codMarca=59&codModelo=5940&codAno=2020-1
```

**Exemplo ilustrativo de resposta:**

```json
{
  "valorFipe": "R$ 45.000,00",
  "marca": "VW - VolksWagen",
  "modelo": "Modelo consultado",
  "ano": 2020,
  "combustivel": "Gasolina"
}
```

Os códigos utilizados nas consultas devem corresponder a combinações válidas retornadas pela API FIPE. Os valores apresentados são apenas exemplos.

## ▶️ Como executar

### Pré-requisitos

- Java 17 ou superior
- Maven
- Git
- Conexão com a internet

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/consulta-fipe-spring.git
```

### 2. Acessar o diretório

```bash
cd consulta-fipe-spring
```

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

Após a inicialização, a API estará disponível, por padrão, em:

```text
http://localhost:8080
```

### 4. Testar os endpoints

Utilize o **Postman**, Insomnia ou outra ferramenta de requisições HTTP para consultar os endpoints disponíveis.

Todas as consultas apresentadas utilizam o método HTTP `GET`.

## 🔄 Fluxo de funcionamento

O processo de consulta segue as etapas:

1. **Tipo de veículo:** escolha entre carros, motos ou caminhões.
2. **Marca:** consulte as marcas disponíveis e selecione uma.
3. **Modelo:** consulte os modelos associados à marca escolhida.
4. **Ano:** selecione um dos anos disponíveis para o modelo.
5. **Consulta FIPE:** envie os códigos selecionados para obter o valor e as informações do veículo.

A API recebe a requisição HTTP, consulta os dados na API FIPE Parallelum, converte a resposta JSON em objetos Java e retorna as informações ao cliente.

## 🔧 Refatoração e evolução do projeto

O projeto passou por uma mudança importante em sua estrutura e funcionamento.

**Versão inicial — aplicação de linha de comando**

- Interação diretamente pelo terminal.
- Menu para seleção do tipo de veículo.
- Escolha de marcas, modelos e anos por meio de entradas do usuário.
- Exibição das informações e do valor FIPE no console.

**Versão atual — API REST**

- Substituição do fluxo interativo no terminal por endpoints HTTP.
- Separação das funcionalidades em controllers.
- Utilização de DTOs com records Java.
- Implementação de paginação para listagens.
- Implementação de buscas por nome para autocomplete.
- Retorno dos dados em formato JSON.
- Estrutura preparada para integração com um frontend.

Essa refatoração tornou a aplicação mais adequada para ser consumida por outros sistemas e interfaces web, além de proporcionar maior organização e separação de responsabilidades.

## 🚀 Próximos passos

O próximo objetivo é desenvolver uma **interface web** para consumir os endpoints da API, permitindo que o usuário selecione o tipo de veículo, marca, modelo e ano de forma visual e consulte o valor FIPE sem precisar utilizar o Postman.

## 📚 Aprendizados

Durante o desenvolvimento e a refatoração deste projeto, foram trabalhados conceitos como:

- Consumo de APIs REST externas com Java.
- Criação de endpoints utilizando Spring Boot.
- Manipulação de requisições e parâmetros HTTP.
- Conversão de JSON utilizando Jackson.
- Utilização de records para transferência de dados.
- Paginação de coleções utilizando `Pageable` e `PageImpl`.
- Manipulação de listas com Stream API.
- Implementação de buscas com filtros e ordenação.
- Organização do código em camadas.
- Refatoração de uma aplicação de terminal para uma API REST.

---

**Projeto desenvolvido com Java e Spring Boot para praticar desenvolvimento backend, consumo de APIs externas e construção de APIs REST.**
