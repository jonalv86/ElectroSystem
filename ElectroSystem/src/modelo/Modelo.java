package modelo;

import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.CostoVariableDTO;
import dto.ElectrodomesticoDTO;
import dto.EnvioDTO;
import dto.FleteroDTO;
import dto.LocalidadDTO;
import dto.MarcaDTO;
import dto.OrdenDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import dto.RankElectrodomesticosDTO;
import dto.RankPiezasDTO;
import dto.SolicitudCompraDTO;
import dto.UsuarioDTO;
import dto.VehiculoDTO;
import dto.ZonaDTO;
import persistencia.dao.ClienteDAO;
import persistencia.dao.CostoVariableDAO;
import persistencia.dao.ElectrodomesticoDAO;
import persistencia.dao.EnvioDAO;
import persistencia.dao.FleteroDAO;
import persistencia.dao.LocalidadDAO;
import persistencia.dao.MarcaDAO;
import persistencia.dao.OrdenDAO;
import persistencia.dao.PiezaDAO;
import persistencia.dao.ProveedorDAO;
import persistencia.dao.ReporteDAO;
import persistencia.dao.SolicitudCompraDAO;
import persistencia.dao.UsuarioDAO;
import persistencia.dao.VehiculoDAO;
import persistencia.dao.ZonaDAO;

public class Modelo {
	private ClienteDAO cliente;
	private OrdenDAO ordenTrabajo;
	private ElectrodomesticoDAO electrodomestico;
	private ProveedorDAO proveedor;
	private MarcaDAO marca;
	private PiezaDAO pieza;
	private SolicitudCompraDAO solicitudCompra;
	private CostoVariableDAO costoVariable;
	private UsuarioDAO usuario;
	private FleteroDAO fletero;
	private VehiculoDAO vehiculo;
	private LocalidadDAO localidad;
	private ZonaDAO zona;
	private EnvioDAO envio;
	private ReporteDAO reportes;

	public Modelo() {
		cliente = new ClienteDAO();
		ordenTrabajo = new OrdenDAO();
		electrodomestico = new ElectrodomesticoDAO();
		marca = new MarcaDAO();
		proveedor = new ProveedorDAO();
		pieza = new PiezaDAO();
		solicitudCompra = new SolicitudCompraDAO();
		costoVariable = new CostoVariableDAO();
		usuario = new UsuarioDAO();
		fletero = new FleteroDAO();
		vehiculo = new VehiculoDAO();
		localidad = new LocalidadDAO();
		zona = new ZonaDAO();
		envio = new EnvioDAO();
		reportes = new ReporteDAO();
	}

	public void agregarCliente(ClienteDTO nuevoCliente) throws Exception {
		try {
			cliente.insert(nuevoCliente);
		} catch (Exception e) {
			throw e;
		}
	}

