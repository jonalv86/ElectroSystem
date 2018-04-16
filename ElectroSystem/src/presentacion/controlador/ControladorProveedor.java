package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dto.MarcaDTO;
import dto.ProveedorDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.Proveedores.VentanaAltaModifProveedor;
import presentacion.ventanas.marca.VentanaAltaModifMarca;

public class ControladorProveedor implements ActionListener {

	private VentanaAltaModifProveedor ventana;
	private Modelo modelo;
	private List<MarcaDTO> noSeleccionadas;
	private List<MarcaDTO> seleccionadas;
	private List<ProveedorDTO> proveedores;
	private DefaultTableModel dtmSi;
	private DefaultTableModel dtmNo;
	private ProveedorDTO proveedor;

	public ControladorProveedor(VentanaAltaModifProveedor ventana, Modelo modelo, List<MarcaDTO> marcas, List<ProveedorDTO> proveedores) {
		this.ventana = ventana;
		this.modelo = modelo;
		this.noSeleccionadas = marcas;
		this.proveedores = proveedores;
		iniciarNuevo();
	}

	public ControladorProveedor(VentanaAltaModifProveedor ventana, Modelo modelo, List<MarcaDTO> marcas, ProveedorDTO proveedor, List<ProveedorDTO> proveedores, boolean editable) {
		this.ventana = ventana;
		this.modelo = modelo;
		this.proveedor = proveedor;
		this.proveedores = proveedores;
		sortDeMarcas(marcas);
		if (editable) {
			iniciarEdicion();
		} else {
			iniciarVer();
		}
	}

	private void iniciarVer() {
		this.ventana.setTitle("Ver " + this.proveedor.getNombre());
		this.ventana.getBtnAnadir().setEnabled(false);
		this.ventana.getBtnAnadirMarca().setEnabled(false);
		this.ventana.getBtnAnadirTodos().setEnabled(false);
		this.ventana.getBtnCancelar().setText("Cerrar");
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnCrear().setVisible(false);
		this.ventana.getBtnQuitar().setEnabled(false);
		this.ventana.getBtnQuitarTodos().setEnabled(false);
		this.ventana.getTfNombre().setText(proveedor.getNombre());
		this.ventana.getTfNombre().setEditable(false);
		this.ventana.getTfCuit().setText(proveedor.getCuit());
		this.ventana.getTfCuit().setEditable(false);
		this.ventana.getTfTelefono().setText(proveedor.getTelefono());
		this.ventana.getTfTelefono().setEditable(false);
		this.ventana.getTfMail().setText(proveedor.getMail());
		this.ventana.getTfMail().setEditable(false);
		this.ventana.getTfContacto().setText(proveedor.getContacto());
		this.ventana.getTfContacto().setEditable(false);
		crearTablas();
		cargarTablas();
		this.ventana.getTableNo().setVisible(false);

		this.ventana.setVisible(true);
	}

	private void iniciarEdicion() {
		this.ventana.setTitle("Editar " + this.proveedor.getNombre());
		this.ventana.getBtnAnadir().addActionListener(this);
		this.ventana.getBtnAnadirMarca().addActionListener(this);
		this.ventana.getBtnAnadirTodos().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnQuitar().addActionListener(this);
		this.ventana.getBtnQuitarTodos().addActionListener(this);
		this.ventana.getBtnCrear().setText("Actualizar");
		this.ventana.getTfNombre().setText(proveedor.getNombre());
		this.ventana.getTfCuit().setText(proveedor.getCuit());
		this.ventana.getTfTelefono().setText(proveedor.getTelefono());
		this.ventana.getTfMail().setText(proveedor.getMail());
		this.ventana.getTfContacto().setText(proveedor.getContacto());
		crearTablas();
		cargarTablas();

		this.ventana.setVisible(true);
	}

