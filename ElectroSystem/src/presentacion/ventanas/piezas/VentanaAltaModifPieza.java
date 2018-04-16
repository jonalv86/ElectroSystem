package presentacion.ventanas.piezas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import dto.MarcaDTO;
import helpers.Validador;

public class VentanaAltaModifPieza extends JDialog {

	private static final long serialVersionUID = -4116766714181222460L;

	private JPanel contentPane;
	private JButton btnCancelar;
	private JButton btnAlta;
	private JLabel lblDireccin;
	private JLabel lblLocalidad;
	private JLabel lblProvincia;
	private JTextField tfDescripcionMarca;
	private JTextField tfPrecioVenta;
	private JComboBox<MarcaDTO> cboMarcas;
	private JButton btnNuevaMarca;
	private JSpinner spinnerBajoStock;
	private JLabel lblBajoStock;
	private JTextField tfIdUnico;
	private JLabel lblIdentificadornico;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public VentanaAltaModifPieza(JFrame padre) {
		super(padre, true);
		setBounds(100, 100, 437, 304);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAlta = new JButton("Dar de alta");
		btnAlta.setBounds(183, 242, 139, 23);
		contentPane.add(btnAlta);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(332, 242, 89, 23);
		contentPane.add(btnCancelar);

		lblDireccin = new JLabel("Marca:");
		lblDireccin.setBounds(27, 102, 99, 14);
		contentPane.add(lblDireccin);

		lblLocalidad = new JLabel("Descripcion: ");
		lblLocalidad.setBounds(27, 133, 99, 14);
		contentPane.add(lblLocalidad);
		
		lblBajoStock = new JLabel("Bajo stock:");
		lblBajoStock.setBounds(27, 194, 99, 14);
		contentPane.add(lblBajoStock);

		lblProvincia = new JLabel("Precio Venta:");
		lblProvincia.setBounds(27, 164, 99, 14);
		contentPane.add(lblProvincia);

		tfDescripcionMarca = new JTextField();
		tfDescripcionMarca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfDescripcionMarca.getText().length() > 19) {
					e.consume();
				}
			}
		});
		tfDescripcionMarca.setColumns(10);
		tfDescripcionMarca.setBounds(133, 130, 236, 20);
		contentPane.add(tfDescripcionMarca);

		tfPrecioVenta = new JTextField();
		tfPrecioVenta.select(tfPrecioVenta.getText().length(), 0);
		tfPrecioVenta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.esNumero(e.getKeyChar()) || tfPrecioVenta.getText().length()>19 ){ 
					e.consume();
				}
				if (tfPrecioVenta.getText().length()>=0) {
					tfPrecioVenta.setText(Validador.armarPrecioValido(tfPrecioVenta.getText()));
				}
				tfPrecioVenta.select(tfPrecioVenta.getText().length(), 0);
			}
		});
		tfPrecioVenta.setColumns(10);
		tfPrecioVenta.setBounds(136, 161, 113, 20);
		contentPane.add(tfPrecioVenta);

		cboMarcas = new JComboBox<MarcaDTO>();
		cboMarcas.setBounds(133, 99, 163, 20);
		contentPane.add(cboMarcas);

		btnNuevaMarca = new JButton("A\u00F1adir marca");
		btnNuevaMarca.setBounds(306, 98, 115, 23);
		contentPane.add(btnNuevaMarca);
		
		spinnerBajoStock = new JSpinner();
		spinnerBajoStock.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerBajoStock.setBounds(133, 191, 49, 20);
		contentPane.add(spinnerBajoStock);
		
		tfIdUnico = new JTextField();
		tfIdUnico.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.esLetra(e.getKeyChar()) && !Validador.esNumero(e.getKeyChar()) || tfIdUnico.getText().length()>7)
					e.consume();
			}
		});
		tfIdUnico.setBounds(183, 68, 86, 20);
		contentPane.add(tfIdUnico);
		tfIdUnico.setColumns(10);
		
		lblIdentificadornico = new JLabel("Identificador \u00FAnico:");
		lblIdentificadornico.setBounds(27, 71, 113, 14);
		contentPane.add(lblIdentificadornico);
	}

	public JLabel getLblBajoStock() {
		return lblBajoStock;
	}

	public JButton getBtnNuevaMarca() {
		return btnNuevaMarca;
	}

	public JTextField getTfIdUnico() {
		return tfIdUnico;
	}

	public JComboBox<MarcaDTO> getCboMarcas() {
		return cboMarcas;
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

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JSpinner getSpinnerBajoStock() {
		return spinnerBajoStock;
	}
}
