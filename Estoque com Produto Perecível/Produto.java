package nicoleEllen.estoqueComProdutoPerecivel;

import java.util.ArrayList;
import java.util.Date;

public class Produto {
	
	protected int cod; 
	protected String desc; //descricao do produto
	protected int min; //estoque mínimo
	protected double lucro;
	protected Fornecedor forn;
	protected int quantidade = 0; //quantidade do produto
	protected double precoCompra;
	protected double precoVenda;
	protected ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
	protected ArrayList<Movimento> historico = new ArrayList<Movimento>();
	
	//construtor para produto
	public Produto (int cod, String desc, int min, double lucro) {
		this.cod = cod;
		this.desc = desc;
		this.min = min;
		this.lucro = lucro; //transformando para porcentagem
	}
	
	//tem que mostrar venda(?)
	//tem que mostrar o estoque final
	
	public int getCodigo() {
		return cod;
	}
	
	public String getDescricao() {
		return desc;
	}

	public int getMin() {
		return min;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public double getPrecoVenda() {
		return precoVenda;
	}
	
	public double getPrecoCompra() {
		return precoCompra;
	}
	
	public double getLucro() {
		return lucro; 
	}
	
	public ArrayList<Fornecedor> getFornecedores() {
		return fornecedores;
	}
	
	public ArrayList<Movimento> getHistorico() {
		return historico;
	}
	
	//checar o tipo dessa daqui aqui
	public void compra(int quant, double val) {
		precoCompra = (precoCompra*quantidade + val*quant)/(quantidade + quant);
		quantidade += quant;
		Movimento operacao = new Movimento(new Date(),"Compra", precoCompra, quant, cod);
		historico.add(operacao); 
		precoVenda = precoCompra*(lucro+1); //verificar, de acordo com os testes 
	}

	public double venda(int quant) {
		if (quant <= 0 || quant > quantidade)
			return -1;
		Movimento operacao = new Movimento(new Date(),"Venda", precoVenda,quant,cod);
		historico.add(operacao);
		quantidade -= quant;
		return precoVenda*quant;
	}

	//adicionar fornecedor
	public void adicionaFornecedor(Fornecedor f) {
		fornecedores.add(f);
	}

	public Fornecedor getForn() {
		return forn;
	}
	
}
