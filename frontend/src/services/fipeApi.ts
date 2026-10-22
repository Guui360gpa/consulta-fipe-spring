import type { DadosFipe, Item, Pagina, TipoVeiculo } from '../types/fipe';

// Todas as URLs estão concentradas aqui. Ajuste somente este arquivo
// se seus @RequestMapping / @GetMapping forem diferentes.
const BASE = '/api';

async function get<T>(path: string, params: Record<string, string | number> = {}): Promise<T> {
  const query = new URLSearchParams(
    Object.entries(params).map(([key, value]) => [key, String(value)])
  );
  const response = await fetch(`${BASE}${path}${query.size ? `?${query}` : ''}`);
  if (!response.ok) {
    throw new Error(`Não foi possível consultar o serviço (HTTP ${response.status}). Verifique se o backend está ativo e se os códigos são válidos.`);
  }
  try { return await response.json() as T; }
  catch { throw new Error('O servidor retornou uma resposta inválida.'); }
}

export const fipeApi = {
  listarMarcas: (tipo: TipoVeiculo, page: number) =>
    get<Pagina<Item>>(`/marcas/${tipo}`, { page, size: 10 }),
  buscarMarcas: (tipo: TipoVeiculo, nome: string) =>
    get<Item[]>(`/marcas/${tipo}/buscar`, { nome }),
  listarModelos: (tipo: TipoVeiculo, codMarca: string, page: number) =>
    get<Pagina<Item>>(`/modelos/${tipo}/${encodeURIComponent(codMarca)}`, { page, size: 10 }),
  buscarModelos: (tipo: TipoVeiculo, codMarca: string, nome: string) =>
    get<Item[]>(`/modelos/${tipo}/buscar`, { cod: codMarca, nome }),
  listarAnos: (tipo: TipoVeiculo, codMarca: string, codModelo: string, page: number) =>
    get<Pagina<Item>>(`/anos/${tipo}`, { codMarca, codModelo, page, size: 10 }),
  buscarAnos: (tipo: TipoVeiculo, codMarca: string, codModelo: string, nome: string) =>
    get<Item[]>(`/anos/${tipo}/buscar`, { codMarca, codModelo, nome }),
  consultar: (tipo: TipoVeiculo, codMarca: string, codModelo: string, codAno: string) =>
    get<DadosFipe>(`/fipe/${tipo}`, { codMarca, codModelo, codAno }),
};
