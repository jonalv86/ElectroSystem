package presentacion.controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import dto.ClienteDTO;
import dto.ElectrodomesticoDTO;
import dto.FleteroDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.PiezaDTO;
import dto.ProveedorDTO;
import dto.SolicitudCompraDTO;
import dto.UsuarioDTO;
import helpers.Validador;
import modelo.ImporterCSV;
import modelo.Modelo;
import modelo.Reporte;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.controlador.fleteros.ControladorAsignacion;
import presentacion.controlador.fleteros.ControladorVentanaNuevoFletero;
import presentacion.controlador.presupuesto.ControladorTecnicoOT;
import presentacion.ventanas.Proveedores.VentanaAltaModifProveedor;
import presentacion.ventanas.cliente.VentanaAltaModifCliente;
import presentacion.ventanas.cliente.VentanaZonas;
import presentacion.ventanas.ed.VentanaAltaModifElectrod;
import presentacion.ventanas.fleteros.VentanaAsignacion;
import presentacion.ventanas.fleteros.VentanaNuevoFletero;
import presentacion.ventanas.logueo.VentanaLogueo;
import presentacion.ventanas.mail.VentanaMail;
import presentacion.ventanas.ot.VentanaAdminOT;
import presentacion.ventanas.ot.VentanaTecnicoOT;
import presentacion.ventanas.piezas.VentanaAltaModifPieza;
import presentacion.ventanas.sc.VentanaNuevaSC;
import presentacion.ventanas.usuario.VentanaAltaModifUsuario;
import presentacion.vista.VistaPrincipal;

public class ControladorPrincipal implements ActionListener, ItemListener, ChangeListener {

	private VistaPrincipal vistaPrincipal;
	private Modelo modelo;
	private List<ClienteDTO> clientes_en_tabla;
	private List<ElectrodomesticoDTO> electrodomestico_en_tabla;
	private List<OrdenDTO> ordenes_en_tabla_UA;
	private List<OrdenDTO> ordenes_en_tabla_UT;
	private List<PiezaDTO> piezas_en_tabla;
	private List<SolicitudCompraDTO> solicitudes_en_tabla;
	private List<MarcaDTO> marcas;
	private List<UsuarioDTO> usuarios_comunes_en_tabla;
	private List<ProveedorDTO> proveedores_en_tabla;
	private List<FleteroDTO> fleteros_en_tabla;
	private List<OrdenDTO> ordenes_a_enviar;

	private final UsuarioDTO usuarioLogueado;

	private VentanaAltaModifCliente ventanaCliente;
	private VentanaAltaModifPieza ventanaPieza;
	private VentanaAdminOT ventanaOT;
	private VentanaAltaModifElectrod ventanaElectrodomestico;
	private VentanaTecnicoOT ventanaPresupuesto;
	private OrdenDTO ordenApresupuestar;
	private OrdenDTO ordenAcargar;
	private VentanaAltaModifUsuario ventanaUsuario;
	private VentanaAltaModifProveedor ventanaProveedor;
	private VentanaNuevoFletero ventanaFletero;
	private VentanaMail ventanaMail;

	private File selectedFile;

	public ControladorPrincipal(VistaPrincipal vistaPrincipal, Modelo modelo, UsuarioDTO usuarioLogueado) {
		this.vistaPrincipal = vistaPrincipal;
		this.modelo = modelo;
		this.usuarioLogueado = usuarioLogueado;
	}

