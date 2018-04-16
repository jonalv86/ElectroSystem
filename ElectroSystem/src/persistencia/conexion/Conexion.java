package persistencia.conexion;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Conexion {
	public static Conexion instancia;
	private final static String driver = "com.mysql.jdbc.Driver";
	private Connection conexion;
	private Properties config = new Properties();

	public Conexion() throws Exception {
		try {
			FileInputStream fin = new FileInputStream("config.ini");
			config.load(fin);			
			
			Class.forName(driver).newInstance();
			conexion = DriverManager.getConnection("jdbc:mysql://"
			+config.getProperty("IPLocal")+":"
			+config.getProperty("Puerto")+"/"
			+config.getProperty("Schema"), 
			config.getProperty("Usuario"),
			config.getProperty("Pass"));
		} catch (Exception e) {
			throw new Exception("Conexión con la base de datos fallida");
		}
	}

	public static Conexion getConexion() {
		if (instancia == null) {
			try {
				instancia = new Conexion();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Ocurrió un error: " + ex.getMessage());
			}
		}
		return instancia;
	}

	public Connection getSQLConexion() {
		return conexion;
	}

	public void cerrarConexion() {
		instancia = null;
	}
}
