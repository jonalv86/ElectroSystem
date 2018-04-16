package persistencia.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import dto.EstadoDTO;
import dto.FleteroDTO;
import dto.VehiculoDTO;
import persistencia.conexion.Conexion;

public class FleteroDAO {

	private final String sp_insertar = "call insertarFletero(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String readall = "SELECT * FROM env_fleteros f, " + "env_vehiculos v, " + "env_estados_vehiculos es "
			+ "WHERE f.IdVehiculo = v.Patente " + "AND f.Fecha_Baja is null " + "AND es.idEstado = v.Estado;";
	private final String sp_eliminar = "call borrarFletero(?, ?, ?, ?)";
	private final String update = "UPDATE env_fleteros f " + "SET " + "f.Nombre = ?, " + "f.Apellido = ?, "
			+ "f.Celular = ?, " + "f.RegistroConducir = ?, " + "f.FechaVencimientoRegistro = ?, f.IdVehiculo = ? "
			+ "WHERE f.IdFletero = ? ";

	private static final Conexion conexion = Conexion.getConexion();

	public void insert(FleteroDTO fletero) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_insertar);

			Date vencimientoRegistro = new Date(fletero.getVencimientoRegistro().getTimeInMillis());

			cs.setString(1, fletero.getNombre());
			cs.setString(2, fletero.getApellido());
			cs.setString(3, fletero.getCelular());
			cs.setString(4, fletero.getRegistroConducir());
			cs.setDate(5, vencimientoRegistro);
			cs.setString(6, fletero.getVehiculo().getPatente());
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0)
				fletero.setIdFletero(cs.getInt("retIdFletero"));

			else
				throw (new Exception(descErr));

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
	}

	public List<FleteroDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		List<FleteroDTO> fleteros = new LinkedList<FleteroDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Calendar vencimientoVTV = getCalendar(resultSet.getDate("FechaVencimientoVTV"));
				Calendar vencimientoOblea = getCalendar(resultSet.getDate("FechaVencimientoOblea"));

				VehiculoDTO vehiculo = new VehiculoDTO(resultSet.getString("v.Patente"), resultSet.getString("v.Marca"),
						resultSet.getString("v.Modelo"), resultSet.getInt("v.CapacidadCarga"), vencimientoVTV,
						resultSet.getBoolean("v.Oblea"), vencimientoOblea,
						new EstadoDTO(resultSet.getInt("es.idEstado"), resultSet.getString("es.nombreEstado")));

				Calendar vencimientoRegistro = getCalendar(resultSet.getDate("FechaVencimientoRegistro"));

				FleteroDTO fletero = new FleteroDTO(resultSet.getInt("f.IdFletero"), resultSet.getString("f.Nombre"),
						resultSet.getString("f.Apellido"), resultSet.getString("f.Celular"),
						resultSet.getString("f.RegistroConducir"), vencimientoRegistro, vehiculo);

				fleteros.add(fletero);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexion.cerrarConexion();
		}
		return fleteros;
	}

	public void delete(int IdFletero, int IdPersonal) throws Exception {

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_eliminar);

			cs.setInt(1, IdFletero);
			cs.setInt(2, IdPersonal);

			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

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

	public void update(FleteroDTO f) {

		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			Date vencimientoRegistro = new Date(f.getVencimientoRegistro().getTimeInMillis());

			statement.setString(1, f.getNombre());
			statement.setString(2, f.getApellido());
			statement.setString(3, f.getCelular());
			statement.setString(4, f.getRegistroConducir());
			statement.setDate(5, vencimientoRegistro);
			statement.setString(6, f.getVehiculo().getPatente());
			statement.setInt(7, f.getIdFletero());

			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Calendar getCalendar(Date sqlDate) throws SQLException {
		if (sqlDate == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(sqlDate.getTime());
		return calendar;
	}
}
