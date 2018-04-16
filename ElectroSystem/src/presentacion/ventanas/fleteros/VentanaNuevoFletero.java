package presentacion.ventanas.fleteros;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import helpers.Validador;
import presentacion.vista.VistaPrincipal;

@SuppressWarnings("serial")
public class VentanaNuevoFletero extends JDialog {

	@SuppressWarnings("unused")
	private VistaPrincipal vistaPrincipal;

	private JLabel lblVlidoHasta;
	private JTextField txtRegistro;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCelular;
	private JTextField txtPatente;

	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnActualizar;
	private JButton btnNuevoVechculo;
	private JButton btnBuscarVehculo;

	private JDatePickerImpl vencimientoRegistro;

	/**
	 * Create the dialog.
	 */
	public VentanaNuevoFletero(JFrame padre) {
		
		super(padre, true);
		setTitle("Nuevo Encargado de Env\u00EDos");
		setBounds(100, 100, 335, 406);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblRegistroDeConducir = new JLabel("Registro de Conducir N\u00BA");
		lblRegistroDeConducir.setBounds(10, 138, 137, 14);
		getContentPane().add(lblRegistroDeConducir);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 51, 64, 14);
		getContentPane().add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(10, 79, 64, 14);
		getContentPane().add(lblApellido);

		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setBounds(10, 107, 64, 14);
		getContentPane().add(lblCelular);

		txtRegistro = new JTextField();
		txtRegistro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterTelefonoValido(e.getKeyChar())|| txtRegistro.getText().length()>19)
					e.consume();
			}
		});
		txtRegistro.setBounds(145, 135, 116, 20);
		getContentPane().add(txtRegistro);
		txtRegistro.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || txtNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		txtNombre.setBounds(71, 51, 190, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		txtApellido = new JTextField();
		txtApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || txtApellido.getText().length()>19) {
					e.consume();
				}
			}
		});
		txtApellido.setBounds(71, 79, 190, 20);
		getContentPane().add(txtApellido);
		txtApellido.setColumns(10);

		txtCelular = new JTextField();
		txtCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterTelefonoValido(e.getKeyChar())|| txtCelular.getText().length()>19)
					e.consume();
			}
		});
		txtCelular.setBounds(71, 104, 190, 20);
		getContentPane().add(txtCelular);
		txtCelular.setColumns(10);

		btnNuevoVechculo = new JButton("Nuevo");
		btnNuevoVechculo.setBounds(95, 279, 75, 23);
		getContentPane().add(btnNuevoVechculo);

		JLabel lblVehculo = new JLabel("Patente:");
		lblVehculo.setBounds(10, 254, 46, 14);
		getContentPane().add(lblVehculo);

		btnBuscarVehculo = new JButton("Buscar");
		btnBuscarVehculo.setBounds(10, 279, 75, 23);
		getContentPane().add(btnBuscarVehculo);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 308, 4);
		getContentPane().add(separator);

		JLabel lblDatosPersonales = new JLabel("Datos Personales");
		lblDatosPersonales.setBounds(10, 11, 124, 14);
		getContentPane().add(lblDatosPersonales);

		JLabel lblDatosDelVehculo = new JLabel("Datos del Veh\u00EDculo");
		lblDatosDelVehculo.setBounds(10, 216, 124, 14);
		getContentPane().add(lblDatosDelVehculo);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 239, 308, 4);
		getContentPane().add(separator_1);

		txtPatente = new JTextField();
		txtPatente.setEditable(false);
		txtPatente.setBounds(62, 251, 246, 20);
		getContentPane().add(txtPatente);
		txtPatente.setColumns(10);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(177, 331, 84, 23);
		getContentPane().add(btnCancelar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(86, 331, 84, 23);
		getContentPane().add(btnAceptar);

		lblVlidoHasta = new JLabel("v\u00E1lido hasta:");
		lblVlidoHasta.setBounds(10, 163, 75, 14);
		getContentPane().add(lblVlidoHasta);

		UtilCalendarModel model = new UtilCalendarModel();
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);

		vencimientoRegistro = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SpringLayout springLayout = (SpringLayout) vencimientoRegistro.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, vencimientoRegistro.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, vencimientoRegistro);
		vencimientoRegistro.setEnabled(false);
		vencimientoRegistro.setBounds(86, 163, 101, 32);
		getContentPane().add(vencimientoRegistro);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(10, 331, 101, 23);
		getContentPane().add(btnActualizar);
		this.btnActualizar.setVisible(false);
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

	public JDatePickerImpl getVencimientoRegistro() {
		return vencimientoRegistro;
	}

	public void setVencimientoRegistro(JDatePickerImpl vencimientoRegistro) {
		this.vencimientoRegistro = vencimientoRegistro;
	}

	public String getTxtRegistro() {
		return txtRegistro.getText();
	}

	public void setTxtRegistro(String txtRegistro) {
		this.txtRegistro.setText(txtRegistro);
	}

	public String getTxtNombre() {
		return txtNombre.getText();
	}

	public void setTxtNombre(String txtNombre) {
		this.txtNombre.setText(txtNombre);
	}

	public String getTxtApellido() {
		return txtApellido.getText();
	}

	public void setTxtApellido(String txtApellido) {
		this.txtApellido.setText(txtApellido);
	}

	public String getTxtCelular() {
		return txtCelular.getText();
	}

	public void setTxtCelular(String txtCelular) {
		this.txtCelular.setText(txtCelular);
	}

	public JTextField getTxtPatente() {
		return txtPatente;
	}

	public void setTxtPatente(String txtPatente) {
		this.txtPatente.setText(txtPatente);
	}

	public JButton getBtnAceptar() {
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

	public JButton getBtnNuevoVechculo() {
		return btnNuevoVechculo;
	}

	public void setBtnNuevoVechculo(JButton btnNuevoVechculo) {
		this.btnNuevoVechculo = btnNuevoVechculo;
	}

	public JButton getBtnBuscarVehculo() {
		return btnBuscarVehculo;
	}

	public void setBtnBuscarVehculo(JButton btnBuscarVehculo) {
		this.btnBuscarVehculo = btnBuscarVehculo;
	}

	public JButton getBtnActualizar() {
		return btnActualizar;
	}

	public void setBtnActualizar(JButton btnActualizar) {
		this.btnActualizar = btnActualizar;
	}

	public void mostrarActualizar() {
		this.btnActualizar.setVisible(true);
		this.btnAceptar.setVisible(false);
	}
	
	public void bloquearCampos() {
		this.txtNombre.setEditable(false);
		this.txtApellido.setEditable(false);
		this.txtCelular.setEditable(false);
		this.txtRegistro.setEditable(false);
		this.txtPatente.setEditable(false);
		this.vencimientoRegistro.setTextEditable(false);
		this.btnCancelar.setText("OK");
		this.getBtnAceptar().setVisible(false);
		this.btnActualizar.setVisible(false);
		this.btnBuscarVehculo.setVisible(false);
		this.btnNuevoVechculo.setVisible(false);
		
		// TODO que traiga la fecha de vecnimiento del registro
		this.vencimientoRegistro.setVisible(false);
		this.lblVlidoHasta.setVisible(false);
		
	}
}
