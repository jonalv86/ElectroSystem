package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dto.ClienteDTO;
import dto.LocalidadDTO;
import dto.ZonaDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.cliente.NuevaLocalidad;
import presentacion.ventanas.cliente.VentanaAltaModifCliente;
import presentacion.ventanas.ot.VentanaAdminOT;

public class ControladorCliente implements ActionListener {

	private VentanaAltaModifCliente ventana;
	private Modelo modelo;
	private ClienteDTO cliente;

	public ControladorCliente(VentanaAltaModifCliente ventana, Modelo modelo) {
		iniciar(ventana, modelo);
		this.ventana.setTitle("Nuevo cliente");
		this.ventana.setVisible(true);
	}

	public ControladorCliente(VentanaAltaModifCliente ventana, Modelo modelo, ClienteDTO cliente, boolean editable, boolean editarDireccion) {
		iniciar(ventana, modelo);
		this.setCliente(cliente);
		this.ventana.getBtnAlta().setText("Guardar cambios");
		this.ventana.setTitle("Modificar cliente " + cliente.getNombre() + " " + cliente.getApellido());
		cargarCliente();
		if (!editable)
			bloquearCampos();
		if (editarDireccion)
			mostrarSoloDireccion();
		this.ventana.setVisible(true);
	}

	private void iniciar(VentanaAltaModifCliente ventana, Modelo modelo) {
		this.setVentana(ventana);
		this.setModelo(modelo);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAlta().addActionListener(this);
		this.ventana.getBtnNuevaLocalidad().addActionListener(this);

		this.ventana.getComboLocalidades().addItem(new LocalidadDTO(0, "(seleccionar)", new ZonaDTO(0, null, 0)));
		for (int i = 0; i < this.modelo.obtenerLocalidades().size(); i++) {
			this.ventana.getComboLocalidades().addItem(this.modelo.obtenerLocalidades().get(i));
		}
	}

	private void bloquearCampos() {
		this.ventana.getTfNombre().setEditable(false);
		this.ventana.getTfApellido().setEditable(false);
		this.ventana.getTfDireccion().setEditable(false);
		// this.ventana.getTfLocalidad().setEditable(false);
		this.ventana.getTfProvincia().setEditable(false);
		// this.ventana.getTfCodPostal().setEditable(false);
		this.ventana.getBtnNuevaLocalidad().setVisible(false);
		this.ventana.getComboLocalidades().setEnabled(false);
		this.ventana.getTfTelefono().setEditable(false);
		this.ventana.getTfMail().setEditable(false);
		this.ventana.getBtnAlta().setVisible(false);
		
		this.ventana.getTxtLocalidad().setVisible(true);
		this.ventana.getTxtLocalidad().setText(this.cliente.getLocalidad().getNombre());
		this.ventana.getComboLocalidades().setVisible(false);
		this.ventana.getBtnNuevaLocalidad().setVisible(false);

		this.ventana.getBtnCancelar().setText("Cerrar");
		this.ventana.getBtnCrearOt().setVisible(true);
		this.ventana.getBtnCrearOt().addActionListener(this);
	}
	
	public void mostrarSoloDireccion() {
		
		this.ventana.setLblDireccin("Dirección de envío: ");
		
		this.ventana.getTfNombre().setEditable(false);
		this.ventana.getTfApellido().setEditable(false);
		this.ventana.getTfDireccion().setEditable(true);
		this.ventana.getTfProvincia().setEditable(true);
		this.ventana.getBtnNuevaLocalidad().setVisible(true);
		this.ventana.getComboLocalidades().setEnabled(true);
		this.ventana.getTfTelefono().setEditable(false);
		this.ventana.getTfMail().setEditable(false);
		
		this.ventana.getTxtLocalidad().setVisible(false);
		this.ventana.getComboLocalidades().setVisible(true);
		this.ventana.getBtnNuevaLocalidad().setVisible(true);

		this.ventana.getBtnAlta().setText("Modificar");
		this.ventana.getBtnAlta().setVisible(true);
		this.ventana.getBtnCancelar().setText("Cancelar");
		this.ventana.getBtnCrearOt().setVisible(false);
	}

	private void cargarCliente() {
		this.ventana.getTfNombre().setText(this.cliente.getNombre());
		this.ventana.getTfApellido().setText(this.cliente.getApellido());
		this.ventana.getTfDireccion().setText(this.cliente.getDireccion());
		// this.ventana.getTfLocalidad().setText(this.cliente.getLocalidad());
		this.ventana.getTfProvincia().setText(this.cliente.getProvincia());
		// this.ventana.getTfCodPostal().setText(Integer.toString(this.cliente.getCodPostal()));
		this.ventana.getTfTelefono().setText(this.cliente.getTelefono());
		this.ventana.getTfMail().setText(this.cliente.geteMail());

		this.ventana.setComboLocalidades(this.cliente.getLocalidad());
	}

	public VentanaAltaModifCliente getVentana() {
		return ventana;
	}

