package dto;

public class EnvioDTO {

	private int idOT;
	private int idFletero;

	public EnvioDTO(int idOT, int idFletero) {
		this.idOT = idOT;
		this.idFletero = idFletero;
	}

	public int getIdOT() {
		return idOT;
	}

	public void setIdOT(int idOT) {
		this.idOT = idOT;
	}

	public int getIdFletero() {
		return idFletero;
	}

	public void setIdFletero(int idFletero) {
		this.idFletero = idFletero;
	}

}