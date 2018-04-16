package modelo;

import dto.PiezaDTO;

public class ImportPrice {

	private PiezaDTO pieza;
	private String nro_serie;
	private String descripcion;
	private String marca;
	private String precio_compra;
	private Integer answer;

	public ImportPrice(String nro_serie, String descripcion, String marca, String precio_compra, PiezaDTO pieza, Integer value) {
		super();
		this.pieza = pieza;
		this.nro_serie = nro_serie;
		this.descripcion = descripcion;
		this.marca = marca;
		this.precio_compra = precio_compra;
		this.answer = value;
	}

	public PiezaDTO getPieza() {
		return pieza;
	}

	public void setPieza(PiezaDTO pieza) {
		this.pieza = pieza;
	}

	public String getNro_serie() {
		return nro_serie;
	}

	public void setNro_serie(String nro_serie) {
		this.nro_serie = nro_serie;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPrecio_compra() {
		return precio_compra;
	}

	public void setPrecio_compra(String precio_compra) {
		this.precio_compra = precio_compra;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

}
