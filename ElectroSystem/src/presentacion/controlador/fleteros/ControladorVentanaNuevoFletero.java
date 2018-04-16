package presentacion.controlador.fleteros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import dto.FleteroDTO;
import dto.UsuarioDTO;
import dto.VehiculoDTO;
import modelo.Modelo;
import presentacion.ventanas.fleteros.VentanaBuscadorVehiculos;
import presentacion.ventanas.fleteros.VentanaNuevoFletero;
import presentacion.ventanas.fleteros.VentanaNuevoVehiculo;

public class ControladorVentanaNuevoFletero implements ActionListener {

	private Modelo modelo;
	private VentanaNuevoFletero ventana;
	private VentanaBuscadorVehiculos buscador;
	private VentanaNuevoVehiculo nuevoVehiculo;

	private VehiculoDTO vehiculoAsociado;
	private FleteroDTO fletero;

	private UsuarioDTO logueado;

	public ControladorVentanaNuevoFletero(VentanaNuevoFletero ventana, Modelo modelo, UsuarioDTO usuario) {

		this.setVentana(ventana);
		this.setModelo(modelo);
		this.logueado = usuario;

		init();

	}

	public ControladorVentanaNuevoFletero(VentanaNuevoFletero ventanaFletero, Modelo modelo, FleteroDTO fletero,
			boolean editable, UsuarioDTO usuario) {

		this.setVentana(ventanaFletero);
		this.setModelo(modelo);
		this.logueado = usuario;

		this.fletero = new FleteroDTO(fletero.getIdFletero(), fletero.getNombre(), fletero.getApellido(),
				fletero.getCelular(), fletero.getRegistroConducir(), fletero.getVencimientoRegistro(),
				fletero.getVehiculo());

		this.vehiculoAsociado = fletero.getVehiculo();

		if (!editable) {
			prepararVentanaVer();
		} else {
			prepararVentanaEditar();
		}
		init();
	}

	public void prepararVentanaEditar() {

		this.ventana.setTitle("Modificar Fletero - ID " + fletero.getIdFletero());

		this.ventana.setTxtApellido(fletero.getApellido());
		this.ventana.setTxtNombre(fletero.getNombre());
		this.ventana.setTxtCelular(fletero.getCelular());
		this.ventana.setTxtRegistro(fletero.getRegistroConducir());
		this.ventana.setTxtPatente(fletero.getVehiculo().getPatente() + " - " + fletero.getVehiculo().getMarca() + " " + fletero.getVehiculo().getModelo());

		// this.ventana.setVencimientoRegistro(fletero.getVencimientoRegistro());

		this.ventana.mostrarActualizar();
	}

	public void prepararVentanaVer() {

		this.prepararVentanaEditar();

		this.ventana.setTitle("Ver Fletero - ID " + fletero.getIdFletero());

		this.ventana.bloquearCampos();

	}

	public void init() {

		this.ventana.setTitle("Nuevo Encargado de Envíos");

		this.ventana.getBtnAceptar().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnActualizar().addActionListener(this);

		this.ventana.getBtnBuscarVehculo().addActionListener(this);
		this.ventana.getBtnNuevoVechculo().addActionListener(this);

		this.ventana.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getBtnBuscarVehculo()) {

			this.buscador = new VentanaBuscadorVehiculos(this.ventana);
			new ControladorVentanaBusquedaVehiculo(buscador, modelo, this.modelo.obtenerVehiculos(), this.logueado);

			if (this.buscador.getVehiculoElegido() != null) {
				this.ventana.setTxtPatente(this.buscador.getVehiculoElegido().getPatente() + " - "
						+ this.buscador.getVehiculoElegido().getMarca() + " "
						+ this.buscador.getVehiculoElegido().getModelo());

				this.vehiculoAsociado = this.buscador.getVehiculoElegido();
			}

		} else if (e.getSource() == this.ventana.getBtnNuevoVechculo()) {

			this.nuevoVehiculo = new VentanaNuevoVehiculo(this.ventana);
			new ControladorVentanaNuevoVehiculo(this.nuevoVehiculo, this.modelo);

			// TODO que se muestre el ultimo vehiculo que se agregó para no
			// tener que buscarlo

			// this.ventana.setTxtPatente(this.modelo.obtenerVehiculos());

		} else if (e.getSource() == this.ventana.getBtnAceptar()) {
			if (fechasValidas()) {
				if (camposCompletos()) {
					crearFletero(0);
					this.ventana.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Fecha inválida. Seleccione una fecha posterior.");
			}

		} else if (e.getSource() == this.ventana.getBtnActualizar()) {
			if (fechasValidas()) {
				if (camposCompletos()) {
					crearFletero(this.fletero.getIdFletero());
					this.ventana.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Fecha inválida. Seleccione una fecha posterior.");
			}

		} else if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();
		}

	}

	public boolean camposCompletos() {

		if (this.ventana.getTxtNombre().isEmpty() || this.ventana.getTxtApellido().isEmpty()
				|| this.ventana.getTxtCelular().isEmpty() || this.ventana.getTxtRegistro().isEmpty()
				|| this.ventana.getVencimientoRegistro().getModel().getValue() == null
				|| this.ventana.getTxtPatente() == null) {
			JOptionPane.showMessageDialog(null, "Por favor, completar todos los campos. ");
			return false;
		}
		return true;
	}

	public void crearFletero(int id) {

		if (id != 0) {

			FleteroDTO fletero = new FleteroDTO(this.fletero.getIdFletero(), this.ventana.getTxtNombre(),
					this.ventana.getTxtApellido(), this.ventana.getTxtCelular(), this.ventana.getTxtRegistro(),
					(Calendar) this.ventana.getVencimientoRegistro().getModel().getValue(),

					new VehiculoDTO(vehiculoAsociado.getPatente().toString(), vehiculoAsociado.getMarca().toString(),
							vehiculoAsociado.getModelo().toString(), vehiculoAsociado.getCapacidadCarga(),
							vehiculoAsociado.getFechaVencimientoVTV(), vehiculoAsociado.isOblea(),
							vehiculoAsociado.getFechaVencimientoOblea(), vehiculoAsociado.getEstado()));

			try {
				this.modelo.actualizarFletero(fletero);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			FleteroDTO fletero = new FleteroDTO(0, this.ventana.getTxtNombre(), this.ventana.getTxtApellido(),
					this.ventana.getTxtCelular(), this.ventana.getTxtRegistro(),
					(Calendar) this.ventana.getVencimientoRegistro().getModel().getValue(),

					new VehiculoDTO(vehiculoAsociado.getPatente().toString(), vehiculoAsociado.getMarca().toString(),
							vehiculoAsociado.getModelo().toString(), vehiculoAsociado.getCapacidadCarga(),
							vehiculoAsociado.getFechaVencimientoVTV(), vehiculoAsociado.isOblea(),
							vehiculoAsociado.getFechaVencimientoOblea(), vehiculoAsociado.getEstado()));

			try {
				this.modelo.agregarFletero(fletero);
				JOptionPane.showMessageDialog(null, "Se agregó al nuevo fletero");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void setVentana(VentanaNuevoFletero ventana) {
		this.ventana = ventana;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public boolean fechasValidas() {
		
		Calendar vencimientoRegistro = (Calendar) this.ventana.getVencimientoRegistro().getModel().getValue();
		vencimientoRegistro.add(Calendar.DAY_OF_MONTH, 0);
		Date fechaRegistro = vencimientoRegistro.getTime();

		if (fechaRegistro.before(new Date())) {
			return false;
		}
		return true;
	}

}
