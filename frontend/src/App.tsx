import { useCallback, useEffect, useState } from 'react';
import { ArrowLeft, ArrowRight, Bike, CalendarDays, CarFront, Check, CheckCircle2, ChevronRight, CircleHelp, Clock3, Code2, Menu, Search, ShieldCheck, Truck, X } from 'lucide-react';
import SelectionList from './components/SelectionList';
import { fipeApi } from './services/fipeApi';
import type { DadosFipe, Item, TipoVeiculo } from './types/fipe';

type Step = 0 | 1 | 2 | 3 | 4;
const types = [
  { id: 'carros' as const, title: 'Carros', detail: 'Passeio e utilitários', Icon: CarFront },
  { id: 'motos' as const, title: 'Motos', detail: 'Motocicletas e scooters', Icon: Bike },
  { id: 'caminhoes' as const, title: 'Caminhões', detail: 'Veículos de carga', Icon: Truck },
];
const labels = ['Tipo de veículo', 'Marca', 'Modelo', 'Ano', 'Resultado'];

export default function App() {
  const [step, setStep] = useState<Step>(0);
  const [tipo, setTipo] = useState<TipoVeiculo | null>(null);
  const [marca, setMarca] = useState<Item | null>(null);
  const [modelo, setModelo] = useState<Item | null>(null);
  const [ano, setAno] = useState<Item | null>(null);
  const [resultado, setResultado] = useState<DadosFipe | null>(null);
  const [resultError, setResultError] = useState('');
  const [resultLoading, setResultLoading] = useState(false);
  const [mobileMenu, setMobileMenu] = useState(false);

  function reset() { setStep(0); setTipo(null); setMarca(null); setModelo(null); setAno(null); setResultado(null); setResultError(''); setMobileMenu(false); }
  function selectTipo(t: TipoVeiculo) { setTipo(t); setMarca(null); setModelo(null); setAno(null); setResultado(null); setStep(1); }
  function selectMarca(item: Item) { setMarca(item); setModelo(null); setAno(null); setResultado(null); setStep(2); }
  function selectModelo(item: Item) { setModelo(item); setAno(null); setResultado(null); setStep(3); }
  function selectAno(item: Item) { setAno(item); setResultado(null); setStep(4); }
  function goBack() { setStep(s => Math.max(s - 1, 0) as Step); }

  const listMarcas = useCallback((page: number) => fipeApi.listarMarcas(tipo!, page), [tipo]);
  const searchMarcas = useCallback((name: string) => fipeApi.buscarMarcas(tipo!, name), [tipo]);
  const listModelos = useCallback((page: number) => fipeApi.listarModelos(tipo!, marca!.codigo, page), [tipo, marca]);
  const searchModelos = useCallback((name: string) => fipeApi.buscarModelos(tipo!, marca!.codigo, name), [tipo, marca]);
  const listAnos = useCallback((page: number) => fipeApi.listarAnos(tipo!, marca!.codigo, modelo!.codigo, page), [tipo, marca, modelo]);
  const searchAnos = useCallback((name: string) => fipeApi.buscarAnos(tipo!, marca!.codigo, modelo!.codigo, name), [tipo, marca, modelo]);

  useEffect(() => {
    if (step !== 4 || !tipo || !marca || !modelo || !ano) return;
    let active = true;
    setResultLoading(true); setResultError('');
    fipeApi.consultar(tipo, marca.codigo, modelo.codigo, ano.codigo)
      .then(data => { if (active) setResultado(data); })
      .catch(err => { if (active) setResultError(err instanceof Error ? err.message : 'Erro na consulta.'); })
      .finally(() => { if (active) setResultLoading(false); });
    return () => { active = false; };
  }, [step, tipo, marca, modelo, ano]);

  return <div className="layout">
    <aside className={`sidebar ${mobileMenu ? 'open' : ''}`}>
      <div className="brand"><div className="brand-symbol"><CarFront size={23}/></div><div><strong>AutoValor<span>.</span></strong><small>CONSULTA FIPE</small></div><button className="mobile-close" onClick={() => setMobileMenu(false)} aria-label="Fechar menu"><X size={20}/></button></div>
      <div className="sidebar-caption">MENU PRINCIPAL</div>
      <button className="nav-item active" onClick={reset}><Search size={19}/> Consultar FIPE <ChevronRight size={15} className="nav-chevron"/></button>
      <div className="sidebar-caption sidebar-caption-second">SOBRE A PLATAFORMA</div>
      <a className="nav-item secondary" href="https://parallelum.com.br/fipe/api/v1/" target="_blank" rel="noreferrer"><Code2 size={19}/> API de dados</a>
      <a className="nav-item secondary" href="https://veiculos.fipe.org.br/" target="_blank" rel="noreferrer"><CircleHelp size={19}/> Sobre a Tabela FIPE</a>
      <div className="sidebar-bottom"><div className="sidebar-tip"><div className="tip-icon"><ShieldCheck size={21}/></div><strong>Consulte com confiança</strong><p>Dados consultados em tempo real por meio da API FIPE Parallelum.</p></div><div className="sidebar-footer"><span className="status-dot"/> Conectado à API via backend</div></div>
    </aside>
    {mobileMenu && <button className="overlay" onClick={() => setMobileMenu(false)} aria-label="Fechar menu"/>}
    <main className="main">
      <header className="topbar"><div className="topbar-left"><button className="menu-toggle" onClick={() => setMobileMenu(true)} aria-label="Abrir menu"><Menu size={22}/></button><span>Plataforma de consulta</span><ChevronRight size={14}/><strong>Tabela FIPE</strong></div><div className="topbar-right"><span className="live-indicator"/><span>Consulta online</span></div></header>
      <div className="page-content">
        <div className="heading"><span className="heading-tag"><span className="tag-dot"/> CONSULTA SIMPLES E RÁPIDA</span><h1>Descubra o valor <span>do seu veículo.</span></h1><p>Consulte o preço médio de carros, motos e caminhões de forma gratuita, rápida e sem complicações.</p></div>
        <div className="journey"><div className="journey-title"><span>PROGRESSO DA CONSULTA</span><span>{step === 4 ? '5' : step + 1} de 5 etapas</span></div><div className="steps">{labels.map((label, i) => <div className={`step ${i === step ? 'current' : ''} ${i < step ? 'completed' : ''}`} key={label}><div className="step-circle">{i < step ? <Check size={16}/> : String(i + 1).padStart(2, '0')}</div><span>{label}</span>{i < 4 && <div className="step-line"/>}</div>)}</div></div>
        <div className="content-grid"><div className="content-primary">
          {step === 0 && <section className="panel vehicle-panel"><div className="section-top"><span className="eyebrow">PASSO 01</span><span className="required-indicator">* Obrigatório</span></div><h2>Qual veículo você deseja consultar?</h2><p className="muted section-description">Selecione uma categoria para começar sua consulta.</p><div className="vehicle-grid">{types.map(({ id, title, detail, Icon }) => <button className="vehicle-card" key={id} onClick={() => selectTipo(id)}><div className="vehicle-icon"><Icon size={34} strokeWidth={1.7}/></div><strong>{title}</strong><span>{detail}</span><div className="vehicle-arrow"><ArrowRight size={20}/></div></button>)}</div><div className="info-strip"><ShieldCheck size={18}/><span>Consulta gratuita. Nenhum cadastro ou informação pessoal necessária.</span></div></section>}
          {step === 1 && tipo && <SelectionList keyId={`marcas-${tipo}`} title="Escolha a marca" description="Selecione ou busque a marca do veículo que deseja consultar." placeholder="Pesquisar marca... Ex.: Volkswagen" onBack={goBack} onSelect={selectMarca} list={listMarcas} search={searchMarcas}/>}
          {step === 2 && tipo && marca && <SelectionList keyId={`modelos-${tipo}-${marca.codigo}`} title="Escolha o modelo" description={`Modelos disponíveis para ${marca.nome}.`} placeholder="Pesquisar modelo... Ex.: Gol" onBack={goBack} onSelect={selectModelo} list={listModelos} search={searchModelos}/>}
          {step === 3 && tipo && marca && modelo && <SelectionList keyId={`anos-${tipo}-${marca.codigo}-${modelo.codigo}`} title="Escolha o ano" description={`Selecione o ano e o combustível de ${modelo.nome}.`} placeholder="Pesquisar ano ou combustível... Ex.: 2020" onBack={goBack} onSelect={selectAno} list={listAnos} search={searchAnos}/>}
          {step === 4 && <section className="panel result-panel"><div className="section-top"><button className="back-link" onClick={goBack}><ArrowLeft size={17}/> Voltar</button><span className="eyebrow">PASSO 05</span></div><div className="result-heading"><div className="result-check"><CheckCircle2 size={25}/></div><div><h2>Resultado da consulta</h2><p className="muted">Confira os dados do veículo selecionado.</p></div></div>{resultLoading && <div className="empty-state">Consultando valor FIPE...</div>}{resultError && <div className="notice error">{resultError}</div>}{resultado && !resultLoading && <><div className="price-card"><span>VALOR DE REFERÊNCIA FIPE</span><strong>{resultado.valorFipe}</strong><small>Preço médio de mercado informado pela fonte consultada</small></div><div className="result-details"><div><span>Marca</span><strong>{resultado.marca}</strong></div><div><span>Modelo</span><strong>{resultado.modelo}</strong></div><div><span>Ano</span><strong>{resultado.ano}</strong></div><div><span>Combustível</span><strong>{resultado.combustivel}</strong></div></div></>}<button className="primary-btn" onClick={reset}>Fazer nova consulta <ArrowRight size={18}/></button></section>}
        </div><aside className="content-aside"><section className="summary-card"><div className="summary-title"><span className="summary-title-icon"><CarFront size={18}/></span><h3>Sua consulta</h3></div><p>Acompanhe as informações selecionadas.</p><div className="summary-rows"><div><span>Tipo de veículo</span><strong>{tipo ? types.find(x => x.id === tipo)?.title : 'Não selecionado'}</strong></div><div><span>Marca</span><strong>{marca?.nome ?? 'Não selecionada'}</strong></div><div><span>Modelo</span><strong>{modelo?.nome ?? 'Não selecionado'}</strong></div><div><span>Ano</span><strong>{ano?.nome ?? 'Não selecionado'}</strong></div></div>{step > 0 && <button className="reset-link" onClick={reset}>Recomeçar consulta <ArrowRight size={15}/></button>}</section><section className="help-card"><div className="help-icon"><Clock3 size={21}/></div><h3>O que é a Tabela FIPE?</h3><p>É uma referência de preços médios de veículos no mercado brasileiro. Os valores podem variar conforme o estado de conservação, região e negociação.</p><a href="https://veiculos.fipe.org.br/" target="_blank" rel="noreferrer">Saiba mais <ArrowRight size={15}/></a></section></aside></div>
        <footer className="footer"><span>© {new Date().getFullYear()} AutoValor. Projeto de consulta FIPE.</span><span><CalendarDays size={15}/> Fonte: API FIPE Parallelum</span></footer>
      </div>
    </main>
  </div>;
}
