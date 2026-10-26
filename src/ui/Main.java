package ui;
 
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.SwingUtilities;

import clase.EstadoJugador;
import clase.Jugador;
import clase.Posicion;

 
/**
 * Clase para arrancar la aplicacion (de momento con jugadores de prueba).
 */
public class Main {
 
	public static void main(String[] args) {
		// Jugadores de ejemplo, copiados del boceto del PDF
		List<Jugador> jugadores = new ArrayList<>();
		jugadores.add(new Jugador("Carlos Ruiz", 1, Posicion.BASE, 20, 185, 78, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador("Marcos Lopez", 5, Posicion.ESCOLTA, 22, 190, 82, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador("David Garcia", 7, Posicion.ALERO, 19, 195, 88, EstadoJugador.LESIONADO));
		jugadores.add(new Jugador("Pablo Martinez", 10, Posicion.ALA_PIVOT, 21, 200, 95, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador("Ivan Torres", 15, Posicion.PIVOT, 23, 205, 102, EstadoJugador.DISPONIBLE));
 
		// Las ventanas Swing se crean dentro de invokeLater (forma recomendada)
		SwingUtilities.invokeLater(() -> {
			VentanaDashboard dashboard = new VentanaDashboard(jugadores);
			dashboard.setVisible(true);
		});
	}
}
