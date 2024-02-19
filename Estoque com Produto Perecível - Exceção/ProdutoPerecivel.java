package nicoleEllen.estoqueComProdutoPerecivelExcecoes;
import java.util.ArrayList;
import java.util.Date;

public class ProdutoPerecivel extends Produto {

	private ArrayList<Lote> lotes = new ArrayList<Lote>();

	//construtor
	public ProdutoPerecivel(int cod, String desc, int min, double lucro) {
		super(cod, desc, min, lucro);
	}

	public ArrayList<Lote> getLote() {
		return lotes;
	}


	public void compra(int quant, double val, Date data) {
		super.compra(quant, val);
		Lote lote = new Lote(quant, data);
		lotes.add(lote);
		//organizarLotes();
	}

	public Lote MenorDataLote() {
		Date atual = new Date();
		Lote loteMaisProximo = null;
		
		for(Lote lote : lotes) {
			if(!lote.getValidade().before(atual)) {
				if(loteMaisProximo == null || lote.getValidade().before(loteMaisProximo.getValidade())) {
					loteMaisProximo = lote;
				}
			}
		}
		
		return loteMaisProximo;
	}

	
	/*public double venda(int quant) throws ProdutoVencido{
		Date atual = new Date();
		//if (quant <= 0 || quant > quantidade)
			//return -1;
		int temp = quant;
		while(temp > 0) {
			Lote L = MenorDataLote();
			if(L == null || L.getValidade().before(atual)) throw new ProdutoVencido();
			if(L.getQuantidade() >= temp) {
				L.setQuantidade(L.getQuantidade() - temp);
				temp = 0;
			} else {
				temp -= L.getQuantidade();
				lotes.remove(L);
			}
		}
		quantidade -= quant;
		return quant * precoVenda;
	}*/

	public double venda(int quant) throws ProdutoVencido{
		Date atual = new Date();
		int temp = quant;
		int sold = 0;
		while(temp > 0) {
			if(quantidadeVencidos() >= temp) throw new ProdutoVencido();
			Lote L = MenorDataLote();
			if(L == null) throw new ProdutoVencido();
			if(L.getValidade().before(atual)) {
				lotes.remove(L);
				continue;
			}
			if(L.getQuantidade() >= temp) {
				L.setQuantidade(L.getQuantidade() - temp);
				sold += temp;
				temp = 0;
			} else {
				temp -= L.getQuantidade();
				sold += L.getQuantidade();
				lotes.remove(L);
			}
		}
		quantidade -= sold;
		return sold * precoVenda;
	}
	
	public int quantidadeVencidos() {
		Date dataAtual = new Date();
		int vencidos = 0;
		for (Lote elemento : lotes) {
			if (elemento.getValidade().before(dataAtual)) {
				vencidos += elemento.getQuantidade();
			}
		}
		return vencidos;
	}
	
	public boolean LoteVencido(Date data) throws ProdutoVencido{
		//data.setTime(data.getTime() + 6000);
		for (Lote l : lotes) {
			if (l.getValidade().before(data)) {
				return true;
			}
		}
		return false;
	}
	
}
