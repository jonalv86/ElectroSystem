package persistencia.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import dto.EstadoDTO;
import dto.UsuarioDTO;
import dto.VehiculoDTO;
import persistencia.conexion.Conexion;

public class VehiculoDAO {

	private static final String insert = "INSERT INTO env_vehiculos("
			+ "Patente, Marca, Modelo, CapacidadCarga, FechaVencimientoVTV," + "Oblea, FechaVencimientoOblea, Estado) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String delete = "UPDATE env_vehiculos SET UsuarioBaja = ? " + "WHERE Patente = ?";

	private static final String readall = "SELECT * FROM env_vehiculos ev, env_estados_vehiculos es "
			+ "WHERE ev.UsuarioBaja is null AND es.idEstado = ev.Estado;";

	private static final String update = "UPDATE env_vehiculos " + "SET Marca = ?, " + "Modelo = ?, "
			+ "CapacidadCarga = ?, " + "FechaVencimientoVTV = ?, " + "Oblea = ?, " + "FechaVencimientoOblea = ?, "
			+ "Estado = ? " + "WHERE Patente = ?";

	private static final Conexion conexion = Conexion.getConexion();

	public boolean insert(VehiculoDTO vehiculo) {

		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);

			Date vencimientoVTV = new Date(vehiculo.getFechaVencimientoVTV().getTimeInMillis());
			Date vencimientoOblea = new Date(vehiculo.getFechaVencimientoVTV().getTimeInMillis());

			statement.setString(1, vehiculo.getPatente());
			statement.setString(2, vehiculo.getMarca());
			statement.setString(3, vehiculo.getModelo());
			statement.setInt(4, vehiculo.getCapacidadCarga());
			statement.setDate(5, vencimientoVTV);
			statement.setBoolean(6, vehiculo.isOblea());
			statement.setDate(7, vencimientoOblea);
			statement.setInt(8, vehiculo.getEstado().getId());

			if (statement.executeUpdate() > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			conexion.cerrarConexion();

		}
		return false;
	}

	public boolean delete(VehiculoDTO vehiculo_a_eliminar, UsuarioDTO usuario) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);

			statement.setInt(1, usuario.getIdPersonal());
			statement.setString(2, vehiculo_a_eliminar.getPatente());

			chequeoUpdate = statement.executeUpdate();

			if (chequeoUpdate > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			conexion.cerrarConexion();

		}
		return false;
	}

	public boolean update(VehiculoDTO vehiculo_a_editar) {

		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			Date vencimientoVTV = new Date(vehiculo_a_editar.getFechaVencimientoVTV().getTimeInMillis());

			statement.setString(1, vehiculo_a_editar.getMarca());
			statement.setString(2, vehiculo_a_editar.getModelo());
			statement.setInt(3, vehiculo_a_editar.getCapacidadCarga());
			statement.setDate(4, vencimientoVTV);
			statement.setBoolean(5, vehiculo_a_editar.isOblea());
			
			Date vencimientoOblea = null;
			
			if (vehiculo_a_editar.isOblea()) {
				vencimientoOblea = new Date(vehiculo_a_editar.getFechaVencimientoOblea().getTimeInMillis());
				statement.setDate(6, vencimientoOblea);
			} else {
				statement.setDate(6, vencimientoOblea);
			}
			
			statement.setInt(7, vehiculo_a_editar.getEstado().getId());
			statement.setString(8, vehiculo_a_editar.getPatente());

			chequeoUpdate = statement.executeUpdate();

			if (chequeoUpdate > 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			conexion.cerrarConexion();

		}
		return false;
	}

	public List<VehiculoDTO> readAll() {

		PreparedStatement statement;
		ResultSet resultSet;
		List<VehiculoDTO> vehiculos = new LinkedList<VehiculoDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Calendar vencimientoVTV = getCalendar(resultSet.getDate("FechaVencimientoVTV"));
				Calendar vencimientoOblea = getCalendar(resultSet.getDate("FechaVencimientoOblea"));

				vehiculos.add(new VehiculoDTO(
						resultSet.getString("Patente"), 
						resultSet.getString("Marca"),
						resultSet.getString("Modelo"), 
						resultSet.getInt("CapacidadCarga"), 
						vencimientoVTV,
						resultSet.getBoolean("Oblea"), 
						vencimientoOblea, 
						new EstadoDTO (resultSet.getInt("es.idEstado"), resultSet.getString("es.nombreEstado"))
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return vehiculos;
	}

	private Calendar getCalendar(Date sqlDate) throws SQLException {
		if (sqlDate == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(sqlDate.getTime());
		return calendar;
	}

}