	private void sortDeMarcas(List<MarcaDTO> marcas) {
		noSeleccionadas = new ArrayList<MarcaDTO>();
		seleccionadas = new ArrayList<MarcaDTO>();
		for (MarcaDTO mNoSe : marcas) {
			boolean bandera = false;
			for (MarcaDTO mSi : proveedor.getMarcas()) {
				if (mSi.equals(mNoSe)) {
					bandera = true;
				}
			}
			if (bandera)
				seleccionadas.add(mNoSe);
			else
				noSeleccionadas.add(mNoSe);
		}
	}

	private void iniciarNuevo() {
		this.ventana.setTitle("Nuevo proveedor");
		this.ventana.getBtnAnadir().addActionListener(this);
		this.ventana.getBtnAnadirMarca().addActionListener(this);
		this.ventana.getBtnAnadirTodos().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnQuitar().addActionListener(this);
		this.ventana.getBtnQuitarTodos().addActionListener(this);
		crearTablas();
		cargarTablas();

		this.ventana.setVisible(true);
	}

	private void crearTablas() {
		vaciarTablas();
		if (seleccionadas != null)
			for (MarcaDTO m : seleccionadas) {
				MarcaDTO[] fila = { m };
				dtmSi.addRow(fila);
			}
		if (noSeleccionadas != null)
			for (MarcaDTO m : noSeleccionadas) {
				MarcaDTO[] fila = { m };
				dtmNo.addRow(fila);
			}
	}

	private void cargarTablas() {
		this.ventana.getTableSi().setModel(dtmSi);
		this.ventana.getTableNo().setModel(dtmNo);
	}

