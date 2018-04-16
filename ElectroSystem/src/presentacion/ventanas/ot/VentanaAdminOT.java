package presentacion.ventanas.ot;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.ElectrodomesticoDTO;
import dto.EstadoDTO;
import dto.MarcaDTO;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class VentanaAdminOT extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDomicilio;
	private JTextField txtFecha;
	private JTextField txtCliente;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JTextField txtUsuario;
	private ButtonGroup group;
	private JRadioButton rbEnvio;
	private JRadioButton rbRetiro;

	private JButton btnAgregarElectrodomstico;
	private JComboBox<String> comboNombre;
	private JComboBox<MarcaDTO> comboMarca;
	private JComboBox<ElectrodomesticoDTO> comboModelo;
	private JButton btnBuscar;
	private JLabel lblDomicilio;
	private JButton btnAgregar;
	private JTextField txtElectrodomestico;
	private JLabel lblUsuarioLogueado;
	private JComboBox<EstadoDTO> cbEstado;
	private JLabel lblEstado;
	private JTextArea txtDetalle;
	private JButton btnEnviarPorMail;
	private JButton btnCambiarDomicilio;

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaAdminOT(JDialog padre) {
		super(padre, true);
		go();
	}

	public VentanaAdminOT(JFrame padre) {
		super(padre, true);
		go();
	}

	private void go() {
		setTitle("Nueva Orden de Trabajo");
		setBounds(100, 100, 450, 537);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDetalleDelTrabajo = new JLabel("Detalle de la falla:");
		lblDetalleDelTrabajo.setBounds(12, 165, 263, 14);
		contentPanel.add(lblDetalleDelTrabajo);

		JLabel lblElectrodomstico = new JLabel("Electrodom\u00E9stico:");
		lblElectrodomstico.setBounds(10, 74, 117, 14);
		contentPanel.add(lblElectrodomstico);

		comboNombre = new JComboBox<String>();
		comboNombre.setBounds(216, 71, 94, 20);
		contentPanel.add(comboNombre);

		comboMarca = new JComboBox<MarcaDTO>();
		comboMarca.setBounds(112, 71, 94, 20);
		contentPanel.add(comboMarca);

		comboModelo = new JComboBox<ElectrodomesticoDTO>();
		comboModelo.setBounds(320, 71, 99, 20);
		contentPanel.add(comboModelo);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 39, 63, 14);
		contentPanel.add(lblCliente);

		lblDomicilio = new JLabel("Domicilio:");
		lblDomicilio.setEnabled(false);
		lblDomicilio.setBounds(10, 333, 63, 14);
		contentPanel.add(lblDomicilio);

		txtDomicilio = new JTextField();
		txtDomicilio.setEditable(false);
		txtDomicilio.setVisible(true);
		txtDomicilio.setBounds(83, 330, 333, 20);
		contentPanel.add(txtDomicilio);
		txtDomicilio.setColumns(10);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 11, 46, 14);
		contentPanel.add(lblFecha);

		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		txtFecha.setBounds(53, 8, 71, 20);
		contentPanel.add(txtFecha);
		txtFecha.setColumns(10);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fecha = new Date();
		txtFecha.setText(sdf.format(fecha));

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setBounds(66, 36, 145, 20);
		contentPanel.add(txtCliente);
		txtCliente.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(221, 35, 89, 23);
		contentPanel.add(btnBuscar);

		btnCrear = new JButton("Aceptar");
		btnCrear.setBounds(238, 465, 89, 23);
		contentPanel.add(btnCrear);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(337, 465, 89, 23);
		contentPanel.add(btnCancelar);

		lblUsuarioLogueado = new JLabel("Usuario logueado:");
		lblUsuarioLogueado.setBounds(158, 11, 135, 14);
		contentPanel.add(lblUsuarioLogueado);

		txtUsuario = new JTextField();
		txtUsuario.setEditable(false);
		txtUsuario.setBounds(303, 8, 123, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);

		rbEnvio = new JRadioButton("Env\u00EDo a domicilio");
		rbEnvio.setBounds(124, 300, 117, 23);
		rbEnvio.setEnabled(true);
		contentPanel.add(rbEnvio);

		rbRetiro = new JRadioButton("Retiro en local");
		rbRetiro.setBounds(12, 300, 109, 23);
		rbRetiro.setEnabled(true);
		contentPanel.add(rbRetiro);

		group = new ButtonGroup();
		group.add(rbEnvio);
		group.add(rbRetiro);

		btnAgregarElectrodomstico = new JButton("Agregar electrodom\u00E9stico");
		btnAgregarElectrodomstico.setBounds(12, 121, 187, 23);
		contentPanel.add(btnAgregarElectrodomstico);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(320, 35, 89, 23);
		contentPanel.add(btnAgregar);

		txtElectrodomestico = new JTextField();
		txtElectrodomestico.setBounds(12, 96, 407, 20);
		contentPanel.add(txtElectrodomestico);
		txtElectrodomestico.setColumns(10);

		cbEstado = new JComboBox<EstadoDTO>();
		cbEstado.setBounds(12, 418, 135, 20);
		contentPanel.add(cbEstado);
		cbEstado.setVisible(false);

		lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(12, 393, 69, 14);
		contentPanel.add(lblEstado);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 190, 407, 86);
		contentPanel.add(scrollPane);

		txtDetalle = new JTextArea();
		scrollPane.setViewportView(txtDetalle);

		btnEnviarPorMail = new JButton("Enviar por mail");
		btnEnviarPorMail.setBounds(12, 465, 133, 23);
		contentPanel.add(btnEnviarPorMail);

		btnCambiarDomicilio = new JButton("Cambiar domicilio");
		btnCambiarDomicilio.setBounds(83, 361, 158, 23);
		contentPanel.add(btnCambiarDomicilio);
		this.btnEnviarPorMail.setVisible(false);

		lblEstado.setVisible(false);
		this.txtElectrodomestico.setVisible(false);

	}

	public JComboBox<EstadoDTO> getCbEstado() {
		return cbEstado;
	}

	public JLabel getLblEstado() {
		return lblEstado;
	}

	public JTextField getTxtElectrodomestico() {
		return txtElectrodomestico;
	}

	public void setTxtElectrodomestico(String txtElectrodomestico) {
		this.txtElectrodomestico.setText(txtElectrodomestico);
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public JComboBox<String> getComboNombre() {
		return comboNombre;
	}

	public JComboBox<MarcaDTO> getComboMarca() {
		return comboMarca;
	}

	public JComboBox<ElectrodomesticoDTO> getComboModelo() {
		return comboModelo;
	}

	public JButton getBtnAgregarElectrodomestico() {
		return btnAgregarElectrodomstico;
	}

	public JRadioButton getButtonEnvio() {
		return rbEnvio;
	}

	public void setButtonEnvio(boolean b) {
		this.rbEnvio.setSelected(b);
		;
	}

	public JRadioButton getButtonRetiro() {
		return rbRetiro;
	}

	public void setButtonRetiro(boolean b) {
		this.rbRetiro.setSelected(b);
	}

	public boolean isDelivery() {
		if (this.rbEnvio.isSelected())
			return true;
		return false;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(String txtUsuario) {
		this.txtUsuario.setText(txtUsuario);
	}

	public String getTxtDetalle() {
		return txtDetalle.getText();
	}

	public JTextArea getDetalle() {
		return txtDetalle;
	}

	public void setTxtDetalle(String t) {
		this.txtDetalle.setText(t);
	}

	public JTextField getTxtDomicilio() {
		return txtDomicilio;
	}

	public void setTxtDomicilio(String domicilio) {
		this.txtDomicilio.setText(domicilio);
	}

	public JTextField getTxtFecha() {
		return txtFecha;
	}

	public void setTxtFecha(JTextField txtFecha) {
		this.txtFecha = txtFecha;
	}

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public void setTxtCliente(String txtCliente) {
		this.txtCliente.setText(txtCliente);
	}

	public void mostrarOpcionesEnvio() {
		this.txtDomicilio.setEnabled(true);
		this.lblDomicilio.setEnabled(true);
	}

	public void esconderOpcionesEnvio() {
		this.txtDomicilio.setEnabled(false);
		this.txtDomicilio.setEditable(false);
		this.lblDomicilio.setEnabled(false);
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnCrear = btnAceptar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JRadioButton getRbEnvio() {
		return rbEnvio;
	}

	public JRadioButton getRbRetiro() {
		return rbRetiro;
	}

	public JLabel getLblDomicilio() {
		return lblDomicilio;
	}

	public void setLblDomicilio(JLabel lblDomicilio) {
		this.lblDomicilio = lblDomicilio;
	}

	public JLabel getLblUsuarioLogueado() {
		return lblUsuarioLogueado;
	}

	public void setLblUsuarioLogueado(String lblUsuarioLogueado) {
		this.lblUsuarioLogueado.setText(lblUsuarioLogueado);
	}

	public void esconderBotones() {
		getBtnBuscar().setVisible(false);
		getBtnAgregar().setVisible(false);
		getBtnAgregarElectrodomestico().setVisible(false);
		getComboMarca().setVisible(false);
		getComboModelo().setVisible(false);
		getComboNombre().setVisible(false);
		setLblUsuarioLogueado("Usuario que dió de alta: ");
		getDetalle().setEditable(false);
		getTxtElectrodomestico().setEditable(false);
		getTxtElectrodomestico().setVisible(true);
	}

	public JButton getBtnEnviarPorMail() {
		return btnEnviarPorMail;
	}

	public void setBtnEnviarPorMail(JButton btnEnviarPorMail) {
		this.btnEnviarPorMail = btnEnviarPorMail;
	}

	public JButton getBtnCambiarDomicilio() {
		return btnCambiarDomicilio;
	}

	public void setBtnCambiarDomicilio(JButton btnCambiarDomicilio) {
		this.btnCambiarDomicilio = btnCambiarDomicilio;
	}
	
}
