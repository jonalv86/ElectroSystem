package presentacion.controlador.presupuesto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.EstadoDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.PiezaDTO;
import dto.UsuarioDTO;
import modelo.Modelo;
import presentacion.controlador.button.column.ButtonColumn;
import presentacion.ventanas.ot.VentanaTecnicoOT;

public class ControladorTecnicoOT implements ActionListener, ItemListener {

	private Modelo modelo;
	private VentanaTecnicoOT ventana;
	private OrdenDTO orden;
	private UsuarioDTO usuario;
	private List<PiezaDTO> presupuestadas;
	private List<PiezaDTO> usadas;
	private double total;
	private DefaultTableModel tabla_de_piezas;

	public ControladorTecnicoOT(VentanaTecnicoOT ventana, Modelo modelo, OrdenDTO orden, UsuarioDTO usuarioLogueado) {
		this.setVentana(ventana);
		this.setModelo(modelo);
		this.orden = orden;
		this.usuario = usuarioLogueado;
		this.presupuestadas = orden.getPiezasPresupuestadas();
		this.usadas = orden.getPiezasUsadas();
		if (orden.getEstado().getId()==1)	//Ingresada hardcodeado
			iniciarPresupuestar();
		else
			iniciarReparar();
	}

	private void iniciarPresupuestar() {
			this.ventana.setTitle("Presupuestando orden de trabajo número: " + orden.getIdOT());
			this.ventana.getBtnCrear().addActionListener(this);
			this.ventana.getBtnCancelar().addActionListener(this);
			this.ventana.getBtnAgregarPieza().addActionListener(this);
			this.ventana.getComboMarca().addItemListener(this);
			cargarComboBoxMarcas();
			cargarOt();
			this.ventana.setVisible(true);
		}
	
	private void iniciarReparar() {
		this.ventana.setTitle("Reparando orden de trabajo número: " + orden.getIdOT());
		this.ventana.getBtnCrear().addActionListener(this);
		this.ventana.getBtnCancelar().addActionListener(this);
		this.ventana.getBtnAgregarPieza().addActionListener(this);
		this.ventana.getComboMarca().addItemListener(this);
		this.ventana.getCbEstado().addActionListener(this);
		cambiarVista();
		cargarComboBoxMarcas();
		cargarOt();
		cargarEstados();
		cargarTablaDeUso();
		this.ventana.setVisible(true);
	}

