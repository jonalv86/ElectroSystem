package dto;

import java.util.List;

public class ProveedorDTO {

	private int idProveedor;
	private String nombre;
	private String contacto;
	private String cuit;
	private String telefono;
	private String mail;
	private List<MarcaDTO> marcas;

	public ProveedorDTO(int idProveedor, String nombre, String contacto, String cuit, String telefono, String mail, List<MarcaDTO> marcas) {
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.contacto = contacto;
		this.cuit = cuit;
		this.telefono = telefono;
		this.mail = mail;
		this.marcas = marcas;
	}

	public void setMarcas(List<MarcaDTO> marcas) {
		this.marcas = marcas;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCuit() {
		return cuit;
	}

	private void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getMail() {
		return mail;
	}

	public String getContacto() {
		return contacto;
	}

	public List<MarcaDTO> getMarcas() {
		return marcas;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
