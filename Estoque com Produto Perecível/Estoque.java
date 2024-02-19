package nicoleEllen.estoqueComProdutoPerecivel;
import java.util.ArrayList;
import java.util.Date;

public class Estoque implements InterfaceEstoque{
	//tem que ser privado?
	
	private ArrayList<Produto> produtos = new ArrayList<Produto>(); //produtos fornecidos?
	
	//COLOCAR CONSTRUTOR PADRÃO AQUI
	public Estoque() {
		ArrayList<Produto> p = new ArrayList<Produto>();
	}
	
	//colocar um produto no estoque
	//pode deixar num?
	public Produto pesquisar(int num) {
		for (Produto p : produtos) {
			if(p.getCodigo() == num) {
				return p; 
			}
		}
		return null;
	}

	// Retorna false se houver algum campo numérico invalido ou texto vazio ou null ou produto já cadastrado.
	// o cod > 0, mas pode ser 0?
	public boolean incluir(Produto p) {
		if (p.getCodigo() < 0) return false;
		if(p.getDescricao() == null) return false;
		if(p.getDescricao().isBlank() == true) return false;
		if(p.getMin() <= 0) return false;
		if(p.lucro < 0) return false;
		Produto outroProduto = pesquisar(p.getCodigo());
		if (outroProduto == null) {
			produtos.add(p);
			return true; //incluiu
		}
		return false;
	}
	
	//receber o código e ver se esse produto existe
	public int quantidade(int cod) {
		Produto p1 = pesquisar(cod);
		if(p1 !=  null) {
			return p1.getQuantidade();
		}
		return -1;
	}
	
	public ArrayList<Fornecedor> fornecedores(int cod){
		ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
		Produto p = pesquisar(cod);
		if(p != null) {
			fornecedores.addAll(p.getFornecedores());
			return fornecedores;
		}
		return fornecedores; //verificar depois
	}
	
	public String movimentacao(int cod, Date inicio, Date fim) {
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
	
	public void adicionarFornecedor(int cod, Fornecedor f) {
		Produto p = pesquisar(cod);
		if(p != null) {
			p.adicionaFornecedor(f);
		}
	}
	
	public double precoDeVenda(int cod) {
		Produto p = pesquisar(cod);
		if(p != null) {
			return p.getPrecoVenda();
		}
		return -1;
	}
	
	//tbm tem que checar a forma de retorno desse daqui
	public double precoDeCompra(int cod) {
		Produto p = pesquisar(cod);
		if(p != null) {
			return p.getPrecoCompra();
		}
		return -1;
	}
	
	//não posso comprar produto vencido!
	public boolean comprar(int cod, int quant, double preco, Date val) {
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
	}
	
	/*public double vender(int cod, int quant) {
		Date hoje = new Date();
		Produto p = pesquisar(cod);
		if (p != null && quant > 0) {
			if(!(p instanceof ProdutoPerecivel) && quant <= p.getQuantidade()) {
				p.venda(quant);
				return quant*(p.getPrecoVenda());
			}
			else if(p instanceof ProdutoPerecivel){
				ProdutoPerecivel prod = (ProdutoPerecivel) p; //cast
				
				prod.venda(quant);
				return quant*(prod.getPrecoVenda());
			}
		}
		return -1;
	}*/
	
	
	public double vender(int cod, int quant) {
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
	public int quantidadeVencidos(int cod) {
		Produto p = pesquisar(cod);
		if(p == null || !(p instanceof ProdutoPerecivel)) return 0;
		int quantidadeVencidos = 0;
		Date atual = new Date();
		ProdutoPerecivel produto = (ProdutoPerecivel) p;
		if(produto instanceof ProdutoPerecivel) {
			produto.quantidadeVencidos();
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
				if(p.LoteVencido(atual)) {
					estoqueVencido.add(elemento);
				}
			}
		}
		return estoqueVencido;
	}

}
