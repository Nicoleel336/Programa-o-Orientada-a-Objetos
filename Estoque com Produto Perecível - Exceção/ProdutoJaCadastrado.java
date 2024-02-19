package nicoleEllen.estoqueComProdutoPerecivelExcecoes;

public class ProdutoJaCadastrado extends Exception {
	Produto p;
	
	//construtor
	ProdutoJaCadastrado(Produto p){
		//super("Produto já cadastrado!");
		//this.p = p;
	}
	
}
