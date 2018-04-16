package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dto.LocalidadDTO;
import dto.ZonaDTO;
import modelo.Modelo;
import presentacion.ventanas.cliente.NuevaLocalidad;

public class ControladorLocalidad implements ActionListener {

	Modelo modelo;
	NuevaLocalidad ventanaLocalidad;

	private LocalidadDTO elegida;

	public ControladorLocalidad(NuevaLocalidad ventana, Modelo modelo) {

		iniciar(ventana, modelo);

		this.ventanaLocalidad.setTitle("Nueva Localidad");
		this.ventanaLocalidad.setVisible(true);
	}

	private void iniciar(NuevaLocalidad ventana, Modelo modelo) {
		this.setVentanaLocalidad(ventana);
		this.setModelo(modelo);

		this.ventanaLocalidad.getCancelButton().addActionListener(this);
		this.ventanaLocalidad.getOkButton().addActionListener(this);

		for (int i = 0; i < this.modelo.obtenerZonas().size(); i++) {
			this.ventanaLocalidad.getComboBox().addItem(this.modelo.obtenerZonas().get(i));
		}
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public NuevaLocalidad getVentanaLocalidad() {
		return ventanaLocalidad;
	}

	public void setVentanaLocalidad(NuevaLocalidad ventanaLocalidad) {
		this.ventanaLocalidad = ventanaLocalidad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventanaLocalidad.getCancelButton()) {

			this.ventanaLocalidad.dispose();
		}

		else if (e.getSource() == this.ventanaLocalidad.getOkButton()) {

			LocalidadDTO localidad = new LocalidadDTO(this.ventanaLocalidad.getTxtCP(),
					this.ventanaLocalidad.getTxtNombre(),
					(ZonaDTO) this.ventanaLocalidad.getComboBox().getSelectedItem());

			this.modelo.agregarLocalidad(localidad);
			this.elegida = localidad;
			this.ventanaLocalidad.dispose();

		}

	}

	public LocalidadDTO getElegida() {
		return elegida;
	}

	public void setElegida(LocalidadDTO elegida) {
		this.elegida = elegida;
	}

}
