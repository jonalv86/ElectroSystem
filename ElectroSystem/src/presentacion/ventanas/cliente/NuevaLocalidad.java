package presentacion.ventanas.cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.ZonaDTO;

public class NuevaLocalidad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCP;
	private JTextField txtNombre;
	private JComboBox<ZonaDTO> comboBox;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public NuevaLocalidad(JDialog padre) {
		super(padre, true);

		setTitle("Nueva Localidad");
		setBounds(100, 100, 265, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblCdigoPostal = new JLabel("C\u00F3digo Postal:");
		lblCdigoPostal.setBounds(10, 11, 86, 14);
		contentPanel.add(lblCdigoPostal);

		txtCP = new JTextField();
		txtCP.setBounds(106, 8, 133, 20);
		contentPanel.add(txtCP);
		txtCP.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 42, 86, 14);
		contentPanel.add(lblNombre);

		JLabel lblZona = new JLabel("Zona:");
		lblZona.setBounds(10, 73, 86, 14);
		contentPanel.add(lblZona);

		txtNombre = new JTextField();
		txtNombre.setBounds(106, 39, 133, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		comboBox = new JComboBox();
		comboBox.setBounds(106, 70, 133, 20);
		contentPanel.add(comboBox);
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
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public int getTxtCP() {
		return Integer.parseInt(this.txtCP.getText().toString());
	}

	public void setTxtCP(JTextField txtCP) {
		this.txtCP = txtCP;
	}

	public String getTxtNombre() {
		return txtNombre.getText().toString();
	}

	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}

	public JComboBox<ZonaDTO> getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox<ZonaDTO> comboBox) {
		this.comboBox = comboBox;
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

}
