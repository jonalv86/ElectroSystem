package dto;

public class RolDTO {

	@Override
	public String toString() {
		return Descripcion;
	}

	private int IdRol;
	private String Descripcion;

	public RolDTO(int IdRol, String Descripcion) {
		super();
		this.setIdRol(IdRol);
		this.setDescripcion(Descripcion);
	}

	public String getDescripcion() {
		return Descripcion;
	}

	private void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public int getIdRol() {
		return IdRol;
	}

	private void setIdRol(int idRol) {
		IdRol = idRol;
	}

}
