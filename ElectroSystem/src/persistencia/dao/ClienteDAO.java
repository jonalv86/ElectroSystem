package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.LocalidadDTO;
import dto.ZonaDTO;
import persistencia.conexion.Conexion;

public class ClienteDAO {

	private final String sp_Insertar = "call insertarCliente(?,?,?,?,?,?,?,?,?,?)";
	private final String sp_Eliminar = "call eliminarCliente(?,?,?,?)";
//	private final String sp_ReadAll = "call traerClientes(?,?)";
	private final String readAll = "select * from cli_clientes cli, localidades l, ot_zonas z where cli.codigo_postal = l.codigoPostal and l.zonaDeEnvio = z.idZonaPosible";
	private final String sp_Modificar = "call modificarCliente(?,?,?,?,?,?,?,?,?,?)";

	private static final Conexion conexion = Conexion.getConexion();

	public void insert(ClienteDTO cliente) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setString(1, cliente.getNombre());
			cs.setString(2, cliente.getApellido());
			cs.setString(3, cliente.getDireccion());
//			cs.setString(4, cliente.getLocalidad());
			cs.setString(4, cliente.getProvincia());
			cs.setInt(5, cliente.getLocalidad().getCodigoPostal());
			cs.setString(6, cliente.getTelefono());
			cs.setString(7, cliente.geteMail());
			
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0)
				cliente.setIdCliente(cs.getInt("retIdCliente"));

			else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
	}

	public void delete(ClienteDTO cliente, int IdPersonal) throws Exception {
		// TODO: Cambiar IdPersonal por el DTO de pers_personal
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Eliminar);

			cs.setInt(1, cliente.getIdCliente());
			cs.setInt(2, IdPersonal);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0) // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	/* public List<ClienteDTO> readAll() throws Exception {

		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					clientes.add(
							new ClienteDTO(
							resultSet.getInt("idCliente"), 
							resultSet.getString("Nombre"),
							resultSet.getString("Apellido"), 
							resultSet.getString("Direccion"),
//							resultSet.getString("Localidad"), 
							resultSet.getString("Provincia"),
							new LocalidadDTO(resultSet.getInt("cli.Codigo_postal"), 
									resultSet.getString("l.nombre"), 
									new ZonaDTO(resultSet.getInt("z.idZonaPosible"), 
											resultSet.getString("z.Nombre"), 
											resultSet.getDouble("z.Precio"))), 
							resultSet.getString("Telefono"),
							resultSet.getString("Email")));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return clientes;
	} */
	
	public List<ClienteDTO> readAll() {
		
		PreparedStatement statement;
		ResultSet resultSet;
		List<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readAll);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				clientes.add(new ClienteDTO(
						resultSet.getInt("idCliente"), 
						resultSet.getString("Nombre"),
						resultSet.getString("Apellido"), 
						resultSet.getString("Direccion"),
//						resultSet.getString("Localidad"), 
						resultSet.getString("Provincia"),
						new LocalidadDTO(resultSet.getInt("cli.Codigo_postal"), 
								resultSet.getString("l.nombre"), 
								new ZonaDTO(resultSet.getInt("z.idZonaPosible"), 
										resultSet.getString("z.Nombre"), 
										resultSet.getDouble("z.Precio"))), 
						resultSet.getString("Telefono"),
						resultSet.getString("Email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally
		
		{
			conexion.cerrarConexion();
		}
		return clientes;
	}

	public void update(ClienteDTO cliente) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Modificar);

			cs.setInt(1, cliente.getIdCliente());
			cs.setString(2, cliente.getNombre());
			cs.setString(3, cliente.getApellido());
			cs.setString(4, cliente.getDireccion());
//			cs.setString(5, cliente.getLocalidad());
			cs.setString(5, cliente.getProvincia());
			cs.setInt(6, cliente.getLocalidad().getCodigoPostal());
			cs.setString(7, cliente.getTelefono());
			cs.setString(8, cliente.geteMail());
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode != 0) // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

}