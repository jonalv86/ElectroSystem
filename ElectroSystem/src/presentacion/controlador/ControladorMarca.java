package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.MarcaDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.marca.VentanaAltaModifMarca;

public class ControladorMarca implements ActionListener {
	private VentanaAltaModifMarca ventana;
	private Modelo modelo;
	private MarcaDTO marca;
	
	public ControladorMarca(VentanaAltaModifMarca ventana, Modelo modelo) {
		this.ventana = ventana;
		this.modelo = modelo;
		iniciar();
	}	

	private void iniciar() {
		this.ventana.getBtnAgregar().addActionListener(this);
		this.ventana.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (campoCorrecto()) {
			if (!marcaExiste()) {
				if (this.marca==null) {
					crearActualizarMarca(0);
					JOptionPane.showMessageDialog(null, "Se ha agregado una nueva marca.",
							"Nuevo marca creada", JOptionPane.INFORMATION_MESSAGE);
								} else {
					crearActualizarMarca(this.marca.getIdMarca());
					JOptionPane.showMessageDialog(null, "Se ha actualizado la marca stisfactoriamente.",
							"Marca actualizada", JOptionPane.INFORMATION_MESSAGE);
								}
				this.ventana.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Ya existe una marca con ese nombre.",
						"Error al crear/actualizar", JOptionPane.WARNING_MESSAGE);			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor llene todos los campos.",
					"Faltan datos", JOptionPane.WARNING_MESSAGE);		}
	}
	
	private void crearActualizarMarca(int id) {
		MarcaDTO nueva = new MarcaDTO(id, this.ventana.getTfNombre().getText());
		this.marca = nueva;
		if (id==0)
			try {
				this.modelo.agregarMarca(nueva);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			this.modelo.actualizarMarca(nueva);
	}

	private boolean marcaExiste() {
		List<MarcaDTO> marcas = this.modelo.obtenerMarcas();
		for (MarcaDTO m : marcas)
			if (m.getNombre().equals(this.ventana.getTfNombre().getText()))
				return true;
		return false;
	}

	private boolean campoCorrecto() {
		if (!this.ventana.getTfNombre().getText().isEmpty())
			this.ventana.getTfNombre().setText(Validador.validarCampo(this.ventana.getTfNombre().getText()));
		return this.ventana.getTfNombre().getText().isEmpty()?false:true;
	}

	public MarcaDTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaDTO marcaAgregada) {
		this.marca = marcaAgregada;
	}
	
}
