package persistencia.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.RankElectrodomesticosDTO;
import dto.RankPiezasDTO;
import persistencia.conexion.Conexion;

public class ReporteDAO {
	
	private final String sp_RankElectrodomesticos = "call RepElectrodomesticos(?,?)";
	private final String sp_RankPiezas = "call RepPiezas(?,?)";
	
	private static final Conexion conexion = Conexion.getConexion();
	
	public List<RankElectrodomesticosDTO> getRankElectrodomesticos() throws Exception {

		ArrayList<RankElectrodomesticosDTO> rank = new ArrayList<RankElectrodomesticosDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_RankElectrodomesticos, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					rank.add(new RankElectrodomesticosDTO(resultSet.getString("Marca"), resultSet.getString("Modelo"), resultSet.getInt("Cantidad")));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return rank;
	}
	
	public List<RankPiezasDTO> getRankPiezas() throws Exception {

		ArrayList<RankPiezasDTO> rank = new ArrayList<RankPiezasDTO>();

		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(sp_RankPiezas, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet resultSet = cs.executeQuery();

			int retCode = cs.getInt("retCode");
			String descErr = cs.getString("descErr");

			if (retCode == 0) {
				while (resultSet.next()) {
					rank.add(new RankPiezasDTO(resultSet.getString("Pieza"), resultSet.getString("Marca"), resultSet.getString("Descripcion"), resultSet.getInt("Cantidad")));
				}
			} else // Excepción desde la base
				throw (new Exception(descErr));

		} catch (SQLException e) {
			throw e;
		} finally {
			conexion.cerrarConexion();
		}
		return rank;
	}
}
