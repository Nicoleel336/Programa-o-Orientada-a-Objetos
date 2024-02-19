package nicoleEllen.associacaoBD;

public class Pagamento {
	Taxa taxa;
	private double pago = 0; //valor que foi pago
	private int numeroAssociado; //identificação de quem pago
	private long data;
	int vigencia;
	
	//construtor
	public Pagamento(Taxa taxa, double pago, long data, int vigencia) {
		this.taxa = taxa;
		this.pago = pago;
		this.data = data;
		this.vigencia = vigencia;
	}
	
	public Taxa getTaxa() {
		return taxa;
	}
	public double getPago() {
		return pago;
	}
	public int getNumeroAssociado() {
		return numeroAssociado;
	}
	
	public int getVigencia() {
		return vigencia;
	}
	//pagar a taxa
	public void Pagar(double valor) {
		pago += valor;
	}

	public void setNumeroAssociado(int numeroAssociado) {
		this.numeroAssociado = numeroAssociado;
	}
	
	public void setPago(double pago) {
		this.pago = pago;
	}

	public long getData() {
		return data;
	}
	
}
