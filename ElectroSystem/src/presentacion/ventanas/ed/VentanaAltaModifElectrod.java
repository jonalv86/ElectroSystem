package presentacion.ventanas.ed;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.MarcaDTO;
import helpers.Validador;

public class VentanaAltaModifElectrod extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfModelo;
	private JTextField tfNombre;
	private JComboBox<MarcaDTO> comboMarca;
	private JButton btnAgregarMarca;
	private JButton btnAgregar;
	private JButton btnCancelar;

	public VentanaAltaModifElectrod(JDialog padre) {
		super(padre, true);
		go();
	}
	

	public VentanaAltaModifElectrod(JFrame padre) {
		super(padre, true);
		go();
	}
	
	private void go() {
		setTitle("Agregar nuevo electrodom\u00E9stico");
		setBounds(100, 100, 450, 258);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		comboMarca = new JComboBox<MarcaDTO>();
		comboMarca.setBounds(80, 37, 195, 20);
		contentPanel.add(comboMarca);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(10, 40, 46, 14);
		contentPanel.add(lblMarca);
		
		btnAgregarMarca = new JButton("Agregar marca");
		btnAgregarMarca.setBounds(285, 36, 139, 23);
		contentPanel.add(btnAgregarMarca);
		
		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(10, 81, 71, 14);
		contentPanel.add(lblModelo);
		
		JLabel lblDescripcin = new JLabel("Nombre:");
		lblDescripcin.setBounds(10, 122, 71, 14);
		contentPanel.add(lblDescripcin);
		
		tfModelo = new JTextField();
		tfModelo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (tfModelo.getText().length()>20 || !Validador.caracterCodigoValido((e.getKeyChar())))
					e.consume();
			}
		});
		tfModelo.setBounds(80, 78, 145, 20);
		contentPanel.add(tfModelo);
		tfModelo.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (tfNombre.getText().length()>59)
					e.consume();
			}
		});
		tfNombre.setBounds(80, 119, 145, 20);
		contentPanel.add(tfNombre);
		tfNombre.setColumns(10);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(335, 186, 89, 23);
		contentPanel.add(btnCancelar);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(236, 186, 89, 23);
		contentPanel.add(btnAgregar);
	}

	public JTextField getTfModelo() {
		return tfModelo;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JComboBox<MarcaDTO> getComboMarca() {
		return comboMarca;
	}

	public JButton getBtnAgregarMarca() {
		return btnAgregarMarca;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
}
