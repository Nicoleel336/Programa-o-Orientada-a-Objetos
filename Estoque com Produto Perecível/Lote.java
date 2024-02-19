package nicoleEllen.estoqueComProdutoPerecivel;

import java.util.Date;

/* quantidade de produto
 * data de validade
 * */

public class Lote {
	private int quantidade;
	private Date val;
	
	//construtor
	public Lote(int quant, Date validade) {
		quantidade = quant;
		val = validade;
	}
	
	public Date getValidade() {
		return val;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quant) {
		quantidade = quant;
	}
	
	public void venda(int quant) {
		quantidade -= quant;
	}
	
	
}
