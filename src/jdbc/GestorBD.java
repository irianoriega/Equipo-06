package jdbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import clase.Entrenamiento;
import clase.TipoEntrenamiento;

// Todo lo que tiene que ver con la base de datos está en esta clase.
// La BD es resources/db/hoopsmanager.db y su estructura está en resources/db/hoopsmanager.sql
//
// Cada método sigue los mismos pasos que puso Unai el miercoles en clase
//   1. Abrir la conexión con la base de datos
//   2. Crear la sentencia SQL
//   3. Ejecutar la sentencia
//   4. Procesar los datos devueltos
//   5. Desconectar la BD

public class GestorBD {

	private static final String URL_BD = "jdbc:sqlite:resources/db/hoopsmanager.db";
	private static final String RUTA_SCRIPT = "resources/db/hoopsmanager.sql";

	// CARGAR EL DRIVER (se llama una vez, al arrancar la aplicación desde Main)
	public static void cargarDriver() {
		// Cargar el driver de JDBC para SQLite (habiendo referenciado el jar de la carpeta lib)
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Driver cargado correctamente");
		} catch (ClassNotFoundException e) {
			System.err.println("No se ha podido cargar el driver de base de datos " + e.getMessage());
		}
	}

	// ABRIR LA CONEXIÓN (lo usan todos los métodos en el paso 1)
	private static Connection conectar() throws SQLException {
		// Si el archivo .db no existe, se crea una BD vacía nueva
		Connection conn = DriverManager.getConnection(URL_BD);

		// SQLite no comprueba las claves foráneas si no se activan en cada conexión
		PreparedStatement stmt = conn.prepareStatement("PRAGMA foreign_keys = ON");
		stmt.execute();
		stmt.close();

		return conn;
	}

	// CREAR LAS TABLAS
	// Ejecuta el script hoopsmanager.sql. Como usa CREATE TABLE IF NOT EXISTS,
	// solo crea las tablas que falten y no borra nada.
	public static boolean crearTablas() {
		// Leer el script entero en un String
		String script;
		try {
			script = Files.readString(Path.of(RUTA_SCRIPT));
		} catch (IOException e) {
			System.err.println("No se ha podido leer el script " + RUTA_SCRIPT + " " + e.getMessage());
			return false;
		}

		try {
			// 1. Abrir la conexión con la base de datos
			Connection conn = conectar();

			// El script tiene varias sentencias separadas por ";" y hay que lanzarlas de una en una
			for (String sentencia : script.split(";")) {
				if (!sentencia.isBlank()) {
					// 2. Crear la sentencia SQL
					PreparedStatement stmt = conn.prepareStatement(sentencia);
					// 3. Ejecutar la sentencia
					stmt.execute();
					stmt.close();
				}
			}
			// 4. Procesar los datos devueltos: un CREATE TABLE no devuelve datos

			// 5. Desconectar la BD
			conn.close();
			return true;

		} catch (SQLException e) {
			System.err.println("Ha habido un error de JDBC/SQL al crear las tablas " + e.getMessage());
			return false;
		}
	}

	// CARGAR TODOS LOS ENTRENAMIENTOS (SELECT)
	// Devuelve la lista ordenada por fecha. Si hay un error, la devuelve vacía.
	public static List<Entrenamiento> cargarEntrenamientos() {
		List<Entrenamiento> entrenamientos = new ArrayList<>();

		try {
			// 1. Abrir la conexión con la base de datos
			Connection conn = conectar();

			// 2. Crear la sentencia SQL
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT id, fecha, tipo, observaciones FROM Entrenamiento ORDER BY fecha;");

			// 3. Ejecutar la sentencia SELECT
			ResultSet rs = stmt.executeQuery();

			// 4. Procesar los datos devueltos: cada vuelta del while es una fila
			while (rs.next()) {
				int id = rs.getInt("id");
				LocalDate fecha = LocalDate.parse(rs.getString("fecha"));             // "2026-11-27" -> LocalDate
				TipoEntrenamiento tipo = TipoEntrenamiento.valueOf(rs.getString("tipo")); // "TECNICO" -> enum
				String observaciones = rs.getString("observaciones");

				entrenamientos.add(new Entrenamiento(id, fecha, tipo, observaciones));
			}

			// 5. Desconectar la BD
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			System.err.println("Ha habido un error de JDBC/SQL al cargar los entrenamientos " + e.getMessage());
		}
		return entrenamientos;
	}

	// INSERTAR UN ENTRENAMIENTO (INSERT)
	// La BD le asigna el id (AUTOINCREMENT) y se lo guardamos al objeto con setId().
	public static boolean insertarEntrenamiento(Entrenamiento entrenamiento) {
		try {
			// 1. Abrir la conexión con la base de datos
			Connection conn = conectar();

			// 2. Crear la sentencia SQL: cada ? se rellena después con un set...()
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO Entrenamiento (fecha, tipo, observaciones) VALUES (?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS); // para poder recuperar el id generado
			stmt.setString(1, entrenamiento.getFecha().toString()); // LocalDate -> "2026-11-27"
			stmt.setString(2, entrenamiento.getTipo().name());      // name() = "TECNICO" (toString() sería "Técnico")
			stmt.setString(3, entrenamiento.getObservaciones());

			// 3. Ejecutar la sentencia
			stmt.executeUpdate();

			// 4. Procesar los datos devueltos: el id que ha generado la BD
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				entrenamiento.setId(rs.getInt(1));
			}

			// 5. Desconectar la BD
			rs.close();
			stmt.close();
			conn.close();
			return true;

		} catch (SQLException e) {
			System.err.println("Ha habido un error de JDBC/SQL al insertar el entrenamiento " + e.getMessage());
			return false;
		}
	}

	// MODIFICAR UN ENTRENAMIENTO (UPDATE)
	// Actualiza la fila que tiene el mismo id que el entrenamiento.
	public static boolean actualizarEntrenamiento(Entrenamiento entrenamiento) {
		try {
			// 1. Abrir la conexión con la base de datos
			Connection conn = conectar();

			// 2. Crear la sentencia SQL
			PreparedStatement stmt = conn.prepareStatement(
					"UPDATE Entrenamiento SET fecha = ?, tipo = ?, observaciones = ? WHERE id = ?;");
			stmt.setString(1, entrenamiento.getFecha().toString());
			stmt.setString(2, entrenamiento.getTipo().name());
			stmt.setString(3, entrenamiento.getObservaciones());
			stmt.setInt(4, entrenamiento.getId());

			// 3. Ejecutar la sentencia: devuelve cuántas filas ha cambiado
			int filas = stmt.executeUpdate();

			// 4. Procesar los datos devueltos: si no ha cambiado ninguna fila, ese id no existía
			boolean actualizado = filas > 0;

			// 5. Desconectar la BD
			stmt.close();
			conn.close();
			return actualizado;

		} catch (SQLException e) {
			System.err.println("Ha habido un error de JDBC/SQL al actualizar el entrenamiento " + e.getMessage());
			return false;
		}
	}

	// ELIMINAR UN ENTRENAMIENTO (DELETE)
	// Primero se borran sus asistencias, porque apuntan al entrenamiento (clave foránea).
	public static boolean eliminarEntrenamiento(int id) {
		try {
			// 1. Abrir la conexión con la base de datos
			Connection conn = conectar();

			// 2. Crear las sentencias SQL
			PreparedStatement stmtAsistencia = conn.prepareStatement(
					"DELETE FROM Asistencia WHERE entrenamiento_id = ?;");
			stmtAsistencia.setInt(1, id);

			PreparedStatement stmtEntrenamiento = conn.prepareStatement(
					"DELETE FROM Entrenamiento WHERE id = ?;");
			stmtEntrenamiento.setInt(1, id);

			// 3. Ejecutar las sentencias (en este orden)
			stmtAsistencia.executeUpdate();
			int filas = stmtEntrenamiento.executeUpdate();

			// 4. Procesar los datos devueltos: si no se ha borrado ninguna fila, ese id no existía
			boolean eliminado = filas > 0;

			// 5. Desconectar la BD
			stmtAsistencia.close();
			stmtEntrenamiento.close();
			conn.close();
			return eliminado;

		} catch (SQLException e) {
			System.err.println("Ha habido un error de JDBC/SQL al eliminar el entrenamiento " + e.getMessage());
			return false;
		}
	}
}
