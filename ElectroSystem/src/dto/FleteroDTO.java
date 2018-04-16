package dto;

import java.util.Calendar;

public class FleteroDTO {

	private int idFletero;
	private String nombre, apellido;
	private String celular;
	private String registroConducir;
	private Calendar vencimientoRegistro;
	private VehiculoDTO vehiculo;

	public FleteroDTO(int idFletero, String nombre, String apellido, String celular, String registroConducir,
			Calendar vencimientoRegistro, VehiculoDTO vehiculo) {

		this.idFletero = idFletero;
		this.nombre = nombre;
		this.apellido = apellido;
		this.celular = celular;
		this.registroConducir = registroConducir;
		this.vencimientoRegistro = vencimientoRegistro;
		this.vehiculo = vehiculo;
	}

	public FleteroDTO() {
	}

	public int getIdFletero() {
		return idFletero;
	}

	public void setIdFletero(int idFletero) {
		this.idFletero = idFletero;
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getRegistroConducir() {
		return registroConducir;
	}

	public void setRegistroConducir(String registroConducir) {
		this.registroConducir = registroConducir;
	}

	public Calendar getVencimientoRegistro() {
		return vencimientoRegistro;
	}

	public void setVencimientoRegistro(Calendar vencimientoRegistro) {
		this.vencimientoRegistro = vencimientoRegistro;
	}

	public VehiculoDTO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoDTO vehiculo) {
		this.vehiculo = vehiculo;
	}

	@Override
	public String toString() {
		return this.nombre + " " + this.apellido;
	}

}
