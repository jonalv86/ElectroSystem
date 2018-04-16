package dto;

public class ElectrodomesticoDTO {

	private int idElectro;
	private MarcaDTO marca;
	private String modelo;
	private String descripcion;

	public ElectrodomesticoDTO(int idElectro, MarcaDTO Marca, String modelo, String descripcion) {
		super();
		this.idElectro = idElectro;
		this.marca = Marca;
		this.modelo = modelo;
		this.descripcion = descripcion;
	}

	public int getIdElectro() {
		return idElectro;
	}

	public void setIdElectro(int idElectro) {
		this.idElectro = idElectro;
	}

	public MarcaDTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaDTO marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return modelo; // Esto es para agregar al comboBox el DTO.
	}
}
