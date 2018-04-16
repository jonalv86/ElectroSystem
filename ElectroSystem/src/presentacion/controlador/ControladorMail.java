package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dto.OrdenDTO;
import dto.SolicitudCompraDTO;
import modelo.Mail;
import modelo.Modelo;
import presentacion.ventanas.mail.VentanaMail;

public class ControladorMail implements ActionListener {
	private VentanaMail ventanaMail;
	private Modelo modelo;

	private boolean fueEnviado;

	public ControladorMail(VentanaMail ventana, Modelo modelo) {
		iniciar(ventana, modelo);
	}

	public ControladorMail(VentanaMail ventana, Modelo modelo, OrdenDTO orden) {

		iniciar(ventana, modelo);

		this.ventanaMail.setTxtEnviarA(orden.getCliente().geteMail());
		this.ventanaMail.setTxtAsunto("Presupuesto - Reparaci�n de " + orden.getElectrodomestico().getDescripcion());

		String presupuesto = "Se adjunta el presupuesto del electrodom�stico.";

		presupuesto += "\nEsperamos su respuesta.\n\nSaludos,\nElectro R";

		this.ventanaMail.setMensaje(presupuesto);

	}

	public ControladorMail(VentanaMail ventana, Modelo modelo, SolicitudCompraDTO solicitud) {

		iniciar(ventana, modelo, solicitud);

	}

	private void iniciar(VentanaMail ventana, Modelo modelo, SolicitudCompraDTO solicitud) {

		this.ventanaMail = ventana;
		this.modelo = modelo;

		this.ventanaMail.setTxtEnviarA(solicitud.getProveedor().getMail());
		this.ventanaMail.setTxtAsunto("Solicitud de compra");
		this.ventanaMail.setMensaje("\nEsperamos su respuesta.\n\nSaludos,\nElectro R");

		this.ventanaMail.getBtnVolver().addActionListener(this);
		this.ventanaMail.getBtnEnviar().addActionListener(this);
		this.ventanaMail.setVisible(true);
	}

	private void iniciar(VentanaMail ventana, Modelo modelo) {

		this.ventanaMail = ventana;
		this.modelo = modelo;
		this.ventanaMail.getBtnVolver().addActionListener(this);
		this.ventanaMail.getBtnEnviar().addActionListener(this);
		this.ventanaMail.setVisible(true);
		this.fueEnviado = false;
	}

	private boolean camposValidos() {
		boolean ret = true;
		ret = ret && this.ventanaMail.getTxtEnviarA().getText().length() > 0;
		ret = ret && this.ventanaMail.getTxtAsunto().getText().length() > 0;
		ret = ret && this.ventanaMail.getMensaje().getText().length() > 0;

		return ret;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventanaMail.getBtnEnviar()) {
			if (camposValidos()) {
				try {
					Mail.generateAndSendEmail(ventanaMail.getTxtEnviarA().getText(),
							ventanaMail.getTxtAsunto().getText(), 
							ventanaMail.getMensaje().getText());
					JOptionPane.showMessageDialog(this.ventanaMail, "Mail enviado correctamente");
					this.fueEnviado = true;
					this.ventanaMail.dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this.ventanaMail, "Error al enviar mail: " + e1.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(this.ventanaMail, "Debe completar todos los campos");
			}
		} else if (e.getSource() == this.ventanaMail.getBtnVolver()) {
			this.ventanaMail.dispose();
		}
	}

	public boolean fueEnviado() {
		return fueEnviado;
	}

	public void fueEnviado(boolean fueEnviado) {
		this.fueEnviado = fueEnviado;
	}

}
