package nicoleEllen.estoqueComProdutoPerecivel;

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

	public int getQuantidade() {
		Date data = new Date();
		int res = 0;
		for (Lote L : lotes) {
			if(L.getValidade().compareTo(data)>0) {
				res +=L.getQuantidade();
			}
		}
		return res;
	}

	public void compra(int quant, double val, Date data) {
		super.compra(quant, val);
		Lote lote = new Lote(quant, data);
		lotes.add(lote);
		//organizarLotes();
	}

	// organizar de acordo com a validade
	/*public void organizarLotes() {
		for (int i = 0; i < lotes.size() - 1; i++) {
			for (int j = 0; j < lotes.size() - i - 1; j++) {
				Lote loteAtual = lotes.get(j);
				Lote loteSeguinte = lotes.get(j + 1);
				// Compara as datas de validade e troca se necessário
				if (loteAtual.getValidade().compareTo(loteSeguinte.getValidade()) > 0) {
					// Troca os lotes de posição
					lotes.set(j, loteSeguinte);
					lotes.set(j + 1, loteAtual);
				}
			}
		}
	}*/
	
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

	
	public double venda(int quant) {
		Date atual = new Date();
		if (quant <= 0 || quant > quantidade)
			return -1;
		for (Lote elemento : lotes) {
			if (elemento.getValidade().before(atual))
				return -1;
			if (quant <= elemento.getQuantidade()) {
				elemento.setQuantidade(getQuantidade() - quant);
				return quant*precoVenda;  
			} else {
				int temp = quant;
				while(temp>0) {
					Lote L = MenorDataLote();
					if(L == null) return -1;
					if(L.getQuantidade() > temp) {
						L.setQuantidade(L.getQuantidade()-temp);
						temp = 0;
					}
					else {
						temp -= L.getQuantidade();
						lotes.remove(L);
					}
				}
				return quant*precoVenda; 
			}
		}
		return -1;
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
	
	public boolean LoteVencido(Date data) {
		data.setTime(data.getTime() + 6000);
		for (Lote l : lotes) {
			if (l.getValidade().before(data)) {
				return true;
			}
		}
		return false;
	}
	
}
