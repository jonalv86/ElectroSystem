package persistencia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.UsuarioDTO;
import persistencia.conexion.Conexion;

public class UsuarioDAO {
	
	private final String sp_Insertar = "call insertarUsuario(?,?,?,?,?,?,?)";
	private final String sp_Eliminar = "call eliminarUsuario(?,?,?,?)";
	private final String sp_ReadAll = "call traerUsuarios(?,?)";
	private final String sp_ReadComunes = "call traerUsuariosComunes(?,?)";
	private final String sp_traerUsuario = "call traerUsuario(?,?,?)";
	private final String sp_Modificar = "call modificarUsuario(?,?,?,?,?,?)";
	private final String sp_verificar = "call verificarPassUsuario(?,?,?,?)";
	private final String sp_verificarReest = "call verificarClaveReest(?,?,?)";
	private final String sp_updateClaveDesbloqueo = "call updateClaveDesbloqueo(?,?,?)";
	private final String sp_reestablecerSuperUsuario = "call reestablecerSuperUsuario(?,?)";
	
	private static final Conexion conexion = Conexion.getConexion();

	public List<UsuarioDTO> readAll() throws Exception {
		List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadAll, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					usuarios.add(new UsuarioDTO(resultSet.getInt("IdPersonal"), resultSet.getInt("IdRol"),
							resultSet.getString("Descripcion"), resultSet.getString("Nombre"),
							resultSet.getString("Apellido"), resultSet.getString("Usuario"),
							resultSet.getString("Pass")));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return usuarios;
	}

	public List<UsuarioDTO> readComunes() throws Exception {
		List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_ReadComunes, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					usuarios.add(new UsuarioDTO(resultSet.getInt("IdPersonal"), resultSet.getInt("IdRol"),
							resultSet.getString("Descripcion"), resultSet.getString("Nombre"),
							resultSet.getString("Apellido"), resultSet.getString("Usuario"),
							resultSet.getString("Pass")));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return usuarios;
	}
	
	public UsuarioDTO obtenerUsuario(String usuario) throws Exception {
		
		UsuarioDTO objUsuario = null;
		
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_traerUsuario, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			cs.setString(1, usuario);
			
			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					objUsuario = new UsuarioDTO(resultSet.getInt("IdPersonal"), resultSet.getInt("IdRol"),
							resultSet.getString("Descripcion"), resultSet.getString("Nombre"),
							resultSet.getString("Apellido"), resultSet.getString("Usuario"),
							resultSet.getString("Pass"));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return objUsuario;
	}
	
	
	public void insert(UsuarioDTO usuario)  throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setInt(1, usuario.getRol().getIdRol());
			cs.setString(2, usuario.getNombre());
			cs.setString(3, usuario.getApellido());
			cs.setString(4, usuario.getUsuario());
			cs.setString(5, usuario.getPass());
			
			cs.registerOutParameter(6, java.sql.Types.INTEGER);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				
			}

			else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		// TODO Auto-generated method stub
		
	}

	public void delete(UsuarioDTO usuario_a_eliminar, UsuarioDTO usuarioLogueado) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Eliminar);

			cs.setInt(1, usuario_a_eliminar.getIdPersonal());
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

	public void update(UsuarioDTO usuario_a_editar) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Modificar);

			cs.setInt(1, usuario_a_editar.getIdPersonal());
			cs.setInt(2, usuario_a_editar.getRol().getIdRol());
			cs.setString(3, usuario_a_editar.getUsuario());
			cs.setString(4, usuario_a_editar.getPass());
			
			cs.registerOutParameter(5, java.sql.Types.INTEGER);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				
			}

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

	public boolean verificarClave(UsuarioDTO usuario, String clave) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_verificar);

			cs.setInt(1, usuario.getIdPersonal());
			cs.setString(2, clave);
			
			ResultSet resultSet = cs.executeQuery();
			
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");
			if (retCode == 0) {
				if (resultSet.next())
					return true;
			}

			else // Excepción desde la base
			
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;		
	}

	public void guardarClaveDesbl(String clave) throws Exception  {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_updateClaveDesbloqueo);

			cs.setString(1, clave);

			cs.executeQuery();
			
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");
			if (retCode == 0) {
				
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
	}

	public String readMail()  throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall("SELECT parm_valor1 FROM acc_parametros WHERE parametro = 'MAIL_ADMIN'");

			
			ResultSet resultSet = cs.executeQuery();
			
			
			if (resultSet.next()) {
				return resultSet.getString("parm_valor1");
			}

			else // Excepción desde la base
			
				throw (new Exception());

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
	}

	public boolean verificarClaveReest(String clave) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_verificarReest);

			cs.setString(1, clave);
			
			ResultSet resultSet = cs.executeQuery();
			
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");
			if (retCode == 0) {
				if (resultSet.next())
					return true;
			}

			else // Excepción desde la base
			
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally // Se ejecuta siempre
		{
			conexion.cerrarConexion();
		}
		return false;		
	}

	public void reestablecerSuperusuario() throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_reestablecerSuperUsuario);

			cs.executeQuery();
			
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				
			}
			else 
				throw (new Exception(descErr));
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}
}
