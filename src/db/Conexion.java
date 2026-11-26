package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	public static void main(String[] args) {
		
		// Carga del driver JDBC para SQLite
		try {
		    Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
		    System.out.println("No se ha podido cargar el driver de la base de datos.");
		}
		
		// Con el driver cargado ya se pueden establecer conexiones a la BD

		try {
		    Connection conn = DriverManager.getConnection("jdbc:sqlite:src/db/hoopsmanager.db");
		    
		    // aquí podemos realizar operaciones con la base de datos

		    conn.close(); // es importante desconectar la conexión al terminar
					
		} catch (SQLException e) {
		    System.out.println("No se ha podido conectar a la base de datos.");
		}

	}
}
