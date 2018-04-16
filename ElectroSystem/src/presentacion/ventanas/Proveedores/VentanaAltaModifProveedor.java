package presentacion.ventanas.Proveedores;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import helpers.Validador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class VentanaAltaModifProveedor extends JDialog {
	private JTextField tfNombre;
	private JTextField tfCuit;
	private JTextField tfTelefono;
	private JTextField tfMail;
	private JTable tableSi;
	private JTable tableNo;
	private JButton btnAnadir;
	private JButton btnQuitar;
	private JButton btnAnadirTodos;
	private JButton btnQuitarTodos;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JButton btnAnadirMarca;
	private JLabel lblFormatoDeMail;
	private JLabel lblNombreObligatorio;
	private JLabel lblCuitObligatorio;
	private JTextField tfContacto;
	private JLabel lblContactoObligatorio;

	public VentanaAltaModifProveedor(JFrame padre) {
		super(padre, true);
		setBounds(100, 100, 620, 531);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 217, 222, 231);
		getContentPane().add(scrollPane);
		
		tableSi = new JTable();
		tableSi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableSi);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(372, 217, 222, 231);
		getContentPane().add(scrollPane_1);
		
		tableNo = new JTable();
		tableNo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(tableNo);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(505, 459, 89, 23);
		getContentPane().add(btnCancelar);
		
		btnCrear = new JButton("Crear");
		btnCrear.setBounds(406, 459, 89, 23);
		getContentPane().add(btnCrear);
		
		btnAnadir = new JButton("A\u00F1adir");
		btnAnadir.setBounds(259, 229, 89, 23);
		getContentPane().add(btnAnadir);
		
		btnQuitar = new JButton("Quitar");
		btnQuitar.setBounds(259, 263, 89, 23);
		getContentPane().add(btnQuitar);
		
		btnAnadirTodos = new JButton("A\u00F1adir todos");
		btnAnadirTodos.setBounds(242, 297, 120, 23);
		getContentPane().add(btnAnadirTodos);
		
		btnQuitarTodos = new JButton("QuitarTodos");
		btnQuitarTodos.setBounds(242, 331, 120, 23);
		getContentPane().add(btnQuitarTodos);
		
		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacioNumero(e.getKeyChar()) || tfNombre.getText().length()>19)
					e.consume();
			}
		});
		tfNombre.setBounds(128, 42, 222, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		
		tfCuit = new JTextField();
		tfCuit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.esNumero(e.getKeyChar()) || tfCuit.getText().length()>10)
					e.consume();
			}
		});
		tfCuit.setBounds(128, 73, 220, 20);
		getContentPane().add(tfCuit);
		tfCuit.setColumns(10);
		
		tfTelefono = new JTextField();
		tfTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterTelefonoValido(e.getKeyChar()) || tfTelefono.getText().length()>19)
					e.consume();
			}
		});
		tfTelefono.setBounds(128, 104, 220, 20);
		getContentPane().add(tfTelefono);
		tfTelefono.setColumns(10);
		
		tfMail = new JTextField();
		tfMail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterMailValido(e.getKeyChar()) || tfMail.getText().length()>29)
					e.consume();
			}
		});
		tfMail.setBounds(128, 135, 220, 20);
		getContentPane().add(tfMail);
		tfMail.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 45, 89, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblCuit = new JLabel("CUIT:");
		lblCuit.setBounds(10, 76, 89, 14);
		getContentPane().add(lblCuit);
		
		JLabel lblTelfono = new JLabel("Tel\u00E9fono:");
		lblTelfono.setBounds(10, 107, 89, 14);
		getContentPane().add(lblTelfono);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(10, 138, 89, 14);
		getContentPane().add(lblMail);
		
		btnAnadirMarca = new JButton("A\u00F1adir marca");
		btnAnadirMarca.setBounds(242, 404, 120, 23);
		getContentPane().add(btnAnadirMarca);
		
		lblFormatoDeMail = new JLabel("Formato de mail no v\u00E1lido.");
		lblFormatoDeMail.setForeground(Color.RED);
		lblFormatoDeMail.setVisible(false);
		lblFormatoDeMail.setBounds(372, 138, 222, 14);
		getContentPane().add(lblFormatoDeMail);
		
		lblNombreObligatorio = new JLabel("Campo obligatorio.");
		lblNombreObligatorio.setForeground(Color.RED);
		lblNombreObligatorio.setVisible(false);
		lblNombreObligatorio.setBounds(372, 45, 123, 14);
		getContentPane().add(lblNombreObligatorio);
		
		lblCuitObligatorio = new JLabel("<html>Campo obligatorio.<br>El CUIT debe tener 11 caracteres.</html>");
		lblCuitObligatorio.setVerticalAlignment(SwingConstants.TOP);
		lblCuitObligatorio.setForeground(Color.RED);
		lblCuitObligatorio.setVisible(false);
		lblCuitObligatorio.setBounds(372, 76, 222, 34);
		getContentPane().add(lblCuitObligatorio);
		
		tfContacto = new JTextField();
		tfContacto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length()>19)
					e.consume();
			}
		});
		tfContacto.setBounds(128, 166, 220, 20);
		getContentPane().add(tfContacto);
		tfContacto.setColumns(10);
		
		JLabel lblContacto = new JLabel("Contacto:");
		lblContacto.setBounds(10, 169, 89, 14);
		getContentPane().add(lblContacto);
		
		lblContactoObligatorio = new JLabel("Campo obligatorio.");
		lblContactoObligatorio.setForeground(Color.RED);
		lblContactoObligatorio.setBounds(372, 169, 197, 14);
		getContentPane().add(lblContactoObligatorio);
		lblContactoObligatorio.setVisible(false);
		

	}

	public JLabel getLblContactoObligatorio() {
		return lblContactoObligatorio;
	}

	public JTextField getTfContacto() {
		return tfContacto;
	}

	public JLabel getLblNombreObligatorio() {
		return lblNombreObligatorio;
	}

	public JLabel getLblCuitObligatorio() {
		return lblCuitObligatorio;
	}

	public JLabel getLblFormatoDeMail() {
		return lblFormatoDeMail;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JTextField getTfCuit() {
		return tfCuit;
	}

	public JTextField getTfTelefono() {
		return tfTelefono;
	}

	public JTextField getTfMail() {
		return tfMail;
	}

	public JTable getTableSi() {
		return tableSi;
	}

	public JTable getTableNo() {
		return tableNo;
	}

	public JButton getBtnAnadir() {
		return btnAnadir;
	}

	public JButton getBtnQuitar() {
		return btnQuitar;
	}

	public JButton getBtnAnadirTodos() {
		return btnAnadirTodos;
	}

	public JButton getBtnQuitarTodos() {
		return btnQuitarTodos;
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnAnadirMarca(JButton btnAnadirMarca) {
		this.btnAnadirMarca = btnAnadirMarca;
	}

	public JButton getBtnAnadirMarca() {
		return btnAnadirMarca;
	}
}
