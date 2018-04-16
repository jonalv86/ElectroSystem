package dto;

public class RankElectrodomesticosDTO {
	private String marca;
	private String modelo;
	private int cantidad;
	
	public RankElectrodomesticosDTO(String marca, String modelo, int cantidad) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.cantidad = cantidad;
	}
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
