package presentacion.controlador.fleteros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dto.EnvioDTO;
import dto.FleteroDTO;
import dto.OrdenDTO;
import modelo.Modelo;
import presentacion.ventanas.fleteros.VentanaAsignacion;

public class ControladorAsignacion implements ActionListener, MouseListener, ItemListener {

	private VentanaAsignacion ventana;
	private Modelo modelo;

	private List<FleteroDTO> fleteros;

	private List<OrdenDTO> ordenes_en_tabla_disponibles;
	private List<OrdenDTO> ordenes_en_tabla_resultado;

	private List<EnvioDTO> nuevosEnvios;

	private DefaultTableModel dtm1;
	private DefaultTableModel dtm2;

	public ControladorAsignacion(VentanaAsignacion ventana, Modelo modelo, List<FleteroDTO> fleteros,
			List<OrdenDTO> ordenes) {

		this.ventana = ventana;
		this.setModelo(modelo);
		this.fleteros = fleteros;
		this.ordenes_en_tabla_disponibles = ordenes;
		this.nuevosEnvios = new LinkedList<EnvioDTO>();
		iniciar();
	}

	private void iniciar() {

		// TODO al iniciar la app cargar tmb fleteros, sino NPE
		for (int i = 0; i < fleteros.size(); i++) {
			this.ventana.getComboFleteros().addItem(fleteros.get(i));
		}

		this.ventana.setLblEnvosAsignadosA("Envíos asignados a " + ventana.getComboFleteros().getSelectedItem());

		llenarTabla();
		cargarTablaResultado((FleteroDTO) this.ventana.getComboFleteros().getSelectedItem());

		this.ventana.getOkButton().addActionListener(this);
		this.ventana.getCancelButton().addActionListener(this);

		this.ventana.getTable().addMouseListener(this);
		this.ventana.getTableResultado().addMouseListener(this);

		this.ventana.getComboFleteros().addItemListener(this);

		this.ventana.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.ventana.getCancelButton()) {
			this.ventana.dispose();
		}

