package presentacion.ventanas.sc;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

import dto.MarcaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;

public class VentanaNuevaSC extends JDialog {

	private static final long serialVersionUID = -1394811355246704888L;

	private JLabel lblProveedor;
	private JTextField txtFecha;
	private JTextField txtUsuario;
	private JTable table;
	private JScrollPane spDatosTabla;
	private JButton btnSolicitar;
	private JButton btnCancelar;
	private JTextField lblPrecioTotal;
	private JComboBox<ProveedorDTO> cboProveedores;
	private JSpinner tfCantidad;
	private JComboBox<PrecioPiezaDTO> cbPiezas;
	private JComboBox<MarcaDTO> cbMarca;
	private JButton btnAgregarPiezas;
	private JLabel label;
	private JLabel lblCantidadTotal;

	private JButton btnEnviar;
	private JTextField txtProveedor;

	public VentanaNuevaSC(JFrame padre) {
		super(padre, true);
		setTitle("Nueva Solicitud de Compra");
		setBounds(100, 100, 449, 460);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 11, 46, 14);
		getContentPane().add(lblFecha);

		setTxtFecha(new JTextField());
		getTxtFecha().setEditable(false);
		getTxtFecha().setBounds(66, 8, 71, 20);
		getContentPane().add(getTxtFecha());
		getTxtFecha().setColumns(10);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = new Date();
		txtFecha.setText(sdf.format(fecha));

		JLabel lblUsuarioLogueado = new JLabel("Usuario logueado:");
		lblUsuarioLogueado.setBounds(204, 11, 121, 14);
		getContentPane().add(lblUsuarioLogueado);

		setTxtUsuario(new JTextField());
		getTxtUsuario().setEditable(false);
		getTxtUsuario().setBounds(316, 8, 117, 20);
		getContentPane().add(getTxtUsuario());
		getTxtUsuario().setColumns(10);

		setLblProveedor(new JLabel("Proveedor:"));
		getLblProveedor().setBounds(10, 42, 63, 14);
		getContentPane().add(getLblProveedor());

		setTable(new JTable());

		setSpDatosTabla(new JScrollPane());
		getSpDatosTabla().setBounds(10, 142, 414, 171);
		getContentPane().add(getSpDatosTabla());
		getSpDatosTabla().setViewportView(getTable());

		setBtnSolicitar(new JButton("Solicitar"));
		getBtnSolicitar().setBounds(236, 388, 89, 23);
		getContentPane().add(getBtnSolicitar());

		setBtnCancelar(new JButton("Cancelar"));
		getBtnCancelar().setBounds(335, 388, 89, 23);
		getContentPane().add(getBtnCancelar());

		JLabel lblItems = new JLabel("Items:");
		lblItems.setBounds(10, 117, 46, 14);
		getContentPane().add(lblItems);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(285, 328, 46, 14);
		getContentPane().add(lblTotal);

		setLblPrecioTotal(new JTextField(""));
		getLblPrecioTotal().setBounds(341, 328, 63, 14);
		getContentPane().add(getLblPrecioTotal());

		setCboProveedores(new JComboBox<ProveedorDTO>());
		getCboProveedores().setBounds(76, 39, 183, 20);
		getContentPane().add(getCboProveedores());

		label = new JLabel("pieza:");
		label.setBounds(10, 83, 40, 14);
		getContentPane().add(label);

		cbMarca = new JComboBox<MarcaDTO>();
		cbMarca.setBounds(76, 80, 94, 20);
		getContentPane().add(cbMarca);

		cbPiezas = new JComboBox<PrecioPiezaDTO>();
		cbPiezas.setBounds(194, 80, 94, 20);
		getContentPane().add(cbPiezas);

		SpinnerModel sm = new SpinnerNumberModel(1, 1, 100, 1);
		tfCantidad = new JSpinner(sm);
		tfCantidad.setBounds(316, 80, 86, 20);
		getContentPane().add(tfCantidad);

