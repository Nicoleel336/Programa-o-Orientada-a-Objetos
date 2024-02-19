package nicoleEllen.estoqueComProdutoPerecivelExcecoes;
import java.util.ArrayList;
import java.util.Date;

import nicoleEllen.estoqueComProdutoPerecivel.InterfaceEstoque;

public class Estoque implements InterfaceEstoqueComExcecoes{
	
	private ArrayList<Produto> produtos = new ArrayList<Produto>();
	
	public Estoque() {
		ArrayList<Produto> p = new ArrayList<Produto>();
	}
	
	
	public Produto pesquisar(int num) throws ProdutoInexistente {
		for (Produto p : produtos) {
			if(p.getCodigo() == num) {
				return p; 
			}
		}
		throw new ProdutoInexistente();
	}

	
	public void incluir(Produto p) throws ProdutoJaCadastrado, DadosInvalidos {
		if (p == null) {
	        throw new DadosInvalidos();
	    }
		try {
			Produto outroProduto = pesquisar(p.getCodigo());
			throw new ProdutoJaCadastrado(p);
		} catch(ProdutoInexistente e) {
			VerificaIncluir(p);
			produtos.add(p);
		}
	}

	/*public void incluir(Produto p) throws ProdutoJaCadastrado, DadosInvalidos{
		try {
			Produto outroProduto = pesquisar(p.getCodigo());
			throw new ProdutoJaCadastrado(outroProduto);
		} 
		catch(ProdutoInexistente e) {
			try {
				VerificaIncluir(p);
				produtos.add(p);
			} catch(DadosInvalidos d) {
				throw new DadosInvalidos();
			}
		}
	}*/
	
	//verificar os dados
	Produto VerificaIncluir(Produto p) throws DadosInvalidos {
		if(p != null && p.getCodigo() > 0 && p.getDescricao() != null && p.getDescricao().isBlank() == false && p.getMin() > 0 && p.lucro > 0) return p;
		throw new DadosInvalidos();
	}
	
	//receber o código e ver se esse produto existe
	public int quantidade(int cod) throws ProdutoInexistente{
		Produto p1 = pesquisar(cod);
		if(p1 !=  null) {
			return p1.getQuantidade();
		}
		throw new ProdutoInexistente();
	}
	
	//VERIFICAR!!!
	public ArrayList<Fornecedor> fornecedores(int cod) throws ProdutoInexistente{
		ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
		Produto p = pesquisar(cod);
		if(p != null) {
			fornecedores.addAll(p.getFornecedores());
			return fornecedores;
		}
		return fornecedores; //verificar depois
	}
	
	
	public String movimentacao(int cod, Date inicio, Date fim) throws ProdutoInexistente{
		String ret = "";
		ArrayList<Movimento> hist;
		Produto p = pesquisar(cod);
		if(p != null) {
			hist = p.getHistorico();
			for(int i = 0; i < hist.size(); i++) {
				if (hist.get(i).getData().after(inicio) && hist.get(i).getData().before(fim)) {
				  ret = ret + hist.get(i).toString();
				}
			}
		}
		return ret;
	}
	
	public void adicionarFornecedor(int cod, Fornecedor f) throws ProdutoInexistente {
		Produto p = pesquisar(cod);
		if(p != null) {
			p.adicionaFornecedor(f);
		}
	}
	
	public double precoDeVenda(int cod) throws ProdutoInexistente {
		Produto p = pesquisar(cod);
		if(p != null) {
			return p.getPrecoVenda();
		}
		return -1;
	}
	
	//tbm tem que checar a forma de retorno desse daqui
	public double precoDeCompra(int cod) throws ProdutoInexistente{
		Produto p = pesquisar(cod);
		if(p != null) {
			return p.getPrecoCompra();
		}
		return -1;
	}
	
	void VerificaCompra(int quant, double preco) throws DadosInvalidos{
		if(quant <= 0 || preco <= 0) throw new DadosInvalidos();
	}
	
	//não posso comprar produto vencido!
	/*public boolean comprar(int cod, int quant, double preco, Date val) throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel {
		Produto p = pesquisar(cod);
		Date dataAtual = new Date();
		//ver se é produto existe
		//depois checa de que tipo é
		if(p != null && quant > 0 && preco > 0) {
			if(!(p instanceof ProdutoPerecivel) && val == null) { //produto normal
				p.compra(quant, preco);
				return true;
			}
			else if ((p instanceof ProdutoPerecivel) && val != null) { //produto perecível com validade
				if(val.after(dataAtual)) { //se estiver dentro da validade = pode comprar
					ProdutoPerecivel prod = (ProdutoPerecivel) p; //cast
					prod.compra(quant, preco, val);
					return true;
				}
				return false;
			}
		}
		return false;
	}*/
	