	public void borrarCliente(ClienteDTO cliente, int idPersona) throws Exception {
		try {
			this.cliente.delete(cliente, idPersona);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<ClienteDTO> obtenerClientes() throws Exception {
		try {
			return cliente.readAll();
		} catch (Exception e) {
			throw e;
		}
	}

	public void actualizarCliente(ClienteDTO cliente) throws Exception {
		try {
			this.cliente.update(cliente);
		} catch (Exception e) {
			throw e;
		}
	}

	public void agregarOrden(OrdenDTO nuevaOrden) throws Exception {
		try {
			ordenTrabajo.insert(nuevaOrden);
		} catch (Exception e) {
			throw e;
		}
	}

	public void presupuestarOT(OrdenDTO orden) throws Exception {
		try {
			ordenTrabajo.presupuestar(orden);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<OrdenDTO> obtenerOrdenes() {
		return this.ordenTrabajo.readAll();
	}

	public List<OrdenDTO> obtenerOrdenesPorEstado(int idEstado) throws Exception {
		return ordenTrabajo.readAllForState(idEstado);
	}

	public List<ElectrodomesticoDTO> obtenerElectrodomesticos() throws Exception {
		try {
			return electrodomestico.readAll();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<MarcaDTO> obtenerMarcas() {
		return marca.readAll();
	}

	public void agregarElectrodomestico(ElectrodomesticoDTO electrodomestico) throws Exception {
		try {
			this.electrodomestico.insert(electrodomestico);
		} catch (Exception e) {
			throw e;
		}
	}

	public void actualizarElectrodomestico(ElectrodomesticoDTO electrodomestico) throws Exception {
		try {
			this.electrodomestico.update(electrodomestico);
		} catch (Exception e) {
			throw e;
		}
	}

	public void agregarMarca(MarcaDTO nuevaMarca) throws Exception {
		try {
			marca.insert(nuevaMarca);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarMarca(MarcaDTO marca_a_actualizar) {
		marca.update(marca_a_actualizar);
	}

	public List<PiezaDTO> obtenerItems() throws Exception {
		try {
			return pieza.readAll();
		} catch (Exception e) {
			throw e;
		}
	}

	public void borrarElectrodomestico(ElectrodomesticoDTO electrodomestico, int idPersonal) throws Exception {
		try {
			this.electrodomestico.delete(electrodomestico, idPersonal);
		} catch (Exception e) {
			throw e;
		}

	}

	public void agregarSolicitud(SolicitudCompraDTO sc) throws Exception {
		try {
			solicitudCompra.insert(sc);
		} catch (Exception e) {
			throw e;
		}
	}

	public void procesarSolicitud(SolicitudCompraDTO sc) throws Exception {
		try {
			solicitudCompra.procesarSolicitud(sc);
		} catch (Exception e) {
			throw e;
		}
	}

	public void actualizarSolicitud(SolicitudCompraDTO sc) throws Exception {
		try {
			solicitudCompra.update(sc);
		} catch (Exception e) {
			throw e;
		}

	}

	public void cambiarEstado(SolicitudCompraDTO sc, int idEstado) throws Exception {
		try {
			solicitudCompra.cambiarEstado(sc, idEstado);
		} catch (Exception e) {
			throw e;
		}
	}

	public List<SolicitudCompraDTO> obtenerSolicitudes() throws Exception {
		try {
			List<SolicitudCompraDTO> solicitudes = solicitudCompra.readAll();
			for (SolicitudCompraDTO s : solicitudes) {
				s.getProveedor().setMarcas(this.proveedor.traerMarcasProvistas(s.getProveedor().getIdProveedor()));
			}
			return solicitudes;
		} catch (Exception e) {
			throw e;
		}

	}

	public List<MarcaDTO> obtenerMarcasProveedor(int idProveedor) throws Exception {
		try {
			return this.proveedor.traerMarcasProvistas(idProveedor);
		} catch (Exception e) {
			throw e;
		}

	}

	public void updatePrecioPiezaProveedor(int idProveedor, List<PrecioPiezaDTO> piezas) throws Exception {
		try {
			this.proveedor.updatePrecioPiezaProveedor(idProveedor, piezas);
		} catch (Exception e) {
			throw e;
		}

	}

	public void agregarPieza(PiezaDTO pieza) throws Exception {
		try {
			this.pieza.insert(pieza);
		} catch (Exception e) {
			throw e;
		}
	}

	public void eliminarPieza(PiezaDTO pieza, int idPersona) throws Exception {
		try {
			this.pieza.delete(pieza, idPersona);
		} catch (Exception e) {
			throw e;
		}
	}

	public void modificarPieza(PiezaDTO pieza, int idMarca) throws Exception {
		try {
			this.pieza.update(pieza, idMarca);
		} catch (Exception e) {
			throw e;
		}
	}

	public CostoVariableDTO obtenerValorHora() throws Exception {
		try {
			return costoVariable.obtenerValorManoObra();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<UsuarioDTO> obtenerUsuarios() throws Exception {
		try {
			return usuario.readAll();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<UsuarioDTO> obtenerUsuariosComunes() throws Exception {
		try {
			return usuario.readComunes();
		} catch (Exception e) {
			throw e;
		}
	}

	public void agregarUsuario(UsuarioDTO usuario) throws Exception {
		try {
			this.usuario.insert(usuario);
		} catch (Exception e) {
			throw e;
		}
	}

	public void borrarUsuario(UsuarioDTO usuario_a_eliminar, UsuarioDTO usuarioLogueado) throws Exception {
		try {
			this.usuario.delete(usuario_a_eliminar, usuarioLogueado);
		} catch (Exception e) {
			throw e;
		}
	}

	public UsuarioDTO obtenerUsuario(String Usuario) throws Exception {
		try {
			return (this.usuario.obtenerUsuario(Usuario));
		} catch (Exception e) {
			throw e;
		}
	}

	public void agregarFletero(FleteroDTO fletero) throws Exception {
		try {
			this.fletero.insert(fletero);
		} catch (Exception exc) {
			throw exc;
		}
	}

	public List<FleteroDTO> obtenerFleteros() throws Exception {
		try {
			return fletero.readAll();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<VehiculoDTO> obtenerVehiculos() {
		return this.vehiculo.readAll();
	}

	public void agregarVehiculo(VehiculoDTO vehiculo) {
		this.vehiculo.insert(vehiculo);
	}

	public void agregarProveedor(ProveedorDTO proveedor_nuevo) throws Exception {
		try {
			this.proveedor.insert(proveedor_nuevo);
		} catch (Exception e) {
			throw e;
		}
	}

	public void insertarMarcaProveedor(int idProveedor, int idMarca) throws Exception {
		try {
			this.proveedor.insertarMarcaProveedor(idProveedor, idMarca);
		} catch (Exception e) {
			throw e;
		}
	}

	public void actualizarProveedor(ProveedorDTO proveedor_a_actualizar, List<MarcaDTO> marcas_anterior)
			throws Exception {
		try {
			this.proveedor.update(proveedor_a_actualizar,
					marcas_a_borrar(marcas_anterior, proveedor_a_actualizar.getMarcas()),
					marcas_a_agregar(marcas_anterior, proveedor_a_actualizar.getMarcas()));
		} catch (Exception e) {
			throw e;
		}
	}

	private List<MarcaDTO> marcas_a_borrar(List<MarcaDTO> anteriores, List<MarcaDTO> nuevas) {
		List<MarcaDTO> marcas_a_quitar = new ArrayList<MarcaDTO>();
		for (MarcaDTO anterior : anteriores) {
			boolean quitar = true;
			for (MarcaDTO nueva : nuevas) {
				if (nueva.equals(anterior))
					quitar = false;
			}
			if (quitar == true)
				marcas_a_quitar.add(anterior);
		}
		return marcas_a_quitar;
	}

	private List<MarcaDTO> marcas_a_agregar(List<MarcaDTO> anteriores, List<MarcaDTO> nuevas) {
		List<MarcaDTO> marcas_a_agregar = new ArrayList<MarcaDTO>();
		for (MarcaDTO nueva : nuevas) {
			boolean agregar = true;
			for (MarcaDTO anterior : anteriores) {
				if (nueva.equals(anterior))
					agregar = false;
			}
			if (agregar == true)
				marcas_a_agregar.add(nueva);
		}
		return marcas_a_agregar;
	}

	public void borrarProveedor(ProveedorDTO proveedor_a_eliminar, UsuarioDTO usuarioLogueado) throws Exception {
		try {
			this.proveedor.delete(proveedor_a_eliminar, usuarioLogueado);
		} catch (Exception e) {
			throw e;
		}

	}

	public List<ProveedorDTO> obtenerProveedores() throws Exception {
		try {
			return proveedor.readAll2();
		} catch (Exception e) {
			throw e;
		}
	}

	public List<ProveedorDTO> obtenerProveedores2() throws Exception {
		try {
			return proveedor.readAll2();
		} catch (Exception e) {
			throw e;
		}
	}

	public void actualizarUsuario(UsuarioDTO usuario_a_editar) throws Exception {
		try {
			this.usuario.update(usuario_a_editar);
		} catch (Exception e) {
			throw e;
		}
	}

	public void borrarFletero(int fletero, int idPersonal) {

		try {
			this.fletero.delete(fletero, idPersonal);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void actualizarFletero(FleteroDTO fletero_a_editar) {
		this.fletero.update(fletero_a_editar);
	}

	public boolean verificarClave(UsuarioDTO usuario, String clave) {
		try {
			return this.usuario.verificarClave(usuario, clave);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void guardarClaveDesbl(String clave) {
		try {
			this.usuario.guardarClaveDesbl(clave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String obtenerMail() {
		try {
			return this.usuario.readMail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean verificarClaveReest(String clave) {
		try {
			return this.usuario.verificarClaveReest(clave);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void reestablecerSuperUsuario() {
		try {
			this.usuario.reestablecerSuperusuario();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarEstado(OrdenDTO orden, int id) {
		try {
			this.ordenTrabajo.actualizarEstado(orden, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void altaStockPieza(PiezaDTO pieza, int cantidad) throws Exception {
		this.pieza.altaStock(pieza, cantidad);
	}

	public void eliminarVehiculo(VehiculoDTO vehiculo, UsuarioDTO usuario) {
		this.vehiculo.delete(vehiculo, usuario);
	}

	public void actualizarVehiculo(VehiculoDTO vehiculo) {
		this.vehiculo.update(vehiculo);

	}

	public List<PrecioPiezaDTO> obtenerPrecioCompraItems(int idProveedor, int idMarca) throws Exception {
		try {
			return pieza.obtenerPrecioCompraItems(idProveedor, idMarca);
		} catch (Exception e) {
			throw e;
		}
	}

	public void bajaStockPieza(PiezaDTO pieza, int cantidad, int estado) throws Exception {
		this.pieza.bajaStock(pieza, cantidad, estado);
	}

	/* public void asignarOrdenFletero(OrdenDTO orden, FleteroDTO fletero) {
		this.ordenTrabajo.asignarFletero(orden, fletero);
	}

	public void borrarAsignacion(OrdenDTO orden) {
		this.ordenTrabajo.borrarAsignacion(orden);
	}

	public List<OrdenDTO> obtenerAsignadasA (FleteroDTO fletero) throws Exception {
		return this.ordenTrabajo.readAsignadasA(fletero);
	} */
	
	public void agregarLocalidad(LocalidadDTO localidad) {
		this.localidad.insert(localidad);
	}

	public List<LocalidadDTO> obtenerLocalidades() {
		return this.localidad.readAll();
	}

	public List<ZonaDTO> obtenerZonas() {
		return this.zona.readAll();
	}
	
	public void actualizarZona (ZonaDTO zona) {
		this.zona.update(zona);
	}

	public void actualizarEnvioOT(OrdenDTO orden) {
		this.ordenTrabajo.updateOpcionesEnvio(orden);
	}

	public boolean revisarStock(PiezaDTO pieza) {
		try {
			return (this.pieza.dameStock(pieza) > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void agregarUsada(OrdenDTO orden, PiezaDTO pieza) throws Exception {
		this.ordenTrabajo.insertUsada(orden, pieza);
	}

	public void quitarUsada(OrdenDTO orden, PiezaDTO pieza) throws Exception {
		this.ordenTrabajo.deleteUsada(orden, pieza);
	}

	public OrdenDTO obtenerOrdenPorId(int id) {
		return this.ordenTrabajo.dameOt(id);
	}
	
	public void agregarEnvio (EnvioDTO envio) {
		this.envio.insert(envio);
	}
	
	public List<EnvioDTO> obtenerEnvios() {
		return this.envio.readAll();
	}
	
	public void eliminarEnvio (EnvioDTO envio) {
		this.envio.delete(envio);
	}
	
	public void actualizarEnvio (EnvioDTO envio) {
		this.envio.update(envio);
	}
	
	public List<EnvioDTO> obtenerAsignadosA (FleteroDTO fletero) {
		return this.envio.readByFletero(fletero);
	}
	
	public List<RankElectrodomesticosDTO> getRankElectrodomesticos() throws Exception {
		return this.reportes.getRankElectrodomesticos();
	}
	
	public List<RankPiezasDTO> getRankPiezas() throws Exception {
		return this.reportes.getRankPiezas();
	}
	
}
