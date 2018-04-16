package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

import dto.MarcaDTO;
import dto.PiezaDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.marca.VentanaAltaModifMarca;
import presentacion.ventanas.piezas.VentanaAltaModifPieza;

public class ControladorPieza implements ActionListener, ItemListener {

	private VentanaAltaModifPieza ventana;
	private Modelo modelo;
	private PiezaDTO pieza;
	private List<MarcaDTO> marcas;

	public ControladorPieza(VentanaAltaModifPieza ventana, Modelo modelo) throws Exception {
		iniciar(ventana, modelo);
		this.ventana.setTitle("Nueva Pieza");
		try {
			this.cargarCboMarcas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ventana.setVisible(true);
	}

	private void iniciar(VentanaAltaModifPieza ventana, Modelo modelo) {
		this.setVentana(ventana);
		this.setModelo(modelo);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAlta().addActionListener(this);
		this.ventana.getCboMarcas().addActionListener(this);
		this.ventana.getBtnNuevaMarca().addActionListener(this);
	}

	public ControladorPieza(VentanaAltaModifPieza ventana, Modelo modelo, PiezaDTO pieza, boolean editable, boolean modificaStock) throws Exception {
		iniciar(ventana, modelo);
		this.setPieza(pieza);
		this.ventana.getBtnAlta().setText("Guardar cambios");
		this.ventana.setTitle("Modificar pieza " + " " + pieza.getMarca().getNombre());
		cargarPieza();
		if (!modificaStock) {
			if (!editable){
				bloquearCampos();
				this.ventana.setTitle("Detalle pieza" + " " + pieza.getMarca());
			}
		} else {
			bloquearCampos();
			this.ventana.setTitle("Reajustar stock");
			this.ventana.getSpinnerBajoStock().setModel(new SpinnerNumberModel(new Integer(pieza.getStock()), new Integer(0), null, new Integer(1)));
			this.ventana.getBtnAlta().setText("Reajustar");
			this.ventana.getBtnAlta().setVisible(true);
			this.ventana.getLblBajoStock().setText("Stock:");
		}
		
		this.ventana.setVisible(true);
	}

	private void bloquearCampos() {
		this.ventana.getTfDescripcionMarca().setEditable(false);
		this.ventana.getTfPrecioVenta().setEditable(false);
		this.ventana.getBtnAlta().setVisible(false);
		this.ventana.getTfIdUnico().setEditable(false);
		this.ventana.getBtnNuevaMarca().setVisible(false);
		this.ventana.getBtnCancelar().setText("Cerrar");
		this.ventana.getSpinnerBajoStock().setModel(new SpinnerNumberModel(new Integer(pieza.getBajo_stock()), new Integer(pieza.getBajo_stock()), new Integer(pieza.getBajo_stock()), new Integer(1)));
	}

	private void cargarPieza() {
		this.ventana.getTfIdUnico().setText(this.pieza.getIdUnico());
		this.ventana.getTfDescripcionMarca().setText(this.pieza.getDescripcion());
		this.ventana.getTfPrecioVenta().setText(""+this.pieza.getPrecio_venta());
		this.ventana.getCboMarcas().addItem(this.pieza.getMarca());
	}

	public VentanaAltaModifPieza getVentana() {
		return ventana;
	}

	public void setVentana(VentanaAltaModifPieza ventana) {
		this.ventana = ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	
	@SuppressWarnings("unchecked")
	private void cargarCboMarcas() throws Exception {
		try
		{
			this.ventana.getCboMarcas().removeAllItems();
			this.marcas = modelo.obtenerMarcas();
			for (MarcaDTO m : marcas) {
				this.ventana.getCboMarcas().addItem(m);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
			if (e.getSource() == this.ventana.getCboMarcas() && this.ventana.getCboMarcas().getSelectedItem() != null) {
		}
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.ventana.getBtnAlta()) {
			if (this.ventana.getBtnAlta().getText().equals("Reajustar")) {
				int stockNuevo = Integer.parseInt((ventana.getSpinnerBajoStock().getModel().getValue().toString()));
				if (stockNuevo  > this.pieza.getStock()) {
					//Alta de piezas en estado disponible.
					try {
						modelo.altaStockPieza(pieza, stockNuevo-this.pieza.getStock());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (stockNuevo  < this.pieza.getStock()) {
					//Baja de piezas en estado perdida.
					try {
						modelo.bajaStockPieza(pieza, this.pieza.getStock()-stockNuevo, 4);//TODO estado 4 = perdida
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "El stock se ha reajustado correctamente", "Reajuste de stock",
						JOptionPane.INFORMATION_MESSAGE);
				this.ventana.dispose();
			} else {
				if (camposCorrectos()) {
					if (this.pieza == null) { // Es alta
						this.pieza = new PiezaDTO(0,(MarcaDTO)this.ventana.getCboMarcas().getSelectedItem(),
								this.ventana.getTfIdUnico().getText(),
								this.ventana.getTfDescripcionMarca().getText(),
								Float.parseFloat(this.ventana.getTfPrecioVenta().getText()),
								Integer.parseInt((ventana.getSpinnerBajoStock().getModel().getValue().toString())));
	
						altaPieza();
					} else { // Es modificación
						//edito la pieza
						modificacionPieza();
					}
					this.ventana.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Por favor verifique todos los campos", "Faltan datos",
							JOptionPane.WARNING_MESSAGE);
				}
			}
			
		} else if (e.getSource() == this.ventana.getBtnCancelar()) {
			this.ventana.dispose();
			
		} else if (e.getSource() == this.ventana.getBtnNuevaMarca()) {
			VentanaAltaModifMarca ventanaMarca = new VentanaAltaModifMarca(ventana);
			ControladorMarca controladorMarca = new ControladorMarca(ventanaMarca, modelo);
			try {
				cargarCboMarcas();
				ventana.getCboMarcas().getModel().setSelectedItem(controladorMarca.getMarca());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	}

	private void modificacionPieza() {
		try {
			if (camposCorrectos()) {
				ActualizarPieza();
				JOptionPane.showMessageDialog(null,
					"La pieza " + ventana.getCboMarcas().getModel().getSelectedItem() + ", " + ventana.getTfDescripcionMarca().getText() + " "
							+ "se ha modificado satisfactoriamente.",
					"Pieza actualizada", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un error al modificar la pieza", "Pieza no actualizada",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void ActualizarPieza() throws Exception {
		try {
			PiezaDTO pieza = new PiezaDTO(this.pieza.getIdProdPieza(),
					((MarcaDTO) ventana.getCboMarcas().getModel().getSelectedItem()),
					this.ventana.getTfIdUnico().getText(),
					this.ventana.getTfDescripcionMarca().getText(),
					Float.parseFloat(this.ventana.getTfPrecioVenta().getText()),
					Integer.parseInt((ventana.getSpinnerBajoStock().getModel().getValue().toString())));
			this.modelo.modificarPieza(pieza, ((MarcaDTO) ventana.getCboMarcas().getModel().getSelectedItem()).getIdMarca());
			this.pieza = pieza; //para que lo devuelva si se lo pide y no ir a la db.
		} catch (Exception e) {
			throw e;
		}
	}

	private void altaPieza() {
		try {
			modelo.agregarPieza(this.pieza);
			JOptionPane.showMessageDialog(null,
					"La pieza, "+ventana.getTfDescripcionMarca().getText()+ " "
							+ "se ha creado satisfactoriamente.",
					"Nueva pieza creada", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Hubo un error al crear la pieza " + e.getMessage(), "La pieza no se ha creado",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean camposCorrectos() {
		if (!this.ventana.getTfIdUnico().getText().isEmpty())
			this.ventana.getTfIdUnico().setText(Validador.validarCampoCodigo(this.ventana.getTfIdUnico().getText()));
		return !this.ventana.getTfDescripcionMarca().getText().isEmpty() &&
				!this.ventana.getTfPrecioVenta().getText().isEmpty() &&
				!this.ventana.getTfIdUnico().getText().isEmpty() &&
				this.ventana.getTfIdUnico().getText().length()==8;
	}

	public PiezaDTO getPieza() {
		return pieza;
	}

	public void setPieza(PiezaDTO pieza) {
		this.pieza = pieza;
	}

}