	public void comprar(int cod, int quant, double preco, Date val)  throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel{
		Produto p = pesquisar(cod);
		Date dataAtual = new Date();
		VerificaCompra(quant, preco);
		
		//se for normal, mas tiver val -> ProdutoNaoPerecivel
		if(!(p instanceof ProdutoPerecivel)) { //produto normal -> ver erro pela val
			if(val != null) throw new ProdutoNaoPerecivel();
			else p.compra(quant, preco);
		}
		else {
			if(val != null && val.after(dataAtual)) {
				ProdutoPerecivel prod = (ProdutoPerecivel) p; //cast
				prod.compra(quant, preco, val);
			}
			else {
				throw new ProdutoNaoPerecivel(); //caso esteja vencido, ou com data null
			}
		}
		
	}
	
	
	/*public double vender(int cod, int quant) throws ProdutoInexistente, ProdutoVencido, DadosInvalidos{
		Date atual = new Date();
		Produto produto = pesquisar(cod);
		if(cod <= 0 || quant <= 0 || produto == null) return -1;
		if(produto.getQuantidade() < quant) return -1;
		if(!(produto instanceof ProdutoPerecivel)) {
			return produto.venda(quant); 
		}
		else {
			if(produto.getQuantidade() - quantidadeVencidos(cod) < quant) return -1;
			for(Produto elemento : produtos) { 
				if(produto.getCodigo() == cod) {
					ProdutoPerecivel prod = (ProdutoPerecivel) produto;
					return prod.venda(quant);
				}
			}
		}
		return -1;
	}*/
	/* 
	 * void VerificaVenda(Produto p,int quant) throws DadosInvalidos{
		if(quant < 0 || p.getQuantidade() <= 0 || p.getQuantidade() < quant) throw new DadosInvalidos();
	}
	*/
	void VerificaVenda(Produto p,int quant, Date atual) throws DadosInvalidos, ProdutoVencido {
		if(p instanceof ProdutoPerecivel) {
			ProdutoPerecivel prod = (ProdutoPerecivel) p;
			if(prod.LoteVencido(atual) == true) throw new ProdutoVencido();
		}
		if(quant < 0 || p.getQuantidade() < quant) throw new DadosInvalidos();
	}
	
	public double vender(int cod, int quant) throws ProdutoInexistente, ProdutoVencido, DadosInvalidos{
		Date atual = new Date();
		Produto produto = pesquisar(cod);

		VerificaVenda(produto, quant, atual);
		
		if(produto.getQuantidade() - quantidadeVencidos(cod) < quant) throw new ProdutoVencido();

		if(!(produto instanceof ProdutoPerecivel)) {
			return produto.venda(quant); 
		}
		else {
			for(Produto elemento : produtos) { 
				if(elemento.getCodigo() == cod) {
					ProdutoPerecivel prod = (ProdutoPerecivel) elemento;
					return prod.venda(quant);
				}
			}
		}
		return -1;
	}
	

	
	//mostra os produtos que estão abaixo do min
	public ArrayList<Produto> estoqueAbaixoDoMinimo(){
		ArrayList<Produto> abaixoDoMinimo = new ArrayList<Produto>();
		for(int i = 0; i < produtos.size(); i++) {
			if(produtos.get(i).getQuantidade() < produtos.get(i).getMin()) {
				abaixoDoMinimo.add(produtos.get(i));
			}
		}
		return abaixoDoMinimo;
	}
	
	/* 1- deve retornar 0 se não houver produtos vencidos
	 * ou a quantidade de produtos vencidos de um determinado código
	 * */
	//FALTA FAZER
	public int quantidadeVencidos(int cod) throws ProdutoInexistente{
		Produto p = pesquisar(cod);
		if(p == null || !(p instanceof ProdutoPerecivel)) return 0;
		int quantidadeVencidos = 0;
		Date atual = new Date();
		ProdutoPerecivel produto = (ProdutoPerecivel) p;
		if(produto instanceof ProdutoPerecivel) {
			quantidadeVencidos = produto.quantidadeVencidos();
		}
		return quantidadeVencidos;
	}

	//FALTA FAZER
	@Override
	public ArrayList<Produto> estoqueVencido() {
		ArrayList<Produto> estoqueVencido = new ArrayList<Produto>();
		Date atual = new Date();
		for(Produto elemento : produtos) {
			if(elemento instanceof ProdutoPerecivel) {
				ProdutoPerecivel p = (ProdutoPerecivel)elemento;
				try {
					p.LoteVencido(atual);
				}
				catch(ProdutoVencido v) {
					estoqueVencido.add(elemento);
				}
			}
		}
		return estoqueVencido;
	}

}
