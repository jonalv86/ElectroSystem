package dto;

public class UsuarioDTO {
	@Override
	public String toString() {
		return nombre + " " + apellido;
	}

	private int idPersonal;
	private RolDTO rol;
	private String nombre;
	private String apellido;
	private String usuario;
	private String contrasenia;
	
	public UsuarioDTO(int idPersonal, int idRol, String rol, String nombre,
			String apellido, String usuario, String contrasenia) {
		this.idPersonal = idPersonal;
		this.rol = new RolDTO(idRol, rol);
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	public int getIdPersonal() {
		return idPersonal;
	}

	public void setIdPersonal(int idPersonal) {
		this.idPersonal = idPersonal;
	}

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
}
