package nicoleEllen.associacaoBD;

import java.util.ArrayList;

public class MinhaAssociacao implements InterfaceAssociacao {
	//private ArrayList<Associacao> associacoes = new ArrayList<Associacao>();
	DAOAssociacao daoAssociacao = new DAOAssociacao();;
	
	public MinhaAssociacao() {
		LimpaLimpaLimpa.limpar();
	}
	
	private void VerificaValores(Associacao a) throws ValorInvalido{
		if(a.getNome() == null || a.getNome().isBlank() || a.getNome().isEmpty()) throw new ValorInvalido();
		if(a.getNum() <= 0) throw new ValorInvalido();
	}
	
	public Associacao pesquisar(int num){
		Associacao a = null;
		a = daoAssociacao.pesquisarPorNumero(num);
		return a;
	}
	
	public void adicionar(Associacao a) throws AssociacaoJaExistente, ValorInvalido{
		VerificaValores(a);
		if(pesquisar(a.getNum()) != null) throw new AssociacaoJaExistente();
		daoAssociacao.inserir(a);
	}
	
	private void VerificaAssociado(Associado a) throws ValorInvalido{
		if(a.getNome() == null || a.getNome().isBlank() || a.getNome().isEmpty()) throw new ValorInvalido();
		if(a.getNascimento() <= 0 || a.getNumero() <= 0) throw new ValorInvalido();
		if(a.getDataAssociacao() <= 0) throw new ValorInvalido();
		if(a.getTelefone() == null || a.getTelefone().isBlank() || a.getTelefone().isEmpty()) throw new ValorInvalido();
	}
	
	//adicionar associado a uma associação
	public void adicionar(int associacao, Associado a) throws AssociacaoNaoExistente, AssociadoJaExistente, ValorInvalido{
		if(associacao <= 0) throw new ValorInvalido();
		Associacao outra = pesquisar(associacao);
		if(outra == null) throw new AssociacaoNaoExistente();
		VerificaAssociado(a);
		for(Associado p: outra.getAssociados()) {
			if(p.getNumero() == a.getNumero()) throw new AssociadoJaExistente();
		}
		outra.adicionarAssociado(a);
	}

	//adiciona uma reunião a uma associação
	public void adicionar(int associacao, Reuniao r) throws AssociacaoNaoExistente, ReuniaoJaExistente, ValorInvalido{
		if(associacao <= 0) throw new ValorInvalido();
		if(r.getAta() == null || r.getAta().isBlank() || r.getAta().isEmpty()) throw new ValorInvalido();
		if(r.getData() <= 0) throw new ValorInvalido(); //verificar dps
		//if(r.getNumeroAssociacao() <= 0) throw new ValorInvalido();
		if(pesquisar(associacao) == null) throw new AssociacaoNaoExistente();
		
		Associacao outra = pesquisar(associacao);
		for(Reuniao reu : outra.getReunioes()) {
			if(reu.getData() == r.getData()) throw new ReuniaoJaExistente();
		}
		
		outra.getReunioes().add(r);
	}
	
	private void VerificaTaxa(Taxa t) throws ValorInvalido{
		if(t.getNome() == null || t.getNome().isBlank() || t.getNome().isEmpty()) throw new ValorInvalido();
		if(t.getParcelas() <= 0 || t.getValorAno() <= 0 || t.getVigencia() <= 0) throw new ValorInvalido();
	}
	
	public void adicionar(int associacao, Taxa t) throws AssociacaoNaoExistente, TaxaJaExistente, ValorInvalido{
		if(associacao <= 0) throw new ValorInvalido();
		Associacao outra = pesquisar(associacao);
		if(outra == null) throw new AssociacaoNaoExistente();
		VerificaTaxa(t);
		
		//ver se já existe -> não pode mesmo nome com mesma vigência
		for(Taxa tax : outra.getTaxas()) {
			if(tax.getNome().equals(t.getNome()) && tax.getVigencia() == t.getVigencia()) throw new TaxaJaExistente();
		}
		
		outra.adicionarTaxa(t);
		
		//verificar se tem que adicionar em associado!
	}
	
