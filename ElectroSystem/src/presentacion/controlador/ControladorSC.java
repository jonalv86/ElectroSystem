package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dto.ItemDTO;
import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import dto.SolicitudCompraDTO;
import dto.UsuarioDTO;
import modelo.Modelo;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.ventanas.mail.VentanaMail;
import presentacion.ventanas.sc.VentanaNuevaSC;

public class ControladorSC implements ActionListener, ItemListener {

	private VentanaNuevaSC ventana;
	private Modelo modelo;
	private List<ProveedorDTO> proveedores;
	private List<MarcaDTO> marcas;
	private List<ItemDTO> piezasSolicitud;
	private DefaultTableModel tabla_de_items;
	private SolicitudCompraDTO solicitud_a_editar;
	private float preciototal = 0;
	private int cantidadPiezas = 0;

	private VentanaMail ventanaMail;

	private UsuarioDTO usuario;

	public ControladorSC(VentanaNuevaSC ventana, Modelo modelo, UsuarioDTO usuario) {
		this.setVentana(ventana);
		this.setModelo(modelo);

		this.usuario = usuario;

		try {
			cargarCboProveedores();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.piezasSolicitud = new ArrayList<ItemDTO>();
		iniciarSolicitud();
	}

	public ControladorSC(VentanaNuevaSC ventana, Modelo modelo, SolicitudCompraDTO sc, boolean editar,
			UsuarioDTO usuario) {

		this.setVentana(ventana);
		this.setModelo(modelo);

		this.usuario = usuario;

		this.marcas = sc.getProveedor().getMarcas();
		this.solicitud_a_editar = sc;
		this.piezasSolicitud = solicitud_a_editar.getPiezas();
		String nombreEstado = solicitud_a_editar.getEstado().getNombre();
		this.ventana.getCboProveedores().addItem(this.solicitud_a_editar.getProveedor());

		this.ventana.getBtnCancelar().addActionListener(this);

		if (!editar) {
			verSolicitud();
		}

		else {
			if (nombreEstado.equals("Ingresada")) {
				iniciarEdicionSolicitud();
			} else if (nombreEstado.equals("Enviada")) {
				iniciarProcesarSolicitud();
			} /*
				 * else iniciarInmodificableSolicitud();
				 */
		}

	}

	/*
	 * private void iniciarInmodificableSolicitud() {
	 * 
	 * this.ventana.setTitle("Procesar solicitud nro.: " +
	 * this.solicitud_a_editar.getId()); JButton btnSolicitar =
	 * this.ventana.getBtnSolicitar(); btnSolicitar.setVisible(false);
	 * this.ventana.getCbMarca().setEnabled(false);
	 * this.ventana.getCbPiezas().setEnabled(false);
	 * this.ventana.getCboProveedores().setEnabled(false);
	 * this.ventana.getBtnEnviar().setVisible(false);
	 * this.ventana.getBtnAgregarPiezas().setEnabled(false);
	 * this.ventana.getTfCantidad().setEnabled(false);
	 * preparedView(btnSolicitar);
	 * 
	 * }
	 */

	private void verSolicitud() {

		this.ventana.setTitle("Solicitud de Compra Nº " + this.solicitud_a_editar.getId());
		this.ventana.getTxtUsuario().setText(usuario.toString());

		this.ventana.getBtnAgregarPiezas().setVisible(false);
		this.ventana.getBtnEnviar().setVisible(false);
		this.ventana.getBtnCancelar().setText("OK");
		this.ventana.getBtnEnviar().setVisible(false);
		this.ventana.getBtnSolicitar().setVisible(false);

		this.ventana.getCboProveedores().setVisible(false);
		this.ventana.setTxtProveedor(solicitud_a_editar.getProveedor().getNombre());
		this.ventana.getTxtProveedor().setVisible(true);

		this.ventana.getLblPieza().setVisible(false);
		this.ventana.getCbMarca().setVisible(false);
		this.ventana.getCbPiezas().setVisible(false);
		this.ventana.getTfCantidad().setVisible(false);

		cargarItems();
		getCantidadPiezas(this.solicitud_a_editar);
		this.ventana.setVisible(true);
	}

	private void iniciarProcesarSolicitud() {

		this.ventana.setTitle("Procesar solicitud nro.: " + this.solicitud_a_editar.getId());
		this.ventana.getTxtUsuario().setText(usuario.toString());
		getCantidadPiezas(this.solicitud_a_editar);

		JButton btnSolicitar = this.ventana.getBtnSolicitar();
		btnSolicitar.setText("Procesar");
		// this.ventana.getCbMarca().setEnabled(false);
		// this.ventana.getCboProveedores().setEnabled(false);
		// this.ventana.getCbPiezas().setEnabled(false);
		// this.ventana.getBtnAgregarPiezas().setEnabled(false);
		// this.ventana.getTfCantidad().setEnabled(false);
		this.ventana.getBtnEnviar().setVisible(false);
		this.ventana.getBtnAgregarPiezas().addActionListener(this);
		preparedView(btnSolicitar);

	}

	private void preparedView(JButton btnSolicitar) {
		btnSolicitar.addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarPiezas().addActionListener(this);
		this.ventana.getCbMarca().addItemListener(this);
		this.ventana.getCboProveedores().addItemListener(this);
		this.ventana.getBtnEnviar().addActionListener(this);

		try {
			cargarCombosMarcas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		crearTabla();
		llenarPiezas();
		this.ventana.setVisible(true);
	}

	private void llenarPiezas() {

		for (ItemDTO entry : piezasSolicitud) {

			PiezaDTO piezaItem = entry.getPieza();
			Object marcaItem = piezaItem.getMarca();

			int cantidadFloat = entry.getCantidadPiezas();
			float precioTotal = piezaItem.getPrecio_venta() * cantidadFloat;
			Object[] fila = { marcaItem, piezaItem, cantidadFloat, piezaItem.getPrecio_venta(), precioTotal, "QUITAR" };

			tabla_de_items.addRow(fila);
			preciototal += precioTotal;
		}
		this.ventana.setLblPrecioTotal(preciototal);

	}

	private void iniciarSolicitud() {

		this.ventana.setTitle("Nueva Solicitud de compra");
		this.ventana.getTxtUsuario().setText(usuario.toString());

		this.ventana.getBtnSolicitar().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarPiezas().addActionListener(this);
		this.ventana.getCbMarca().addItemListener(this);
		this.ventana.getCboProveedores().addItemListener(this);
		this.ventana.getBtnEnviar().setVisible(false);

		this.ventana.getBtnEnviar().addActionListener(this);

		try {
			cargarCboProveedores();
			cargarCombosMarcas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		crearTabla();
		this.ventana.setVisible(true);
	}

	private void iniciarEdicionSolicitud() {
		this.ventana.setTitle("Editar solicitud nro.: " + this.solicitud_a_editar.getId());
		this.ventana.getTxtUsuario().setText(usuario.toString());
		getCantidadPiezas(this.solicitud_a_editar);

		JButton btnSolicitar = this.ventana.getBtnSolicitar();
		btnSolicitar.setText("Modificar");
		preparedView(btnSolicitar);
	}

	private void getCantidadPiezas(SolicitudCompraDTO s) {
		if (s.getId() == 0) {
			this.ventana.setLblCantidadTotal(cantidadPiezas);

		} else {
			for (int i = 0; i < s.getPiezas().size(); i++) {
				this.cantidadPiezas += solicitud_a_editar.getPiezas().get(i).getCantidadPiezas();
			}
		}

		this.ventana.setLblCantidadTotal(cantidadPiezas);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getBtnCancelar()) {
			ventana.dispose();

		} else {
			JButton btnSolicitar = this.ventana.getBtnSolicitar();
			if (e.getSource() == btnSolicitar) {
				if (this.ventana.getCboProveedores() != null) {

					// if (btnSolicitar.getText().equals("Modificar") ||
					// btnSolicitar.getText().equals("Solicitar")) {

					if (btnSolicitar.getText().equals("Modificar")) {
						if (this.tabla_de_items.getRowCount() != 0) {
							try {
								crearSC();
								ventana.dispose();
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "No se ha podido modificar la solicitud de compra.",
										"¡Atención!", JOptionPane.ERROR_MESSAGE);
								ventana.dispose();
							}
							JOptionPane.showMessageDialog(null, "Se modificó la solicitud de la orden de compra");
						} else {
							JOptionPane.showMessageDialog(null, "No se han agregado piezas a la solicitud.",
									"¡Atención!", JOptionPane.ERROR_MESSAGE);
						}

					} else if (btnSolicitar.getText().equals("Solicitar")) {

						if (this.tabla_de_items.getRowCount() != 0) {
							try {
								crearSC();
								ventana.dispose();
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "No se ha podido crear la solicitud de compra.",
										"¡Atención!", JOptionPane.ERROR_MESSAGE);
								ventana.dispose();
							}
							JOptionPane.showMessageDialog(null, "Se realizó la solicitud de la orden de compra");
						} else {
							JOptionPane.showMessageDialog(null, "No se han agregado piezas a la solicitud.",
									"¡Atención!", JOptionPane.ERROR_MESSAGE);
						}

					} else if (btnSolicitar.getText().equals("Procesar")) {

						try {
							this.modelo.procesarSolicitud(solicitud_a_editar);
							ventana.dispose();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "No se ha podido procesar la solicitud de compra.",
									"¡Atención!", JOptionPane.ERROR_MESSAGE);
							ventana.dispose();
						}

						JOptionPane.showMessageDialog(null, "Se proceso la solicitud de la orden de compra");
						ventana.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun proveedor.", "¡Atención!",
							JOptionPane.ERROR_MESSAGE);
				}

			} else if (e.getSource() == this.ventana.getBtnAgregarPiezas()) {

				String cantidad = this.ventana.getTfCantidad().getValue().toString();
				int valorCantidad = Integer.parseInt(cantidad);

				if (valorCantidad > 0 && this.ventana.getCbMarca().getSelectedItem() != null
						&& this.ventana.getCbPiezas().getSelectedItem() != null) {
					agregarItem();
				}

			} else if (e.getSource() == this.ventana.getBtnEnviar()) {

				try {

					this.ventanaMail = new VentanaMail(this.ventana);
					ControladorMail controladorMail = new ControladorMail(ventanaMail, this.modelo, solicitud_a_editar);

					// arreglo a lo indio, no me juzgen D: - Dario Rick
					// Thread.sleep(10);

					if (controladorMail.fueEnviado()) {
						modelo.cambiarEstado(solicitud_a_editar, 2);
						JOptionPane.showMessageDialog(null, "Se ha enviado la solicitud de compra satisfactoriamente.",
								"¡Exito!", JOptionPane.INFORMATION_MESSAGE);
						ventana.dispose();
					} else
						JOptionPane.showMessageDialog(null, "No se ha podido enviar la solicitud de compra.",
								"¡Atención!", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JComboBox<MarcaDTO> cbMarca = this.ventana.getCbMarca();
		JComboBox<ProveedorDTO> cbProveedores = this.ventana.getCboProveedores();
		if (e.getSource() == cbMarca && cbMarca.getSelectedItem() != null) {
			ProveedorDTO proveedor = (ProveedorDTO) cbProveedores.getSelectedItem();
			MarcaDTO marca = (MarcaDTO) cbMarca.getSelectedItem();
			try {
				cargarComboPiezas(proveedor.getIdProveedor(), marca.getIdMarca());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == cbProveedores && cbProveedores.getSelectedItem() != null) {
			cargarCombosMarcas((ProveedorDTO) cbProveedores.getSelectedItem());
		}
	}

	private void crearSC() throws Exception {

		SolicitudCompraDTO solicitudCompra = new SolicitudCompraDTO(0,
				(ProveedorDTO) this.ventana.getCboProveedores().getSelectedItem(), null, piezasSolicitud);
		if (solicitud_a_editar != null) {
			solicitud_a_editar.setPiezas(piezasSolicitud);
			this.modelo.actualizarSolicitud(solicitud_a_editar);
		} else
			this.modelo.agregarSolicitud(solicitudCompra);
	}

	private void agregarItem() {

		PrecioPiezaDTO piezaItem = (PrecioPiezaDTO) this.ventana.getCbPiezas().getSelectedItem();
		int cantidadXU = 0;
		cantidadXU = this.ventana.getCantidad();
		ItemDTO item = new ItemDTO(piezaItem.getPieza(), cantidadXU, null, null);

		if (!piezasSolicitud.contains(item) || cantidadPiezas == 0)
			agregar(piezaItem, item);
		else
			modificar(piezaItem, item);
	}

	public void agregar(PrecioPiezaDTO piezaItem, ItemDTO item) {

		Object marcaItem = this.ventana.getCbMarca().getSelectedItem();
		float precioTotal = piezaItem.getPrecio() * item.getCantidadPiezas();
		Object[] fila = { marcaItem, piezaItem.getPieza(), item.getCantidadPiezas(), piezaItem.getPrecio(), precioTotal,
				"QUITAR" };
		tabla_de_items.addRow(fila);

		preciototal += precioTotal;
		this.ventana.setLblPrecioTotal(preciototal);
		this.ventana.getTfCantidad().setValue(1);

		piezasSolicitud.add(item);

		int cantidadPiezasNuevas = 0;
		cantidadPiezasNuevas = item.getCantidadPiezas();
		cantidadPiezas += cantidadPiezasNuevas;
		this.ventana.setLblCantidadTotal(cantidadPiezas);
	}

	public void modificar(PrecioPiezaDTO piezaItem, ItemDTO item) {

		int fila = 0;
		for (int i = 0; i < this.ventana.getTable().getRowCount(); i++) {
			if (this.ventana.getTable().getValueAt(i, 1).toString()
					.equals(String.valueOf(item.getPieza().getIdUnico()))) {
				fila = i;
			}
		}

		preciototal -= Float.parseFloat(ventana.getTable().getValueAt(fila, 4).toString());
		cantidadPiezas -= Integer.parseInt(ventana.getTable().getValueAt(fila, 2).toString());

		tabla_de_items.removeRow(fila);
		piezasSolicitud.remove(item);

		float precioTotal = piezaItem.getPrecio() * item.getCantidadPiezas();
		Object[] data = { this.ventana.getCbMarca().getSelectedItem(), piezaItem.getPieza(), item.getCantidadPiezas(),
				piezaItem.getPrecio(), precioTotal, "QUITAR" };
		tabla_de_items.addRow(data);
		piezasSolicitud.add(item);

		preciototal += precioTotal;
		this.ventana.setLblPrecioTotal(preciototal);
		cantidadPiezas += item.getCantidadPiezas();
		this.ventana.setLblCantidadTotal(cantidadPiezas);

		// clean spinner
		this.ventana.getTfCantidad().setValue(1);
	}

	private void cargarCboProveedores() throws Exception {
		try {
			JComboBox<ProveedorDTO> cboProveedores = this.ventana.getCboProveedores();
			cboProveedores.removeAllItems();
			this.proveedores = modelo.obtenerProveedores();
			for (ProveedorDTO p : proveedores) {
				cboProveedores.addItem(p);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void cargarCombosMarcas() {
		cargarCombosMarcas(null);
	}

	private void cargarCombosMarcas(ProveedorDTO proveedor) {
		try {
			JComboBox<MarcaDTO> cbMarca = this.ventana.getCbMarca();
			cbMarca.removeAllItems();
			if (this.marcas == null || proveedor != null)
				this.marcas = modelo.obtenerMarcasProveedor(proveedor.getIdProveedor());
			for (MarcaDTO marca : marcas) {
				cbMarca.addItem(marca);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarComboPiezas(int idProveedor, int idMarca) throws Exception {
		JComboBox<PrecioPiezaDTO> cbPiezas = this.ventana.getCbPiezas();
		cbPiezas.removeAllItems();
		List<PrecioPiezaDTO> pieza = modelo.obtenerPrecioCompraItems(idProveedor, idMarca);
		for (PrecioPiezaDTO precioPieza : pieza) {
			cbPiezas.addItem(precioPieza);
		}
	}

	@SuppressWarnings("serial")
	private void crearTabla() {
		String[] columns = { "Marca", "Producto", "Cantidad", "Precio x U", "Total Items", "" };

		tabla_de_items = new DefaultTableModel(null, columns) {

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 5) {
					/*
					 * String nombre =
					 * solicitud_a_editar.getEstado().getNombre(); if
					 * (solicitud_a_editar != null &&
					 * (nombre.equals("Procesada") ||
					 * nombre.equals("Cancelada")))
					 */
					return true;
				}
				return false;
			}
		};

		tabla_de_items.setColumnIdentifiers(columns);
		this.ventana.getTable().setModel(tabla_de_items);

		new ButtonColumn(this.ventana.getTable(), quitarItem(), 5);

	}

	private void cargarItems() {

		String[] columns = { "Marca", "Producto", "Cantidad", "Precio X U", "Total item" };

		tabla_de_items = new DefaultTableModel(null, columns);

		tabla_de_items.setColumnIdentifiers(columns);
		this.ventana.getTable().setModel(tabla_de_items);

		for (ItemDTO entry : piezasSolicitud) {

			PiezaDTO piezaItem = entry.getPieza();
			Object marcaItem = piezaItem.getMarca();

			int cantidadFloat = entry.getCantidadPiezas();
			float precioTotal = piezaItem.getPrecio_venta() * cantidadFloat;
			Object[] fila = { marcaItem, piezaItem, cantidadFloat, piezaItem.getPrecio_venta(), precioTotal };

			tabla_de_items.addRow(fila);
			preciototal += precioTotal;
		}
		this.ventana.setLblPrecioTotal(preciototal);

	}

	@SuppressWarnings("serial")
	private Action quitarItem() {

		Action quitar = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int rowAEliminar = ventana.getTable().getSelectedRow();

				try {

					int respuesta = JOptionPane.showConfirmDialog(ventana, "¿Está seguro que desea eliminar la pieza?",
							null, JOptionPane.YES_NO_OPTION);

					if (respuesta == JOptionPane.YES_OPTION) {
						PiezaDTO piezaTable = (PiezaDTO) tabla_de_items.getValueAt(ventana.getTable().getSelectedRow(),
								1);

						for (ItemDTO itemDTO : piezasSolicitud) {
							if (itemDTO.getPieza().getIdProdPieza() == piezaTable.getIdProdPieza()) {
								piezasSolicitud.remove(itemDTO);
								break;
							}
						}

						String valorString = tabla_de_items.getValueAt(rowAEliminar, 4).toString();
						Float precioARestar = Float.valueOf(valorString);
						preciototal -= precioARestar;
						ventana.setLblPrecioTotal(preciototal);

						int cantidadRestar = 0;
						cantidadRestar = (int) tabla_de_items.getValueAt(ventana.getTable().getSelectedRow(), 2);
						cantidadPiezas -= cantidadRestar;
						ventana.setLblCantidadTotal(cantidadPiezas);

						tabla_de_items.removeRow(ventana.getTable().getSelectedRow());
						ventana.getTable().setModel(tabla_de_items);

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return quitar;
	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	private void setVentana(VentanaNuevaSC ventana) {
		this.ventana = ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}
}