	private void vaciarTablas() {
		String[] columns = { "Marcas provistas" };
		String[] columns2 = { "Marcas no provistas" };

		dtmSi = new DefaultTableModel(null, columns) {
			private static final long serialVersionUID = 2696090498772781634L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dtmNo = new DefaultTableModel(null, columns2) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6884681473949351391L;

			@Override
			public boolean isCellEditable(int row, int column2) {
				return false;
			}
		};
		dtmSi.setColumnIdentifiers(columns);
		dtmNo.setColumnIdentifiers(columns2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ventana.getBtnAnadir()) {
			if (ventana.getTableNo().getSelectedRow() != -1) {
				MarcaDTO[] fila = { (MarcaDTO) ventana.getTableNo().getModel().getValueAt(ventana.getTableNo().getSelectedRow(), 0) };
				dtmNo.removeRow(ventana.getTableNo().getSelectedRow());
				dtmSi.addRow(fila);
				cargarTablas();
			}

		} else if (e.getSource() == ventana.getBtnQuitar()) {
			if (ventana.getTableSi().getSelectedRow() != -1) {
				MarcaDTO[] fila = { (MarcaDTO) ventana.getTableSi().getModel().getValueAt(ventana.getTableSi().getSelectedRow(), 0) };
				dtmSi.removeRow(ventana.getTableSi().getSelectedRow());
				dtmNo.addRow(fila);
				cargarTablas();
			}

		} else if (e.getSource() == ventana.getBtnAnadirTodos()) {
			vaciarTablas();
			if (seleccionadas != null)
				for (MarcaDTO m : seleccionadas) {
					MarcaDTO[] fila = { m };
					dtmSi.addRow(fila);
				}
			if (noSeleccionadas != null)
				for (MarcaDTO m : noSeleccionadas) {
					MarcaDTO[] fila = { m };
					dtmSi.addRow(fila);
				}
			cargarTablas();

		} else if (e.getSource() == ventana.getBtnQuitarTodos()) {
			vaciarTablas();
			if (seleccionadas != null)
				for (MarcaDTO m : seleccionadas) {
					MarcaDTO[] fila = { m };
					dtmNo.addRow(fila);
				}
			if (noSeleccionadas != null)
				for (MarcaDTO m : noSeleccionadas) {
					MarcaDTO[] fila = { m };
					dtmNo.addRow(fila);
				}
			cargarTablas();

		} else if (e.getSource() == ventana.getBtnAnadirMarca()) {
			VentanaAltaModifMarca ventanaMarca = new VentanaAltaModifMarca(this.ventana);
			ControladorMarca controladorMarca = new ControladorMarca(ventanaMarca, this.modelo);
			if (controladorMarca.getMarca() != null) {
				MarcaDTO[] fila = { controladorMarca.getMarca() };
				dtmSi.addRow(fila);
				seleccionadas.add(controladorMarca.getMarca());
			}

		} else if (e.getSource() == ventana.getBtnCrear()) {
			this.ventana.getLblFormatoDeMail().setVisible(false);
			this.ventana.getLblNombreObligatorio().setVisible(false);
			this.ventana.getLblCuitObligatorio().setVisible(false);
			this.ventana.getLblContactoObligatorio().setVisible(false);
			if (camposLlenos()) {
				if (!proveedorExiste()) {
					if (ventana.getTfMail().getText().isEmpty() || Validador.mailValido(ventana.getTfMail().getText())) {
						if (proveedor == null) {
							crearModificarProveedor(0);
							JOptionPane.showMessageDialog(null, "El proveedor " + ventana.getTfNombre().getText() + " " + "se ha creado satisfactoriamente.", "Nuevo proveedor creado", JOptionPane.INFORMATION_MESSAGE);
							this.ventana.dispose();
						} else {
							crearModificarProveedor(proveedor.getIdProveedor());
							JOptionPane.showMessageDialog(null, "El proveedor " + ventana.getTfNombre().getText() + " " + "se ha modificado satisfactoriamente.", "Proveedor actualizado", JOptionPane.INFORMATION_MESSAGE);
							this.ventana.dispose();
						}
					} else {
						this.ventana.getLblFormatoDeMail().setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Ya existe un proveedor con ese número de CUIT.", "Proveedor ya existe", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				this.ventana.getLblNombreObligatorio().setVisible(true);
				this.ventana.getLblCuitObligatorio().setVisible(true);
				this.ventana.getLblContactoObligatorio().setVisible(true);
			}

		} else if (e.getSource() == ventana.getBtnCancelar()) {
			this.ventana.dispose();

		} 
	}

	private void crearModificarProveedor(int id) {
		this.seleccionadas = new ArrayList<MarcaDTO>();
		for (int i = 0; i < dtmSi.getRowCount(); i++)
			seleccionadas.add((MarcaDTO) dtmSi.getValueAt(i, 0));
		ProveedorDTO nuevo = new ProveedorDTO(id,
				this.ventana.getTfNombre().getText(),
				this.ventana.getTfContacto().getText(),
				this.ventana.getTfCuit().getText(),
				this.ventana.getTfTelefono().getText().isEmpty() ? "" : this.ventana.getTfTelefono().getText(),
				this.ventana.getTfMail().getText().isEmpty() ? "" : this.ventana.getTfMail().getText(),
				seleccionadas);
		if (id == 0)
			try {
				modelo.agregarProveedor(nuevo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			try {
				modelo.actualizarProveedor(nuevo, proveedor.getMarcas());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private boolean proveedorExiste() {
		boolean b = false;
		for (ProveedorDTO p : proveedores) {
			if (p.getCuit().equals(ventana.getTfCuit().getText()))
				b = true;
			if (proveedor != null)
				if (p.getIdProveedor() == proveedor.getIdProveedor())
					b = false;
		}
		return b;
	}

	private boolean camposLlenos() {
		if (!ventana.getTfNombre().getText().isEmpty() && !ventana.getTfContacto().getText().isEmpty()) {
			ventana.getTfNombre().setText(Validador.validarCampo(ventana.getTfNombre().getText()));
				ventana.getTfContacto().setText(Validador.validarCampo(ventana.getTfContacto().getText()));
	}
		else
			return false;
		if (!ventana.getTfCuit().getText().isEmpty() &&
				!ventana.getTfNombre().getText().isEmpty() &&
				!ventana.getTfContacto().getText().isEmpty())
			if (ventana.getTfCuit().getText().length() == 11)
				return true;
		return false;
	}
}
