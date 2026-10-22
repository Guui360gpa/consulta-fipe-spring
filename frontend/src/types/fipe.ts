export type TipoVeiculo = 'carros' | 'motos' | 'caminhoes';
export interface Item { codigo: string; nome: string; }
export interface DadosFipe {
  valorFipe: string;
  marca: string;
  modelo: string;
  ano: number;
  combustivel: string;
}
export interface Pagina<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  number: number;
  first: boolean;
  last: boolean;
}
