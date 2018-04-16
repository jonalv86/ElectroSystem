package persistencia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.MarcaDTO;
import dto.PrecioPiezaDTO;
import dto.ProveedorDTO;
import dto.UsuarioDTO;
import persistencia.conexion.Conexion;

public class ProveedorDAO {

	private final String sp_Insertar = "call insertarProveedor(?,?,?,?,?,?,?,?)";
	private final String sp_Update = "call modificarProveedor(?,?,?,?,?,?,?,?)";
	private final String sp_ReadAll = "call traerProveedores(?,?)";
	private final String sp_InsertarProveeMarca = "call insertarProveeMarca(?,?,?,?)";
	private final String sp_BorrarProveeMarca = "call BorrarProveeMarca(?,?,?,?)";
	private final String sp_Eliminar = "call eliminarProveedor(?,?,?,?)";
	private final String sp_traerMarcasProvistas = "call traerMarcasProvistas(?,?,?)";
	private final String sp_insmodpreciocompra = "call insModPrecioCompra(?,?,?,?,?)";

	private static final Conexion conexion = Conexion.getConexion();

	public void insert(ProveedorDTO proveedor) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setString(1, proveedor.getNombre());
			cs.setString(2, proveedor.getContacto());
			cs.setString(3, proveedor.getCuit());
			cs.setString(4, proveedor.getTelefono());
			cs.setString(5, proveedor.getMail());
			cs.registerOutParameter(6, java.sql.Types.INTEGER);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				proveedor.setIdProveedor(cs.getInt("retIdProveedor"));

				for (MarcaDTO m : proveedor.getMarcas()) {
					insertarMarcaProveedor(proveedor.getIdProveedor(), m.getIdMarca());
				}

			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
	}

	public List<MarcaDTO> traerMarcasProvistas(int idProveedor) throws Exception {

		List<MarcaDTO> marcas = new ArrayList<MarcaDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_traerMarcasProvistas, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			cs.setInt(1, idProveedor);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					marcas.add(new MarcaDTO(resultSet.getInt("idProdMarca"), resultSet.getString("Nombre")));
				}
			} else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return marcas;
	}

	public void delete(ProveedorDTO proveedor_a_eliminar, UsuarioDTO usuarioLogueado) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Eliminar);

			cs.setInt(1, proveedor_a_eliminar.getIdProveedor());
			cs.setInt(2, usuarioLogueado.getIdPersonal());

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0) // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}

	}

	public List<ProveedorDTO> readAll2() throws Exception {

		ArrayList<ProveedorDTO> proveedores = new ArrayList<ProveedorDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");
			if (retCode == 0) {
				while (resultSet.next()) {
					proveedores.add(new ProveedorDTO(resultSet.getInt("IdProveedor"), resultSet.getString("Nombre"), resultSet.getString("Contacto"), resultSet.getString("Cuit"), resultSet.getString("Telefono"), resultSet.getString("Mail"), traerMarcasProvistas(resultSet.getInt("IdProveedor"))));
				}

			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return proveedores;
	}

	public void update(ProveedorDTO proveedor_a_actualizar, List<MarcaDTO> marcas_a_borrar, List<MarcaDTO> marcas_a_agregar) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Update);

			cs.setInt(1, proveedor_a_actualizar.getIdProveedor());
			cs.setString(2, proveedor_a_actualizar.getNombre());
			cs.setString(3, proveedor_a_actualizar.getContacto());
			cs.setString(4, proveedor_a_actualizar.getCuit());
			cs.setString(5, proveedor_a_actualizar.getTelefono());
			cs.setString(6, proveedor_a_actualizar.getMail());
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {

				for (MarcaDTO m : marcas_a_borrar) {
					borrarMarcaProveedor(proveedor_a_actualizar.getIdProveedor(), m.getIdMarca());
				}
				for (MarcaDTO m : marcas_a_agregar) {
					insertarMarcaProveedor(proveedor_a_actualizar.getIdProveedor(), m.getIdMarca());
				}

			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
	}

	private void borrarMarcaProveedor(int idProveedor, int idMarca) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_BorrarProveeMarca);

			cs.setInt(1, idProveedor);
			cs.setInt(2, idMarca);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {

			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
	}

	public void insertarMarcaProveedor(int idProveedor, int idMarca) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_InsertarProveeMarca);

			cs.setInt(1, idProveedor);
			cs.setInt(2, idMarca);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {

			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
	}

	public void updatePrecioPiezaProveedor(int idProveedor, List<PrecioPiezaDTO> piezas) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_insmodpreciocompra);

			for (PrecioPiezaDTO precioPiezaDTO : piezas) {
				
				System.out.println(precioPiezaDTO.getPrecio());

				cs.setInt(1, idProveedor);
				cs.setInt(2, precioPiezaDTO.getPieza().getIdProdPieza());
				cs.setFloat(3, precioPiezaDTO.getPrecio());
				cs.registerOutParameter(4, java.sql.Types.INTEGER);
				cs.registerOutParameter(5, java.sql.Types.VARCHAR);

				cs.executeUpdate();

				int retCode = cs.getInt("retCode");
				String descErr = cs.getString("descErr");

				if (retCode != 0)
					throw (new Exception(descErr));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
	}
}
