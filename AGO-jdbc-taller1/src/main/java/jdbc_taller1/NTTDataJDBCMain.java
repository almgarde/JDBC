package jdbc_taller1;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDBC - Taller 1
 * 
 * @author agadelao
 * 
 *         Clase principal
 *
 */
public class NTTDataJDBCMain {

	/**
	 * Logger
	 */
	final static Logger LOGGER = LoggerFactory.getLogger(NTTDataJDBCMain.class);

	/**
	 * Método principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		stablishODBConnection();

	}

	private static void stablishODBConnection() {

		LOGGER.info("Inicio del método stablishODBConnection");
		try {

			// Driver de conexión a BBDD
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Apertura de conexión con BBDD
			final Connection dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "jdbc",
					"jdbc");

			// Consulta
			final Statement sentence = dbConnection.createStatement();
			final String query = "SELECT sp.name AS playerName, st.name AS teamName, sp.first_rol AS rol1, sp.second_rol AS rol2, sp.birth_date AS birthD from nttdata_oracle_soccer_player sp JOIN nttdata_oracle_soccer_team st ON sp.id_soccer_team = st.id_soccer_team";
			final ResultSet queryResult = sentence.executeQuery(query);

			// Iteración de resultados
			StringBuilder playerInfo = new StringBuilder();
			while (queryResult.next()) {

				playerInfo.append("Nombre: ");
				playerInfo.append(queryResult.getString("playerName"));

				playerInfo.append(" | Equipo: ");
				playerInfo.append(queryResult.getString("teamName"));

				playerInfo.append(" | Demarcación: ");
				playerInfo.append(queryResult.getString("rol1"));

				playerInfo.append(" | Demarcación alternativa: ");
				playerInfo.append(queryResult.getString("rol2"));

				playerInfo.append(" | Fecha nacimiento: ");
				playerInfo.append(queryResult.getString("birthD"));

				playerInfo.append("\n");

			}

			System.out.println(playerInfo.toString());

			// Cierre de la conexión con BBDD
			dbConnection.close();

			LOGGER.info("Fin del método stablishODBConnection");

		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("[ERROR] - Error en la conexión con BBDD" + e);
		}
	}
}
