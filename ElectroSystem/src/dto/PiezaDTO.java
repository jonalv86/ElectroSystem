package dto;

public class PiezaDTO {

	private int idProdStock;
	private int idProdPieza;
	private MarcaDTO marca;
	private String idUnico;
	private String Descripcion;
	private float Precio_venta;
	private int bajo_stock;
	private int stock;

	public PiezaDTO(int idProdPieza, MarcaDTO marca, String idUnico, String Descripcion, float Precio_venta, int bajo_stock, int stock) {
		this.setIdProdPieza(idProdPieza);
		this.setMarca(marca);
		this.idUnico = idUnico;
		this.setDescripcion(Descripcion);
		this.setPrecio_venta(Precio_venta);
		setBajo_stock(bajo_stock);
		this.stock = stock;
	}

	public PiezaDTO(int idProdPieza, MarcaDTO marca, String idUnico, String Descripcion, float Precio_venta, int bajo_stock) {
		this.setIdProdPieza(idProdPieza);
		this.setMarca(marca);
		this.idUnico = idUnico;
		this.setDescripcion(Descripcion);
		this.setPrecio_venta(Precio_venta);
		setBajo_stock(bajo_stock);
	}

	public PiezaDTO(int idProdStock, int idProdPieza, MarcaDTO marca, String idUnico, String descripcion, float precio_venta,
			int bajo_stock) {
		this.idProdStock = idProdStock;
		this.idProdPieza = idProdPieza;
		this.marca = marca;
		this.idUnico = idUnico;
		Descripcion = descripcion;
		Precio_venta = precio_venta;
		this.bajo_stock = bajo_stock;
	}

	public String getIdUnico() {
		return idUnico;
	}

	public int getIdProdPieza() {
		return idProdPieza;
	}

	public void setIdProdPieza(int idProdPieza) {
		this.idProdPieza = idProdPieza;
	}

	public MarcaDTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaDTO marca) {
		this.marca = marca;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public float getPrecio_venta() {
		return Precio_venta;
	}

	public void setPrecio_venta(float precio_venta) {
		Precio_venta = precio_venta;
	}

	public int getBajo_stock() {
		return bajo_stock;
	}

	public void setBajo_stock(int bajo_stock) {
		this.bajo_stock = bajo_stock;
	}

	@Override
	public String toString() {
		return idUnico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProdPieza;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PiezaDTO other = (PiezaDTO) obj;
		if (idProdPieza != other.getIdProdPieza())
			return false;
		return true;
	}

	public int getStock() {
		return stock;
	}

	public int getIdProdStock() {
		return idProdStock;
	}

	public void setIdProdStock(int idProdStock) {
		this.idProdStock = idProdStock;
	}

}