	public double calcularFrequencia(int numAssociado, int numAssociacao, long inicio, long fim) throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente, ValorInvalido{
		if(numAssociacao <= 0 || numAssociado <= 0) throw new ValorInvalido();
		Associacao outra = pesquisar(numAssociacao);
		if(outra == null) throw new AssociacaoNaoExistente();
		
		Associado ass = null;
		for(Associado a : outra.getAssociados()) {
			if(a.getNumero() == numAssociado) ass = a;
		}
		if(ass == null) throw new AssociadoNaoExistente();
		
		//verificar as reuniões dentro desse limite de tempo
		//long d1 = inicio.getTime(); //problema no tipo de reunião
		//long d2 = fim.getTime();
		ArrayList<Reuniao> intervalo = outra.pesquisarReunioes(inicio, fim);
		if(intervalo.size() == 0) throw new ReuniaoNaoExistente();
		
		//frequencia = soma/total
		//checar em cada reunião, entrar na área dos associados e somar os comparencimentos dele
		double frequencia = 0;
		for(Reuniao r : intervalo) {
			for(Associado a : r.getParticipantes()) {
				for(Associado p : r.getParticipantes()) {
					if(a.getNumero() == numAssociado) {
						frequencia++;
					}
				}
			}
		}
		
		frequencia = frequencia/intervalo.size();
		
		return frequencia;
	}
	
	
	public void registrarFrequencia(int codigoAssociado, int numAssociacao, long dataReuniao) throws AssociadoNaoExistente, ReuniaoNaoExistente, AssociacaoNaoExistente, FrequenciaJaRegistrada, FrequenciaIncompativel, ValorInvalido{
		if(codigoAssociado <= 0 || numAssociacao <= 0 || dataReuniao <= 0) throw new ValorInvalido();
		
		Associacao a = pesquisar(numAssociacao);
		if(a == null) throw new AssociacaoNaoExistente();
		
		Associado p = a.pesquisarAssociado(codigoAssociado);
		if(p == null) throw new AssociadoNaoExistente();
		
		Reuniao r = a.pesquisarReuniao(dataReuniao);
		if(r == null) throw new ReuniaoNaoExistente();
		
		//caso a reuniao tenha acontecido antes do associado entrar na associação
		if(p.getDataAssociacao() > r.getData()) throw new FrequenciaIncompativel();
		for(Associado presente : r.getParticipantes()) {
			if(presente.getNumero() == p.getNumero()) throw new FrequenciaJaRegistrada();
		}
		
		r.getParticipantes().add(p);
	}

	public double calcularTotalDeTaxas (int numAssociacao, int vigencia) throws AssociacaoNaoExistente, TaxaNaoExistente, ValorInvalido {
		if(numAssociacao <= 0 || vigencia <= 0) throw new ValorInvalido();
		Associacao a = pesquisar(numAssociacao);
		if(a == null) throw new AssociacaoNaoExistente();
		
		double totalTaxas = 0;
		for(Taxa t : a.getTaxas()) {
			if(t.getVigencia() == vigencia) totalTaxas += t.getValorAno();
		}
		
		if(totalTaxas == 0) throw new TaxaNaoExistente();
		
		return totalTaxas;
	}

