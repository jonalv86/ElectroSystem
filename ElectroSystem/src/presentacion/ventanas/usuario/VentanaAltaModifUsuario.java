package presentacion.ventanas.usuario;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import helpers.Validador;

import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JSeparator;
import java.awt.Color;

public class VentanaAltaModifUsuario extends JDialog {
	private JTextField tfNombre;
	private JTextField tfApellido;
	private JTextField tfUsuario;
	private JPasswordField pfPass;
	private JPasswordField pfRepitaPass;
	private JRadioButton rbAdm;
	private JRadioButton rbTec;
	private JButton btnCrear;
	private JButton btnCancelar;
	private JButton btnGenerarUsuario;
	private JLabel lblApellidoObligatorio;
	private JLabel lblNombreObligatorio;
	private JLabel lblPass;
	private JLabel lblRepita;
	private JLabel lblIngresePass;
	private JLabel lblRepitaPass;
	private JLabel lblRol;

	public VentanaAltaModifUsuario(JFrame padre) {
		super (padre, true);
		setBounds(100, 100, 530, 303);
		setResizable(false);
		getContentPane().setLayout(null);
		
		tfNombre = new JTextField();
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		tfNombre.setBounds(104, 11, 229, 20);
		getContentPane().add(tfNombre);
		tfNombre.setColumns(10);
		
		tfApellido = new JTextField();
		tfApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterConEspacio(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		tfApellido.setBounds(104, 42, 229, 20);
		getContentPane().add(tfApellido);
		tfApellido.setColumns(10);
		
		tfUsuario = new JTextField();
		tfUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterUserPass(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		tfUsuario.setBounds(186, 86, 147, 20);
		getContentPane().add(tfUsuario);
		tfUsuario.setColumns(10);
		
		pfPass = new JPasswordField();
		pfPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterUserPass(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		pfPass.setBounds(186, 117, 147, 20);
		getContentPane().add(pfPass);
		
		pfRepitaPass = new JPasswordField();
		pfRepitaPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!Validador.caracterUserPass(e.getKeyChar()) || tfNombre.getText().length()>19) {
					e.consume();
				}
			}
		});
		pfRepitaPass.setBounds(186, 148, 147, 20);
		getContentPane().add(pfRepitaPass);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(415, 233, 89, 23);
		getContentPane().add(btnCancelar);
		
		btnCrear = new JButton("Crear");
		btnCrear.setBounds(316, 233, 89, 23);
		getContentPane().add(btnCrear);
		
		rbAdm = new JRadioButton("Administrativo");
		rbAdm.setSelected(true);
		rbAdm.setBounds(65, 187, 109, 23);
		getContentPane().add(rbAdm);
		
		rbTec = new JRadioButton("T\u00E9cnico");
		rbTec.setBounds(65, 213, 109, 23);
		getContentPane().add(rbTec);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rbAdm);
		group.add(rbTec);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 14, 74, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(20, 45, 74, 14);
		getContentPane().add(lblApellido);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario:");
		lblNombreDeUsuario.setBounds(20, 89, 156, 14);
		getContentPane().add(lblNombreDeUsuario);
		
		lblRol = new JLabel("Rol:");
		lblRol.setBounds(20, 191, 46, 14);
		getContentPane().add(lblRol);
		
		lblIngresePass = new JLabel("Ingrese su contrase\u00F1a:");
		lblIngresePass.setBounds(20, 120, 156, 14);
		getContentPane().add(lblIngresePass);
		
		lblRepitaPass = new JLabel("Repita su contrase\u00F1a:");
		lblRepitaPass.setBounds(20, 151, 156, 14);
		getContentPane().add(lblRepitaPass);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 73, 494, 2);
		getContentPane().add(separator);
		
		btnGenerarUsuario = new JButton("Generar usuario");
		btnGenerarUsuario.setBounds(366, 85, 138, 23);
		getContentPane().add(btnGenerarUsuario);
		
		lblApellidoObligatorio = new JLabel("Campos obligatorios.");
		lblApellidoObligatorio.setForeground(Color.RED);
		lblApellidoObligatorio.setBounds(359, 45, 145, 14);
		getContentPane().add(lblApellidoObligatorio);
		lblApellidoObligatorio.setVisible(false);
		
		lblNombreObligatorio = new JLabel("Campos obligatorios.");
		lblNombreObligatorio.setForeground(Color.RED);
		lblNombreObligatorio.setBounds(359, 14, 145, 14);
		getContentPane().add(lblNombreObligatorio);
		lblNombreObligatorio.setVisible(false);
		
		lblPass = new JLabel("Ingrese una contrase\u00F1a.");
		lblPass.setForeground(Color.RED);
		lblPass.setBounds(359, 120, 145, 14);
		getContentPane().add(lblPass);
		lblPass.setVisible(false);
		
		lblRepita = new JLabel("contrase\u00F1a no coincide.");
		lblRepita.setForeground(Color.RED);
		lblRepita.setBounds(359, 151, 145, 14);
		getContentPane().add(lblRepita);
		lblRepita.setVisible(false);
	}

	public JLabel getLblRol() {
		return lblRol;
	}

	public JLabel getLblIngresePass() {
		return lblIngresePass;
	}

	public JLabel getLblRepitaPass() {
		return lblRepitaPass;
	}

	public JLabel getLblApellidoObligatorio() {
		return lblApellidoObligatorio;
	}

	public JLabel getLblNombreObligatorio() {
		return lblNombreObligatorio;
	}

	public JLabel getLblPass() {
		return lblPass;
	}

	public JLabel getLblRepita() {
		return lblRepita;
	}

	public JButton getBtnGenerarUsuario() {
		return btnGenerarUsuario;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public JTextField getTfApellido() {
		return tfApellido;
	}

	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	public JPasswordField getPfPass() {
		return pfPass;
	}

	public JPasswordField getPfRepitaPass() {
		return pfRepitaPass;
	}

	public JRadioButton getRbAdm() {
		return rbAdm;
	}

	public JRadioButton getRbTec() {
		return rbTec;
	}

	public JButton getBtnCrear() {
		return btnCrear;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
}