		btnAgregarPiezas = new JButton("Agregar pieza");
		btnAgregarPiezas.setBounds(140, 108, 187, 23);
		getContentPane().add(btnAgregarPiezas);

		JLabel lblCantItems = new JLabel("");
		lblCantItems.setBounds(55, 117, 63, 14);
		getContentPane().add(lblCantItems);

		btnEnviar = new JButton("Enviar por mail");
		btnEnviar.setBounds(10, 388, 142, 23);
		getContentPane().add(btnEnviar);
		
		txtProveedor = new JTextField();
		txtProveedor.setBounds(76, 39, 183, 20);
		getContentPane().add(txtProveedor);
		txtProveedor.setColumns(10);
		txtProveedor.setVisible(false);
		txtProveedor.setEditable(false);
		
		JLabel lblCantidadTotalDe = new JLabel("Cantidad de piezas:");
		lblCantidadTotalDe.setBounds(10, 328, 111, 14);
		getContentPane().add(lblCantidadTotalDe);
		
		lblCantidadTotal = new JLabel("");
		lblCantidadTotal.setBounds(124, 328, 46, 14);
		getContentPane().add(lblCantidadTotal);

	}

	public JButton getBtnAgregarPiezas() {
		return btnAgregarPiezas;
	}

	public JSpinner getTfCantidad() {
		return tfCantidad;
	}
	
	public int getCantidad () {
		return (int) tfCantidad.getValue();
	}

	public JComboBox<PrecioPiezaDTO> getCbPiezas() {
		return cbPiezas;
	}

	public JComboBox<MarcaDTO> getCbMarca() {
		return cbMarca;
	}

	public JComboBox<ProveedorDTO> getCboProveedores() {
		return cboProveedores;
	}

	void setCboProveedores(JComboBox<ProveedorDTO> cboProveedores) {
		this.cboProveedores = cboProveedores;
	}

	public JLabel getLblProveedor() {
		return lblProveedor;
	}

	private void setLblProveedor(JLabel lblProveedor) {
		this.lblProveedor = lblProveedor;
	}

	public JTextField getTxtFecha() {
		return txtFecha;
	}

	private void setTxtFecha(JTextField txtFecha) {
		this.txtFecha = txtFecha;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	private void setTxtUsuario(JTextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public JTable getTable() {
		return table;
	}

	private void setTable(JTable table) {
		this.table = table;
	}

	public JScrollPane getSpDatosTabla() {
		return spDatosTabla;
	}

	private void setSpDatosTabla(JScrollPane spDatosTabla) {
		this.spDatosTabla = spDatosTabla;
	}

	public JButton getBtnSolicitar() {
		return btnSolicitar;
	}

	private void setBtnSolicitar(JButton btnSolicitar) {
		this.btnSolicitar = btnSolicitar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	private void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JTextField getLblPrecioTotal() {
		return lblPrecioTotal;
	}

	private void setLblPrecioTotal(JTextField jTextField) {
		this.lblPrecioTotal = jTextField;
		lblPrecioTotal.setBorder(null);
		lblPrecioTotal.setEditable(false);
		lblPrecioTotal.setBackground(UIManager.getColor("Label.background"));
	}

	public void setLblPrecioTotal(String lbPrecioTotal) {
		this.lblPrecioTotal.setText(lbPrecioTotal);
	}

	public void setLblPrecioTotal(float f) {
		this.lblPrecioTotal.setText(f + "");
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
	}

	public JTextField getTxtProveedor() {
		return txtProveedor;
	}

	public void setTxtProveedor(String txtProveedor) {
		this.txtProveedor.setText(txtProveedor);
	}

	public JLabel getLblPieza() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public String getLblCantidadTotal() {
		return lblCantidadTotal.toString();
	}

	public void setLblCantidadTotal(int lblCantidadTotal) {
		this.lblCantidadTotal.setText(String.valueOf(lblCantidadTotal));	}
}
