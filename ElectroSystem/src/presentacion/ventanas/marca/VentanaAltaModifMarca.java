package presentacion.ventanas.marca;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import helpers.Validador;

public class VentanaAltaModifMarca extends JDialog {

	private static final long serialVersionUID = -954582607576612263L;
	private JTextField tfNombre;
	private JButton btnAgregar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentanaAltaModifMarca dialog = new VentanaAltaModifMarca(null);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public VentanaAltaModifMarca(JDialog padre) {
		super(padre, true);
		setTitle("Nueva marca");
		setBounds(100, 100, 316, 148);
		setResizable(false);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(21, 43, 66, 14);
		getContentPane().add(lblNombre);

		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfNombre.getText().length() > 39) {
					e.consume();
				}
			}
		});
		tfNombre.setBounds(97, 40, 141, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(107, 71, 89, 23);
		getContentPane().add(btnAgregar);

	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}
}
