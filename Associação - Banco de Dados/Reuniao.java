package nicoleEllen.associacaoBD;

import java.util.ArrayList;

public class Reuniao {
	private long data; //tive que mudar para long
	private String ata;
	private ArrayList<Associado> participantes = new ArrayList<Associado>();
	private int numeroAssociacao; //de qual associacao foi feita
	
	public Reuniao(long data, String ata) {
		this.data = data;
		this.ata = ata;
	}

	public long getData() {
		return data;
	}

	public String getAta() {
		return ata;
	}

	public ArrayList<Associado> getParticipantes() {
		return participantes;
	}

	public int getNumeroAssociacao() {
		return numeroAssociacao;
	}

	public void setData(long data) {
		this.data = data;
	}

	public void setAta(String ata) {
		this.ata = ata;
	}

	public void setParticipantes(ArrayList<Associado> participantes) {
		this.participantes = participantes;
	}

	public void setNumeroAssociacao(int numeroAssociacao) {
		this.numeroAssociacao = numeroAssociacao;
	}
	
	
	
}