	/*
	public void registrarPagamento(int numAssociacao, String taxa, int vigencia, int numAssociado, long data, double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido{
		if(numAssociacao <= 0 || vigencia <= 0 || numAssociado <= 0 || valor <= 0 || data <= 0) throw new ValorInvalido();
		if(taxa == null || taxa.isBlank() || taxa.isEmpty()) throw new ValorInvalido();
		
		Associacao a = pesquisar(numAssociacao);
		if(a == null) throw new AssociacaoNaoExistente();
		
		Associado associado = a.pesquisarAssociado(numAssociado);
		if(associado == null) throw new AssociadoNaoExistente();
		
		Taxa t = a.pesquisarTaxa(taxa, vigencia);
		if(t == null) throw new TaxaNaoExistente();
		
		if(valor > t.getValorAno()) throw new ValorInvalido();
		
		Pagamento pagamento = new Pagamento(t);
		associado.getPagamentos().add(pagamento);
		
		for(Pagamento pag : associado.getPagamentos()) {
			if(pag.getTaxa().getNome().equals(taxa) && pag.getTaxa().getVigencia() == vigencia) 
				pagamento = pag;
		}
		
        if(associado instanceof AssociadoRemido && t.isAdmnistrativa() && ((AssociadoRemido)associado).getDataRemissao()<=(data)) throw new AssociadoJaRemido();
		
		double parcela = t.getValorAno()/t.getParcelas();
		double diferenca = t.getValorAno() - pagamento.getPago();
		
		if(valor < parcela && diferenca > parcela) throw new ValorInvalido();
		
		pagamento.Pagar(valor);
		pagamento.setNumeroAssociado(numAssociado);
	}*/
	
	
	public void registrarPagamento(int numAssociacao, String taxa, int vigencia, int numAssociado, long data, double valor) throws AssociacaoNaoExistente, AssociadoNaoExistente, AssociadoJaRemido, TaxaNaoExistente, ValorInvalido{
		if(numAssociacao <= 0 || vigencia <= 0 || numAssociado <= 0 || valor <= 0 || data <= 0) throw new ValorInvalido();
		if(taxa == null || taxa.isBlank() || taxa.isEmpty()) throw new ValorInvalido();
		
		Associacao a = pesquisar(numAssociacao);
		if(a == null) throw new AssociacaoNaoExistente();
		
		Associado associado = a.pesquisarAssociado(numAssociado);
		if(associado == null) throw new AssociadoNaoExistente();
		
		Taxa t = a.pesquisarTaxa(taxa, vigencia);
		if(t == null) throw new TaxaNaoExistente();
		
		if(valor > t.getValorAno()) throw new ValorInvalido();
		
		if(associado instanceof AssociadoRemido) {
			///AssociadoRemido remido = (AssociadoRemido) associado;
			//System.out.println(data +" : "+remido.getDataRemissao());
			//if(remido.getDataRemissao() <= data ) System.out.println("data ja coisou");
			//if(t.isAdministrativa()) System.out.println("e é adm");
			if(t.isAdministrativa()) throw new AssociadoJaRemido();
		}
		
		double parcela = t.getValorAno()/t.getParcelas();
		double totalPago = 0; 
		
		for(Pagamento p : associado.getPagamentos()) {
			if(p.getTaxa().getNome().equals(t.getNome()) && p.getTaxa().getVigencia() == vigencia) 
				totalPago += p.getPago();
		}
		
		if(t.getValorAno() - totalPago > parcela) {
			if(valor < parcela) throw new ValorInvalido();
		}
		
		associado.registrarPagamento(t, data, valor);
		
	}
	
	/*
	public double somarPagamentoDeAssociado (int numAssociacao, int numAssociado, String nomeTaxa, int vigencia, long inicio, long fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente{
		Associacao a = pesquisar(numAssociacao);
		if(a == null) throw new AssociacaoNaoExistente();
		
		Associado associado = a.pesquisarAssociado(numAssociado);
		if(associado == null) throw new AssociadoNaoExistente();
		
		Taxa t = a.pesquisarTaxa(nomeTaxa, vigencia);
		if(t == null) throw new TaxaNaoExistente();
		
		double pagamento = 0;
		
		for(Pagamento pag : associado.getPagamentos()) {
			if(pag.getTaxa().getNome().equals(nomeTaxa) && pag.getTaxa().getVigencia() == vigencia) {
				pagamento = pag.getPago();
			}
		}
		
		//pagamento = associado.getPagamento(t);
		
		return pagamento;
	}
	*/
	public double somarPagamentoDeAssociado (int numAssociacao, int numAssociado, String nomeTaxa, int vigencia, long inicio, long fim) throws AssociacaoNaoExistente, AssociadoNaoExistente, TaxaNaoExistente{
		Associacao a = pesquisar(numAssociacao);
		if(a == null) throw new AssociacaoNaoExistente();
		
		Associado associado = a.pesquisarAssociado(numAssociado);
		if(associado == null) throw new AssociadoNaoExistente();
		
		Taxa t = a.pesquisarTaxa(nomeTaxa, vigencia);
		if(t == null) throw new TaxaNaoExistente();
		
		double pagamento = 0;
		
		pagamento = associado.somarPagamentos(t, inicio, fim);
		
		return pagamento;
	}
	
	
}
