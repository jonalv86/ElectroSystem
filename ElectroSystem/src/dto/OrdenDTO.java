package dto;

import java.util.Calendar;
import java.util.List;

public class OrdenDTO {

	private int idOT;
	private ClienteDTO cliente;
	private ElectrodomesticoDTO electrodomestico;
	private String descripcion;
	private UsuarioDTO usuarioAlta;
	private UsuarioDTO tecnicoAsoc;
	private boolean esDelivery;
	private Calendar vencPresup;
	private Calendar fechaReparado;
	private Calendar expiraGarantia;
	private OrdenDTO otAsociada;
	private EstadoDTO estado;
	private List<PiezaDTO> piezasPresupuestadas;
	private List<PiezaDTO> piezasUsadas;
	private double manoDeObra;
	private double costoDeEnvio;
	
	public OrdenDTO(int idOT, ClienteDTO cliente, ElectrodomesticoDTO electrodomestico, String descripcion,
			UsuarioDTO usuarioAlta, UsuarioDTO tecnicoAsoc, boolean esDelivery, Calendar vencPresup, Calendar fechaReparado,
			Calendar expiraGarantia, OrdenDTO otAsociada, EstadoDTO estado,
			List<PiezaDTO> piezasPresupuestadas, List<PiezaDTO> piezasUsadas, double manoDeObra, double costoDeEnvio) {
		this.idOT = idOT;
		this.cliente = cliente;
		this.electrodomestico = electrodomestico;
		this.descripcion = descripcion;
		this.usuarioAlta = usuarioAlta;
		this.tecnicoAsoc = tecnicoAsoc;
		this.esDelivery = esDelivery;
		this.vencPresup = vencPresup;
		this.fechaReparado = fechaReparado;
		this.expiraGarantia = expiraGarantia;
		this.otAsociada = otAsociada;
		this.estado = estado;
		this.piezasPresupuestadas = piezasPresupuestadas;
		this.piezasUsadas = piezasUsadas;
		this.manoDeObra = manoDeObra;
		this.costoDeEnvio = costoDeEnvio;
	}
	
	public OrdenDTO(int id, ClienteDTO clienteAsociado, ElectrodomesticoDTO electrodomestico, String detalle,
			UsuarioDTO usuario, boolean esDelivery) {
		this.idOT = id;
		this.cliente = clienteAsociado;
		this.electrodomestico = electrodomestico;
		this.descripcion = detalle;
		this.usuarioAlta = usuario;
		this.esDelivery = esDelivery;
	}
	

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public UsuarioDTO getTecnicoAsoc() {
		return tecnicoAsoc;
	}

	public void setTecnicoAsoc(UsuarioDTO tecnicoAsoc) {
		this.tecnicoAsoc = tecnicoAsoc;
	}

	public boolean isEsDelivery() {
		return esDelivery;
	}

	public void setEsDelivery(boolean esDelivery) {
		this.esDelivery = esDelivery;
	}

	public OrdenDTO getOtAsociada() {
		return otAsociada;
	}

	public void setOtAsociada(OrdenDTO otAsociada) {
		this.otAsociada = otAsociada;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	public double getManoDeObra() {
		return manoDeObra;
	}

	public void setManoDeObra(double manoDeObra) {
		this.manoDeObra = manoDeObra;
	}

	public Calendar getExpiraGarantia() {
		return expiraGarantia;
	}

	public List<PiezaDTO> getPiezasPresupuestadas() {
		return piezasPresupuestadas;
	}

	public void setPiezasPresupuestadas(List<PiezaDTO> piezasPresupuestadas) {
		this.piezasPresupuestadas = piezasPresupuestadas;
	}

	public List<PiezaDTO> getPiezasUsadas() {
		return piezasUsadas;
	}

	public void setPiezasUsadas(List<PiezaDTO> piezasUsadas) {
		this.piezasUsadas = piezasUsadas;
	}

	public void setExpiraGarantia(Calendar expiraGarantia) {
		this.expiraGarantia = expiraGarantia;
	}

	public Calendar getVencPresup() {
		return vencPresup;
	}

	public void setVencPresup(Calendar vencPresup) {
		this.vencPresup = vencPresup;
	}

	public ElectrodomesticoDTO getElectrodomestico() {
		return electrodomestico;
	}

	public void setElectrodomestico(ElectrodomesticoDTO electrodomestico) {
		this.electrodomestico = electrodomestico;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdOT() {
		return idOT;
	}

	public void setIdOT(int idOT) {
		this.idOT = idOT;
	}



	@Override
	public String toString() {
		return String.valueOf(idOT);
	}

	public double getCostoDeEnvio() {
		return costoDeEnvio;
	}

	public void setCostoDeEnvio(double costoDeEnvio) {
		this.costoDeEnvio = costoDeEnvio;
	}

	public Calendar getFechaReparado() {
		return fechaReparado;
	}

	public void setFechaReparado(Calendar fechaReparado) {
		this.fechaReparado = fechaReparado;
	}
}
