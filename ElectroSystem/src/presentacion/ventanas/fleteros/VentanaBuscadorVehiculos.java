package presentacion.ventanas.fleteros;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dto.VehiculoDTO;

@SuppressWarnings("serial")
public class VentanaBuscadorVehiculos extends JDialog {

	private JPanel panel;
	private JComboBox<String> cbBuscarPor;
	private JTextField txtDatos;
	private JLabel lblResultados;
	private JTable table;
	private JScrollPane spDatosTabla;
	private JButton btnBuscar;
	private JButton btnListo;
	private JLabel lblSeleccionarVehiculo;

	private VehiculoDTO vehiculoElegido;

	public VentanaBuscadorVehiculos(JDialog padre) {
		
		super(padre, true);
		setTitle("Buscar Vehículo");
		setBounds(100, 100, 480, 334);
		setResizable(false);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 480, 261);
		this.getContentPane().add(panel);
		panel.setLayout(null);

		table = new JTable();
		table.setBounds(10, 82, 414, 166);

		spDatosTabla = new JScrollPane(table);
		spDatosTabla.setBounds(10, 81, 455, 170);
		panel.add(spDatosTabla);
		spDatosTabla.setVisible(false);

		JLabel lblBsquedaPor = new JLabel("Búsqueda según ");
		lblBsquedaPor.setBounds(10, 11, 89, 14);
		panel.add(lblBsquedaPor);

		cbBuscarPor = new JComboBox<String>();
		cbBuscarPor.setBounds(94, 8, 74, 20);
		cbBuscarPor.addItem("Patente");
		cbBuscarPor.addItem("Marca");
		cbBuscarPor.addItem("Modelo");
		panel.add(cbBuscarPor);

		txtDatos = new JTextField();
		txtDatos.setBounds(178, 8, 188, 20);
		panel.add(txtDatos);
		txtDatos.setColumns(10);

		lblResultados = new JLabel("Resultados:");
		lblResultados.setBounds(10, 56, 89, 14);
		panel.add(lblResultados);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(376, 7, 89, 23);
		panel.add(btnBuscar);

		lblSeleccionarVehiculo = new JLabel("(seleccionar un veh\u00EDculo");
		lblSeleccionarVehiculo.setForeground(Color.RED);
		lblSeleccionarVehiculo.setBounds(83, 56, 147, 14);
		panel.add(lblSeleccionarVehiculo);
		lblSeleccionarVehiculo.setVisible(false);

		btnListo = new JButton("Listo");
		btnListo.setBounds(375, 271, 89, 23);
		getContentPane().add(btnListo);

	}

	public void mostrarTabla() {
		this.spDatosTabla.setVisible(true);
	}

	public void mostrarLblSeleccionar() {
		this.lblSeleccionarVehiculo.setVisible(true);
	}

	public JTextField getTxtDatos() {
		return txtDatos;
	}

	public void setTxtDatos(JTextField txtDatos) {
		this.txtDatos = txtDatos;
	}

	public JComboBox<String> getComboBox() {
		return cbBuscarPor;
	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.cbBuscarPor = comboBox;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JScrollPane getSpDatosTabla() {
		return spDatosTabla;
	}

	public void setSpDatosTabla(JScrollPane spDatosTabla) {
		this.spDatosTabla = spDatosTabla;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(JButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public JButton getBtnListo() {
		return btnListo;
	}

	public void setBtnListo(JButton btnListo) {
		this.btnListo = btnListo;
	}

	public VehiculoDTO getVehiculoElegido() {
		return vehiculoElegido;
	}

	public void setVehiculoElegido(VehiculoDTO vehiculoElegido) {
		this.vehiculoElegido = vehiculoElegido;
	}

}
