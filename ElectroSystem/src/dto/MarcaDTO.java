package dto;

public class MarcaDTO {

	private int idMarca;
	private String nombre;

	public MarcaDTO(int idMarca, String nombre) {
		this.idMarca = idMarca;
		this.nombre = nombre;
	}

	public int getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean equals(MarcaDTO marca) {
		return this.getIdMarca() == marca.getIdMarca() && this.getNombre().equals(marca.getNombre());
	}

	@Override
	public String toString() {
		return nombre; // Esto es para agregar al comboBox el DTO.
	}
}
