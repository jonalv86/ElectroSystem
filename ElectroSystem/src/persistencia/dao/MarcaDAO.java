package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.MarcaDTO;
import persistencia.conexion.Conexion;

public class MarcaDAO {
	private static final String delete = "DELETE FROM prod_marcas WHERE IdProdMarca = ?";
	private static final String readall = "SELECT * FROM prod_marcas";
	private static final String update = "UPDATE prod_marcas SET Nombre = ? WHERE IdProdMarca = ?";
	private static final Conexion conexion = Conexion.getConexion();
	private final String sp_Insertar = "call insertarMarca(?,?,?,?)";
	
	public void insert(MarcaDTO marca) throws Exception {
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_Insertar);

			cs.setString(1, marca.getNombre());
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0)
				marca.setIdMarca(cs.getInt("retIdMarca"));

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
	
	
	public boolean delete(MarcaDTO marca_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(marca_a_eliminar.getIdMarca()));
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

	public List<MarcaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<MarcaDTO> marcas = new ArrayList<MarcaDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				marcas.add(new MarcaDTO(resultSet.getInt("IdProdMarca"), 
						resultSet.getString("Nombre")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally
		{
			conexion.cerrarConexion();
		}
		return marcas;
	}

	public boolean update(MarcaDTO marca_a_actualizar) {	//Sin testear.
		PreparedStatement statement;
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, marca_a_actualizar.getNombre());
			statement.setInt(2, marca_a_actualizar.getIdMarca());
						
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally
		{
			conexion.cerrarConexion();
		}
		return false;
	}
}