	void iniciar() {
		this.vistaPrincipal.setTitle("Electro R (S.R.L.)");
		this.vistaPrincipal.setLocationRelativeTo(null);// centrado en pantalla
		this.vistaPrincipal.getCbMarcas().addItemListener(this);
		this.vistaPrincipal.getBtnAgregarCliente().addActionListener(this);
		this.vistaPrincipal.getBtnNuevaOt().addActionListener(this);
		this.vistaPrincipal.getBtnNuevaSc().addActionListener(this);
		this.vistaPrincipal.getBtnAadirPiezas().addActionListener(this);
		this.vistaPrincipal.getBtnAgregarUsuario().addActionListener(this);
		this.vistaPrincipal.getBtnNuevoProveedor().addActionListener(this);
		this.vistaPrincipal.getBtnNuevoFletero().addActionListener(this);
		this.vistaPrincipal.getBtnAgregarEd().addActionListener(this);
		this.vistaPrincipal.getTabbedPane().addChangeListener(this);
		this.vistaPrincipal.getBtnEnviarMailOT().addActionListener(this);
		this.vistaPrincipal.getBtnEnviarMailSC().addActionListener(this);
		this.vistaPrincipal.getBtnAsignar().addActionListener(this);

		this.vistaPrincipal.getBtnCrearBackup().addActionListener(this);
		this.vistaPrincipal.getBtnCargarBackup().addActionListener(this);

		this.vistaPrincipal.getComboEstadosUA().addItemListener(this);
		this.vistaPrincipal.getComboEstadosUT().addItemListener(this);

		this.vistaPrincipal.getMntmDatos().addActionListener(this);
		this.vistaPrincipal.getMntmSalir().addActionListener(this);

		this.vistaPrincipal.getMntmActualizar().addActionListener(this);
		this.vistaPrincipal.getMntmEditarZonas().addActionListener(this);

		this.vistaPrincipal.getBtnProcesar().addActionListener(this);
		this.vistaPrincipal.getFileChooser().addActionListener(this);
		
		this.vistaPrincipal.getBtnRankElectrodomesticos().addActionListener(this);

		try {
			this.marcas = this.modelo.obtenerMarcas();
			this.electrodomestico_en_tabla = this.modelo.obtenerElectrodomesticos();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.vistaPrincipal.getMenuLog().setText("Bienvenido/a " + usuarioLogueado);

		switch (usuarioLogueado.getRol().getDescripcion()) {
		case "Administrativo":
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabUsuarios());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabUsuarios());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getPanelProveedores());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabFleteros());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabOT_UT());
			llenarTablaClientes();
			break;
		case "Tecnico":
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabClientes());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getPanelScs());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabPiezas());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabEds());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabUsuarios());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getPanelProveedores());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabFleteros());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabOT_UA());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabOT_UA());
			this.vistaPrincipal.getTabbedPane().remove(this.vistaPrincipal.getTabImportarPrecios());
			// TODO ¿El tecnico no envia mails?
			this.vistaPrincipal.getBtnEnviarMailOT().setVisible(false);
			llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
			break;
		case "Superusuario":
			llenarTablaClientes();
			break;
		}

		this.vistaPrincipal.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void llenarTablaClientes() {
		try {
			String[] columns = { "Nro. de cliente", "Nombre", "Telefono", "Email", "", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 4 || column == 5 || column == 6)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			clientes_en_tabla = modelo.obtenerClientes();
			for (ClienteDTO c : clientes_en_tabla) {
				Object[] fila = { c.getIdCliente(), c, c.getTelefono(), c.geteMail(), "BORRAR", "EDITAR", "VER" };
				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableClientes().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTableClientes(), borrarCliente(), 4);
			new ButtonColumn(this.vistaPrincipal.getTableClientes(), editarCliente(), 5);
			new ButtonColumn(this.vistaPrincipal.getTableClientes(), verCliente(), 6);
			this.vistaPrincipal.getTableClientes().getColumnModel().getColumn(3).setWidth(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.vistaPrincipal.getTableClientes().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}

	private void llenarTablaOT_UA(String estado) {
		try {

			String[] columns = { "Nro. de orden", "Cliente", "Producto", "Descripcion", "Estado", "" };

			DefaultTableModel dtm = new DefaultTableModel(null, columns) {

				private static final long serialVersionUID = 3054660273548654771L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 5)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);

			ordenes_en_tabla_UA = filtrarSegun(estado);

			for (OrdenDTO orden : ordenes_en_tabla_UA) {

				ClienteDTO cliente = orden.getCliente();
				ElectrodomesticoDTO electrodomestico = orden.getElectrodomestico();

				Object[] fila = { orden, Validador.contactenarStrings(cliente.getNombre(), cliente.getApellido()),
						Validador.contactenarStrings(electrodomestico.getMarca().getNombre(),
								electrodomestico.getModelo(), electrodomestico.getDescripcion()),
						orden.getDescripcion(), orden.getEstado().getNombre(), "VER" };

				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableOT_UA().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTableOT_UA(), verOT_UA(), 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void llenarTablaOT_UT(String estado) {
		try {

			String[] columns = { "Nro. de orden", "Cliente", "Producto", "Descripcion", "A cargo de", "" };

			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 5)
						return true;
					else
						return false;
				}
			};

			dtm.setColumnIdentifiers(columns);

			this.ordenes_en_tabla_UT = new LinkedList<OrdenDTO>();
			for (int i = 0; i < modelo.obtenerOrdenes().size(); i++) {
				OrdenDTO actual = modelo.obtenerOrdenes().get(i);
				if (actual.getEstado().getNombre().equals("Ingresada")
						|| actual.getEstado().getNombre().equals("Aprobada")
						|| actual.getEstado().getNombre().equals("En reparación"))
					if (actual.getEstado().getNombre().equals(estado))
						ordenes_en_tabla_UT.add(actual);
			}

			for (OrdenDTO orden : ordenes_en_tabla_UT) {

				String buttonName = null;
				switch (orden.getEstado().getNombre()) {
				case "Ingresada":
					buttonName = "PRESUPUESTAR";
					break;
				case "Aprobada":
					buttonName = "REPARAR";
					break;
				case "En reparación":
					buttonName = "CONTINUAR";
					break;
				default:
					break;
				}

				ClienteDTO cliente = orden.getCliente();
				ElectrodomesticoDTO electrodomestico = orden.getElectrodomestico();

				Object[] fila = { orden, Validador.contactenarStrings(cliente.getNombre(), cliente.getApellido()),
						Validador.contactenarStrings(electrodomestico.getMarca().getNombre(),
								electrodomestico.getModelo(), electrodomestico.getDescripcion()),
						orden.getDescripcion(), orden.getTecnicoAsoc()!=null?orden.getTecnicoAsoc():"Sin tecnico",buttonName};

				dtm.addRow(fila);
			}

			this.vistaPrincipal.getTableOT_UT().setModel(dtm);

			Action actionPresupuestar = presupuestarOT();
			Action actionReparar = reparar();
			Action action = null;

			for (OrdenDTO orden : ordenes_en_tabla_UT) {

				switch (orden.getEstado().getNombre()) {
				case "Ingresada":
					action = actionPresupuestar;
					break;
				case "Aprobada":
					action = actionReparar;
					break;
				case "En reparación":
					action = actionReparar;
					break;

				default:
					break;
				}
			}

			new ButtonColumn(this.vistaPrincipal.getTableOT_UT(), action, 5);

		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	private void llenarTablaScs() {
		try {
			String[] columns = { "Nro. de solicitud", "Proveedor", "Estado", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 3)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			solicitudes_en_tabla = modelo.obtenerSolicitudes();
			for (SolicitudCompraDTO sc : solicitudes_en_tabla) {
				Object[] fila = { sc.getId(), sc.getProveedor().getNombre(), sc.getEstado().getNombre(), "VER/EDITAR" };
				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableScs().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTableScs(), editarSC(), 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void llenarTablaProveedores() {
		try {
			String[] columns = { "CUIT", "Nombre", "Contacto", "Teléfono", "", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				private static final long serialVersionUID = 6446529126548721007L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 4 || column == 5 || column == 6)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			proveedores_en_tabla = modelo.obtenerProveedores2();
			for (ProveedorDTO p : proveedores_en_tabla) {
				Object[] fila = { p.getCuit(), p, p.getContacto(), p.getTelefono(), "VER", "EDITAR", "BORRAR" };
				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableProveedores().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTableProveedores(), verProveedor(), 4);
			new ButtonColumn(this.vistaPrincipal.getTableProveedores(), editarProveedor(), 5);
			new ButtonColumn(this.vistaPrincipal.getTableProveedores(), borrarProveedor(), 6);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarCbMarcas() {
		marcas = this.modelo.obtenerMarcas();
		for (MarcaDTO m : marcas)
			this.vistaPrincipal.getCbMarcas().addItem(m);
	}

	@SuppressWarnings("serial")
	private void llenarTablaPiezas() {
		try {
			String[] columns = { "Nro. de pieza", "Marca", "Descripcion", "Precio Venta", "Stock", "", "", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 5 || column == 6 || column == 7 || column == 8)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			piezas_en_tabla = modelo.obtenerItems();
			for (PiezaDTO p : piezas_en_tabla) {
				if (p.getMarca().equals((MarcaDTO) this.vistaPrincipal.getCbMarcas().getSelectedItem())) {
					Object[] fila = { p, p.getMarca(), p.getDescripcion(), p.getPrecio_venta(), p.getStock(), "BORRAR",
							"EDITAR", "VER", "MODIFICAR STOCK" };
					dtm.addRow(fila);
				}
			}
			this.vistaPrincipal.getTablePiezas().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTablePiezas(), borrarPieza(), 5);
			new ButtonColumn(this.vistaPrincipal.getTablePiezas(), editarPieza(), 6);
			new ButtonColumn(this.vistaPrincipal.getTablePiezas(), verPieza(), 7);
			new ButtonColumn(this.vistaPrincipal.getTablePiezas(), modificarStock(), 8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void llenarTablaElectrodomesticos() {
		try {
			String[] columns = { "Producto", "Marca", "Modelo", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 3 || column == 4)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			electrodomestico_en_tabla = modelo.obtenerElectrodomesticos();
			for (ElectrodomesticoDTO e : electrodomestico_en_tabla) {
				Object[] fila = { e.getDescripcion(), e.getMarca().getNombre(), e, "BORRAR", "EDITAR" };
				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableEds().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTableEds(), borrarElectrodomestico(), 3);
			new ButtonColumn(this.vistaPrincipal.getTableEds(), editarElectrodomestico(), 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void llenarTablaUsuarios() {
		try {
			String[] columns = { "Nombre de empleado", "Nombre de usuario", "Tipo", "", "" };
			DefaultTableModel dtm = new DefaultTableModel(null, columns) {
				private static final long serialVersionUID = 4428870321225072154L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 3 || column == 4)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);
			usuarios_comunes_en_tabla = modelo.obtenerUsuariosComunes();
			for (UsuarioDTO u : usuarios_comunes_en_tabla) {
				Object[] fila = { u, u.getUsuario(), u.getRol(), "BORRAR", "BLANQUEAR" };
				dtm.addRow(fila);
			}
			this.vistaPrincipal.getTableUsuarios().setModel(dtm);
			new ButtonColumn(this.vistaPrincipal.getTableUsuarios(), borrarUsuario(), 3);
			new ButtonColumn(this.vistaPrincipal.getTableUsuarios(), blanquearUsuario(), 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	private void llenarTablaFleteros() {

		try {
			String[] columns = { "Nombre y Apellido", "Celular", "Registro Nro. ", "Vehículo", "", "", "" , ""};

			DefaultTableModel dtm = new DefaultTableModel(null, columns) {

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 4 || column == 5 || column == 6 || column == 7)
						return true;
					else
						return false;
				}
			};
			dtm.setColumnIdentifiers(columns);

			fleteros_en_tabla = modelo.obtenerFleteros();

			for (FleteroDTO f : fleteros_en_tabla) {
				Object[] fila = { f, f.getCelular(), f.getRegistroConducir(), f.getVehiculo().getPatente(), "VER",
						"EDITAR", "BORRAR" , "HOJA DE RUTA"};
				dtm.addRow(fila);
			}

			this.vistaPrincipal.getTableFleteros().setModel(dtm);

			new ButtonColumn(this.vistaPrincipal.getTableFleteros(), verFletero(), 4);
			new ButtonColumn(this.vistaPrincipal.getTableFleteros(), editarFletero(), 5);
			new ButtonColumn(this.vistaPrincipal.getTableFleteros(), borrarFletero(), 6);
			new ButtonColumn(this.vistaPrincipal.getTableFleteros(), verHojaRuta(), 7);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Action borrarFletero() {
		Action borrarFletero = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				int modelRow = Integer.valueOf(a.getActionCommand());

				try {
					int rta = JOptionPane.showConfirmDialog(vistaPrincipal,
							"¿Está seguro que desea eliminar al empleado?", null, JOptionPane.YES_NO_OPTION);

					if (rta == JOptionPane.YES_OPTION) {
						FleteroDTO fletero = (FleteroDTO) table.getModel().getValueAt(table.getSelectedRow(), 0);
						modelo.borrarFletero(fletero.getIdFletero(), usuarioLogueado.getIdPersonal());
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
					}

					llenarTablaFleteros();

				} catch (Exception exc) {

					exc.printStackTrace();
				}
			}

		};
		return borrarFletero;

	}

	private Action editarFletero() {
		Action editarFletero = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				ventanaFletero = new VentanaNuevoFletero(vistaPrincipal);

				try {
					FleteroDTO fletero = (FleteroDTO) table.getModel().getValueAt(table.getSelectedRow(), 0);
					new ControladorVentanaNuevoFletero(ventanaFletero, modelo, fletero, true, usuarioLogueado);

					llenarTablaFleteros();

				} catch (Exception exc) {
					exc.printStackTrace();
				}

			}
		};

		return editarFletero;

	}

	public Action verFletero() {
		Action verFletero = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent a) {

				JTable table = (JTable) a.getSource();
				ventanaFletero = new VentanaNuevoFletero(vistaPrincipal);

				try {
					FleteroDTO fletero = (FleteroDTO) table.getModel().getValueAt(table.getSelectedRow(), 0);
					new ControladorVentanaNuevoFletero(ventanaFletero, modelo, fletero, false, usuarioLogueado);

					llenarTablaFleteros();

				} catch (Exception exc) {
					exc.printStackTrace();
				}

			}
		};

		return verFletero;
	}

	private Action borrarCliente() {
		Action borrarCliente = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal,
							"¿Está seguro que desea eliminar el cliente?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.borrarCliente((ClienteDTO) table.getModel().getValueAt(table.getSelectedRow(), 1),
								usuarioLogueado.getIdPersonal());
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						clientes_en_tabla = null;
						// Por si se vuelve a cargar la tabla de clientes.
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return borrarCliente;
	}
	
	public Action verHojaRuta() {
		Action verHoja = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent a) {
				
				JOptionPane.showMessageDialog(null, "NOT AVAILABLE YET");
				
//				JTable table = (JTable) a.getSource();
//				ventanaFletero = new VentanaNuevoFletero(vistaPrincipal);
//
//				try {
//					FleteroDTO fletero = (FleteroDTO) table.getModel().getValueAt(table.getSelectedRow(), 0);
//					new ControladorVentanaNuevoFletero(ventanaFletero, modelo, fletero, false, usuarioLogueado);
//
//					llenarTablaFleteros();
//
//				} catch (Exception exc) {
//					exc.printStackTrace();
//				}

			}
		};

		return verHoja;
	}

	public Action editarCliente() {
		Action editarCliente = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaCliente = new VentanaAltaModifCliente(vistaPrincipal);
				try {
					new ControladorCliente(ventanaCliente, modelo,
							(ClienteDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), true, false);
					clientes_en_tabla = null;
					llenarTablaClientes();
					ordenes_en_tabla_UT = null;
					llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return editarCliente;
	}

	public Action verCliente() {
		Action verCliente = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaCliente = new VentanaAltaModifCliente(vistaPrincipal);
				try {
					new ControladorCliente(ventanaCliente, modelo,
							(ClienteDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), false, false);
					marcas = modelo.obtenerMarcas();
					llenarTablaElectrodomesticos();// Pudo haber creado una OT
													// con ED y marcas nuevos.
					llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return verCliente;
	}

	private Action presupuestarOT() {
		Action presupuestar = new AbstractAction() {

			private static final long serialVersionUID = -2772361624419816539L;

			@Override
			public void actionPerformed(ActionEvent e) {

				ventanaPresupuesto = new VentanaTecnicoOT(vistaPrincipal);

				ordenApresupuestar = (OrdenDTO) vistaPrincipal.getTableOT_UT()
						.getValueAt(vistaPrincipal.getTableOT_UT().getSelectedRow(), 0);
				new ControladorTecnicoOT(ventanaPresupuesto, modelo, ordenApresupuestar, usuarioLogueado);
				llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
			}
		};
		return presupuestar;
	}

	private Action reparar() {
		Action reparar = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaPresupuesto = new VentanaTecnicoOT(vistaPrincipal);

				ordenApresupuestar = (OrdenDTO) vistaPrincipal.getTableOT_UT()
						.getValueAt(vistaPrincipal.getTableOT_UT().getSelectedRow(), 0);
				new ControladorTecnicoOT(ventanaPresupuesto, modelo, ordenApresupuestar, usuarioLogueado);

				llenarTablaOT_UT(vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
			}
		};
		return reparar;
	}

	private Action editarSC() {
		Action editarSC = new AbstractAction() {

			private static final long serialVersionUID = -1791065090765145181L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				VentanaNuevaSC ventanaSC = new VentanaNuevaSC(vistaPrincipal);
				SolicitudCompraDTO sc = obtenerScSeleccionada(
						(int) table.getModel().getValueAt(table.getSelectedRow(), 0));
				if (sc != null)
					new ControladorSC(ventanaSC, modelo, sc, true, usuarioLogueado);
				llenarTablaScs();
			}

			private SolicitudCompraDTO obtenerScSeleccionada(int id) {
				for (SolicitudCompraDTO s : solicitudes_en_tabla)
					if (s.getId() == id)
						return s;
				return null;
			}
		};
		return editarSC;
	}

	private Action borrarProveedor() {
		Action borrarProveedor = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal,
							"¿Está seguro que desea eliminar este proveedor?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.borrarProveedor((ProveedorDTO) table.getModel().getValueAt(table.getSelectedRow(), 1),
								usuarioLogueado);
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return borrarProveedor;
	}

	private Action editarProveedor() {
		Action editarProveedor = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaProveedor = new VentanaAltaModifProveedor(vistaPrincipal);
				try {
					new ControladorProveedor(ventanaProveedor, modelo, marcas,
							(ProveedorDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), proveedores_en_tabla,
							true);
					llenarTablaProveedores();
					marcas = modelo.obtenerMarcas();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return editarProveedor;
	}

	private Action verProveedor() {
		Action verProveedor = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaProveedor = new VentanaAltaModifProveedor(vistaPrincipal);
				try {
					new ControladorProveedor(ventanaProveedor, modelo, marcas,
							(ProveedorDTO) table.getModel().getValueAt(table.getSelectedRow(), 1), proveedores_en_tabla,
							false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return verProveedor;
	}

	private Action verPieza() {
		Action verPieza = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					new ControladorPieza(ventanaPieza, modelo,
							(PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 0), false, false);
				} catch (Exception e2) {
					// Mensaje no se puede ver la pieza.
					e2.printStackTrace();
				}
			}
		};
		return verPieza;
	}

	private Action editarPieza() {
		Action editarPieza = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					new ControladorPieza(ventanaPieza, modelo,
							(PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 0), true, false);
					llenarTablaPiezas();
				} catch (Exception e2) {
					// Mensaje no se puede ver la pieza.
					e2.printStackTrace();
				}
			}
		};
		return editarPieza;
	}

	private Action borrarPieza() {
		Action borrarPieza = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal,
							"¿Está seguro que desea eliminar la pieza?");
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.eliminarPieza((PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 0),
								usuarioLogueado.getIdPersonal());
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						// Por si se vuelve a cargar la tabla de piezas.
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return borrarPieza;
	}

	private Action modificarStock() {
		Action modificarPieza = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				ventanaPieza = new VentanaAltaModifPieza(vistaPrincipal);
				try {
					new ControladorPieza(ventanaPieza, modelo,
							(PiezaDTO) table.getModel().getValueAt(table.getSelectedRow(), 0), false, true);
				} catch (Exception e2) {
					// Mensaje no se puede ver la pieza.
					e2.printStackTrace();
				}
				llenarTablaPiezas();
			}
		};
		return modificarPieza;
	}

	private Action borrarElectrodomestico() {
		Action borrarElectrodomestico = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal,
							"¿Está seguro que desea eliminar el electrodoméstico?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.borrarElectrodomestico(
								(ElectrodomesticoDTO) table.getModel().getValueAt(table.getSelectedRow(), 2),
								usuarioLogueado.getIdPersonal());
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						llenarTablaElectrodomesticos();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return borrarElectrodomestico;
	}

	public Action editarElectrodomestico() {
		Action editarElectrodomestico = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				try {
					ventanaElectrodomestico = new VentanaAltaModifElectrod(vistaPrincipal);
					new ControladorElectrodomestico(ventanaElectrodomestico, modelo,
							(ElectrodomesticoDTO) table.getModel().getValueAt(table.getSelectedRow(), 2));
					llenarTablaElectrodomesticos();
					marcas = modelo.obtenerMarcas();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return editarElectrodomestico;
	}

	private Action verOT_UA() {

		Action verOT = new AbstractAction() {
			private static final long serialVersionUID = 7386169802184763591L;

			@Override
			public void actionPerformed(ActionEvent e) {

				ventanaOT = new VentanaAdminOT(vistaPrincipal);

				ordenAcargar = (OrdenDTO) vistaPrincipal.getTableOT_UA()
						.getValueAt(vistaPrincipal.getTableOT_UA().getSelectedRow(), 0);
				new ControladorAdminOT(ventanaOT, modelo, ordenAcargar, usuarioLogueado);

				llenarTablaOT_UA(vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());
			}
		};
		return verOT;

	}

	private Action borrarUsuario() {
		Action borrarUsuario = new AbstractAction() {

			private static final long serialVersionUID = -6922096709923256228L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal,
							"¿Está seguro que desea eliminar al usuario?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						modelo.borrarUsuario((UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 1),
								usuarioLogueado);// Usuario
						((DefaultTableModel) table.getModel()).removeRow(modelRow);
						usuarios_comunes_en_tabla = null;
						llenarTablaUsuarios();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return borrarUsuario;
	}

	private Action blanquearUsuario() {
		Action blanquearUsuario = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				try {
					int respuesta = JOptionPane.showConfirmDialog(vistaPrincipal,
							"¿Está seguro que desea reestablecer la contraseña?", null, JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {
						((UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 1))
								.setContrasenia("qwerasdf");
						modelo.actualizarUsuario(((UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 1)));
						JOptionPane.showMessageDialog(vistaPrincipal,
								"La contraseña se ha reestablecido correctamente a \"qwerasdf\".",
								"Contraseña reestablecida", JOptionPane.INFORMATION_MESSAGE, null);
						llenarTablaUsuarios();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		return blanquearUsuario;
	}

	@SuppressWarnings("unused")
	private Action editarUsuario() {
		Action editarUsuario = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				try {
					ventanaUsuario = new VentanaAltaModifUsuario(vistaPrincipal);
					new ControladorUsuario(ventanaUsuario, modelo, usuarios_comunes_en_tabla,
							(UsuarioDTO) table.getModel().getValueAt(table.getSelectedRow(), 1));
					llenarTablaUsuarios();
					// Problemas con el editar cuando chequea si ya existe.
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		return editarUsuario;
	}

	public void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 50; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			columnModel.getColumn(column).setPreferredWidth(width);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.vistaPrincipal.getBtnAgregarCliente()) {
			this.ventanaCliente = new VentanaAltaModifCliente(this.vistaPrincipal);
			ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente, modelo);
			llenarTablaClientes();

		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevaOt()) {
			this.ventanaOT = new VentanaAdminOT(this.vistaPrincipal);
			ControladorAdminOT controladorOT = new ControladorAdminOT(ventanaOT, modelo, usuarioLogueado);
			marcas = modelo.obtenerMarcas();
			llenarTablaOT_UA(vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());

		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevaSc()) {
			VentanaNuevaSC ventanaSC = new VentanaNuevaSC(this.vistaPrincipal);
			ControladorSC controladorSC = new ControladorSC(ventanaSC, modelo, usuarioLogueado);
			llenarTablaScs();

		} else if (e.getSource() == this.vistaPrincipal.getBtnAadirPiezas()) {
			this.ventanaPieza = new VentanaAltaModifPieza(this.vistaPrincipal);
			try {
				ControladorPieza controladorPieza = new ControladorPieza(this.ventanaPieza, this.modelo);
				llenarTablaPiezas();

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == this.vistaPrincipal.getBtnAgregarUsuario()) {
			this.ventanaUsuario = new VentanaAltaModifUsuario(this.vistaPrincipal);
			ControladorUsuario controladorUsuario = new ControladorUsuario(this.ventanaUsuario, this.modelo,
					this.usuarios_comunes_en_tabla);
			llenarTablaUsuarios();

		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevoProveedor()) {
			this.ventanaProveedor = new VentanaAltaModifProveedor(this.vistaPrincipal);
			ControladorProveedor controladorProveedor = new ControladorProveedor(this.ventanaProveedor, this.modelo,
					this.marcas, this.proveedores_en_tabla);
			llenarTablaProveedores();
			marcas = modelo.obtenerMarcas();

		} else if (e.getSource() == this.vistaPrincipal.getBtnNuevoFletero()) {

			this.ventanaFletero = new VentanaNuevoFletero(this.vistaPrincipal);
			ControladorVentanaNuevoFletero controladorFletero = new ControladorVentanaNuevoFletero(this.ventanaFletero,
					this.modelo, usuarioLogueado);
			llenarTablaFleteros();

		} else if (e.getSource() == this.vistaPrincipal.getBtnCrearBackup()) {

			try {

				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(null);

				String bat = "mysqldump -ugrupo1 -pgrupo1 20161_service_g1 > " + f.getSelectedFile().toString()
						+ "\\backup.sql";

				final File file = new File("backup.bat");
				file.createNewFile();
				PrintWriter writer = new PrintWriter(file, "UTF-8");
				writer.println(bat);
				writer.close();

				Process p = Runtime.getRuntime().exec("cmd /c backup.bat");
				p.waitFor();
				file.delete();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "No se creó el backup. " + ex.getMessage());
			}

		} else if (e.getSource() == this.vistaPrincipal.getBtnCargarBackup()) {

			try {

				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				f.showOpenDialog(null);

				String bat = "mysql -ugrupo1 -pgrupo1 20161_service_g1 < " + f.getSelectedFile().toString();

				final File file = new File("backup.bat");
				file.createNewFile();
				PrintWriter writer = new PrintWriter(file, "UTF-8");
				writer.println(bat);
				writer.close();

				Process p = Runtime.getRuntime().exec("cmd /c backup.bat");
				p.waitFor();
				file.delete();

				actualizarTabla();
				// TODO mostrar mensaje/progress bar porque tarda mucho

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "No se cargó el backup correctamente. " + ex.getMessage());
			}

		} else if (e.getSource() == this.vistaPrincipal.getBtnAgregarEd()) {
			this.ventanaElectrodomestico = new VentanaAltaModifElectrod(this.vistaPrincipal);
			ControladorElectrodomestico controladorEd = new ControladorElectrodomestico(ventanaElectrodomestico,
					modelo);
			marcas = modelo.obtenerMarcas();
			llenarTablaElectrodomesticos();

		} else if (e.getSource() == this.vistaPrincipal.getMntmDatos()) {
			ventanaUsuario = new VentanaAltaModifUsuario(vistaPrincipal);
			new ControladorUsuario(ventanaUsuario, modelo, usuarios_comunes_en_tabla, usuarioLogueado);
			llenarTablaUsuarios();

		} else if (e.getSource() == this.vistaPrincipal.getMntmSalir()) {
			VentanaLogueo ventanaLogueo = new VentanaLogueo();
			ControladorLogueo controladorLogueo = new ControladorLogueo(ventanaLogueo, modelo);
			controladorLogueo.iniciar();
			this.vistaPrincipal.dispose();

		} else if (e.getSource() == this.vistaPrincipal.getBtnEnviarMailOT()) {
			VentanaMail ventanaMail = new VentanaMail(vistaPrincipal);
			ControladorMail controladorMail = new ControladorMail(ventanaMail, this.modelo);

		} else if (e.getSource() == this.vistaPrincipal.getBtnEnviarMailSC()) {
			ventanaMail = new VentanaMail(vistaPrincipal);
			ControladorMail controladorMail = new ControladorMail(ventanaMail, this.modelo);

		} else if (e.getSource() == this.vistaPrincipal.getMntmActualizar())
			actualizarTabla();

		else if (e.getSource() == this.vistaPrincipal.getBtnProcesar()) {
			Object item = this.vistaPrincipal.getComboBoxProvedores().getSelectedItem();
			if (item != null) {
				if (selectedFile != null) {
					try {
						ImporterCSV importer = new ImporterCSV(vistaPrincipal, modelo,
								((ProveedorDTO) item).getIdProveedor(), selectedFile.getAbsolutePath());
						importer.importPrices();
						JOptionPane.showMessageDialog(vistaPrincipal, "Se ha importado el archivo exitosamente.",
								"Importado!", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(vistaPrincipal,
								"No se ha podido importar el archivo. " + e1.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else
					JOptionPane.showMessageDialog(vistaPrincipal,
							"Debe seleccionar un archivo valido para poder importar los precios del archivo", "Error",
							JOptionPane.ERROR_MESSAGE);
			} else
				JOptionPane.showMessageDialog(vistaPrincipal,
						"Debe seleccionar un proveedor para poder importar los precios del archivo", "Error",
						JOptionPane.ERROR_MESSAGE);
			selectedFile = null;

		} else if (e.getSource() == this.vistaPrincipal.getFileChooser()) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(vistaPrincipal);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
			}

		} else if (e.getSource() == this.vistaPrincipal.getBtnAsignar()) {
				
				ordenes_a_enviar = new LinkedList<OrdenDTO>();

				ordenes_a_enviar = obtenerReparadasDelivery(7); // OTs reparadas con delivery

				VentanaAsignacion ventanaAsignacion = new VentanaAsignacion();
				new ControladorAsignacion(ventanaAsignacion, modelo, fleteros_en_tabla, ordenes_a_enviar);
		
		} else if (e.getSource() == this.vistaPrincipal.getMntmEditarZonas()) {
			
			VentanaZonas ventanaZonas = new VentanaZonas(this.vistaPrincipal);
			ControladorZonas controladorZonas = new ControladorZonas(ventanaZonas, modelo);
			
		} else if (e.getSource() == this.vistaPrincipal.getBtnRankElectrodomesticos()){
			Reporte reporte = new Reporte();
			try {
				reporte.ReporteRankElectrodomesticos();
				reporte.mostrar();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == this.vistaPrincipal.getBtnRankPiezas()){
			Reporte reporte = new Reporte();
			try {
				reporte.ReporteRankPiezas();;
				reporte.mostrar();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.vistaPrincipal.getCbMarcas()
				&& this.vistaPrincipal.getCbMarcas().getSelectedItem() != null)
			llenarTablaPiezas();

		else if (e.getSource() == this.vistaPrincipal.getComboEstadosUA()) {
			llenarTablaOT_UA(this.vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());
		}

		else if (e.getSource() == this.vistaPrincipal.getComboEstadosUT()) {
			llenarTablaOT_UT(this.vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		actualizarTabla();
	}

	private void actualizarTabla() {
		if (this.vistaPrincipal.getTabClientes().isShowing())
			llenarTablaClientes();
		if (this.vistaPrincipal.getTabOT_UA().isShowing())
			llenarTablaOT_UA(this.vistaPrincipal.getComboEstadosUA().getSelectedItem().toString());
		if (this.vistaPrincipal.getTabOT_UT().isShowing())
			llenarTablaOT_UT(this.vistaPrincipal.getComboEstadosUT().getSelectedItem().toString());
		if (this.vistaPrincipal.getPanelScs().isShowing())
			llenarTablaScs();
		if (this.vistaPrincipal.getPanelProveedores().isShowing())
			llenarTablaProveedores();
		if (this.vistaPrincipal.getTabPiezas().isShowing()) {
			cargarCbMarcas();
			llenarTablaPiezas();
		}
		if (this.vistaPrincipal.getTabEds().isShowing())
			llenarTablaElectrodomesticos();
		if (this.vistaPrincipal.getTabUsuarios().isShowing())
			llenarTablaUsuarios();
		if (this.vistaPrincipal.getTabFleteros().isShowing())
			llenarTablaFleteros();
		if (this.vistaPrincipal.getTabImportarPrecios().isShowing())
			llenarComboProveedores();

	}

	public List<OrdenDTO> filtrarSegun(String s) {
		List<OrdenDTO> result = new LinkedList<OrdenDTO>();

		if (s.equals("seleccionar"))
			result = this.modelo.obtenerOrdenes();
		else {
			for (int i = 0; i < this.modelo.obtenerOrdenes().size(); i++) {
				if (modelo.obtenerOrdenes().get(i).getEstado().getNombre().equals(s)) {
					result.add(modelo.obtenerOrdenes().get(i));
				}
			}
		}
		return result;
	}

	public List<OrdenDTO> obtenerReparadasDelivery(int estado) { // reparadas con envios no asignadas

		List<OrdenDTO> resultado = new LinkedList<OrdenDTO>();
		
		List<Integer> envios = new LinkedList<Integer>();
		
		for (int i = 0; i < this.modelo.obtenerEnvios().size(); i++) {
			envios.add(this.modelo.obtenerEnvios().get(i).getIdOT());
		}
					
		for (int i = 0; i < this.modelo.obtenerOrdenes().size(); i++) {
			OrdenDTO actual = this.modelo.obtenerOrdenes().get(i);
			
			if (actual.getEstado().getId() == estado 
					&& actual.isEsDelivery()
					&& !envios.contains(actual.getIdOT())) {
				resultado.add(actual);
			}
		}

		return resultado;
	}

	private void llenarComboProveedores() {
		this.vistaPrincipal.getComboBoxProvedores().removeAllItems();

		List<ProveedorDTO> proveedores;
		try {
			proveedores = modelo.obtenerProveedores2();
			for (ProveedorDTO proveedorDTO : proveedores) {
				this.vistaPrincipal.getComboBoxProvedores().addItem(proveedorDTO);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(vistaPrincipal, "No se han podido cargar los proveedores", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}