		else if (e.getSource() == this.ventana.getOkButton()) {

			reasignarEnvios();
			asignarEnvios();
			this.ventana.dispose();
		}

	}

	public void asignarEnvios() {
		for (int i = 0; i < nuevosEnvios.size(); i++) {
			this.modelo.agregarEnvio(nuevosEnvios.get(i));
		}
	}

	public void reasignarEnvios() {

		for (int i = 0; i < this.modelo.obtenerOrdenes().size(); i++) {
			OrdenDTO actual = this.modelo.obtenerOrdenes().get(i);

			for (int j = 0; j < this.ordenes_en_tabla_disponibles.size(); j++) {
				if (actual.getIdOT() == ordenes_en_tabla_disponibles.get(j).getIdOT()) {
					EnvioDTO envio_a_borrar = new EnvioDTO(actual.getIdOT(), 0); 
					this.modelo.eliminarEnvio(envio_a_borrar);
				}
			}
		}
	}

	public void llenarTabla() {

		// TODO falta precio a cobrar, domicilio, ED
		String[] columns = { "OT", "Electrodoméstico", "Domicilio", "Asignar" };

		dtm1 = new DefaultTableModel(null, columns) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 3)
					return true;
				else
					return false;
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int columnIndex) {
				if (columnIndex == 3)
					return Boolean.class;
				return super.getColumnClass(columnIndex);
			}

		};
		dtm1.setColumnIdentifiers(columns);

		JTable table = this.ventana.getTable();

		for (OrdenDTO o : this.ordenes_en_tabla_disponibles) {
			Object[] fila = { o, o.getElectrodomestico().getModelo(), o.getCliente().getDireccion(), false };
			dtm1.addRow(fila);
		}

		table.setModel(dtm1);

	}

	public void llenarTablaResultado(FleteroDTO fletero) {

		try {

			String[] columns = { "OT", "Quitar" };

			dtm2 = new DefaultTableModel(null, columns) {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 1)
						return true;
					else
						return false;
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Class getColumnClass(int columnIndex) {
					if (columnIndex == 1)
						return Boolean.class;
					return super.getColumnClass(columnIndex);
				}

			};
			dtm2.setColumnIdentifiers(columns);

			JTable table = this.ventana.getTableResultado();

			for (OrdenDTO o : this.ordenes_en_tabla_resultado) {
				Object[] fila = { o, true };
				dtm2.addRow(fila);
			}

			table.setModel(dtm2);

		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	public void cargarTablaResultado(FleteroDTO fletero) {

		// trae las de la DB ya asignadas
		try {

			String[] columns = { "OT", "Quitar" };

			dtm2 = new DefaultTableModel(null, columns) {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 1)
						return true;
					else
						return false;
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Class getColumnClass(int columnIndex) {
					if (columnIndex == 1)
						return Boolean.class;
					return super.getColumnClass(columnIndex);
				}

			};
			dtm2.setColumnIdentifiers(columns);

			JTable table = this.ventana.getTableResultado();

			List<Integer> envios = new LinkedList<Integer>();
			for (int i = 0; i < this.modelo.obtenerAsignadosA(fletero).size(); i++) {
				EnvioDTO actual = this.modelo.obtenerAsignadosA(fletero).get(i);
				envios.add(actual.getIdOT());
			}

			List<OrdenDTO> ordenesResultado = new LinkedList<OrdenDTO>();
			for (int i = 0; i < this.modelo.obtenerOrdenes().size(); i++) {
				OrdenDTO actual = this.modelo.obtenerOrdenes().get(i);
				if (envios.contains(actual.getIdOT()))
					ordenesResultado.add(actual);
			}

			this.ordenes_en_tabla_resultado = ordenesResultado;

			for (OrdenDTO o : this.ordenes_en_tabla_resultado) {
				Object[] fila = { o, true };
				dtm2.addRow(fila);
			}

			table.setModel(dtm2);

		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 1) {

			JTable target = (JTable) e.getSource();
			int row = target.getSelectedRow();
			int column = target.getSelectedColumn();

			if (e.getSource() == this.ventana.getTable()) { // asignar

				OrdenDTO orden = (OrdenDTO) ventana.getTable().getValueAt(row, 0);
				int i = fleteros.indexOf(ventana.getComboFleteros().getSelectedItem());
				EnvioDTO envio = new EnvioDTO(orden.getIdOT(), fleteros.get(i).getIdFletero());

				if ((boolean) ventana.getTable().getValueAt(row, column)) {

					nuevosEnvios.add(envio);

					this.ordenes_en_tabla_disponibles.remove(orden);
					llenarTabla();

					this.ordenes_en_tabla_resultado.add(orden);
					llenarTablaResultado(fleteros.get(i));
				}

			} else if (e.getSource() == this.ventana.getTableResultado()) { // quitar

				OrdenDTO orden = (OrdenDTO) ventana.getTableResultado().getValueAt(row, 0);
				int i = fleteros.indexOf(ventana.getComboFleteros().getSelectedItem());

				EnvioDTO envio = new EnvioDTO(orden.getIdOT(), fleteros.get(i).getIdFletero());

				if (!(boolean) ventana.getTableResultado().getValueAt(row, column)) {

					nuevosEnvios.remove(envio);

					this.ordenes_en_tabla_disponibles.add(orden);
					llenarTabla();

					this.ordenes_en_tabla_resultado.remove(orden);
					llenarTablaResultado(fleteros.get(i));
				}
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		if (e.getSource() == this.ventana.getComboFleteros()) {
			this.ventana
					.setLblEnvosAsignadosA("Envíos asignados a " + this.ventana.getComboFleteros().getSelectedItem());
			this.cargarTablaResultado((FleteroDTO) this.ventana.getComboFleteros().getSelectedItem());
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	private void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

}
