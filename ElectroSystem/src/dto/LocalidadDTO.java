package dto;

public class LocalidadDTO {

	private int codigoPostal;
	private String nombre;
	private ZonaDTO zonaDeEnvio;

	public LocalidadDTO(int codigoPostal, String nombre, ZonaDTO zonaDeEnvio) {
		this.codigoPostal = codigoPostal;
		this.nombre = nombre;
		this.zonaDeEnvio = zonaDeEnvio;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ZonaDTO getZonaDeEnvio() {
		return zonaDeEnvio;
	}

	public void setZonaDeEnvio(ZonaDTO zonaDeEnvio) {
		this.zonaDeEnvio = zonaDeEnvio;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

}
