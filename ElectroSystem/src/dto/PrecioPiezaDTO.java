package dto;

public class PrecioPiezaDTO {

	private PiezaDTO pieza;
	private Float precio;

	public PrecioPiezaDTO(PiezaDTO pieza, Float precio) {
		super();
		this.pieza = pieza;
		this.precio = precio;
	}

	public PiezaDTO getPieza() {
		return pieza;
	}

	public void setPieza(PiezaDTO pieza) {
		this.pieza = pieza;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return pieza.toString();
	}

}
