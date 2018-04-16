package presentacion.ventanas.fleteros;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

import dto.EstadoDTO;

@SuppressWarnings("serial")
public class VentanaNuevoVehiculo extends JDialog {
	private JLabel lblFechaDeVencimiento;
	private JTextField txtPatente;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JRadioButton rdbtnSi;
	private JRadioButton rdbtnNo;
	private ButtonGroup grupo;
	private JSpinner spinnerCapacidad;
	private JComboBox<EstadoDTO> comboEstado;
	private JButton btnCancelar;
	private JButton btnAceptar;

	private JDatePickerImpl vencimientoVTV;
	private JDatePickerImpl vencimientoOblea;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 * 
	 * @param padre
	 */
	public VentanaNuevoVehiculo(JDialog padre) {
		super(padre, true);
		setTitle("Nuevo Veh\u00EDculo");
		setBounds(100, 100, 352, 323);
		setResizable(false);
		getContentPane().setLayout(null);
		{
			JLabel lblPatente = new JLabel("Patente:");
			lblPatente.setBounds(10, 11, 46, 14);
			getContentPane().add(lblPatente);
		}
		{
			JLabel lblMarca = new JLabel("Marca:");
			lblMarca.setBounds(10, 39, 46, 14);
			getContentPane().add(lblMarca);
		}
		{
			JLabel lblModelo = new JLabel("Modelo:");
			lblModelo.setBounds(168, 39, 71, 14);
			getContentPane().add(lblModelo);
		}
		{
			JLabel lblCapacidadDeCarga = new JLabel("Capacidad de carga:");
			lblCapacidadDeCarga.setBounds(10, 70, 118, 14);
			getContentPane().add(lblCapacidadDeCarga);
		}
		{
			JLabel lblKgs = new JLabel("kgs.");
			lblKgs.setBounds(185, 67, 29, 14);
			getContentPane().add(lblKgs);
		}
		{
			JLabel lblFechaVencimientoVtv = new JLabel("Fecha vencimiento VTV:");
			lblFechaVencimientoVtv.setBounds(10, 103, 141, 14);
			getContentPane().add(lblFechaVencimientoVtv);
		}
		{
			JLabel lblOblea = new JLabel("Oblea:");
			lblOblea.setBounds(10, 138, 46, 14);
			getContentPane().add(lblOblea);
		}

		rdbtnSi = new JRadioButton("s\u00ED");
		rdbtnSi.setBounds(62, 134, 38, 23);
		getContentPane().add(rdbtnSi);

		rdbtnNo = new JRadioButton("no");
		rdbtnNo.setBounds(113, 134, 38, 23);
		getContentPane().add(rdbtnNo);

		grupo = new ButtonGroup();
		grupo.add(rdbtnSi);
		grupo.add(rdbtnNo);

		lblFechaDeVencimiento = new JLabel("Fecha de vencimiento:");
		lblFechaDeVencimiento.setBounds(10, 175, 141, 14);
		getContentPane().add(lblFechaDeVencimiento);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(10, 210, 46, 14);
		getContentPane().add(lblEstado);

		txtPatente = new JTextField();
		txtPatente.setBounds(66, 8, 86, 20);
		txtPatente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtPatente.getText().length() > 5) {
					e.consume();
				}
			}
		});
		getContentPane().add(txtPatente);
		txtPatente.setColumns(10);

		txtMarca = new JTextField();
		txtMarca.setBounds(66, 36, 86, 20);
		getContentPane().add(txtMarca);
		txtMarca.setColumns(10);

		txtModelo = new JTextField();
		txtModelo.setBounds(249, 36, 86, 20);
		getContentPane().add(txtModelo);
		txtModelo.setColumns(10);

		SpinnerModel smCapacidad = new SpinnerNumberModel(1, 1, 100, 1);
		spinnerCapacidad = new JSpinner(smCapacidad);
		spinnerCapacidad.setBounds(142, 64, 38, 20);
		getContentPane().add(spinnerCapacidad);

		comboEstado = new JComboBox<EstadoDTO>();
		comboEstado.setBounds(58, 207, 122, 20);
		getContentPane().add(comboEstado);
		comboEstado.addItem(new EstadoDTO(1, "operativo"));
		comboEstado.addItem(new EstadoDTO(2, "en reparación"));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(234, 260, 101, 23);
		getContentPane().add(btnCancelar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(123, 260, 101, 23);
		getContentPane().add(btnAceptar);

		UtilCalendarModel model = new UtilCalendarModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);

		vencimientoVTV = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		SpringLayout springLayout = (SpringLayout) vencimientoVTV.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, vencimientoVTV.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				vencimientoVTV);
		vencimientoVTV.setEnabled(false);
		vencimientoVTV.setBounds(152, 95, 118, 32);
		getContentPane().add(vencimientoVTV);

		UtilCalendarModel model2 = new UtilCalendarModel();
		Properties properties2 = new Properties();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, properties2);

		vencimientoOblea = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		SpringLayout springLayout2 = (SpringLayout) vencimientoOblea.getLayout();
		springLayout2.putConstraint(SpringLayout.SOUTH, vencimientoOblea.getJFormattedTextField(), 0,
				SpringLayout.SOUTH, vencimientoOblea);
		vencimientoOblea.setEnabled(false);
		vencimientoOblea.setBounds(152, 157, 118, 32);
		getContentPane().add(vencimientoOblea);

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

	public void mostrarOpciones() {
		if (rdbtnSi.isSelected()) {
			this.vencimientoOblea.setVisible(true);
			this.lblFechaDeVencimiento.setVisible(true);
		}
	}

	public void esconderOpciones() {
		if (rdbtnNo.isSelected()) {
			this.vencimientoOblea.setVisible(false);
			this.lblFechaDeVencimiento.setVisible(false);
		}
	}

	public String getTxtPatente() {
		return txtPatente.getText().toString();
	}

	public void setTxtPatente(String txtPatente) {
		this.txtPatente.setText(txtPatente);
	}

	public String getTxtMarca() {
		return txtMarca.getText().toString();
	}

	public void setTxtMarca(String txtMarca) {
		this.txtMarca.setText(txtMarca);
	}

	public String getTxtModelo() {
		return txtModelo.getText().toString();
	}

	public void setTxtModelo(String txtModelo) {
		this.txtModelo.setText(txtModelo);
	}

	public JRadioButton getRdbtnSi() {
		return rdbtnSi;
	}

	public void setRdbtnSi(JRadioButton rdbtnSi) {
		this.rdbtnSi = rdbtnSi;
	}

	public JRadioButton getRdbtnNo() {
		return rdbtnNo;
	}

	public void setRdbtnNo(JRadioButton rdbtnNo) {
		this.rdbtnNo = rdbtnNo;
	}

	public boolean conOblea() {

		if (rdbtnSi.isSelected())
			return true;
		else
			return false;

	}

	public void setGrupo(ButtonGroup grupo) {
		this.grupo = grupo;
	}

	public int getSpinnerCapacidad() {
		return (int) spinnerCapacidad.getValue();
	}

	public void setSpinnerCapacidad(int spinnerCapacidad) {
		this.spinnerCapacidad.setValue(spinnerCapacidad);
	}

	public EstadoDTO getComboEstado() {
		return (EstadoDTO) comboEstado.getSelectedItem();
	}

	public void setComboEstado(EstadoDTO estado) {
		this.comboEstado.setSelectedItem(estado);
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public void setVencimientoVTV(JDatePickerImpl vencimientoVTV) {
		this.vencimientoVTV = vencimientoVTV;
	}

	public void setVencimientoOblea(JDatePickerImpl vencimientoOblea) {
		this.vencimientoOblea = vencimientoOblea;
	}

	public JDatePickerImpl getVencimientoVTV() {
		return vencimientoVTV;
	}

	public JDatePickerImpl getVencimientoOblea() {
		return vencimientoOblea;
	}

	public void bloquear() {
		this.txtPatente.setEditable(false);
		this.txtMarca.setEditable(false);
		this.txtModelo.setEditable(false);
		this.comboEstado.setEnabled(false);
		this.rdbtnNo.setEnabled(false);
		this.rdbtnSi.setEnabled(false);
		this.vencimientoVTV.getComponent(1).setEnabled(false);
		this.vencimientoOblea.setEnabled(false);
		this.vencimientoOblea.getComponent(1).setEnabled(false);
		this.spinnerCapacidad.setEnabled(false);
	}
}
