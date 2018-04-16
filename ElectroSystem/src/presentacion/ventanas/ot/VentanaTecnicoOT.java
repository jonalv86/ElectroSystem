package presentacion.ventanas.ot;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import dto.MarcaDTO;
import dto.PiezaDTO;
import helpers.Validador;

import javax.swing.JScrollPane;
import dto.EstadoDTO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class VentanaTecnicoOT extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private JTextField txtDetalle;
	private JTextField txtFecha;
	private JTextField txtUsuario;
	private JTextField lblidOT;
	private JTextField txtCliente;
	private JTextField txtElectro;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnAgregarPieza;
	private JDatePickerImpl dateFechaVencimiento;
	private JComboBox<PiezaDTO> comboPieza;
	private JComboBox<MarcaDTO> comboMarca;
	private JTable table;
	private JLabel txtCostoEnvio;
	private JLabel lblTotalPresupuesto;
	private JLabel lblElectrodomstico_1;
	private JScrollPane scrollPane;
	private JLabel lblManoDeObra;
	private JTextField tfManoDeObra;
	private JLabel lblEstado;
	private JComboBox<EstadoDTO> cbEstado;
	private JLabel lblFechaExpiraGaranta;

	public void setLblidOT(int i) {
		this.lblidOT.setText(String.valueOf(i));
	}

	public VentanaTecnicoOT(JFrame padre) {
		super(padre, true);
		initialize();
	}

	private void initialize() {
		setTitle("Presupuesto - Orden Trabajo");
		setBounds(100, 100, 693, 537);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDetalleDelTrabajo = new JLabel("Detalle del problema");
		lblDetalleDelTrabajo.setBounds(12, 113, 127, 14);
		contentPanel.add(lblDetalleDelTrabajo);

		txtDetalle = new JTextField();
		txtDetalle.setEditable(false);
		txtDetalle.setToolTipText("Ejemplo: reparar el bot\u00F3n de encendido");
		txtDetalle.setBounds(12, 139, 665, 40);
		contentPanel.add(txtDetalle);
		txtDetalle.setColumns(10);

		JLabel lblElectrodomstico = new JLabel("Seleccionar pieza:");
		lblElectrodomstico.setBounds(12, 219, 110, 14);
		contentPanel.add(lblElectrodomstico);

		comboPieza = new JComboBox<PiezaDTO>();
		comboPieza.setBounds(223, 216, 161, 20);
		contentPanel.add(comboPieza);

		comboMarca = new JComboBox<MarcaDTO>();
		comboMarca.setBounds(119, 216, 94, 20);
		contentPanel.add(comboMarca);

		txtCostoEnvio = new JLabel();
		txtCostoEnvio.setBounds(119, 313, 86, 20);
		txtCostoEnvio.setEnabled(false);
		contentPanel.add(txtCostoEnvio);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 43, 46, 14);
		contentPanel.add(lblFecha);

		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		txtFecha.setBounds(58, 40, 71, 20);
		contentPanel.add(txtFecha);
		txtFecha.setColumns(10);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = new Date();
		txtFecha.setText(sdf.format(fecha));

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(489, 465, 89, 23);
		contentPanel.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(588, 465, 89, 23);
		contentPanel.add(btnCancelar);

		JLabel lblUsuarioLogueado = new JLabel("Usuario logueado:");
		lblUsuarioLogueado.setBounds(411, 11, 110, 14);
		contentPanel.add(lblUsuarioLogueado);

		txtUsuario = new JTextField();
		txtUsuario.setEditable(false);
		txtUsuario.setBounds(531, 8, 146, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);

		lblFechaExpiraGaranta = new JLabel("Fecha vencimiento:");
		lblFechaExpiraGaranta.setBounds(12, 404, 127, 23);
		contentPanel.add(lblFechaExpiraGaranta);
		UtilCalendarModel model = new UtilCalendarModel();
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		dateFechaVencimiento = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SpringLayout springLayout = (SpringLayout) dateFechaVencimiento.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, dateFechaVencimiento.getJFormattedTextField(), 0, SpringLayout.SOUTH, dateFechaVencimiento);
		dateFechaVencimiento.setEnabled(false);
		dateFechaVencimiento.setBounds(125, 404, 119, 32);
		contentPanel.add(dateFechaVencimiento);

		btnAgregarPieza = new JButton("Agregar pieza al presupuesto");
		btnAgregarPieza.setBounds(490, 215, 187, 23);
		contentPanel.add(btnAgregarPieza);

		JLabel lblOT = new JLabel("Orden de trabajo N\u00BA");
		lblOT.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblOT.setBounds(10, 11, 117, 14);
		contentPanel.add(lblOT);

		lblidOT = new JTextField("");
		lblidOT.setEditable(false);
		lblidOT.setHorizontalAlignment(SwingConstants.CENTER);
		lblidOT.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblidOT.setBounds(137, 8, 53, 17);
		contentPanel.add(lblidOT);

		SpinnerModel sm = new SpinnerNumberModel(1, 1, 100, 1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 258, 667, 108);
		contentPanel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		SpinnerModel sm1 = new SpinnerNumberModel(1, 1, 100, 1);

		lblTotalPresupuesto = new JLabel("Total presupuesto");
		lblTotalPresupuesto.setBounds(281, 408, 123, 14);
		contentPanel.add(lblTotalPresupuesto);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(158, 42, 55, 16);
		contentPanel.add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setBounds(207, 40, 217, 20);
		contentPanel.add(txtCliente);
		txtCliente.setColumns(10);

		lblElectrodomstico_1 = new JLabel("Electrodom\u00E9stico:");
		lblElectrodomstico_1.setBounds(10, 74, 110, 16);
		contentPanel.add(lblElectrodomstico_1);

		txtElectro = new JTextField();
		txtElectro.setEditable(false);
		txtElectro.setBounds(119, 72, 558, 19);
		contentPanel.add(txtElectro);
		txtElectro.setColumns(10);
		
		lblManoDeObra = new JLabel("Mano de obra:");
		lblManoDeObra.setBounds(12, 379, 94, 14);
		contentPanel.add(lblManoDeObra);
		
		tfManoDeObra = new JTextField();
		tfManoDeObra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.esNumero(e.getKeyChar()) || tfManoDeObra.getText().length()>19 ){ 
					e.consume();
				}
				if (tfManoDeObra.getText().length()>=0) {
					tfManoDeObra.setText(Validador.armarPrecioValido(tfManoDeObra.getText()));
				}
				tfManoDeObra.select(tfManoDeObra.getText().length(), 0);
			}
		});
		tfManoDeObra.setBounds(119, 377, 86, 20);
		contentPanel.add(tfManoDeObra);
		tfManoDeObra.setColumns(10);
		
		cbEstado = new JComboBox<EstadoDTO>();
		cbEstado.setBounds(553, 376, 124, 20);
		cbEstado.setVisible(false);
		contentPanel.add(cbEstado);
		
		lblEstado = new JLabel("Estado en el que se deja el electrodom\u00E9stico:");
		lblEstado.setBounds(265, 377, 278, 14);
		lblEstado.setVisible(false);
		contentPanel.add(lblEstado);
		agregarDias();
		agregarMeses();
		agregarYears();

	}

	public JLabel getLblFechaExpiraGaranta() {
		return lblFechaExpiraGaranta;
	}

	public JLabel getLblManoDeObra() {
		return lblManoDeObra;
	}

	public JLabel getLblEstado() {
		return lblEstado;
	}

	public JComboBox<EstadoDTO> getCbEstado() {
		return cbEstado;
	}

	public JTextField getTfManoDeObra() {
		return tfManoDeObra;
	}

	public void agregarDias() {
	}

	public void agregarMeses() {

	}

	public void agregarYears() {
	}

	public JTextField getTxtElectro() {
		return txtElectro;
	}

	public void setTxtElectro(String s) {
		this.txtElectro.setText(s);
	}

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public void setTxtCliente(String txtCliente) {
		this.txtCliente.setText(txtCliente);
	}

	private class DateLabelFormatter extends AbstractFormatter {

		private String datePattern = "dd/MM/yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}

	}

	public JComboBox<PiezaDTO> getComboPieza() {
		return comboPieza;
	}

	public JComboBox<MarcaDTO> getComboMarca() {
		return comboMarca;
	}

	public JButton getBtnAgregarPieza() {
		return btnAgregarPieza;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(JTextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public String getTxtDetalle() {
		return txtDetalle.getText();
	}

	public void setTxtDetalle(String txtDetalle) {
		this.txtDetalle.setText(txtDetalle);
	}

	public JLabel getTxtCostoEnvio() {
		return txtCostoEnvio;
	}

	public void setTxtCostoEnvio(String costo) {
		this.txtCostoEnvio.setText(costo);
	}

	public JTextField getTxtFecha() {
		return txtFecha;
	}

	public void setTxtFecha(JTextField txtFecha) {
		this.txtFecha = txtFecha;
	}

	public JButton getBtnCrear() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public void agregarMarcas(Collection<MarcaDTO> marcas) {
		this.comboMarca.removeAllItems();
		for (MarcaDTO marca : marcas) {
			this.comboMarca.addItem(marca);
		}
	}

	public void agregarPiezas(Collection<PiezaDTO> piezas) {
		this.comboPieza.removeAllItems();
		for (PiezaDTO pieza : piezas) {
			this.comboPieza.addItem(pieza);
		}
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JLabel getLblTotalPresupuesto() {
		return lblTotalPresupuesto;
	}

	public void setLblTotalPresupuesto(String i) {
		this.lblTotalPresupuesto.setText(i);
	}

	public void setTxtCostoEnvio(JLabel txtCostoEnvio) {
		this.txtCostoEnvio = txtCostoEnvio;
	}

	public void setBtnCrear(JButton btnCrear) {
		this.btnAceptar = btnCrear;
	}

	public void setBtnAgregarPieza(JButton btnAgregarPieza) {
		this.btnAgregarPieza = btnAgregarPieza;
	}

	public void setComboPieza(JComboBox<PiezaDTO> comboPieza) {
		this.comboPieza = comboPieza;
	}

	public void setComboMarca(JComboBox<MarcaDTO> comboMarca) {
		this.comboMarca = comboMarca;
	}

	public JDatePickerImpl getDateFechaVencimiento() {
		return dateFechaVencimiento;
	}

	public JTextField getLblidOT() {
		return lblidOT;
	}

	public void setLblidOT(JTextField lblidOT) {
		this.lblidOT = lblidOT;
	}

	public void setDateFechaVencimiento(JDatePickerImpl dateFechaVencimiento) {
		this.dateFechaVencimiento = dateFechaVencimiento;
	}

	public void setLblTotalPresupuesto(JLabel lblTotalPresupuesto) {
		this.lblTotalPresupuesto = lblTotalPresupuesto;
	}

	public void setTxtCliente(JTextField txtCliente) {
		this.txtCliente = txtCliente;
	}

	public void setTxtElectro(JTextField txtElectro) {
		this.txtElectro = txtElectro;
	}
}
