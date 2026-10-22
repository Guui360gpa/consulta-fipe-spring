import { useEffect, useState } from 'react';
import { AlertCircle, ArrowLeft, ArrowRight, ChevronLeft, ChevronRight, LoaderCircle, Search } from 'lucide-react';
import type { Item, Pagina } from '../types/fipe';

type Props = {
  keyId: string;
  title: string;
  description: string;
  placeholder: string;
  onBack: () => void;
  onSelect: (item: Item) => void;
  list: (page: number) => Promise<Pagina<Item>>;
  search: (name: string) => Promise<Item[]>;
};

export default function SelectionList({ keyId, title, description, placeholder, onBack, onSelect, list, search }: Props) {
  const [text, setText] = useState('');
  const [page, setPage] = useState(0);
  const [items, setItems] = useState<Item[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const searching = text.trim().length > 0;

  useEffect(() => { setText(''); setPage(0); }, [keyId]);
  useEffect(() => {
    let active = true;
    const timeout = setTimeout(async () => {
      setLoading(true); setError('');
      try {
        if (searching) {
          const data = await search(text.trim());
          if (active) { setItems(data); setTotalPages(0); setTotalElements(data.length); }
        } else {
          const data = await list(page);
          if (active) { setItems(data.content); setTotalPages(data.totalPages); setTotalElements(data.totalElements); }
        }
      } catch (err) {
        if (active) { setItems([]); setError(err instanceof Error ? err.message : 'Falha ao carregar os dados.'); }
      } finally { if (active) setLoading(false); }
    }, searching ? 320 : 0);
    return () => { active = false; clearTimeout(timeout); };
  }, [keyId, text, page, searching, list, search]);

  return <section className="selector panel" aria-label={title}>
    <div className="section-top"><button className="back-link" onClick={onBack}><ArrowLeft size={17}/> Voltar</button><span className="eyebrow">SELEÇÃO DE VEÍCULO</span></div>
    <h2>{title}</h2><p className="muted section-description">{description}</p>
    <label className="search-box"><Search size={20} className="muted-icon"/><input value={text} onChange={e => { setText(e.target.value); setPage(0); }} placeholder={placeholder} aria-label={placeholder}/>{loading && <LoaderCircle size={17} className="spin"/>}</label>
    <div className="list-meta"><span>{searching ? 'RESULTADOS DA BUSCA' : 'OPÇÕES DISPONÍVEIS'}</span><span>{!loading && `${totalElements} ${searching ? 'encontrado(s)' : 'resultado(s)'}`}</span></div>
    {error && <div className="notice error"><AlertCircle size={18}/><span>{error}</span><button onClick={() => { setText(''); setPage(0); }}>Limpar busca</button></div>}
    <div className="options-list" aria-busy={loading}>
      {loading && items.length === 0 ? <div className="empty-state"><LoaderCircle className="spin"/> Buscando informações...</div> : null}
      {!loading && !error && items.length === 0 ? <div className="empty-state">Nenhum resultado encontrado. Tente outra pesquisa.</div> : null}
      {items.map(item => <button className="option" key={item.codigo} onClick={() => onSelect(item)}><span className="option-monogram">{item.nome.charAt(0).toUpperCase()}</span><span className="option-label">{item.nome}</span><span className="option-code">{item.codigo}</span><ArrowRight size={18} className="option-arrow"/></button>)}
    </div>
    {!searching && totalPages > 1 && <div className="pagination"><span>Página {page + 1} de {totalPages}</span><div className="pagination-buttons"><button disabled={page === 0 || loading} onClick={() => setPage(n => n - 1)} aria-label="Página anterior"><ChevronLeft size={18}/></button><button disabled={page + 1 >= totalPages || loading} onClick={() => setPage(n => n + 1)} aria-label="Próxima página"><ChevronRight size={18}/></button></div></div>}
  </section>;
}