	public void setVentana(VentanaAltaModifCliente ventana) {
		this.ventana = ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.ventana.getBtnAlta()) {
			
			if (!ventana.getTfMail().getText().isEmpty() && !Validador.mailValido(ventana.getTfMail().getText())) {
				ventana.getLblAdvertenciaMail().setVisible(true);
			
			} else {
				ventana.getLblAdvertenciaMail().setVisible(false);
			}
			
			if (camposCorrectos() && !ventana.getLblAdvertenciaMail().isVisible()) {
				if (this.cliente == null) { // Es alta
					altaCliente();
				} else { // Es modificación
					modificacionCliente(this.cliente.getIdCliente());
				}
				this.ventana.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Por favor verifique todos los campos", "Faltan datos",
						JOptionPane.WARNING_MESSAGE);
			}

		} else if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();

		} else if (e.getSource() == this.ventana.getBtnCrearOt()) {
			this.ventana.dispose();
			VentanaAdminOT ventanaOT = new VentanaAdminOT(ventana);
			ControladorAdminOT controladorOT = new ControladorAdminOT(ventanaOT, modelo, cliente);

		} else if (e.getSource() == this.ventana.getBtnNuevaLocalidad()) {

			NuevaLocalidad ventanaLocalidad = new NuevaLocalidad(this.ventana);
			ControladorLocalidad controladorLocalidad = new ControladorLocalidad(ventanaLocalidad, this.modelo);
			
			if (controladorLocalidad.getElegida() != null && !controladorLocalidad.getElegida().getNombre().equals("(seleccionar)")) {
				this.ventana.getComboLocalidades().addItem(controladorLocalidad.getElegida());
				this.ventana.getComboLocalidades().setSelectedItem(controladorLocalidad.getElegida());
			}
		}
	}

	private void modificacionCliente(int idCliente) {
		try {
			crearActualizarCliente(idCliente);
			JOptionPane.showMessageDialog(null,
					"El cliente " + ventana.getTfNombre().getText() + " " + ventana.getTfApellido().getText() + " "
							+ "se ha modificado satisfactoriamente.",
					"Cliente actualizado", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hubo un error al modificar el cliente", "Cliente no actualizado",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void altaCliente() {
		try {
			crearActualizarCliente(0);
			JOptionPane.showMessageDialog(null,
					"El cliente " + ventana.getTfNombre().getText() + " " + ventana.getTfApellido().getText() + " "
							+ "se ha creado satisfactoriamente.",
					"Nuevo cliente creado", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Hubo un error al crear el cliente " + e.getMessage(),
					"El cliente no se ha creado", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void crearActualizarCliente(int id) throws Exception {
		try {
			ClienteDTO nuevoCliente = new ClienteDTO(id, this.ventana.getTfNombre().getText(),
					this.ventana.getTfApellido().getText(),
					this.ventana.getTfDireccion().getText().isEmpty() ? "" : this.ventana.getTfDireccion().getText(),
					// this.ventana.getTfLocalidad().getText().isEmpty()?"":this.ventana.getTfLocalidad().getText(),
					this.ventana.getTfProvincia().getText().isEmpty() ? "" : this.ventana.getTfProvincia().getText(),
					// Integer.parseInt(this.ventana.getTfCodPostal().getText().isEmpty()?"0":this.ventana.getTfCodPostal().getText()),
					(LocalidadDTO) this.ventana.getComboLocalidades().getSelectedItem(),
					this.ventana.getTfTelefono().getText(), this.ventana.getTfMail().getText());
			if (id == 0)
				this.modelo.agregarCliente(nuevoCliente);
			else
				this.modelo.actualizarCliente(nuevoCliente);
			this.cliente = nuevoCliente; // para que lo devuelva si se lo pide y
											// no ir a la db.
		} catch (Exception e) {
			throw e;
		}
	}

	private boolean camposCorrectos() {
		if (camposLlenos()) {
			ventana.getTfNombre().setText(Validador.validarCampo(ventana.getTfNombre().getText()));
			ventana.getTfApellido().setText(Validador.validarCampo(ventana.getTfApellido().getText()));
			if (!ventana.getTfDireccion().getText().isEmpty())
				ventana.getTfDireccion().setText(Validador.validarCampo(ventana.getTfDireccion().getText()));
			// if (!ventana.getTfLocalidad().getText().isEmpty())
			// ventana.getTfLocalidad().setText(Validador.validarCampo(ventana.getTfLocalidad().getText()));
			if (!ventana.getTfProvincia().getText().isEmpty())
				ventana.getTfProvincia().setText(Validador.validarCampo(ventana.getTfProvincia().getText()));
		}
		return camposLlenos() ? true : false;
	}

	private boolean camposLlenos() {
		return !this.ventana.getTfNombre().getText().isEmpty() && !this.ventana.getTfApellido().getText().isEmpty()
				&& !this.ventana.getTfTelefono().getText().isEmpty() 
				&& !this.ventana.getComboLocalidades().getSelectedItem().toString().equals("(seleccionar)");
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
}