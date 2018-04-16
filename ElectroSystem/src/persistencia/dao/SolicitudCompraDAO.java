package persistencia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EstadoDTO;
import dto.ItemDTO;
import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.ProveedorDTO;
import dto.SolicitudCompraDTO;
import persistencia.conexion.Conexion;

public class SolicitudCompraDAO {

	private final String sp_Insertar = "call insertarSolicitudCompra(?,?,?,?,?,?)";
	private final String sp_update = "call eliminarPiezasSolicitud(?,?,?)";
	private final String sp_ReadAll = "call traerSolicitudes(?,?)";
	private final String sp_insertarPiezasSolicitud = "call insertarPiezasSolicitud(?,?,?,?,?)";
	private final String sp_traerPiezasSolicitud = "call traerPiezasSolicitud(?,?,?)";
	private final String sp_cambiarEstado = "call cambiarEstadoSC(?,?,?,?)";
	private final String sp_altaPiezas = "call altaPiezaStockFisico(?,?,?,?)";

	private static final Conexion conexion = Conexion.getConexion();

	public void insert(SolicitudCompraDTO solicitudCompra) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setInt(1, 1); // Estado ingresada hardcodeado.
			cs.setInt(2, 1); // TODO Usuario alta hardcodeado
			cs.setInt(3, solicitudCompra.getProveedor().getIdProveedor());
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				solicitudCompra.setId(cs.getInt("retIdSC"));

				// Agrego las piezas a la SC
				for (ItemDTO piezas : solicitudCompra.getPiezas()) {
					PiezaDTO pieza = piezas.getPieza();
					insertarPiezasSolicitud(solicitudCompra.getId(), pieza.getIdProdPieza(), piezas.getCantidadPiezas());
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	private void insertarPiezasSolicitud(int idSolcCompra, int idProdPieza, int cantidad) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_insertarPiezasSolicitud);

			cs.setInt(1, idSolcCompra);
			cs.setInt(2, idProdPieza);
			cs.setInt(3, cantidad);
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0)
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public void update(SolicitudCompraDTO solicitudCompra) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_update);
			// borro las piezas
			cs.setInt(1, solicitudCompra.getId());
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {

				// Agrego las piezas a la SC
				for (ItemDTO piezas : solicitudCompra.getPiezas()) {
					PiezaDTO pieza = piezas.getPieza();
					insertarPiezasSolicitud(solicitudCompra.getId(), pieza.getIdProdPieza(), piezas.getCantidadPiezas());
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public List<SolicitudCompraDTO> readAll() throws Exception {

		ArrayList<SolicitudCompraDTO> solicitudes = new ArrayList<SolicitudCompraDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					solicitudes.add(new SolicitudCompraDTO(resultSet.getInt("idSolcCompra"), new ProveedorDTO(resultSet.getInt("IdProveedor"), resultSet.getString("Nombre"), resultSet.getString("Contacto"), resultSet.getString("Cuit"), resultSet.getString("Telefono"), resultSet.getString("Mail"), null), new EstadoDTO(resultSet.getInt("IdSolcEstado"), resultSet.getString("Descripcion")), traerPiezasSolicitud(resultSet.getInt("idSolcCompra"))));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return solicitudes;
	}

	private List<ItemDTO> traerPiezasSolicitud(int idSolcCompra) throws Exception {

		ArrayList<ItemDTO> piezas = new ArrayList<ItemDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_traerPiezasSolicitud, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			cs.setInt(1, idSolcCompra);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					PiezaDTO pieza = new PiezaDTO(resultSet.getInt("idProdPieza"), new MarcaDTO(resultSet.getInt("idProdMarca"), resultSet.getString("Nombre")), resultSet.getString("idUnico"), resultSet.getString("Descripcion"), resultSet.getFloat("Precio_venta"), resultSet.getInt("bajo_stock"));
					piezas.add(new ItemDTO(pieza, resultSet.getInt("cantidad"), null, null));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return piezas;
	}

	public void procesarSolicitud(SolicitudCompraDTO solicitudCompra) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_altaPiezas);

			List<ItemDTO> piezas = solicitudCompra.getPiezas();
			for (ItemDTO itemDTO : piezas) {

				cs.setInt(1, itemDTO.getPieza().getIdProdPieza());
				cs.setInt(2, itemDTO.getCantidadPiezas());
				cs.registerOutParameter(3, java.sql.Types.INTEGER);
				cs.registerOutParameter(4, java.sql.Types.VARCHAR);
				cs.executeUpdate();

				int retCode = cs.getInt("retCode");
				String descErr = cs.getString("descErr");

				if (retCode != 0)
					throw (new Exception(descErr));
			}
			cambiarEstado(solicitudCompra, 3);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}

	}

	public void cambiarEstado(SolicitudCompraDTO solicitudCompra, int idEstado) throws SQLException, Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_cambiarEstado);
			cs.setInt(1, solicitudCompra.getId());
			cs.setInt(2, idEstado);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0)
				throw (new Exception(descErr));
		} catch (

		SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

}