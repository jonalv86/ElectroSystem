package presentacion.controlador.fleteros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dto.VehiculoDTO;
import modelo.Modelo;
import presentacion.ventanas.fleteros.VentanaNuevoVehiculo;

public class ControladorVentanaNuevoVehiculo implements ActionListener {

	private Modelo modelo;
	private VentanaNuevoVehiculo ventana;

	public ControladorVentanaNuevoVehiculo(VentanaNuevoVehiculo ventana, Modelo modelo) {

		this.modelo = modelo;
		this.ventana = ventana;

		init(true);

	}

	public ControladorVentanaNuevoVehiculo(VentanaNuevoVehiculo ventana, Modelo modelo, VehiculoDTO vehiculo,
			boolean edit) {

		this.modelo = modelo;
		this.ventana = ventana;

		traerDatos(vehiculo);

		if (!edit)
			bloquearCampos();

		init(false);

	}

	public void init(boolean nuevo) {

		this.ventana.getBtnAceptar().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);

		this.ventana.getRdbtnSi().addActionListener(this);
		this.ventana.getRdbtnNo().addActionListener(this);

		this.ventana.setVisible(true);

		if (nuevo) {
			this.ventana.setTitle("Nuevo Vehículo");
			this.ventana.getRdbtnNo().setSelected(true);
			this.ventana.esconderOpciones();
		} else
			this.ventana.setTitle("Editar Vehículo");

	}

	public void traerDatos(VehiculoDTO vehiculo) {

		this.ventana.setTxtPatente(vehiculo.getPatente());
		this.ventana.setTxtMarca(vehiculo.getMarca());
		this.ventana.setTxtModelo(vehiculo.getModelo());
		this.ventana.setSpinnerCapacidad(vehiculo.getCapacidadCarga());
		// this.ventana.setVencimientoVTV(vehiculo.getFechaVencimientoVTV());
		if (vehiculo.isOblea()) {
			this.ventana.getRdbtnSi().setSelected(true);
			// this.ventana.setVencimientoOblea(vehiculo.getFechaVencimientoOblea());
		} else
			this.ventana.getRdbtnNo().setSelected(true);
		this.ventana.setComboEstado(vehiculo.getEstado());
	}

	public void bloquearCampos() {
		this.ventana.bloquear();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getRdbtnNo()) {
			this.ventana.esconderOpciones();

		} else if (e.getSource() == this.ventana.getRdbtnSi()) {
			this.ventana.mostrarOpciones();
		}

		if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();
		}

		else if (e.getSource() == this.ventana.getBtnAceptar()) {

			if (!existeVehiculo()) {
				if (fechasValidas()) {

					if (camposCompletos()) {
						crearVehiculo();
						JOptionPane.showMessageDialog(null, "Se agregó correctamente el vehículo.");
						this.ventana.dispose();
					} else
						JOptionPane.showMessageDialog(null, "Complete todos los campos.");

				} else {
					JOptionPane.showMessageDialog(null, "Fecha inválida. Seleccione una fecha posterior.");
				}

			} else {
				if (fechasValidas()) {

					if (camposCompletos()) {
						modificarVehiculo();
						JOptionPane.showMessageDialog(null, "Se actualizaron los datos del vehículo.");
						this.ventana.dispose();
					} else
						JOptionPane.showMessageDialog(null, "Complete todos los campos.");

				} else {
					JOptionPane.showMessageDialog(null, "Fecha inválida. Seleccione una fecha posterior.");
				}
			}
		}
	}

	public boolean existeVehiculo() {
		List<VehiculoDTO> vehiculos = this.modelo.obtenerVehiculos();
		for (int i = 0; i < vehiculos.size(); i++) {
			if (vehiculos.get(i).getPatente().toUpperCase().equals(this.ventana.getTxtPatente().toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public void crearVehiculo() {

		VehiculoDTO nuevo = new VehiculoDTO(this.ventana.getTxtPatente(), this.ventana.getTxtMarca(),
				this.ventana.getTxtModelo(), this.ventana.getSpinnerCapacidad(),
				(Calendar) this.ventana.getVencimientoVTV().getModel().getValue(), this.ventana.conOblea(),
				(Calendar) this.ventana.getVencimientoOblea().getModel().getValue(), this.ventana.getComboEstado());

		this.modelo.agregarVehiculo(nuevo);
	}

	public void modificarVehiculo() {

		if (this.ventana.conOblea()) {
			VehiculoDTO nuevo = new VehiculoDTO(this.ventana.getTxtPatente(), this.ventana.getTxtMarca(),
					this.ventana.getTxtModelo(), this.ventana.getSpinnerCapacidad(),
					(Calendar) this.ventana.getVencimientoVTV().getModel().getValue(), true,
					(Calendar) this.ventana.getVencimientoOblea().getModel().getValue(), this.ventana.getComboEstado());

			this.modelo.actualizarVehiculo(nuevo);

		} else {
			VehiculoDTO nuevo = new VehiculoDTO(this.ventana.getTxtPatente(), this.ventana.getTxtMarca(),
					this.ventana.getTxtModelo(), this.ventana.getSpinnerCapacidad(),
					(Calendar) this.ventana.getVencimientoVTV().getModel().getValue(), false, null,
					this.ventana.getComboEstado());

			this.modelo.actualizarVehiculo(nuevo);

		}

	}

	public boolean camposCompletos() {
		if (this.ventana.getTxtPatente() == null || this.ventana.getTxtModelo() == null
				|| this.ventana.getTxtMarca() == null
				|| this.ventana.getVencimientoVTV().getModel().getValue() == null) {
			return false;
		}
		return true;
	}

	public boolean fechasValidas() {
		Calendar vencimientoVTV = (Calendar) this.ventana.getVencimientoVTV().getModel().getValue();
		vencimientoVTV.add(Calendar.DAY_OF_MONTH, 0);
		Date fechaVTV = vencimientoVTV.getTime();

		if (fechaVTV.before(new Date())) {
			return false;
		}

		if (this.ventana.conOblea()) {
			Calendar vencimientoOblea = (Calendar) this.ventana.getVencimientoOblea().getModel().getValue();
			vencimientoOblea.add(Calendar.DAY_OF_MONTH, 0);
			Date fechaOblea = vencimientoOblea.getTime();

			if (fechaOblea.before(new Date())) {
				return false;
			}
		}

		return true;
	}

}
