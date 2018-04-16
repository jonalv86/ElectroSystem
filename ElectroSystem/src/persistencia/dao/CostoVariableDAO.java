package persistencia.dao;

import java.sql.SQLException;

import dto.CostoVariableDTO;
import persistencia.conexion.Conexion;

public class CostoVariableDAO {

	private static final Conexion conexion = Conexion.getConexion();
	private static final String readHour = "call getCostoVariable (?,?,?,?)";

	public CostoVariableDTO obtenerValorManoObra() throws Exception {
		CostoVariableDTO costo = null;
		try {
			java.sql.CallableStatement cs = conexion.getSQLConexion().prepareCall(readHour);

			cs.setString(1, "COSTO_HORA_MANO_OBRA");
			cs.registerOutParameter(2, java.sql.Types.DECIMAL);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);

			cs.executeQuery();

			costo = new CostoVariableDTO(1, "COSTO_HORA_MANO_OBRA", cs.getBigDecimal("Precio").floatValue());

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
		return costo;
	}
}
