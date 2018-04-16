package presentacion.vista;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import JCDesktopPane.JCDesktopPane;
import dto.EstadoDTO;
import dto.MarcaDTO;
import dto.ProveedorDTO;

public class VistaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	JCDesktopPane jCDesktopPane1;
	private JPanel tabClientes;
	private JTabbedPane tabbedPane;
	private JComboBox<MarcaDTO> cbMarcas;
	private JScrollPane spEds;
	private JPanel tabUsuarios;
	private JButton btnAgregarCliente;
	private JButton btnNuevaOt;
	private JButton btnNuevaSc;
	private JButton btnAadirPiezas;
	private JButton btnAgregarUsuario;
	private JButton btnNuevoProveedor;
	private JTable tablePiezas;
	private JTable tableClientes;

	private JTable tableOT_UT;
	private JComboBox<EstadoDTO> comboEstadosUT;
	private JTable tableOT_UA;
	private JComboBox<EstadoDTO> comboEstadosUA;

	private JTable tableScs;
	private JTable tableEds;
	private JTable tableUsuarios;
	private JTable tableProveedores;
	private JPanel tabFleteros;
	private JButton btnNuevoFletero;
	private JTable tableFleteros;
	private JScrollPane spFleteros;
	private JMenuBar menuBar;
	private JButton btnEnviarMailOT;
	private JButton btnEnviarMailSC;

	private JMenu mnHerramientas;
	private JMenuItem btnCrearBackup;
	private JMenuItem btnCargarBackup;
	private JPanel tabOT_UT;
	private JPanel tabOT_UA;
	private JPanel panelScs;
	private JPanel panelProveedores;
	private JPanel tabPiezas;
	private JPanel tabEds;
	private JButton btnAgregarEd;
	private JMenuItem mntmDatos;
	private JMenuItem mntmSalir;
	private JMenu menuLog;
	private JMenuItem mntmActualizar;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel tabImportarPrecios;
	private JComboBox<ProveedorDTO> comboBoxProvedores;
	private JLabel lblSeleccioneElArchivo;

	private JButton btnProcesar;

	private JButton fileChooser;

	private JButton btnAsignar;
	private JMenu mnEdicin;
	private JMenuItem mntmEditarZonas;
	
	private JButton btnRankElectrodomesticos;
	private JButton btnRankPiezas;
	private JPanel panel_4;

	/** Creates new form principal */
	public VistaPrincipal() {
		super();
		try {
			UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		getContentPane().setLayout(new CardLayout(0, 0));

		setTabbedPane(new JTabbedPane(JTabbedPane.TOP));
		getContentPane().add(getTabbedPane(), "name_20870881958318");

		tabClientes = new JPanel();
		tabClientes.setToolTipText("");
		getTabbedPane().addTab("Clientes", null, tabClientes, null);
		GridBagLayout gbl_tabClientes = new GridBagLayout();
		gbl_tabClientes.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabClientes.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabClientes.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_tabClientes.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabClientes.setLayout(gbl_tabClientes);

		btnAgregarCliente = new JButton("Agregar cliente");
		GridBagConstraints gbc_btnAgregarCliente = new GridBagConstraints();
		gbc_btnAgregarCliente.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAgregarCliente.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregarCliente.gridx = 1;
		gbc_btnAgregarCliente.gridy = 1;
		tabClientes.add(btnAgregarCliente, gbc_btnAgregarCliente);

		JScrollPane spDatosCliente = new JScrollPane();
		GridBagConstraints gbc_spDatosCliente = new GridBagConstraints();
		gbc_spDatosCliente.fill = GridBagConstraints.BOTH;
		gbc_spDatosCliente.gridx = 1;
		gbc_spDatosCliente.gridy = 2;
		tabClientes.add(spDatosCliente, gbc_spDatosCliente);

		tableClientes = new JTable();
		spDatosCliente.setColumnHeaderView(tableClientes);
		spDatosCliente.setViewportView(tableClientes);

		tabOT_UT = new JPanel();
		getTabbedPane().addTab("Ordenes de trabajo - UT", null, tabOT_UT, null);
		GridBagLayout gbl_tabOT_UT = new GridBagLayout();
		gbl_tabOT_UT.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabOT_UT.rowHeights = new int[] { 5, 32, 367, 0 };
		gbl_tabOT_UT.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_tabOT_UT.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabOT_UT.setLayout(gbl_tabOT_UT);

		tabOT_UA = new JPanel();
		getTabbedPane().addTab("Ordenes de trabajo - UA", null, tabOT_UA, null);
		GridBagLayout gbl_tabOT_UA = new GridBagLayout();
		gbl_tabOT_UA.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabOT_UA.rowHeights = new int[] { 5, 32, 367, 0 };
		gbl_tabOT_UA.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_tabOT_UA.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		tabOT_UA.setLayout(gbl_tabOT_UA);

		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		tabOT_UA.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 46, 118, 188, 119, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 23, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblEstado = new JLabel("Estado:");
		GridBagConstraints gbc_lblEstado = new GridBagConstraints();
		gbc_lblEstado.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEstado.insets = new Insets(0, 0, 0, 5);
		gbc_lblEstado.gridx = 0;
		gbc_lblEstado.gridy = 0;
		panel_1.add(lblEstado, gbc_lblEstado);

		comboEstadosUA = new JComboBox<EstadoDTO>();
		GridBagConstraints gbc_comboEstadosUA = new GridBagConstraints();
		gbc_comboEstadosUA.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEstadosUA.insets = new Insets(0, 0, 0, 5);
		gbc_comboEstadosUA.gridx = 1;
		gbc_comboEstadosUA.gridy = 0;
		panel_1.add(comboEstadosUA, gbc_comboEstadosUA);
		comboEstadosUA.addItem(new EstadoDTO(0, "seleccionar"));
		comboEstadosUA.addItem(new EstadoDTO(1, "Ingresada"));
		comboEstadosUA.addItem(new EstadoDTO(2, "Presupuestada"));
		comboEstadosUA.addItem(new EstadoDTO(3, "Aprobada"));
		comboEstadosUA.addItem(new EstadoDTO(4, "Desaprobada"));
		comboEstadosUA.addItem(new EstadoDTO(5, "En reparación"));
		comboEstadosUA.addItem(new EstadoDTO(6, "En espera de piezas"));
		comboEstadosUA.addItem(new EstadoDTO(7, "Reparada"));
		comboEstadosUA.addItem(new EstadoDTO(8, "Irreparable"));
		comboEstadosUA.addItem(new EstadoDTO(9, "Despachada"));
		comboEstadosUA.addItem(new EstadoDTO(10, "Entregada"));

		btnNuevaOt = new JButton("Nueva orden de trabajo");
		GridBagConstraints gbc_btnNuevaOt = new GridBagConstraints();
		gbc_btnNuevaOt.anchor = GridBagConstraints.NORTH;
		gbc_btnNuevaOt.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNuevaOt.insets = new Insets(0, 0, 0, 5);
		gbc_btnNuevaOt.gridx = 2;
		gbc_btnNuevaOt.gridy = 0;
		panel_1.add(btnNuevaOt, gbc_btnNuevaOt);

		btnEnviarMailOT = new JButton("Enviar mail");
		GridBagConstraints gbc_btnEnviarMailOT = new GridBagConstraints();
		gbc_btnEnviarMailOT.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnviarMailOT.anchor = GridBagConstraints.NORTH;
		gbc_btnEnviarMailOT.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnviarMailOT.gridx = 3;
		gbc_btnEnviarMailOT.gridy = 0;
		panel_1.add(btnEnviarMailOT, gbc_btnEnviarMailOT);

		btnAsignar = new JButton("Asignar OTs");
		GridBagConstraints gbc_btnNuevaHojaDe = new GridBagConstraints();
		gbc_btnNuevaHojaDe.gridx = 4;
		gbc_btnNuevaHojaDe.gridy = 0;
		panel_1.add(btnAsignar, gbc_btnNuevaHojaDe);

		JScrollPane spDatosOT_UA = new JScrollPane();
		GridBagConstraints gbc_spDatosOT_UA = new GridBagConstraints();
		gbc_spDatosOT_UA.fill = GridBagConstraints.BOTH;
		gbc_spDatosOT_UA.gridx = 1;
		gbc_spDatosOT_UA.gridy = 2;
		tabOT_UA.add(spDatosOT_UA, gbc_spDatosOT_UA);

		tableOT_UA = new JTable();
		spDatosOT_UA.setViewportView(tableOT_UA);

		panelScs = new JPanel();
		getTabbedPane().addTab("Solicitudes de compra", null, panelScs, null);
		GridBagLayout gbl_panelScs = new GridBagLayout();
		gbl_panelScs.columnWidths = new int[] { 5, 830, 0 };
		gbl_panelScs.rowHeights = new int[] { 5, 34, 367, 0 };
		gbl_panelScs.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_panelScs.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		panelScs.setLayout(gbl_panelScs);

		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.WEST;
		gbc_panel_2.fill = GridBagConstraints.VERTICAL;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panelScs.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 211, 119, 0 };
		gbl_panel_2.rowHeights = new int[] { 23, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		btnNuevaSc = new JButton("Nueva solicitud de compra");
		GridBagConstraints gbc_btnNuevaSc = new GridBagConstraints();
		gbc_btnNuevaSc.anchor = GridBagConstraints.NORTH;
		gbc_btnNuevaSc.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNuevaSc.insets = new Insets(0, 0, 0, 5);
		gbc_btnNuevaSc.gridx = 0;
		gbc_btnNuevaSc.gridy = 0;
		panel_2.add(btnNuevaSc, gbc_btnNuevaSc);

		btnEnviarMailSC = new JButton("Enviar por mail");
		GridBagConstraints gbc_btnEnviarMailSC = new GridBagConstraints();
		gbc_btnEnviarMailSC.anchor = GridBagConstraints.NORTH;
		gbc_btnEnviarMailSC.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnviarMailSC.gridx = 1;
		gbc_btnEnviarMailSC.gridy = 0;
		panel_2.add(btnEnviarMailSC, gbc_btnEnviarMailSC);

		JScrollPane spDatosScs = new JScrollPane();
		GridBagConstraints gbc_spDatosScs = new GridBagConstraints();
		gbc_spDatosScs.fill = GridBagConstraints.BOTH;
		gbc_spDatosScs.gridx = 1;
		gbc_spDatosScs.gridy = 2;
		panelScs.add(spDatosScs, gbc_spDatosScs);

		tableScs = new JTable();
		spDatosScs.setViewportView(tableScs);

		panelProveedores = new JPanel();
		getTabbedPane().addTab("Proveedores", null, panelProveedores, null);
		GridBagLayout gbl_panelProveedores = new GridBagLayout();
		gbl_panelProveedores.columnWidths = new int[] { 5, 830, 0 };
		gbl_panelProveedores.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_panelProveedores.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_panelProveedores.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		panelProveedores.setLayout(gbl_panelProveedores);

		btnNuevoProveedor = new JButton("A\u00F1adir proveedor");
		GridBagConstraints gbc_btnNuevoProveedor = new GridBagConstraints();
		gbc_btnNuevoProveedor.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNuevoProveedor.insets = new Insets(0, 0, 5, 0);
		gbc_btnNuevoProveedor.gridx = 1;
		gbc_btnNuevoProveedor.gridy = 1;
		panelProveedores.add(btnNuevoProveedor, gbc_btnNuevoProveedor);

		JScrollPane spProveedores = new JScrollPane();
		GridBagConstraints gbc_spProveedores = new GridBagConstraints();
		gbc_spProveedores.fill = GridBagConstraints.BOTH;
		gbc_spProveedores.gridx = 1;
		gbc_spProveedores.gridy = 2;
		panelProveedores.add(spProveedores, gbc_spProveedores);

		tableProveedores = new JTable();
		spProveedores.setViewportView(tableProveedores);

		tabPiezas = new JPanel();
		getTabbedPane().addTab("Piezas", null, tabPiezas, null);
		GridBagLayout gbl_tabPiezas = new GridBagLayout();
		gbl_tabPiezas.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabPiezas.rowHeights = new int[] { 5, 37, 370, 0 };
		gbl_tabPiezas.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabPiezas.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabPiezas.setLayout(gbl_tabPiezas);

		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.WEST;
		gbc_panel_3.fill = GridBagConstraints.VERTICAL;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 1;
		tabPiezas.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 133, 115, 0 };
		gbl_panel_3.rowHeights = new int[] { 23, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		cbMarcas = new JComboBox<MarcaDTO>();
		GridBagConstraints gbc_cbMarcas = new GridBagConstraints();
		gbc_cbMarcas.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbMarcas.insets = new Insets(0, 0, 0, 5);
		gbc_cbMarcas.gridx = 0;
		gbc_cbMarcas.gridy = 0;
		panel_3.add(cbMarcas, gbc_cbMarcas);

		btnAadirPiezas = new JButton("A\u00F1adir piezas");
		GridBagConstraints gbc_btnAadirPiezas = new GridBagConstraints();
		gbc_btnAadirPiezas.anchor = GridBagConstraints.NORTH;
		gbc_btnAadirPiezas.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAadirPiezas.gridx = 1;
		gbc_btnAadirPiezas.gridy = 0;
		panel_3.add(btnAadirPiezas, gbc_btnAadirPiezas);

		JScrollPane spDatosPiezas = new JScrollPane();
		GridBagConstraints gbc_spDatosPiezas = new GridBagConstraints();
		gbc_spDatosPiezas.fill = GridBagConstraints.BOTH;
		gbc_spDatosPiezas.gridx = 1;
		gbc_spDatosPiezas.gridy = 2;
		tabPiezas.add(spDatosPiezas, gbc_spDatosPiezas);

		tablePiezas = new JTable();
		spDatosPiezas.setViewportView(tablePiezas);

		tabEds = new JPanel();
		getTabbedPane().addTab("Electrodomesticos", null, tabEds, null);
		GridBagLayout gbl_tabEds = new GridBagLayout();
		gbl_tabEds.columnWidths = new int[]{5, 830, 0};
		gbl_tabEds.rowHeights = new int[]{5, 23, 367, 0};
		gbl_tabEds.columnWeights = new double[]{0.0, 0.1, Double.MIN_VALUE};
		gbl_tabEds.rowWeights = new double[]{0.0, 0.0, 0.1, Double.MIN_VALUE};
		tabEds.setLayout(gbl_tabEds);
		
			panel_4 = new JPanel();
			GridBagConstraints gbc_panel_4 = new GridBagConstraints();
			gbc_panel_4.anchor = GridBagConstraints.WEST;
			gbc_panel_4.fill = GridBagConstraints.VERTICAL;
			gbc_panel_4.insets = new Insets(0, 0, 5, 0);
			gbc_panel_4.gridx = 1;
			gbc_panel_4.gridy = 1;
			tabEds.add(panel_4, gbc_panel_4);
			GridBagLayout gbl_panel_4 = new GridBagLayout();
			gbl_panel_4.columnWidths = new int[]{133, 115, 0};
			gbl_panel_4.rowHeights = new int[]{23, 0};
			gbl_panel_4.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panel_4.setLayout(gbl_panel_4);
			
				btnAgregarEd = new JButton("Agregar electrodom\u00E9stico");
				GridBagConstraints gbc_btnAgregarEd = new GridBagConstraints();
				gbc_btnAgregarEd.anchor = GridBagConstraints.NORTHWEST;
				gbc_btnAgregarEd.insets = new Insets(0, 0, 5, 5);
				gbc_btnAgregarEd.gridx = 0;
				gbc_btnAgregarEd.gridy = 0;
				panel_4.add(btnAgregarEd, gbc_btnAgregarEd);
			
						btnRankElectrodomesticos = new JButton("Mas vendidos");
						GridBagConstraints gbc_btnRankElectrodomesticos = new GridBagConstraints();
						gbc_btnRankElectrodomesticos.insets = new Insets(0, 0, 5, 5);
						gbc_btnRankElectrodomesticos.gridx = 1;
						gbc_btnRankElectrodomesticos.gridy = 0;
						panel_4.add(btnRankElectrodomesticos, gbc_btnRankElectrodomesticos);
		
		
		
				spEds = new JScrollPane();
				GridBagConstraints gbc_spEds = new GridBagConstraints();
				gbc_spEds.fill = GridBagConstraints.BOTH;
				gbc_spEds.gridx = 1;
				gbc_spEds.gridy = 2;
				tabEds.add(spEds, gbc_spEds);
				
						tableEds = new JTable();
						spEds.setViewportView(tableEds);

		tabUsuarios = new JPanel();
		getTabbedPane().addTab("Usuarios", null, tabUsuarios, null);
		GridBagLayout gbl_tabUsuarios = new GridBagLayout();
		gbl_tabUsuarios.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabUsuarios.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabUsuarios.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabUsuarios.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabUsuarios.setLayout(gbl_tabUsuarios);

		btnAgregarUsuario = new JButton("Agregar Usuario");
		GridBagConstraints gbc_btnAgregarUsuario = new GridBagConstraints();
		gbc_btnAgregarUsuario.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAgregarUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregarUsuario.gridx = 1;
		gbc_btnAgregarUsuario.gridy = 1;
		tabUsuarios.add(btnAgregarUsuario, gbc_btnAgregarUsuario);

		JScrollPane spUsuarios = new JScrollPane();
		GridBagConstraints gbc_spUsuarios = new GridBagConstraints();
		gbc_spUsuarios.fill = GridBagConstraints.BOTH;
		gbc_spUsuarios.gridx = 1;
		gbc_spUsuarios.gridy = 2;
		tabUsuarios.add(spUsuarios, gbc_spUsuarios);

		tableUsuarios = new JTable();
		spUsuarios.setViewportView(tableUsuarios);

		tabFleteros = new JPanel();
		tabbedPane.addTab("Fleteros", null, tabFleteros, null);
		GridBagLayout gbl_tabFleteros = new GridBagLayout();
		gbl_tabFleteros.columnWidths = new int[] { 5, 830, 0 };
		gbl_tabFleteros.rowHeights = new int[] { 5, 23, 367, 0 };
		gbl_tabFleteros.columnWeights = new double[] { 0.0, 0.1, Double.MIN_VALUE };
		gbl_tabFleteros.rowWeights = new double[] { 0.0, 0.0, 0.1, Double.MIN_VALUE };
		tabFleteros.setLayout(gbl_tabFleteros);

		btnNuevoFletero = new JButton("Agregar");
		GridBagConstraints gbc_btnNuevoFletero = new GridBagConstraints();
		gbc_btnNuevoFletero.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNuevoFletero.insets = new Insets(0, 0, 5, 0);
		gbc_btnNuevoFletero.gridx = 1;
		gbc_btnNuevoFletero.gridy = 1;
		tabFleteros.add(btnNuevoFletero, gbc_btnNuevoFletero);

		spFleteros = new JScrollPane();
		GridBagConstraints gbc_spFleteros = new GridBagConstraints();
		gbc_spFleteros.fill = GridBagConstraints.BOTH;
		gbc_spFleteros.gridx = 1;
		gbc_spFleteros.gridy = 2;
		tabFleteros.add(spFleteros, gbc_spFleteros);

		tableFleteros = new JTable();
		spFleteros.setViewportView(tableFleteros);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnEdicin = new JMenu("Edici\u00F3n");
		menuBar.add(mnEdicin);

		mntmEditarZonas = new JMenuItem("Editar Zonas");
		mnEdicin.add(mntmEditarZonas);

		mnHerramientas = new JMenu("Herramientas");
		menuBar.add(mnHerramientas);
		menuBar.add(Box.createHorizontalGlue());

		btnCrearBackup = new JMenuItem("Crear backup");
		mnHerramientas.add(btnCrearBackup);

		btnCargarBackup = new JMenuItem("Cargar backup");
		mnHerramientas.add(btnCargarBackup);

		mntmActualizar = new JMenuItem("Actualizar");
		mnHerramientas.add(mntmActualizar);

		menuLog = new JMenu("New menu");
		menuBar.add(menuLog);

		mntmDatos = new JMenuItem("Mis datos");
		menuLog.add(mntmDatos);

		mntmSalir = new JMenuItem("Salir");
		menuLog.add(mntmSalir);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		tabOT_UT.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 53, 118, 0 };
		gbl_panel.rowHeights = new int[] { 20, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblEstadoUT = new JLabel("Estado:");
		GridBagConstraints gbc_lblEstadoUT = new GridBagConstraints();
		gbc_lblEstadoUT.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEstadoUT.insets = new Insets(0, 0, 0, 5);
		gbc_lblEstadoUT.gridx = 0;
		gbc_lblEstadoUT.gridy = 0;
		panel.add(lblEstadoUT, gbc_lblEstadoUT);

		comboEstadosUT = new JComboBox<EstadoDTO>();
		GridBagConstraints gbc_comboEstadosUT = new GridBagConstraints();
		gbc_comboEstadosUT.fill = GridBagConstraints.BOTH;
		gbc_comboEstadosUT.gridx = 1;
		gbc_comboEstadosUT.gridy = 0;
		panel.add(comboEstadosUT, gbc_comboEstadosUT);
		comboEstadosUT.addItem(new EstadoDTO(1, "Ingresada"));
		comboEstadosUT.addItem(new EstadoDTO(3, "Aprobada"));
		comboEstadosUT.addItem(new EstadoDTO(5, "En reparación"));

		JScrollPane spDatosOT_UT = new JScrollPane();
		GridBagConstraints gbc_spDatosOT_UT = new GridBagConstraints();
		gbc_spDatosOT_UT.fill = GridBagConstraints.BOTH;
		gbc_spDatosOT_UT.gridx = 1;
		gbc_spDatosOT_UT.gridy = 2;
		tabOT_UT.add(spDatosOT_UT, gbc_spDatosOT_UT);

		tableOT_UT = new JTable();
		spDatosOT_UT.setViewportView(tableOT_UT);

		tabImportarPrecios = new JPanel();
		tabbedPane.addTab("Importar precios proveedores", null, tabImportarPrecios, null);
		tabImportarPrecios.setLayout(null);

		comboBoxProvedores = new JComboBox<ProveedorDTO>();
		comboBoxProvedores.setBounds(132, 45, 154, 20);
		tabImportarPrecios.add(comboBoxProvedores);

		JLabel lblNewLabel = new JLabel("Proveedor:");
		lblNewLabel.setBounds(43, 45, 68, 20);
		tabImportarPrecios.add(lblNewLabel);

		fileChooser = new JButton("Seleccionar Archivo");
		fileChooser.setBounds(509, 45, 112, 20);
		tabImportarPrecios.add(fileChooser);

		lblSeleccioneElArchivo = new JLabel("Seleccione el archivo:");
		lblSeleccioneElArchivo.setBounds(380, 45, 103, 20);
		tabImportarPrecios.add(lblSeleccioneElArchivo);

		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(255, 95, 89, 23);
		tabImportarPrecios.add(btnProcesar);

		initialize();
	}

	public JMenuItem getMntmActualizar() {
		return mntmActualizar;
	}

	public JButton getBtnEnviarMailOT() {
		return btnEnviarMailOT;
	}

	public JButton getBtnEnviarMailSC() {
		return btnEnviarMailSC;
	}

	public JMenuItem getMntmDatos() {
		return mntmDatos;
	}

	public JMenuItem getMntmSalir() {
		return mntmSalir;
	}

	public JMenu getMenuLog() {
		return menuLog;
	}

	public JButton getBtnAgregarEd() {
		return btnAgregarEd;
	}

	public JPanel getTabUsuarios() {
		return tabUsuarios;
	}

	public JPanel getTabOts() {
		return tabOT_UT;
	}

	public JPanel getPanelScs() {
		return panelScs;
	}

	public JPanel getPanelProveedores() {
		return panelProveedores;
	}

	public JPanel getTabPiezas() {
		return tabPiezas;
	}

	public JPanel getTabEds() {
		return tabEds;
	}

	public JMenuItem getBtnCrearBackup() {
		return btnCrearBackup;
	}

	public void setBtnCrearBackup(JMenuItem btnCrearBackup) {
		this.btnCrearBackup = btnCrearBackup;
	}

	public JMenuItem getBtnCargarBackup() {
		return btnCargarBackup;
	}

	public void setBtnCargarBackup(JMenuItem btnCargarBackup) {
		this.btnCargarBackup = btnCargarBackup;
	}

	public JButton getBtnAgregarUsuario() {
		return btnAgregarUsuario;
	}

	public JTable getTableUsuarios() {
		return tableUsuarios;
	}

	public JTable getTablePiezas() {
		return tablePiezas;
	}

	public JComboBox<MarcaDTO> getCbMarcas() {
		return cbMarcas;
	}

	public JTable getTableEds() {
		return tableEds;
	}

	public JButton getBtnAadirPiezas() {
		return btnAadirPiezas;
	}

	public JTable getTableScs() {
		return tableScs;
	}

	public JButton getBtnNuevaSc() {
		return btnNuevaSc;
	}

	public JButton getBtnNuevaOt() {
		return btnNuevaOt;
	}

	// public JTable getTableOts() {
	// return tableOT_UT;
	// }

	public JButton getBtnAgregarCliente() {
		return btnAgregarCliente;
	}

	public JTable getTableClientes() {
		return tableClientes;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 913, 487);
		jCDesktopPane1 = new JCDesktopPane();

	}

	public JCDesktopPane getjCDesktopPane1() {
		return jCDesktopPane1;
	}

	public JPanel getTabClientes() {
		return tabClientes;
	}

	public JTable getTableProveedores() {
		return tableProveedores;
	}

	public JButton getBtnNuevoProveedor() {
		return btnNuevoProveedor;
	}

	private void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public JPanel getTabFleteros() {
		return tabFleteros;
	}

	public void setTabFleteros(JPanel tabFleteros) {
		this.tabFleteros = tabFleteros;
	}

	public JButton getBtnNuevoFletero() {
		return btnNuevoFletero;
	}

	public void setBtnNuevoFletero(JButton btnNuevoFletero) {
		this.btnNuevoFletero = btnNuevoFletero;
	}

	public JTable getTableFleteros() {
		return tableFleteros;
	}

	public void setTableFleteros(JTable tableFleteros) {
		this.tableFleteros = tableFleteros;
	}

	public JComboBox<EstadoDTO> getComboEstadosUT() {
		return comboEstadosUT;
	}

	public void setComboEstadosUT(JComboBox<EstadoDTO> comboEstadosUT) {
		this.comboEstadosUT = comboEstadosUT;
	}

	public JComboBox<EstadoDTO> getComboEstadosUA() {
		return comboEstadosUA;
	}

	public void setComboEstadosUA(JComboBox<EstadoDTO> comboEstadosUA) {
		this.comboEstadosUA = comboEstadosUA;
	}

	public void setBtnNuevaOt(JButton btnNuevaOt) {
		this.btnNuevaOt = btnNuevaOt;
	}

	public void setBtnNuevaSc(JButton btnNuevaSc) {
		this.btnNuevaSc = btnNuevaSc;
	}

	public void setBtnEnviarMailOT(JButton btnEnviarMailOT) {
		this.btnEnviarMailOT = btnEnviarMailOT;
	}

	public void setBtnEnviarMailSC(JButton btnEnviarMailSC) {
		this.btnEnviarMailSC = btnEnviarMailSC;
	}

	public JTable getTableOT_UT() {
		return tableOT_UT;
	}

	public void setTableOT_UT(JTable tableOT_UT) {
		this.tableOT_UT = tableOT_UT;
	}

	public JTable getTableOT_UA() {
		return tableOT_UA;
	}

	public void setTableOT_UA(JTable tableOT_UA) {
		this.tableOT_UA = tableOT_UA;
	}

	public JPanel getTabOT_UT() {
		return tabOT_UT;
	}

	public void setTabOT_UT(JPanel tabOT_UT) {
		this.tabOT_UT = tabOT_UT;
	}

	public JPanel getTabOT_UA() {
		return tabOT_UA;
	}

	public void setTabOT_UA(JPanel tabOT_UA) {
		this.tabOT_UA = tabOT_UA;
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

	public JPanel getTabImportarPrecios() {
		return tabImportarPrecios;
	}

	public JComboBox<ProveedorDTO> getComboBoxProvedores() {
		return comboBoxProvedores;
	}

	public JButton getBtnProcesar() {
		return btnProcesar;
	}

	public JButton getFileChooser() {
		return fileChooser;
	}

	public JButton getBtnAsignar() {
		return btnAsignar;
	}

	public void setBtnAsignar(JButton btnAsignar) {
		this.btnAsignar = btnAsignar;
	}

	public JMenuItem getMntmEditarZonas() {
		return mntmEditarZonas;
	}

	public void setMntmEditarZonas(JMenuItem mntmEditarZonas) {
		this.mntmEditarZonas = mntmEditarZonas;
	}

	public JButton getBtnRankElectrodomesticos() {
		return btnRankElectrodomesticos;
	}

	public void setBtnRankElectrodomesticos(JButton btnRankElectrodomesticos) {
		this.btnRankElectrodomesticos = btnRankElectrodomesticos;
	}

	public JButton getBtnRankPiezas() {
		return btnRankPiezas;
	}

	public void setBtnRankPiezas(JButton btnRankPiezas) {
		this.btnRankPiezas = btnRankPiezas;
	}
	
}
