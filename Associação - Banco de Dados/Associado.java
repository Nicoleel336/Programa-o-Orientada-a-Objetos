package nicoleEllen.associacaoBD;

import java.util.ArrayList;

public class Associado {
	//numero positivo (int), nome (String), telefone (String), nascimento (long), dataAssociacao (long)
	private int numero;
	private String nome;
	private String telefone;
	private long nascimento;
	private long dataAssociacao;
	//se paga = não é remido; tem que registrar os pagamentos
	//guarda os pagamentos feitos por um associado 'X'
	//ArrayList<Pagamento> pagamentos = new ArrayList<Pagamento>();
	DAOPagamentos daoPagamentos = new DAOPagamentos();
	
	//construtor
	public Associado (int numero, String nome, String telefone, long dataAssociacao, long nascimento) {
		this.numero = numero;
		this.nome = nome;
		this.telefone = telefone;
		this.dataAssociacao = dataAssociacao;
		this.nascimento = nascimento;
	}

	public int getNumero() {
		return numero;
	}

	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public long getNascimento() {
		return nascimento;
	}

	public long getDataAssociacao() {
		return dataAssociacao;
	}

	public ArrayList<Pagamento> getPagamentos() {
		ArrayList<Pagamento> pagamentos = daoPagamentos.getPagamentos(numero);
		return pagamentos;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setNascimento(long nascimento) {
		this.nascimento = nascimento;
	}

	public void setDataAssociacao(long dataAssociacao) {
		this.dataAssociacao = dataAssociacao;
	}

	public void registrarPagamento(Taxa t, long data, double valor) {
		Pagamento pag = new Pagamento(t, valor, data, t.getVigencia());
		pag.setNumeroAssociado(this.numero);
		daoPagamentos.inserir(pag); 
	}
	
	public double somarPagamentos(Taxa t, long inicio, long fim) {
		double total = 0;
		
		for(Pagamento p : daoPagamentos.getPagamentos(numero)) {
			if(p.getData() >= inicio && p.getData() <= fim && p.getTaxa().getNome().equals(t.getNome()))
					total += p.getPago();
		}
		
		return total;
	}

}
