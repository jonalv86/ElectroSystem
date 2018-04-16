package dto;

import java.util.List;

public class SolicitudCompraDTO {

	private int id;
	private ProveedorDTO proveedor;
	private EstadoDTO estado;
	private List<ItemDTO> piezas;

	public SolicitudCompraDTO(int id, ProveedorDTO proveedor, EstadoDTO estado, List<ItemDTO> piezas) {
		super();
		this.id = id;
		this.proveedor = proveedor;
		this.estado = estado;
		this.piezas = piezas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProveedorDTO getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorDTO proveedor) {
		this.proveedor = proveedor;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	public List<ItemDTO> getPiezas() {
		return piezas;
	}

	public void setPiezas(List<ItemDTO> piezas) {
		this.piezas = piezas;
	}

}
