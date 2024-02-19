package nicoleEllen.associacaoBD;

import java.util.ArrayList;

public class Associacao {
	private int num;
	private String nome;
	//private ArrayList<Associado> associados = new ArrayList<Associado>();
	DAOAssociado daoAssociado = new DAOAssociado();
	private ArrayList<Reuniao> reunioes = new ArrayList<Reuniao>();
	//private ArrayList<Taxa> taxas = new ArrayList<Taxa>();
	DAOTaxa daoTaxa = new DAOTaxa();
	
	//construtor
	public Associacao(int num, String nome) {
		this.num = num;
		this.nome = nome;
	}

	public int getNum() {
		return num;
	}

	public String getNome() {
		return nome;
	}

	public ArrayList<Associado> getAssociados() {
		ArrayList<Associado> associados = daoAssociado.getAssociadosDaAssociacao(num);
		return associados;
	}
	
	
	public ArrayList<Reuniao> getReunioes() {
		return reunioes;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Taxa> getTaxas() {
		ArrayList<Taxa> taxas = daoTaxa.getTaxasDaAssociacao(num);
		return taxas;
	}
	
	public ArrayList<Reuniao> pesquisarReunioes(long inicio, long termino){
		ArrayList<Reuniao> intervalo = new ArrayList<Reuniao>();
		for(Reuniao r : reunioes) {
			if(r.getData() >= inicio && r.getData() <= termino) {
				intervalo.add(r);
			}
		}
		return intervalo;
	}
	
	public Associado pesquisarAssociado(int numero) {
		ArrayList<Associado> associados = daoAssociado.getAssociadosDaAssociacao(this.num);
		for(Associado a : associados) {
			if(a.getNumero() == numero) 
				return a;
		}
		return null;
	}
	
	public Reuniao pesquisarReuniao(long data) {
		for(Reuniao r : reunioes) {
			if(r.getData() == data) return r;
		}
		return null;
	}
	
	public Taxa pesquisarTaxa(String nome, int vigencia) {
		for(Taxa t : daoTaxa.pesquisarTodas()) {
			if(t.getNome().equals(nome) && t.getVigencia() == vigencia) return t;
		}
		return null;
	}

	// método para adicionar associado
	public void adicionarAssociado(Associado associado){
		daoAssociado.inserir(associado,num);
	}
	
	// adicionar uma taxa
	public void adicionarTaxa(Taxa taxa) {
		daoTaxa.inserir(taxa, num);
	}
}
