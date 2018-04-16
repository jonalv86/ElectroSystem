package presentacion.ventanas.fleteros;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import dto.FleteroDTO;

@SuppressWarnings("serial")
public class VentanaAsignacion extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public JTable table;
	public JTable tableResultado;

	private JScrollPane spDatosTabla;
	private JScrollPane spDatosTablaResultado;

	private JComboBox<FleteroDTO> comboFleteros;

	private JButton okButton;
	private JButton cancelButton;

	private JLabel lblEnvosAsignadosA;

	public VentanaAsignacion() {
		setTitle("Asignaci\u00F3n env\u00EDos");
		setBounds(100, 100, 677, 382);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblFletero = new JLabel("Fletero");
		lblFletero.setBounds(10, 22, 64, 14);
		contentPanel.add(lblFletero);

		comboFleteros = new JComboBox<FleteroDTO>();
		comboFleteros.setBounds(62, 19, 121, 20);
		contentPanel.add(comboFleteros);

		table = new JTable();
		table.setBounds(10, 47, 287, 252);

		spDatosTabla = new JScrollPane(table);
		spDatosTabla.setBounds(10, 47, 400, 252);
		contentPanel.add(spDatosTabla);

		tableResultado = new JTable();
		tableResultado.setBounds(356, 47, 295, 252);

		spDatosTablaResultado = new JScrollPane(tableResultado);
		spDatosTablaResultado.setBounds(420, 47, 231, 252);
		contentPanel.add(spDatosTablaResultado);

		lblEnvosAsignadosA = new JLabel();
		lblEnvosAsignadosA.setBounds(420, 22, 231, 14);
		contentPanel.add(lblEnvosAsignadosA);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JComboBox<FleteroDTO> getComboFleteros() {
		return comboFleteros;
	}

	public void setComboFleteros(JComboBox<FleteroDTO> comboFleteros) {
		this.comboFleteros = comboFleteros;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JTable getTableResultado() {
		return tableResultado;
	}

	public void setTableResultado(JTable tableResultado) {
		this.tableResultado = tableResultado;
	}

	public JLabel getLblEnvosAsignadosA() {
		return lblEnvosAsignadosA;
	}

	public void setLblEnvosAsignadosA(String lblEnvosAsignadosA) {
		this.lblEnvosAsignadosA.setText(lblEnvosAsignadosA);
	}

}
