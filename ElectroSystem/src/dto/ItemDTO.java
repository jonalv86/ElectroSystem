package dto;

public class ItemDTO {

	private PiezaDTO pieza;
	private Integer cantidadPiezas;
	private CostoVariableDTO costo;
	private Integer horasEstimadas;

	public ItemDTO(PiezaDTO pieza, Integer cantidadPiezas, CostoVariableDTO costo, Integer horasEstimadas) {
		super();
		this.pieza = pieza;
		this.cantidadPiezas = cantidadPiezas;
		this.costo = costo;
		this.horasEstimadas = horasEstimadas;
	}

	public PiezaDTO getPieza() {
		return pieza;
	}

	public void setPieza(PiezaDTO pieza) {
		this.pieza = pieza;
	}

	public CostoVariableDTO getCosto() {
		return costo;
	}

	public void setCosto(CostoVariableDTO costo) {
		this.costo = costo;
	}

	public Integer getCantidadPiezas() {
		return cantidadPiezas;
	}

	public void setCantidadPiezas(Integer cantidadPiezas) {
		this.cantidadPiezas = cantidadPiezas;
	}

	public Integer getHorasEstimadas() {
		return horasEstimadas;
	}

	public void setHorasEstimadas(Integer horasEstimadas) {
		this.horasEstimadas = horasEstimadas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((costo == null) ? 0 : costo.hashCode());
		result = prime * result + ((pieza == null) ? 0 : pieza.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDTO other = (ItemDTO) obj;
		if (costo == null) {
			if (other.costo != null)
				return false;
			if (pieza == null) {
				if (other.pieza != null)
					return false;
			} else if (!pieza.equals(other.pieza))
				return false;

		} else if (!costo.equals(other.costo))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "ItemDTO [pieza=" + pieza + ", cantidadPiezas=" + cantidadPiezas + ", costo=" + costo + ", horasEstimadas=" + horasEstimadas + "]";
	}

}
