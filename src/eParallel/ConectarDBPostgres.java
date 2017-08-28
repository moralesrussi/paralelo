package eParallel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This program  make database connection to PostgreSQL
 * server using JDBC.
 * 
 * @author Internet code base - M
 * 
 **/
public class ConectarDBPostgres {

	public static Connection main(String[] args) {
		// Conexion BD local 
		Connection conn = null;
		String db_user = Configuration.getInstance().getValue("DATABASE_USER");
		String db_pass = Configuration.getInstance().getValue("DATABASE_PSWD");
		String db_server = Configuration.getInstance().getValue("DATABASE_SERVER");
		String db_instancia = Configuration.getInstance().getValue("DATABASE_CATALOG");
		String db_port = Configuration.getInstance().getValue("DATABASE_PORT");
		String dbURL = "jdbc:postgresql://" + db_server + ":" + db_port + "/" + db_instancia;

		try {
			// Connect
			// String dbURL = "jdbc:postgresql://localhost:5432/ProductDB3";
			Properties parameters = new Properties();
			parameters.put("user", db_user);
			parameters.put("password", db_pass);

			conn = DriverManager.getConnection(dbURL, parameters);

			if (conn != null) {
				System.out.println("Conectado a la base de datos.");
				return conn;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					// conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return conn;
	}
}