package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FleteroDTO;
import dto.LocalidadDTO;
import dto.OrdenDTO;
import dto.ZonaDTO;
import persistencia.conexion.Conexion;

public class ZonaDAO {

	private static final Conexion conexion = Conexion.getConexion();

	private static final String readall = "SELECT * FROM ot_zonas z";
	private static final String update = "update ot_zonas z set z.nombre = ?, z.precio = ? where z.idZonaPosible = ?";

	public List<ZonaDTO> readAll() {

		PreparedStatement statement;
		ResultSet resultSet;

		List<ZonaDTO> zonas = new ArrayList<ZonaDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				zonas.add(new ZonaDTO(
						resultSet.getInt("idZonaPosible"), 
						resultSet.getString("Nombre"),
						resultSet.getDouble("Precio")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally

		{
			conexion.cerrarConexion();
		}
		return zonas;
	}
	
	public void update (ZonaDTO zona) {

		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, zona.getNombre());
			statement.setDouble(2, zona.getPrecio());
			statement.setInt(3, zona.getId());

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
