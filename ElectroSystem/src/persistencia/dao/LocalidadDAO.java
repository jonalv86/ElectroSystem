package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LocalidadDTO;
import dto.ZonaDTO;
import persistencia.conexion.Conexion;

public class LocalidadDAO {
	
	private static final Conexion conexion = Conexion.getConexion();
	
	private static final String insert = "INSERT INTO localidades (codigoPostal, nombre, zonaDeEnvio) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM localidades WHERE codigoPostal = ?";
	private static final String readall = "SELECT * FROM localidades l, ot_zonas z where l.zonaDeEnvio = z.idZonaPosible";
	
	public boolean insert(LocalidadDTO localidad) {
		
		PreparedStatement statement;
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, localidad.getCodigoPostal());
			statement.setString(2, localidad.getNombre());
			statement.setInt(3, localidad.getZonaDeEnvio().getId());
			
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally
		
		{ conexion.cerrarConexion();
		
		}return false;
	}
	
	public boolean delete(LocalidadDTO localidad_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			
			statement = conexion.getSQLConexion().prepareStatement(delete);
			
			statement.setInt(1, localidad_a_eliminar.getCodigoPostal());
			
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
		
		} catch (SQLException e) {
			e.printStackTrace();
		
		} finally
		{
			conexion.cerrarConexion();
		}
		return false;
	}
	
	public List<LocalidadDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		List<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				localidades.add(new LocalidadDTO(
						resultSet.getInt("l.codigoPostal"), 
						resultSet.getString("l.nombre"),
						new ZonaDTO(
								resultSet.getInt("idZonaPosible"), 
								resultSet.getString("Nombre"), 
								resultSet.getDouble("Precio"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally
		
		{
			conexion.cerrarConexion();
		}
		return localidades;
	}

}
