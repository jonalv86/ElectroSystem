package presentacion.ventanas.logueo;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class VentanaLogueo extends JFrame {

	private static final long serialVersionUID = -4417480508461644016L;
	private JPanel contentPane;
	private JTextField tfUsuario;
	private JPasswordField pfPassword;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private JMenuItem mntmreestablecer;
	private JMenuItem mntmIngresarClave;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentanaLogueo frame = new VentanaLogueo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaLogueo() {
		
		try {
			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("Acceso a usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 198);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(10, 26, 96, 14);
		contentPane.add(lblUsuario);

		tfUsuario = new JTextField();
		tfUsuario.setBounds(116, 23, 114, 20);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(tfUsuario, popupMenu);
		
		mntmreestablecer = new JMenuItem("<html>Reestablecer<br>superusuarios<html>");
		mntmreestablecer.setIcon(new ImageIcon(VentanaLogueo.class.getResource("/recursos/reestablecer.png")));
		popupMenu.add(mntmreestablecer);
		
		mntmIngresarClave = new JMenuItem("<html>Ingresar clave<br>(Solo superusuarios)</html>");
		popupMenu.add(mntmIngresarClave);

		pfPassword = new JPasswordField();
		pfPassword.setBounds(116, 54, 114, 20);
		contentPane.add(pfPassword);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(10, 57, 96, 14);
		contentPane.add(lblContrasea);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(VentanaLogueo.class.getResource("/recursos/cancel.png")));
		btnCancelar.setBounds(215, 119, 115, 29);
		contentPane.add(btnCancelar);
		
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setIcon(new ImageIcon(VentanaLogueo.class.getResource("/recursos/log in.png")));
		btnAceptar.setBounds(90, 119, 115, 29);
		btnAceptar.setHorizontalTextPosition(SwingConstants.RIGHT);
		contentPane.add(btnAceptar);
	}

	public JMenuItem getMntmIngresarClave() {
		return mntmIngresarClave;
	}

	public JMenuItem getMntmreestablecer() {
		return mntmreestablecer;
	}

	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	public JPasswordField getPfPassword() {
		return pfPassword;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
