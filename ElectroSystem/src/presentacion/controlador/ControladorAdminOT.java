package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import dto.ClienteDTO;
import dto.ElectrodomesticoDTO;
import dto.EstadoDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.UsuarioDTO;
import modelo.Modelo;
import presentacion.ventanas.cliente.VentanaAltaModifCliente;
import presentacion.ventanas.ed.VentanaAltaModifElectrod;
import presentacion.ventanas.mail.VentanaMail;
import presentacion.ventanas.ot.VentanaAdminOT;
import presentacion.ventanas.ot.VentanaBuscadorCliente;

public class ControladorAdminOT implements ActionListener, ItemListener {

	private Modelo modelo;
	private VentanaAdminOT ventana;
	private VentanaAltaModifElectrod ventanaElectrod;
	private VentanaBuscadorCliente ventanaBuscador;
	private List<ClienteDTO> clientes;
	private List<ElectrodomesticoDTO> electrodomesticos;
	private List<MarcaDTO> marcas;
	private ClienteDTO clienteAsociado;
	private VentanaAltaModifCliente ventanaCliente;
	private VentanaMail ventanaMail;
	private OrdenDTO orden;
	private UsuarioDTO usuario;
	
	public ControladorAdminOT(VentanaAdminOT ventana, Modelo modelo, UsuarioDTO usuarioLogueado) {
		this.ventana = ventana;
		this.modelo = modelo;
		this.usuario = usuarioLogueado;
		this.marcas = modelo.obtenerMarcas();
		try {
			this.electrodomesticos = modelo.obtenerElectrodomesticos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		iniciarAlta();
	}
	
	public ControladorAdminOT(VentanaAdminOT ventana, Modelo modelo, ClienteDTO cliente) {
		this.setVentana(ventana);
		this.setModelo(modelo);
		this.clienteAsociado = cliente;
		try {
			this.electrodomesticos = modelo.obtenerElectrodomesticos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		iniciarAltaConCliente();
	}

	public ControladorAdminOT(VentanaAdminOT ventana, Modelo modelo, OrdenDTO orden, UsuarioDTO usuarioLogueado) {
		this.ventana = ventana;
		this.modelo = modelo;
		this.usuario = usuarioLogueado;
		this.clienteAsociado = orden.getCliente();
		this.orden = orden;
		iniciarVer();
	}

	private void iniciarAlta() {
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregar().addActionListener(this);
		this.ventana.getBtnBuscar().addActionListener(this);
		this.ventana.getBtnAgregarElectrodomestico().addActionListener(this);
		this.ventana.getButtonEnvio().addActionListener(this);
		this.ventana.getButtonRetiro().addActionListener(this);
		this.ventana.getBtnCambiarDomicilio().addActionListener(this);
		this.ventana.getComboMarca().addItemListener(this);
		this.ventana.getComboNombre().addItemListener(this);
		this.ventana.getComboModelo().addItemListener(this);
		this.ventana.getComboNombre().setEnabled(false);
		this.ventana.getComboModelo().setEnabled(false);
		this.ventana.getTxtUsuario().setText(usuario.toString());
		this.ventana.getButtonRetiro().setSelected(true);
		this.ventana.getButtonEnvio().setEnabled(false);
		cargarComboBoxMarcas();
		this.ventana.setVisible(true);
	}
	
	private void iniciarAltaConCliente() {
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarElectrodomestico().addActionListener(this);
		this.ventana.getButtonEnvio().addActionListener(this);
		this.ventana.getButtonRetiro().addActionListener(this);
		this.ventana.getComboMarca().addItemListener(this);
		this.ventana.getComboNombre().addItemListener(this);
		this.ventana.getComboModelo().addItemListener(this);
		this.ventana.getComboNombre().setEnabled(false);
		this.ventana.getComboModelo().setEnabled(false);
		this.ventana.getTxtUsuario().setText(usuario.toString());
		this.ventana.getButtonRetiro().setSelected(true);
		cargarComboBoxMarcas();
		this.ventana.getBtnBuscar().setEnabled(false);
		this.ventana.getBtnAgregar().setEnabled(false);
		this.ventana.setTxtCliente(this.clienteAsociado.getApellido() + ", " + this.clienteAsociado.getNombre());
		habilitarEnvio();	
		this.ventana.setVisible(true);
	}
	
	private void iniciarVer() {
		this.ventana.setTitle("Orden número " + orden.getIdOT());
		this.ventana.getBtnCrear().setText("Aceptar");
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarElectrodomestico().setVisible(false);
		this.ventana.getButtonEnvio().addActionListener(this);
		this.ventana.getButtonRetiro().addActionListener(this);
		this.ventana.getTxtUsuario().setText(usuario.toString());
		this.ventana.getBtnEnviarPorMail().setVisible(true);
		this.ventana.esconderBotones();
		cargarCampos(orden);
		cargarEstados();
		cargarOpcionesDeEnvio();
		this.ventana.setVisible(true);		
	}

	public void cargarCampos(OrdenDTO orden) {
		this.ventana.setTxtElectrodomestico(orden.getElectrodomestico().getDescripcion() + " - "
				+ orden.getElectrodomestico().getMarca() + " - " + orden.getElectrodomestico().getModelo());
		this.ventana.setTxtDetalle(orden.getDescripcion());
		this.ventana.setTxtUsuario(orden.getUsuarioAlta().getApellido() + ", " + orden.getUsuarioAlta().getNombre());
		this.ventana.setTxtCliente(orden.getCliente().toString());
		this.ventana.setTxtDetalle(orden.getDescripcion());

		if (orden.isEsDelivery()) {
			this.ventana.getButtonEnvio().setSelected(true);
			this.ventana.getLblDomicilio().setEnabled(true);
			this.ventana.getTxtDomicilio().setEnabled(true);
			this.ventana.getTxtDomicilio().setText(orden.getCliente().getDireccion());
		} else {
			this.ventana.getButtonRetiro().setSelected(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventana.getBtnAgregarElectrodomestico()) {
			this.ventanaElectrod = new VentanaAltaModifElectrod(this.ventana);
			ControladorElectrodomestico controlElectrod = new ControladorElectrodomestico(this.ventanaElectrod,
					this.modelo);
			if (controlElectrod.getElectrodomestico() != null) {
				try {
					actualizarDatosMarcaElectrodomestico();
					seleccionarElectrodomesticoAgregado(controlElectrod.getElectrodomestico());
				} catch (Exception e1) {
					// TODO: Imprimir error en pantalla
					e1.printStackTrace();
				}
			}
			
		} else if (e.getSource() == this.ventana.getBtnBuscar()) {
			try {
				this.clientes = modelo.obtenerClientes();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.ventanaBuscador = new VentanaBuscadorCliente(this.ventana);
			ControladorBuscadorCliente controladorBuscador = new ControladorBuscadorCliente(this.ventanaBuscador, this.modelo, this.clientes);
			System.out.println(clienteAsociado);
			if (controladorBuscador.getClienteSeleccionado()!=null) {
				this.clienteAsociado = controladorBuscador.getClienteSeleccionado();
				this.ventana.setTxtCliente(this.clienteAsociado.getApellido() + ", " + this.clienteAsociado.getNombre());
				habilitarEnvio();
			}
			
		} else if (e.getSource() == this.ventana.getButtonEnvio()) {
			this.ventana.mostrarOpcionesEnvio();
			this.ventana.setTxtDomicilio(this.clienteAsociado.getDireccion() + " - "
					+ this.clienteAsociado.getLocalidad() + ", " + this.clienteAsociado.getProvincia());

		} else if (e.getSource() == this.ventana.getButtonRetiro()) {
			this.ventana.esconderOpcionesEnvio();

		} else if (e.getSource() == this.ventana.getBtnCrear()) {
			if (this.orden == null)
				try {
					if (validarCampos()) {
						crearOT();
						this.ventana.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, "Ocurrió un error " + exc.getMessage(), "Error",
							JOptionPane.WARNING_MESSAGE);
					this.ventana.dispose();
				}
			else {
				if (orden.getEstado().getId()!=1) {
					if (orden.isEsDelivery() != ventana.isDelivery())
						modelo.actualizarEnvioOT(orden);
				}
				cambiarEstado();
				this.ventana.dispose();
			}

		} else if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();

		} else if (e.getSource() == this.ventana.getBtnAgregar()) {
			ventanaCliente = new VentanaAltaModifCliente(ventana);
			try {
				ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente, modelo);
				if (controladorCliente.getCliente()!= null) {
					this.clienteAsociado = controladorCliente.getCliente();
					this.ventana.setTxtCliente(this.clienteAsociado.getApellido() + ", " + this.clienteAsociado.getNombre());
					habilitarEnvio();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		} else if (e.getSource() == this.ventana.getBtnEnviarPorMail()) {
			ventanaMail = new VentanaMail(this.ventana);
			new ControladorMail(ventanaMail, modelo, orden);

		} else if (e.getSource() == this.ventana.getBtnCambiarDomicilio()) {
			
			if (this.clienteAsociado != null) {
				VentanaAltaModifCliente ventanaCliente = new VentanaAltaModifCliente(this.ventana);
				ControladorCliente controladorClente = 
						new ControladorCliente(ventanaCliente, this.modelo, this.clienteAsociado, true, true);
				this.ventana.setTxtDomicilio(this.clienteAsociado.getDireccion() + " - " + this.clienteAsociado.getLocalidad().getNombre() + ", " + this.clienteAsociado.getProvincia());
			} else {
				JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para cambiar el domicilio.");
			}
			
			
		}
	}

	public OrdenDTO getOrden() {
		return orden;
	}

	private void cargarEstados() {
		this.ventana.getCbEstado().setVisible(true);
		this.ventana.getLblEstado().setVisible(true);
		this.ventana.getCbEstado().addItem(this.orden.getEstado());
		if (this.orden.getEstado().getNombre().equals("Presupuestada")) {
			if (orden.getVencPresup().compareTo(Calendar.getInstance())!=1) {
				//TODO: Se paso de la fecha de presupuesto, se debe represupuestar.
				//TODO: Avisar al cliente, si se envia el mail pasar a ingresada y cerrar la ventana.
			} else {
				this.ventana.getCbEstado().addItem(new EstadoDTO(3, "Aprobada"));
				this.ventana.getCbEstado().addItem(new EstadoDTO(4, "Desaprobada"));
			}
			
		} else if (this.orden.getEstado().getNombre().equals("En espera de piezas")) {
			this.ventana.getCbEstado().addItem(new EstadoDTO(8, "Irreparable"));

		} else if (this.orden.getEstado().getNombre().equals("Reparada")) {
			this.ventana.getCbEstado().addItem(new EstadoDTO(9, "Despachada"));
			this.ventana.getCbEstado().addItem(new EstadoDTO(10, "Entregada"));
			
		} else if (this.orden.getEstado().getNombre().equals("Despachada")) {
			this.ventana.getCbEstado().addItem(new EstadoDTO(10, "Entregada"));
			this.ventana.getCbEstado().addItem(new EstadoDTO(7, "Reparada"));
		}
	}

	private void cambiarEstado() {
		if (!this.orden.getEstado().equals((EstadoDTO) ventana.getCbEstado().getSelectedItem())) {
			modelo.actualizarEstado(orden, ((EstadoDTO) ventana.getCbEstado().getSelectedItem()).getId());
		}
	}

	private boolean validarCampos() {
		try {
			if (this.ventana.getTxtCliente().getText().isEmpty() || this.ventana.getTxtDetalle().isEmpty()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void cargarOpcionesDeEnvio() {
		int estado = orden.getEstado().getId();
		if (estado == 4 || estado == 7 || estado == 8 || estado == 9 || estado == 10) {
			ventana.getButtonEnvio().setVisible(false);
			ventana.getButtonRetiro().setVisible(false);
			ventana.esconderOpcionesEnvio();
		}
	}

	private void habilitarEnvio() {
		this.ventana.getRbEnvio().setEnabled(true);
		if (this.clienteAsociado.getDireccion()!=null)
			this.ventana.getRbRetiro().setEnabled(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.ventana.getComboMarca() && this.ventana.getComboMarca().getSelectedItem() != null) {
			this.ventana.getComboNombre().setEnabled(true);
			this.ventana.getComboModelo().setEnabled(false);
			cargarComboBoxNombre();

		} else if (e.getSource() == this.ventana.getComboNombre()) {
			this.ventana.getComboModelo().setEnabled(true);
			cargarComboBoxModelo();
		}
	}

	private void actualizarDatosMarcaElectrodomestico() throws Exception {
		try {
			this.marcas = this.modelo.obtenerMarcas();
			this.electrodomesticos = this.modelo.obtenerElectrodomesticos();
		} catch (Exception e) {
			throw e;
		}
	}

	private void cargarComboBoxMarcas() {
		this.ventana.getComboMarca().removeAllItems();
		this.marcas = modelo.obtenerMarcas();
		for (MarcaDTO m : marcas) {
			this.ventana.getComboMarca().addItem(m);
		}
	}

	private void cargarComboBoxNombre() {
		this.ventana.getComboNombre().removeAllItems();
		if (electrodomesticos != null) {
			for (ElectrodomesticoDTO e : electrodomesticos) {
				if (e.getMarca().equals(((MarcaDTO) this.ventana.getComboMarca().getSelectedItem()))) {
					this.ventana.getComboNombre().addItem(e.getDescripcion());
				}
			}
		}
	}

	private void cargarComboBoxModelo() {
		this.ventana.getComboModelo().removeAllItems();
		if (electrodomesticos != null && this.ventana.getComboNombre().getSelectedItem() != null) {
			for (ElectrodomesticoDTO e : electrodomesticos) {
				if (e.getMarca().equals(((MarcaDTO) this.ventana.getComboMarca().getSelectedItem()))
						&& e.getDescripcion().equals(this.ventana.getComboNombre().getSelectedItem()))
					this.ventana.getComboModelo().addItem(e);
			}
		}
	}

	private void seleccionarElectrodomesticoAgregado(ElectrodomesticoDTO ed) {
		cargarComboBoxMarcas();
		this.ventana.getComboMarca().getModel().setSelectedItem(ed.getMarca());
		this.ventana.getComboNombre().getModel().setSelectedItem(ed.getDescripcion());
		this.ventana.getComboModelo().getModel().setSelectedItem(ed);
	}

	private void crearOT() {
		OrdenDTO nuevaOrden = new OrdenDTO(0,
				clienteAsociado,
				((ElectrodomesticoDTO) this.ventana.getComboModelo().getSelectedItem()),
				this.ventana.getTxtDetalle(),
				this.usuario,
				this.ventana.isDelivery());
		try {
			this.modelo.agregarOrden(nuevaOrden);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ClienteDTO> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteDTO> clientes) {
		this.clientes = clientes;
	}

	public List<ElectrodomesticoDTO> getElectrodomesticos() {
		return electrodomesticos;
	}

	public void setElectrodomesticos(List<ElectrodomesticoDTO> electrodomesticos) {
		this.electrodomesticos = electrodomesticos;
	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	private void setVentana(VentanaAdminOT ventana) {
		this.ventana = ventana;
	}

	public VentanaAdminOT getVentana() {
		return ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public ClienteDTO getClienteAsociado() {
		return clienteAsociado;
	}

	public void setClienteAsociado(ClienteDTO clienteAsociado) {
		this.clienteAsociado = clienteAsociado;
	}
}
