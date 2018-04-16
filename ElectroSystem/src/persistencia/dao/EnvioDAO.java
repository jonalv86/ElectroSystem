package persistencia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EnvioDTO;
import dto.FleteroDTO;
import persistencia.conexion.Conexion;

public class EnvioDAO {
	
	// Esta solo con ids, sorry

	private static final Conexion conexion = Conexion.getConexion();

	private static final String insert = "insert into env_envios (idOT, idFletero) values (?, ?)";
	private static final String readall = "select * from env_envios";
	private static final String delete = "delete from env_envios where idOT = ?";
	private static final String update = "update env_envios set idFletero = ? where idOT = ?";
	private static final String readByFletero = "select * from env_envios where idFletero = ?";
	
	public void insert (EnvioDTO envio) {
		
		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);

			statement.setInt(1, envio.getIdOT());
			statement.setInt(2, envio.getIdFletero());

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<EnvioDTO> readAll() {

		PreparedStatement statement;
		ResultSet resultSet;

		List<EnvioDTO> envios = new ArrayList<EnvioDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				envios.add(
						new EnvioDTO(resultSet.getInt("idOT"), 
								resultSet.getInt("idFletero")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally

		{
			conexion.cerrarConexion();
		}
		return envios;
	}

	public void delete(EnvioDTO envio) {

		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);

			statement.setInt(1, envio.getIdOT());

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(EnvioDTO envio) {

		PreparedStatement statement;

		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setInt(1, envio.getIdFletero());
			statement.setInt(2, envio.getIdOT());

			statement.executeUpdate();
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<EnvioDTO> readByFletero(FleteroDTO fletero) {

		PreparedStatement statement;
		ResultSet resultSet;

		List<EnvioDTO> envios = new ArrayList<EnvioDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readByFletero);
			
			statement.setInt(1, fletero.getIdFletero());

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				envios.add(
						new EnvioDTO(resultSet.getInt("idOT"), 
								resultSet.getInt("idFletero")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally

		{
			conexion.cerrarConexion();
		}
		return envios;
	}
	
}
