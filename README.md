# 🚗 Consulta FIPE — Full Stack | Java, Spring Boot, React e TypeScript

Aplicação web Full Stack desenvolvida com **Java, Spring Boot, React e TypeScript**, que permite consultar preços de veículos com base na Tabela FIPE, utilizando os dados disponibilizados pela [API FIPE Parallelum](https://parallelum.com.br/fipe/api/v1/).

O projeto começou como uma aplicação de linha de comando e evoluiu para uma solução web completa, com **backend REST e frontend responsivo**, oferecendo uma experiência mais prática e intuitiva para consultar carros, motos e caminhões.

## 💻 Tecnologias utilizadas

### Backend
- Java 17+
- Spring Boot
- Spring Web
- Spring Data Commons
- Maven
- Jackson
- Lombok
- API FIPE Parallelum

### Frontend
- React
- TypeScript
- Vite
- CSS
- Lucide React
- Fetch API

### Ferramentas de desenvolvimento
- IntelliJ IDEA
- Visual Studio Code
- Postman
- Git e GitHub

## ✨ Funcionalidades

A aplicação oferece as seguintes funcionalidades:

- Seleção do tipo de veículo: **carros, motos ou caminhões**.
- Listagem de marcas em ordem alfabética.
- Busca dinâmica de marcas com autocomplete.
- Listagem de modelos conforme a marca selecionada.
- Busca de modelos por nome.
- Listagem de anos disponíveis para cada modelo.
- Busca de anos e combustíveis.
- Paginação dos resultados.
- Consulta do valor FIPE do veículo selecionado.
- Exibição da marca, modelo, ano, combustível e valor FIPE.
- Interface responsiva para diferentes tamanhos de tela.
- Comunicação entre frontend e backend por meio de requisições HTTP.

## 🏗️ Arquitetura do projeto

A aplicação foi desenvolvida com separação entre frontend e backend.

```text
                   USUÁRIO
                      |
                      v
             FRONTEND (REACT)
               TypeScript
                      |
                 HTTP / JSON
                      |
                      v
           BACKEND (SPRING BOOT)
                      |
                Controllers
                      |
                  Services
                      |
             Consumo da API FIPE
                      |
                      v
             API FIPE PARALLELUM
                      |
                 Dados FIPE
                      |
                      v
              RESPOSTA EM JSON
                      |
                      v
               FRONTEND REACT
                      |
                      v
             RESULTADO NA TELA
```

### Backend — Spring Boot

Responsável por receber as requisições HTTP, processar os parâmetros, consumir a API FIPE e retornar os dados em JSON.

O backend está organizado em:

- **Controllers:** definem os endpoints REST.
- **Services:** concentram a lógica de negócio e o processamento das consultas.
- **DTOs:** representam os dados transferidos entre a API externa e a aplicação, utilizando records Java.

A aplicação não necessita de banco de dados para o fluxo principal, pois consulta diretamente a API FIPE.

### Frontend — React + TypeScript

Responsável pela interface visual e interação com o usuário.

O frontend permite navegar entre as etapas da consulta, pesquisar informações, selecionar veículos e visualizar o resultado.

O TypeScript contribui para a organização e segurança dos tipos de dados utilizados na comunicação com o backend.

## 📁 Estrutura do projeto

### Backend

```text
backend/
└── src/main/java/br/com/consultafipe/
    ├── ConsultafipeApplication.java
    ├── controller/
    │   ├── MarcaController.java
    │   ├── ModeloController.java
    │   ├── AnoModeloController.java
    │   └── FipeController.java
    ├── service/
    │   ├── MarcaService.java
    │   ├── ModeloService.java
    │   ├── AnoModeloService.java
    │   ├── ConsumoApi.java
    │   └── ConverterDados.java
    └── dto/
        ├── DadosMarca.java
        ├── DadosModelo.java
        ├── DadosItemModelo.java
        ├── DadosAnoVeiculo.java
        └── DadosFipe.java
```

### Frontend

```text
consulta-fipe-frontend/
├── src/
│   ├── components/
│   │   └── SelectionList.tsx
│   ├── services/
│   │   └── fipeApi.ts
│   ├── types/
│   │   └── fipe.ts
│   ├── App.tsx
│   ├── main.tsx
│   └── styles.css
├── package.json
├── vite.config.ts
└── index.html
```

*As estruturas representam a organização dos projetos. Os nomes das pastas principais podem variar conforme a configuração do repositório.*

## 🌐 Endpoints da API REST

O backend disponibiliza os seguintes endpoints:

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/marcas/{tipoVeiculo}` | Listar marcas |
| GET | `/marcas/{tipoVeiculo}/buscar` | Buscar marcas por nome |
| GET | `/modelos/{tipoVeiculo}/{codMarca}` | Listar modelos |
| GET | `/modelos/{tipoVeiculo}/buscar` | Buscar modelos por nome |
| GET | `/anos/{tipoVeiculo}` | Listar anos |
| GET | `/anos/{tipoVeiculo}/buscar` | Buscar anos por nome |
| GET | `/fipe/{tipoVeiculo}` | Consultar valor FIPE |

Os endpoints de listagem oferecem paginação, enquanto as buscas por nome permitem implementar o autocomplete no frontend.

### Exemplo de consulta FIPE

```http
GET http://localhost:8080/fipe/carros?codMarca=59&codModelo=5940&codAno=2020-1
```

**Exemplo ilustrativo de resposta JSON:**

```json
{
  "valorFipe": "R$ 45.000,00",
  "marca": "VW - VolksWagen",
  "modelo": "Modelo consultado",
  "ano": 2020,
  "combustivel": "Gasolina"
}
```

Os valores e códigos devem corresponder aos dados reais disponibilizados pela API FIPE.

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de iniciar, certifique-se de possuir:

- JDK 17 ou superior
- Node.js compatível com o Vite
- npm
- Maven ou Maven Wrapper
- Git
- Conexão com a internet

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
```

Acesse a pasta do projeto:

```bash
cd SEU_REPOSITORIO
```

### 2. Executar o backend

Abra a pasta do backend no IntelliJ IDEA ou utilize o terminal.

No Windows, caso o projeto tenha Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou execute a classe principal `ConsultafipeApplication.java` pela IDE.

Por padrão, o backend estará disponível em:

```text
http://localhost:8080
```

### 3. Executar o frontend

Abra outro terminal e acesse a pasta do frontend:

```powershell
cd consulta-fipe-frontend
```

Instale as dependências:

```powershell
npm install
```

Inicie o servidor de desenvolvimento:

```powershell
npm run dev
```

Caso o PowerShell bloqueie o comando `npm`, utilize `npm.cmd` no lugar de `npm`.

Por padrão, o frontend estará disponível em:

```text
http://localhost:5173
```

### 4. Acessar a aplicação

Abra o navegador e acesse:

**http://localhost:5173**

Mantenha os servidores do frontend e backend executando simultaneamente.

## 🔗 Integração entre frontend e backend

O frontend realiza requisições HTTP ao backend por meio da Fetch API.

As chamadas estão centralizadas no arquivo:

```text
src/services/fipeApi.ts
```

Durante o desenvolvimento, o Vite utiliza um proxy para encaminhar requisições iniciadas por `/api` ao Spring Boot.

Exemplo de configuração:

```typescript
server: {
  port: 5173,
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, ''),
    },
  },
}
```

Assim, o frontend pode realizar uma chamada como:

```text
/api/marcas/carros
```

E o Vite encaminha a requisição ao backend:

```text
http://localhost:8080/marcas/carros
```

Essa configuração facilita a integração local entre React e Spring Boot.

## 🔄 Evolução do projeto

### Versão inicial — Aplicação de terminal

O projeto começou com uma interface de linha de comando, na qual o usuário selecionava o veículo e visualizava as informações diretamente no console.

### Refatoração — API REST

A aplicação passou por uma refatoração para separar responsabilidades e disponibilizar as funcionalidades por meio de endpoints HTTP.

As principais mudanças foram:

- Criação de controllers REST.
- Separação da lógica de negócio em services.
- Utilização de DTOs com records Java.
- Implementação de paginação e ordenação.
- Criação de buscas dinâmicas para marcas, modelos e anos.

### Versão atual — Aplicação Full Stack

Com a implementação do frontend em React e TypeScript, o projeto passou a oferecer uma interface gráfica integrada ao backend.

O usuário pode realizar todo o fluxo de consulta diretamente pelo navegador, sem precisar utilizar o terminal ou ferramentas como Postman.

## 📚 Aprendizados

O desenvolvimento deste projeto proporcionou a aplicação prática de conceitos importantes de desenvolvimento Full Stack:

- Desenvolvimento de APIs REST com Spring Boot.
- Consumo de APIs externas.
- Organização do backend em controllers, services e DTOs.
- Serialização e desserialização JSON.
- Utilização de Java Streams.
- Paginação com Spring Data Commons.
- Desenvolvimento de interfaces com React.
- Tipagem de dados com TypeScript.
- Gerenciamento de estados e componentes.
- Requisições HTTP com Fetch API.
- Integração entre frontend e backend.
- Configuração de proxy com Vite.
- Refatoração e evolução de projetos.

## 🔮 Melhorias futuras

- Publicação da aplicação na internet.
- Aprimoramento da experiência de usuário.
- Implementação de testes automatizados.

## 👨‍💻 Autor

**Guilherme Paiva Alves**

Desenvolvido como projeto de estudo e prática em desenvolvimento Full Stack com Java, Spring Boot, React e TypeScript.

---

⭐ Se este projeto foi útil ou interessante, considere deixar uma estrela no repositório!