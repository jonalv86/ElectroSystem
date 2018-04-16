package presentacion.ventanas.piezas;

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

import helpers.Validador;

public class VentanaImportarPrecios extends JDialog {

	private static final long serialVersionUID = -4705006605964330581L;

	private JPanel contentPane;
	private JTextField tfIDPieza;
	private JButton btnCancelar;
	private JButton btnAlta;
	private JLabel lblIdMarca;
	private JLabel lblIdPieza;
	private JLabel lblDireccin;
	private JLabel lblLocalidad;
	private JLabel lblProvincia;
	private JTextField tfIDMarca;
	private JTextField tfNombreMarca;
	private JTextField tfDescripcionMarca;
	private JTextField tfPrecioVenta;
	private JComboBox cboMarcas;
	private JButton btnNuevaMarca;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public VentanaImportarPrecios(JFrame padre) {
		super(padre, true);
		setBounds(100, 100, 364, 304);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblIdPieza = new JLabel("ID Pieza:");
		lblIdPieza.setBounds(27, 25, 99, 14);
		contentPane.add(lblIdPieza);

		tfIDPieza = new JTextField();
		tfIDPieza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfIDPieza.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfIDPieza.setBounds(133, 22, 150, 20);
		contentPane.add(tfIDPieza);
		tfIDPieza.setColumns(10);

		btnAlta = new JButton("Dar de alta");
		btnAlta.setBounds(43, 219, 139, 23);
		contentPane.add(btnAlta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(199, 219, 89, 23);
		contentPane.add(btnCancelar);

		lblIdMarca = new JLabel("ID Marca:");
		lblIdMarca.setBounds(27, 56, 99, 14);
		contentPane.add(lblIdMarca);

		lblDireccin = new JLabel("Marca:");
		lblDireccin.setBounds(27, 86, 99, 14);
		contentPane.add(lblDireccin);

		lblLocalidad = new JLabel("Descripcion: ");
		lblLocalidad.setBounds(27, 117, 99, 14);
		contentPane.add(lblLocalidad);

		lblProvincia = new JLabel("Precio Venta:");
		lblProvincia.setBounds(27, 148, 99, 14);
		contentPane.add(lblProvincia);

		tfIDMarca = new JTextField();
		tfIDMarca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfIDPieza.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfIDMarca.setColumns(10);
		tfIDMarca.setBounds(133, 53, 150, 20);
		contentPane.add(tfIDMarca);

		tfNombreMarca = new JTextField();
		tfNombreMarca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfIDPieza.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfNombreMarca.setColumns(10);
		tfNombreMarca.setBounds(133, 83, 150, 20);
		contentPane.add(tfNombreMarca);

		tfDescripcionMarca = new JTextField();
		tfDescripcionMarca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfIDPieza.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfDescripcionMarca.setColumns(10);
		tfDescripcionMarca.setBounds(133, 114, 150, 20);
		contentPane.add(tfDescripcionMarca);

		tfPrecioVenta = new JTextField();
		tfPrecioVenta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				/*
				 * if (!Validador.esDouble(e.getKeyChar()) || e.getKeyChar()!='.') { e.consume(); }
				 */
			}
		});
		tfPrecioVenta.setColumns(10);
		tfPrecioVenta.setBounds(133, 145, 150, 20);
		contentPane.add(tfPrecioVenta);

		cboMarcas = new JComboBox();
		cboMarcas.setBounds(133, 83, 150, 20);
		contentPane.add(cboMarcas);

		btnNuevaMarca = new JButton("A\u00F1adir marca");
		btnNuevaMarca.setBounds(27, 21, 115, 23);
		contentPane.add(btnNuevaMarca);
	}

	public JButton getBtnNuevaMarca() {
		return btnNuevaMarca;
	}

	public JComboBox getCboMarcas() {
		return cboMarcas;
	}

	public void setCboMarcas(JComboBox cboMarcas) {
		this.cboMarcas = cboMarcas;
	}

	public JTextField getTfIDMarca() {
		return tfIDMarca;
	}

	public JTextField getTfNombreMarca() {
		return tfNombreMarca;
	}

	public JTextField getTfDescripcionMarca() {
		return tfDescripcionMarca;
	}

	public JTextField getTfPrecioVenta() {
		return tfPrecioVenta;
	}

	public JButton getBtnAlta() {
		return btnAlta;
	}

	public JTextField getTfIDPieza() {
		return tfIDPieza;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JLabel getLblIdMarca() {
		return lblIdMarca;
	}

	public JLabel getLblIdPieza() {
		return lblIdPieza;
	}
}
