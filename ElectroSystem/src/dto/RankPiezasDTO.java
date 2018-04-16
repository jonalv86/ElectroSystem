package dto;

public class RankPiezasDTO {
	private String Pieza;
	private String Marca;
	private String Descripcion;
	private int Cantidad;
	
	public RankPiezasDTO(String pieza, String marca, String descripcion, int cantidad) {
		super();
		Pieza = pieza;
		Marca = marca;
		Descripcion = descripcion;
		Cantidad = cantidad;
	}

	public String getPieza() {
		return Pieza;
	}

	public void setPieza(String pieza) {
		Pieza = pieza;
	}

	public String getMarca() {
		return Marca;
	}

	public void setMarca(String marca) {
		Marca = marca;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
}
