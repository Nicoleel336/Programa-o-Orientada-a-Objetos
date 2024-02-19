package nicoleEllen.associacaoBD;

public class Taxa {
	private String nome;
	private double valorAno;
	private int vigencia; //ano de vigencia
	private boolean admnistrativa; // adm = true
	private int parcelas;
	
	//construtor
	public Taxa(String nome, int vigencia, double valorAno, int parcelas, boolean administrativa) {
		this.nome = nome;
		this.vigencia = vigencia;
		this.valorAno = valorAno;
		this.admnistrativa = administrativa;
		this.parcelas = parcelas;
	}

	public String getNome() {
		return nome;
	}

	public double getValorAno() {
		return valorAno;
	}

	public int getVigencia() {
		return vigencia;
	}

	public boolean isAdministrativa() {
		return admnistrativa;
	}

	public int getParcelas() {
		return parcelas;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValorAno(double valorAno) {
		this.valorAno = valorAno;
	}

	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}

	public void setAdmnistrativa(boolean admnistrativa) {
		this.admnistrativa = admnistrativa;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}
	
}
