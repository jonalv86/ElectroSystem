package dto;

public class ClienteDTO {

	private int idCliente;
	private String nombre;
	private String apellido;
	private String direccion;
	// private String localidad;
	private String provincia;
	// private int codPostal;
	private LocalidadDTO localidad;
	private String telefono;
	private String eMail;

	public ClienteDTO(int idCliente, String nombre, String apellido, String direccion, String provincia,
			LocalidadDTO localidad, String telefono, String eMail) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.direccion = direccion;
		// this.localidad = localidad;
		this.provincia = provincia;
		// this.codPostal = codPostal;
		this.localidad = localidad;
		this.telefono = telefono;
		this.eMail = eMail;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LocalidadDTO getLocalidad() {
		return localidad;
	}

	public void setLocalidad(LocalidadDTO localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

//	public int getCodPostal() {
//		return codPostal;
//	}
//
//	public void setCodPostal(int codPostal) {
//		this.codPostal = codPostal;
//	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}
}
