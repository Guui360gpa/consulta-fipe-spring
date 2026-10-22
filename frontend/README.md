# AutoValor — Frontend Consulta FIPE

Frontend desenvolvido com React, TypeScript e Vite para consumir sua API REST Spring Boot `consultafipe`.

## Requisitos

- Node.js **20.19+** ou **22.12+**
- npm
- Sua API Spring Boot ativa na porta **8080**

## Executar (Windows / PowerShell ou terminal)

```bash
npm install
npm run dev
```

Acesse **http://localhost:5173**.

## Comunicação frontend → backend

O React faz requisições com prefixo `/api` (ex.: `/api/marcas/carros`). O proxy configurado em `vite.config.ts` encaminha as chamadas para `http://localhost:8080` removendo `/api`. Assim o Spring Boot recebe `/marcas/carros`.

**Não é necessário habilitar CORS apenas para desenvolvimento com esse proxy.** Ao publicar separadamente, será preciso configurar a URL pública da API e CORS ou um reverse proxy.

### Rotas assumidas

| Função | Rota no Spring Boot |
|---|---|
| Marcas paginadas | `GET /marcas/{tipoVeiculo}?page=0&size=10` |
| Busca de marcas | `GET /marcas/{tipoVeiculo}/buscar?nome=vol` |
| Modelos paginados | `GET /modelos/{tipoVeiculo}/{codMarca}?page=0&size=10` |
| Busca de modelos | `GET /modelos/{tipoVeiculo}/buscar?cod=59&nome=gol` |
| Anos paginados | `GET /anos/{tipoVeiculo}?codMarca=59&codModelo=5940&page=0&size=10` |
| Busca de anos | `GET /anos/{tipoVeiculo}/buscar?codMarca=59&codModelo=5940&nome=2020` |
| Consulta FIPE | `GET /fipe/{tipoVeiculo}?codMarca=59&codModelo=5940&codAno=2020-1` |

**Importante:** as rotas de marca e modelo devem ser conferidas com seus controllers atuais; adapte `src/services/fipeApi.ts` caso sejam diferentes. Os códigos de veículo da tabela são apenas ilustrativos.

## Estrutura

```text
src/
  components/SelectionList.tsx  # Listagem, paginação e autocomplete
  services/fipeApi.ts          # Todas as chamadas HTTP
  types/fipe.ts                # Tipos TypeScript dos DTOs
  App.tsx                      # Fluxo de seleção e resultado
  styles.css                   # Visual responsivo
  main.tsx                     # Entrada da aplicação
vite.config.ts                # Proxy /api → localhost:8080
```

## Resposta esperada

Listagens paginadas usam o formato de `PageImpl` do Spring (`content`, `totalPages`, `number`, `totalElements`). As buscas retornam arrays de `{ "codigo": "...", "nome": "..." }`. O resultado FIPE retorna `{ "valorFipe": "...", "marca": "...", "modelo": "...", "ano": 2020, "combustivel": "..." }`.

## Build de produção

```bash
npm run build
```

Os arquivos gerados ficam em `dist/`. O proxy Vite funciona **somente no servidor de desenvolvimento**. Configure um proxy HTTP no deploy ou use URL de backend configurável no arquivo de serviço, com CORS no Spring Boot, se hospedados em origens diferentes.
