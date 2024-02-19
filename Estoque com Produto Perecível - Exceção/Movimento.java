package nicoleEllen.estoqueComProdutoPerecivelExcecoes;
import java.util.Date;

public class Movimento {
	
	Date data;
	private String tipo;
	private double valor;
	private int quantidade;
	private int codigo;
	
	
	public Movimento(Date d, String tp, double val, int quant, int cod) {
		data = d;
		tipo = tp;
		valor = val;
		quantidade = quant;
		codigo = cod;
	}


	public String toString() {
		String dtStr = data.getDate() + "/" + (data.getMonth()+1) + "/" + (data.getYear() + 1900);
		String opStr = ". " + tipo + "." + " Valor: " + valor + ". Quant: " + quantidade + ".\n";
		return dtStr + opStr;
	}
	
	
	public Date getData() {
		return data;
	}


	public String getTipo() {
		return tipo;
	}


	public double getValor() {
		return valor;
	}


	public int getQuantidade() {
		return quantidade;
	}


	public int getCodigo() {
		return codigo;
	}

	
	
	
}
