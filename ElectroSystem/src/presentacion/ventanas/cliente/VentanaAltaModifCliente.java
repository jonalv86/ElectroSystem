package presentacion.ventanas.cliente;

import java.awt.Color;
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

import dto.LocalidadDTO;
import helpers.Validador;

public class VentanaAltaModifCliente extends JDialog {

	private JPanel contentPane;
	private JTextField tfNombre;
	private JTextField tfTelefono;
	private JTextField tfMail;
	private JButton btnCancelar;
	private JButton btnAlta;
	private JLabel lblApellido;
	private JLabel lblDireccin;
	private JLabel lblLocalidad;
	private JLabel lblProvincia;
	private JTextField tfApellido;
	private JTextField tfDireccion;
	private JTextField tfProvincia;
	private JLabel lblAdvertenciaMail;
	private JButton btnCrearOt;
	private JButton btnNuevaLocalidad;
	private JComboBox<LocalidadDTO> comboLocalidades;
	private JTextField txtLocalidad;

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaAltaModifCliente(JDialog padre) {
		super(padre, true);
		go();
	}

	public VentanaAltaModifCliente(JFrame padre) {
		super(padre, true);
		go();
	}

	private void go() {
		setBounds(100, 100, 481, 384);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(27, 25, 173, 14);
		contentPane.add(lblNombre);

		JLabel lblTelfono = new JLabel("Tel\u00E9fono:");
		lblTelfono.setBounds(27, 209, 173, 14);
		contentPane.add(lblTelfono);

		JLabel lblCorreoElectronico = new JLabel("Correo electr\u00F3nico:");
		lblCorreoElectronico.setBounds(27, 240, 173, 14);
		contentPane.add(lblCorreoElectronico);

		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfNombre.setBounds(210, 22, 238, 20);
		contentPane.add(tfNombre);
		tfNombre.setColumns(10);

		tfTelefono = new JTextField();
		tfTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterTelefonoValido(e.getKeyChar()) || tfTelefono.getText().length() > 19)
					e.consume();
			}
		});
		tfTelefono.setColumns(10);
		tfTelefono.setBounds(210, 206, 238, 20);
		contentPane.add(tfTelefono);

		tfMail = new JTextField();
		tfMail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterMailValido(e.getKeyChar()) || tfMail.getText().length() > 29)
					e.consume();
			}
		});
		tfMail.setColumns(10);
		tfMail.setBounds(210, 237, 238, 20);
		contentPane.add(tfMail);

		btnAlta = new JButton("Dar de alta");
		btnAlta.setBounds(210, 312, 128, 23);
		contentPane.add(btnAlta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(348, 312, 100, 23);
		contentPane.add(btnCancelar);

		lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(27, 56, 173, 14);
		contentPane.add(lblApellido);

		lblDireccin = new JLabel("Direcci\u00F3n:");
		lblDireccin.setBounds(27, 86, 173, 14);
		contentPane.add(lblDireccin);

		lblLocalidad = new JLabel("Localidad:");
		lblLocalidad.setBounds(27, 117, 173, 14);
		contentPane.add(lblLocalidad);

		lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(27, 148, 173, 14);
		contentPane.add(lblProvincia);

		tfApellido = new JTextField();
		tfApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfApellido.setColumns(10);
		tfApellido.setBounds(210, 53, 238, 20);
		contentPane.add(tfApellido);

		tfDireccion = new JTextField();
		tfDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfDireccion.setColumns(10);
		tfDireccion.setBounds(210, 83, 238, 20);
		contentPane.add(tfDireccion);

		tfProvincia = new JTextField();
		tfProvincia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfProvincia.setColumns(10);
		tfProvincia.setBounds(210, 145, 238, 20);
		contentPane.add(tfProvincia);

		lblAdvertenciaMail = new JLabel("Formato de mail inv\u00E1lido.");
		lblAdvertenciaMail.setForeground(Color.RED);
		lblAdvertenciaMail.setBounds(210, 268, 155, 14);
		lblAdvertenciaMail.setVisible(false);
		contentPane.add(lblAdvertenciaMail);

		btnCrearOt = new JButton("Crear OT");
		btnCrearOt.setBounds(10, 312, 104, 23);
		btnCrearOt.setVisible(false);
		contentPane.add(btnCrearOt);

		comboLocalidades = new JComboBox<LocalidadDTO>();
		comboLocalidades.setBounds(210, 114, 100, 20);
		contentPane.add(comboLocalidades);

		btnNuevaLocalidad = new JButton("Nueva Localidad");
		btnNuevaLocalidad.setBounds(320, 113, 128, 23);
		contentPane.add(btnNuevaLocalidad);

		txtLocalidad = new JTextField();
		txtLocalidad.setEditable(false);
		txtLocalidad.setBounds(210, 114, 238, 20);
		contentPane.add(txtLocalidad);
		txtLocalidad.setColumns(10);
		this.txtLocalidad.setVisible(false);
	}

	public JLabel getLblAdvertenciaMail() {
		return lblAdvertenciaMail;
	}

	public JTextField getTfApellido() {
		return tfApellido;
	}

	public JButton getBtnCrearOt() {
		return btnCrearOt;
	}

	public JTextField getTfDireccion() {
		return tfDireccion;
	}

	// public JTextField getTfLocalidad() {
	// return tfLocalidad;
	// }

	public JTextField getTfProvincia() {
		return tfProvincia;
	}

	// public JTextField getTfCodPostal() {
	// return tfCodPostal;
	// }

	public JButton getBtnAlta() {
		return btnAlta;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JTextField getTfTelefono() {
		return tfTelefono;
	}

	public JTextField getTfMail() {
		return tfMail;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnNuevaLocalidad() {
		return btnNuevaLocalidad;
	}

	public void setBtnNuevaLocalidad(JButton btnNuevaLocalidad) {
		this.btnNuevaLocalidad = btnNuevaLocalidad;
	}

	public JComboBox<LocalidadDTO> getComboLocalidades() {
		return comboLocalidades;
	}

	public void setComboLocalidades(LocalidadDTO localidad) {
		this.comboLocalidades.getModel().setSelectedItem(localidad);
	}

	public JTextField getTxtLocalidad() {
		return txtLocalidad;
	}

	public void setTxtLocalidad(JTextField txtLocalidad) {
		this.txtLocalidad = txtLocalidad;
	}

	public JLabel getLblDireccin() {
		return lblDireccin;
	}

	public void setLblDireccin(String lblDireccin) {
		this.lblDireccin.setText(lblDireccin);
	}
	
	

}