	private void cambiarVista() {
		this.ventana.getLblManoDeObra().setVisible(false);
		this.ventana.getTfManoDeObra().setVisible(false);
		this.ventana.getDateFechaVencimiento().setVisible(false);
		this.ventana.getLblFechaExpiraGaranta().setVisible(false);
		this.ventana.getLblTotalPresupuesto().setVisible(false);
		this.ventana.getLblEstado().setVisible(true);
		this.ventana.getCbEstado().setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		if (e.getSource() == this.ventana.getBtnAgregarPieza()) {
			this.presupuestadas.add((PiezaDTO) this.ventana.getComboPieza().getSelectedItem());
				if (orden.getEstado().getId()==1) {
					llenarTablaPresupuesto();
					this.ventana.setLblTotalPresupuesto("Total: $" + total);
				} else {
					AgregarTablaReparar();
				}
		}
	
		else if (e.getSource() == this.ventana.getBtnCancelar()) {
			ventana.dispose();
		}
	
		else if (e.getSource() == this.ventana.getBtnCrear()) {
			if (orden.getEstado().getId()==1) {
				if (this.ventana.getDateFechaVencimiento().getModel().getValue() != null) {
					if (!this.ventana.getTfManoDeObra().getText().isEmpty()) {
						if (presupuestadas==null || presupuestadas.isEmpty()) {
							int respuesta = JOptionPane.showConfirmDialog(ventana, "No se han seleccionado piezas. ¿Desea continuar?", null, JOptionPane.YES_NO_OPTION);
							if (respuesta == JOptionPane.YES_OPTION) 
								crearPresupuesto();
							JOptionPane.showMessageDialog(null, "La orden " + orden.getIdOT() + " se ha presupuestado correctamente", "Orden presupuestada", JOptionPane.INFORMATION_MESSAGE);
							this.ventana.dispose();
						} else {
							crearPresupuesto();
							JOptionPane.showMessageDialog(null, "La orden " + orden.getIdOT() + " se ha presupuestado correctamente", "Orden presupuestada", JOptionPane.INFORMATION_MESSAGE);
							this.ventana.dispose();
						}
					} else {
						int respuesta = JOptionPane.showConfirmDialog(ventana, "No se ha agregado costo por mano de obra. ¿Desea continuar?", null, JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) 
							crearPresupuesto();
						JOptionPane.showMessageDialog(null, "La orden " + orden.getIdOT() + " se ha presupuestado correctamente", "Orden presupuestada", JOptionPane.INFORMATION_MESSAGE);
						this.ventana.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha ", "Error", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				cambiarEstado();
				this.ventana.dispose();
			}
		}		
	}

	private void crearPresupuesto() {
		this.orden.setVencPresup((Calendar) ventana.getDateFechaVencimiento().getModel().getValue());
		this.orden.setTecnicoAsoc(usuario);
		this.orden.setPiezasPresupuestadas(presupuestadas);
		this.orden.setManoDeObra(Double.parseDouble(this.ventana.getTfManoDeObra().getText()));
		if (orden.isEsDelivery())
			this.orden.setCostoDeEnvio(orden.getCliente().getLocalidad().getZonaDeEnvio().getPrecio());
		else
			this.orden.setCostoDeEnvio(0.0);
		try {
			modelo.presupuestarOT(orden);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void llenarTablaPresupuesto() {
		total = 0.0;
		String[] columnas = { "Descripción", "Precio Venta", "" };
		DefaultTableModel tabla_de_presupuestadas = new DefaultTableModel(null, columnas) {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column) {
			if (column == 2)
				return true;
			else
				return false;
			}
		};
		tabla_de_presupuestadas.setColumnIdentifiers(columnas);
		for (PiezaDTO p : this.presupuestadas) {
			Object[] fila = {p.getDescripcion(), p.getPrecio_venta(), "QUITAR" };
			tabla_de_presupuestadas.addRow(fila);
			total += p.getPrecio_venta();
		}
		this.ventana.getTable().setModel(tabla_de_presupuestadas);
		new ButtonColumn(this.ventana.getTable(), quitarDelPresupuesto(), 2);
	}
	
	private void cargarTablaDeUso() {
		this.presupuestadas = orden.getPiezasPresupuestadas();
		this.usadas = orden.getPiezasUsadas();
		String[] columnas = { "Presupuestada", "Numero de pieza", "Descripción", "", "" };
		tabla_de_piezas = new DefaultTableModel(null, columnas) {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int column) {
			if (column == 3 || column == 4)
				return true;
			else
				return false;
			}
		};
		tabla_de_piezas.setColumnIdentifiers(columnas);
		
		List<PiezaDTO> agregar_usadas = new ArrayList<PiezaDTO>();
		for (PiezaDTO p : this.usadas) {
			agregar_usadas.add(p);
		}
		
		for (PiezaDTO pp : this.presupuestadas) {
			boolean agregada = false;
			PiezaDTO pieza = null;
			for (PiezaDTO pu : agregar_usadas) {
				if (pp.getIdProdPieza() == pu.getIdProdPieza() && agregada==false) {
					Object[] fila = {"Si", pu, pu.getDescripcion(), "QUITAR", "SIN STOCK" };
					tabla_de_piezas.addRow(fila);
					agregada = true;
					pieza = pu;
				}
			}
			if (pieza!=null)
				agregar_usadas.remove(pieza);
			if (!agregada) {
				Object[] fila = {"Si", pp, pp.getDescripcion(), "USAR", "SIN STOCK" };
				tabla_de_piezas.addRow(fila);
			}
		}
		if (!agregar_usadas.isEmpty()) {
			for (PiezaDTO pu : agregar_usadas) {
				Object[] fila = {"No", pu, pu.getDescripcion(), "QUITAR", "SIN STOCK" };
				tabla_de_piezas.addRow(fila);
			}
		}
		this.ventana.getTable().setModel(tabla_de_piezas);
		new ButtonColumn(this.ventana.getTable(), usar(), 3);	
		new ButtonColumn(this.ventana.getTable(), reportar(), 4);	
	}

	private void AgregarTablaReparar() {
		PiezaDTO p = (PiezaDTO) this.ventana.getComboPieza().getSelectedItem();
		Object[] fila = {"No", p, p.getDescripcion(), "USAR", "SIN STOCK" };
		tabla_de_piezas.addRow(fila);
		this.ventana.getTable().setModel(tabla_de_piezas);
	}
	
	private Action quitarDelPresupuesto() {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				total -= presupuestadas.get(modelRow).getPrecio_venta();
				ventana.setLblTotalPresupuesto("Total: $" + total);
				presupuestadas.remove(modelRow);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		};
	}

	private Action usar() {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				PiezaDTO pieza = (PiezaDTO) table.getModel().getValueAt(modelRow, 1);
				if (table.getModel().getValueAt(modelRow, 3).equals("USAR")) {
					if (modelo.revisarStock(pieza)) {
						try {
							modelo.agregarUsada(orden, pieza);
							orden = modelo.obtenerOrdenPorId(orden.getIdOT());
							cargarTablaDeUso();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
//						modelo.crearAlerta(pieza);
						ventana.getCbEstado().removeAllItems();
						ventana.getCbEstado().addItem(new EstadoDTO(6, "En espera de piezas"));
						cambiarEstado();//TODO: Se puede seguir reparando por mas que no tenga una pieza.
						table.getModel().setValueAt("SIN STOCK", modelRow, 3);
					}
				} else if (table.getModel().getValueAt(modelRow, 3).equals("QUITAR")) {
					try {
						modelo.quitarUsada(orden, pieza);
						orden = modelo.obtenerOrdenPorId(orden.getIdOT());
						cargarTablaDeUso();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		};
	}

	private Action reportar() {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				PiezaDTO pieza = (PiezaDTO) table.getModel().getValueAt(modelRow, 1);
				if (table.getModel().getValueAt(modelRow, 3).equals("SIN STOCK")) {
				} else {
					int seleccion = JOptionPane.showOptionDialog(ventana, "¿La pieza está rota o perdida?",
							"Baja de stock", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, new Object[] { "ROTA", "PERDIDA" }, "ROTA");

					if (seleccion != -1) {//TODO 3 Rota, 4 Perdida
						if (modelo.revisarStock(pieza)) {
							try {
								modelo.bajaStockPieza(pieza, 1, seleccion+3);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}

			}
		};
	}
	
	public void cargarOt() {
		this.ventana.getTxtUsuario().setText(this.usuario.toString());
		this.ventana.setLblidOT(this.orden.getIdOT());
		this.ventana.setTxtCliente(orden.getCliente().getApellido() + ", " + orden.getCliente().getNombre());
		this.ventana.setTxtDetalle(this.orden.getDescripcion());
		this.ventana.setTxtElectro(orden.getElectrodomestico().getMarca() + " - " + orden.getElectrodomestico().getModelo());
	}
	
	private void cargarComboBoxMarcas() {
		this.ventana.agregarMarcas(modelo.obtenerMarcas());
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == this.ventana.getComboMarca() && this.ventana.getComboMarca().getSelectedItem() != null) {
			MarcaDTO marca = (MarcaDTO) this.ventana.getComboMarca().getSelectedItem();
			try {
				List<PiezaDTO> piezas = modelo.obtenerItems();//TODO: preguntar a Lucas si hace falta traer tooooodas las piezas.
				List<PiezaDTO> piezasMarca = new ArrayList<PiezaDTO>();
				for (PiezaDTO piezaDTO : piezas) {
					if (piezaDTO.getMarca().equals(marca))
						piezasMarca.add(piezaDTO);
				}
				this.ventana.agregarPiezas(piezasMarca);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void cargarEstados() {
		this.ventana.getCbEstado().addItem(this.orden.getEstado());
		if (this.orden.getEstado().getNombre().equals("Aprobada")) {
			this.ventana.getCbEstado().removeItem(this.orden.getEstado());
			this.ventana.getCbEstado().addItem(new EstadoDTO(5, "En reparación"));
			this.ventana.getCbEstado().addItem(new EstadoDTO(7, "Reparada"));
			this.ventana.getCbEstado().addItem(new EstadoDTO(8, "Irreparable"));
				
		} else if (this.orden.getEstado().getNombre().equals("En reparación")) {
			this.ventana.getCbEstado().addItem(new EstadoDTO(7, "Reparada"));
			this.ventana.getCbEstado().addItem(new EstadoDTO(8, "Irreparable"));
		}
	}
	
	private void cambiarEstado() {
		if (!this.orden.getEstado().equals((EstadoDTO) ventana.getCbEstado().getSelectedItem())) {
			modelo.actualizarEstado(orden, ((EstadoDTO) ventana.getCbEstado().getSelectedItem()).getId());
		}
	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	private void setVentana(VentanaTecnicoOT ventana) {
		this.ventana = ventana;
	}

	public VentanaTecnicoOT getVentana() {
		return ventana;
	}

	public Modelo getModelo() {
		return modelo;
	}
}
