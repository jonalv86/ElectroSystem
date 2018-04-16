package presentacion.ventanas.cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.ZonaDTO;

public class VentanaZonas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDescripcion;
	private JTextField txtPrecio;
	private JButton okButton;
	private JButton cancelButton;

	private JComboBox<ZonaDTO> comboZonas;

	public VentanaZonas(JFrame padre) {

		super(padre, true);
		setTitle("Editar Zonas");
		setBounds(100, 100, 256, 265);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblZona = new JLabel("Zona:");
			lblZona.setBounds(10, 26, 46, 14);
			contentPanel.add(lblZona);
		}
		{
			comboZonas = new JComboBox<ZonaDTO>();
			comboZonas.setBounds(66, 23, 114, 20);
			contentPanel.add(comboZonas);
		}
		{
			JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
			lblDescripcin.setBounds(10, 74, 80, 14);
			contentPanel.add(lblDescripcin);
		}
		{
			JLabel lblPrecio = new JLabel("Precio:");
			lblPrecio.setBounds(10, 102, 80, 14);
			contentPanel.add(lblPrecio);
		}
		{
			txtDescripcion = new JTextField();
			txtDescripcion.setBounds(81, 71, 149, 20);
			contentPanel.add(txtDescripcion);
			txtDescripcion.setColumns(10);
		}
		{
			txtPrecio = new JTextField();
			txtPrecio.setBounds(81, 99, 86, 20);
			contentPanel.add(txtPrecio);
			txtPrecio.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Guardar cambios");
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

	public JTextField getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(JTextField txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
	}

	public JTextField getTxtPrecio() {
		return txtPrecio;
	}

	public void setTxtPrecio(JTextField txtPrecio) {
		this.txtPrecio = txtPrecio;
	}

	public ZonaDTO getZona() {
		return (ZonaDTO) comboZonas.getSelectedItem();
	}

	public void setZonas(List<ZonaDTO> zonas) {
		for (int i = 0; i < zonas.size(); i++) {
			this.comboZonas.addItem(zonas.get(i));
		}
	}

	public JComboBox<ZonaDTO> getComboZonas() {
		return comboZonas;
	}

	public void setComboZonas(JComboBox<ZonaDTO> comboZonas) {
		this.comboZonas = comboZonas;
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
