package dto;

import java.util.Calendar;

public class VehiculoDTO {

	private String patente, marca, modelo;
	private int capacidadCarga;
	private Calendar fechaVencimientoVTV;
	private boolean oblea;
	private Calendar fechaVencimientoOblea;
	private EstadoDTO estado;

	public VehiculoDTO(String patente, 
			String marca, String modelo, int capacidadCarga, Calendar fechaVencimientoVTV,
			boolean oblea, Calendar fechaVencimientoOblea, EstadoDTO estado) {

		this.patente = patente;
		this.marca = marca;
		this.modelo = modelo;
		this.capacidadCarga = capacidadCarga;
		this.fechaVencimientoVTV = fechaVencimientoVTV;
		this.oblea = oblea;
		this.fechaVencimientoOblea = fechaVencimientoOblea;
		this.estado = estado;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
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

	public int getCapacidadCarga() {
		return capacidadCarga;
	}

	public void setCapacidadCarga(int capacidadCarga) {
		this.capacidadCarga = capacidadCarga;
	}

	public Calendar getFechaVencimientoVTV() {
		return fechaVencimientoVTV;
	}

	public void setFechaVencimientoVTV(Calendar fechaVencimientoVTV) {
		this.fechaVencimientoVTV = fechaVencimientoVTV;
	}

	public boolean isOblea() {
		return oblea;
	}

	public void setOblea(boolean oblea) {
		this.oblea = oblea;
	}

	public Calendar getFechaVencimientoOblea() {
		return fechaVencimientoOblea;
	}

	public void setFechaVencimientoOblea(Calendar fechaVencimientoOblea) {
		this.fechaVencimientoOblea = fechaVencimientoOblea;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

}
