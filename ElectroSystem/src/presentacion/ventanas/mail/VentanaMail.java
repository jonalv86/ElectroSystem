package presentacion.ventanas.mail;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaMail extends JDialog {

	private JTextField txtEnviarA;
	private JTextField txtAsunto;
	private JEditorPane editorPane;
	private JButton btnEnviar;
	private JButton btnVolver;

	/**
	 * @wbp.parser.constructor
	 */
	public VentanaMail(JFrame padre) {
		super(padre, true);
		setBounds(100, 100, 522, 371);
		setTitle("Enviar Mail");
		go();
	}

	public VentanaMail(JDialog padre) {
		super(padre, true);
		// setModal(false); // Dario Rick
		setBounds(100, 100, 524, 371);
		setTitle("Enviar Mail");

		go();
	}

	private void go() {
		getContentPane().setLayout(null);
		setResizable(false);
		JLabel lblEnviarA = new JLabel("Enviar a:");
		lblEnviarA.setBounds(37, 39, 73, 14);
		getContentPane().add(lblEnviarA);

		JLabel lblAsunto = new JLabel("Asunto:");
		lblAsunto.setBounds(37, 81, 73, 14);
		getContentPane().add(lblAsunto);

		JLabel lblTexto = new JLabel("Texto:");
		lblTexto.setBounds(37, 131, 73, 14);
		getContentPane().add(lblTexto);

		txtEnviarA = new JTextField();
		txtEnviarA.setBounds(131, 36, 333, 20);
		getContentPane().add(txtEnviarA);
		txtEnviarA.setColumns(10);

		txtAsunto = new JTextField();
		txtAsunto.setColumns(10);
		txtAsunto.setBounds(131, 78, 333, 20);
		getContentPane().add(txtAsunto);

		editorPane = new JEditorPane();
		editorPane.setBounds(131, 125, 333, 139);
		getContentPane().add(editorPane);

		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(149, 298, 89, 23);
		getContentPane().add(btnEnviar);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(279, 298, 89, 23);
		getContentPane().add(btnVolver);
	}

	public JTextField getTxtEnviarA() {
		return txtEnviarA;
	}

	public JTextField getTxtAsunto() {
		return txtAsunto;
	}

	public JEditorPane getMensaje() {
		return editorPane;
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}

	public void setTxtEnviarA(String txtEnviarA) {
		this.txtEnviarA.setText(txtEnviarA);
	}

	public void setTxtAsunto(String txtAsunto) {
		this.txtAsunto.setText(txtAsunto);
	}

	public void setMensaje(String editorPane) {
		this.editorPane.setText(editorPane);
	}

}
