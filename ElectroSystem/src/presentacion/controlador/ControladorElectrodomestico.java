package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.ElectrodomesticoDTO;
import dto.MarcaDTO;
import helpers.Validador;
import modelo.Modelo;
import presentacion.ventanas.ed.VentanaAltaModifElectrod;
import presentacion.ventanas.marca.VentanaAltaModifMarca;

public class ControladorElectrodomestico implements ActionListener {

	private VentanaAltaModifElectrod ventana;
	private List<MarcaDTO> marcas;
	private Modelo modelo;
	private ElectrodomesticoDTO electrodomestico;
	private MarcaDTO marcaSeleccionada;
	private VentanaAltaModifMarca ventanaMarca;
	
	public ControladorElectrodomestico(VentanaAltaModifElectrod ventana, Modelo modelo) {
		this.ventana = ventana;
		this.modelo = modelo;
		cargarComboBoxMarcas();
		iniciar();
	}

	public ControladorElectrodomestico(VentanaAltaModifElectrod ventana, Modelo modelo, ElectrodomesticoDTO electrodomestico) {
		this.ventana = ventana;
		this.marcaSeleccionada = electrodomestico.getMarca();
		this.modelo = modelo;
		this.electrodomestico = electrodomestico;	
		iniciarEdicion();
		}

	private void iniciar() {
		this.ventana.getComboMarca().addActionListener(this);
		this.ventana.getBtnAgregar().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarMarca().addActionListener(this);
		this.ventana.setVisible(true);
	}
	
	private void iniciarEdicion() {
		this.ventana.getComboMarca().addItem(this.marcaSeleccionada);
		this.ventana.getComboMarca().setSelectedItem(this.marcaSeleccionada);
		this.ventana.getTfNombre().setText(this.electrodomestico.getDescripcion());
		this.ventana.getTfModelo().setText(this.electrodomestico.getModelo());
		this.ventana.getBtnAgregarMarca().setVisible(false);
		this.ventana.getComboMarca().setEditable(false);
		this.ventana.getBtnAgregar().setText("Acualizar");
		iniciar();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.ventana.getBtnAgregar()) {
			if (camposCorrectos()) {
				if (!electrodomesticoExiste()) {
					if (electrodomestico==null) {
						crearActualizarElectrodomestico(0);
						JOptionPane.showMessageDialog(null, "Se ha agregado un nuevo electrodoméstico.",
								"Nuevo electrodoméstico creado", JOptionPane.INFORMATION_MESSAGE);
					} else { 
						crearActualizarElectrodomestico(electrodomestico.getIdElectro());
						JOptionPane.showMessageDialog(null, "Se ha actualizado el electrodoméstico.",
								"Electrodoméstico actualizado",
								JOptionPane.INFORMATION_MESSAGE);
					}
					this.ventana.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Ya hay un electrodoméstico de esa marca con ese código.",
							"Error al crear/actualizar", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Por favor llene todos los campos.",
						"Faltan datos", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource()==this.ventana.getBtnCancelar()) {
			this.ventana.dispose();
		} else if (e.getSource()==this.ventana.getBtnAgregarMarca()) {
			this.ventanaMarca = new VentanaAltaModifMarca(this.ventana);
			ControladorMarca controladorMarca = new ControladorMarca(this.ventanaMarca, this.modelo);
			if (controladorMarca.getMarca()!=null) {
				cargarComboBoxMarcas();
				seleccionarMarcaAgregada(controladorMarca.getMarca());
			}
		}
	}

	private void crearActualizarElectrodomestico(int id) {
		ElectrodomesticoDTO nuevo = new ElectrodomesticoDTO(
				id,
				(MarcaDTO) this.ventana.getComboMarca().getSelectedItem(),
				this.ventana.getTfModelo().getText(), 
				this.ventana.getTfNombre().getText()
				);

		//Por si hay que consultarlo desde afuera, por ejemplo cargarlo en el comboBox.
		this.marcaSeleccionada = (MarcaDTO) this.ventana.getComboMarca().getSelectedItem();
		if (id==0) {
			try {
				this.modelo.agregarElectrodomestico(nuevo);
			} catch (Exception e) {
				// TODO Mostrar mensaje de error!
				e.printStackTrace();
			}
			this.electrodomestico = nuevo;
		}
		else {
			try {
				this.modelo.actualizarElectrodomestico(nuevo);
			} catch (Exception e) {
				// TODO Mostrar mensaje de error!
				e.printStackTrace();
			}
		}
	}

	private boolean electrodomesticoExiste() { //No puede haber un electrodomestico con la misma marca y modelo.
		try	{
		int idED = 0;
		if (this.electrodomestico!=null)
			idED = this.electrodomestico.getIdElectro();
		List<ElectrodomesticoDTO> electrodomesticos = this.modelo.obtenerElectrodomesticos();
		for (ElectrodomesticoDTO e : electrodomesticos)
			if (e.getIdElectro()!=idED)
				if (e.getMarca().getNombre().equals(this.ventana.getComboMarca().getSelectedItem().toString())&&e.getModelo().equals(this.ventana.getTfModelo().getText()))
					return true;
		}
		catch (Exception e)
		{
		//Mostrar mensaje de error!
		return false;
		}
		return false;
	}

	private boolean camposCorrectos() {
		if (camposLlenos()) {
			ventana.getTfNombre().setText(Validador.validarCampo(ventana.getTfNombre().getText()));
			ventana.getTfModelo().setText(Validador.validarCampoCodigo(ventana.getTfModelo().getText()));
		}
		return camposLlenos()?true:false;
	}
	
	private void seleccionarMarcaAgregada(MarcaDTO marca) {
		for (MarcaDTO m : this.marcas)
			if (m.getNombre().equals(marca.getNombre()))
				this.marcaSeleccionada = m;
		this.ventana.getComboMarca().getModel().setSelectedItem(marcaSeleccionada);
	}

	private boolean camposLlenos() {
		return !this.ventana.getTfNombre().getText().isEmpty()&&
				!this.ventana.getTfModelo().getText().isEmpty();
	}

	private void cargarComboBoxMarcas() {
		this.marcas = this.modelo.obtenerMarcas();
		for (MarcaDTO m : marcas)
			this.ventana.getComboMarca().addItem(m);
	}

	public VentanaAltaModifElectrod getVentana() {
		return ventana;
	}

	public void setVentana(VentanaAltaModifElectrod ventana) {
		this.ventana = ventana;
	}

	public List<MarcaDTO> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<MarcaDTO> marcas) {
		this.marcas = marcas;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public ElectrodomesticoDTO getElectrodomestico() {
		return electrodomestico;
	}

	public void setElectrodomestico(ElectrodomesticoDTO electrodomestico) {
		this.electrodomestico = electrodomestico;
	}

	public MarcaDTO getMarcaSeleccionada() {
		return marcaSeleccionada;
	}

	public void setMarcaSeleccionada(MarcaDTO marcaSeleccionada) {
		this.marcaSeleccionada = marcaSeleccionada;
	}
}
