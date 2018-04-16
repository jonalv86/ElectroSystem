package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.MarcaDTO;
import dto.PiezaDTO;
import dto.PrecioPiezaDTO;
import persistencia.conexion.Conexion;

public class PiezaDAO {

	private final String sp_ReadAll = "call traerPiezas(?,?)";
	private final String sp_Insert = "call insertarPieza(?,?)";
	private final String sp_Delete = "call eliminarPieza(?,?)";
	private final String sp_Modify = "call modificarPieza(?,?,?,?)";
	private final String sp_StateChange = "call cambiarEstadoDePieza(?,?,?,?,?)";

	private final String update = "UPDATE prod_piezas piez SET piez.Descripcion = ?,  piez.Precio_venta = ? WHERE piez.idProdPieza = ?;";
	private final String insert = "INSERT INTO prod_piezas (idProdPieza, idProdMarca, idUnico, Descripcion, Precio_venta, bajo_stock) VALUES (?,?,?,?,?,?)";
	private final String delete = "UPDATE prod_piezas piez SET piez.Usuario_baja = ?, Fecha_Baja = now() WHERE piez.idProdPieza = ?;";
	private final String subirPiezaAlStock = "INSERT INTO prod_piezas_stock (idProdPieza,idProdEstado) VALUES(?,1);";
	private final String contarDisponibles = "SELECT Count(idProdPieza) as Cantidad FROM prod_piezas_stock WHERE idProdEstado = 1 AND idProdPieza = ?";
	
	private final String sp_preciosProveedor = "call getPreciosProveedor(?,?,?,?)";

	private static final Conexion conexion = Conexion.getConexion();

	public ArrayList<PiezaDTO> readAll() throws Exception {

		ArrayList<PiezaDTO> piezas = new ArrayList<PiezaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					PiezaDTO pieza = new PiezaDTO(resultSet.getInt("idProdPieza"), new MarcaDTO(resultSet.getInt("idProdMarca"), resultSet.getString("Nombre")), resultSet.getString("idUnico"), resultSet.getString("Descripcion"), resultSet.getFloat("Precio_venta"), resultSet.getInt("bajo_stock"), resultSet.getInt("cantidad"));

					piezas.add(pieza);
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return piezas;
	}

	public void insert(PiezaDTO pieza) throws Exception {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, 0);
			statement.setInt(2, pieza.getMarca().getIdMarca());
			statement.setString(3, pieza.getIdUnico());
			statement.setString(4, pieza.getDescripcion());
			statement.setFloat(5, pieza.getPrecio_venta());
			statement.setInt(6, pieza.getBajo_stock());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

	}

	public void delete(PiezaDTO pieza, int idPersona) throws Exception {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, idPersona);
			statement.setInt(2, pieza.getIdProdPieza());
			statement.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}

	}

	public void update(PiezaDTO pieza, int idMarca) throws Exception {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, pieza.getDescripcion());
			statement.setFloat(2, pieza.getPrecio_venta());
			statement.setInt(3, pieza.getIdProdPieza());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

	}

	public void updatePreciosProveedor(List<PrecioPiezaDTO> pieza) throws Exception {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			// statement.setString(1, pieza.getDescripcion());
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}

	}

	public ArrayList<PrecioPiezaDTO> obtenerPrecioCompraItems(int idProveedor, int idMarca) throws Exception {

		ArrayList<PrecioPiezaDTO> piezas = new ArrayList<PrecioPiezaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_preciosProveedor, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cs.setInt(1, idProveedor);
			cs.setInt(2, idMarca);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {

					PiezaDTO pieza = new PiezaDTO(resultSet.getInt("idProdPieza"), null, resultSet.getString("idUnico"), resultSet.getString("Descripcion"), resultSet.getFloat("Precio_venta"), resultSet.getInt("bajo_stock"));
					float precio = resultSet.getFloat("precio_compra");
					piezas.add(new PrecioPiezaDTO(pieza, (precio == 0) ? null : precio));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return piezas;
	}

	public void altaStock(PiezaDTO pieza, int cantidad) throws Exception {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(subirPiezaAlStock);
			statement.setInt(1, pieza.getIdProdPieza());
			while (cantidad!=0) {
				statement.executeUpdate();
				cantidad--;
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public void bajaStock(PiezaDTO pieza, int cantidad, int estado) throws Exception {
		PreparedStatement statement;
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_StateChange, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			cs.setInt(1, pieza.getIdProdPieza());
			cs.setInt(2, 1);	//De disponible
			cs.setInt(3, estado);	//a perdida.
			cs.registerOutParameter(4, java.sql.Types.INTEGER);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			while (cantidad!=0) {
				
				ResultSet resultSet = cs.executeQuery();
				int retCode = cs.getInt("retCode");
				String descErr = cs.getString("descErr");

				if (retCode == 0) {
				
				} else // Excepción desde la base
					throw (new Exception(descErr));
				
				cantidad--;
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public int dameStock(PiezaDTO pieza) throws Exception {
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(contarDisponibles);
			ResultSet resultSet;
			statement.setInt(1, pieza.getIdProdPieza());
			resultSet = statement.executeQuery();
			if (resultSet.next())
				return resultSet.getInt("Cantidad");

			
			
		} catch (Exception e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return 0;
	}
}
