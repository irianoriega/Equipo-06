package ui;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.SwingUtilities;

import clase.EstadoJugador;
import clase.Jugador;
import clase.LocalizacionPartido;
import clase.Partido;
import clase.Posicion;

 
public class Main {
 
	public static void main(String[] args) {
		// ejemplo
		List<Jugador> jugadores = new ArrayList<>();
		jugadores.add(new Jugador("Carlos Ruiz", 1, Posicion.BASE, 20, 185, 78, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador("Marcos Lopez", 5, Posicion.ESCOLTA, 22, 190, 82, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador("David Garcia", 7, Posicion.ALERO, 19, 195, 88, EstadoJugador.LESIONADO));
		jugadores.add(new Jugador("Pablo Martinez", 10, Posicion.ALA_PIVOT, 21, 200, 95, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador("Ivan Torres", 15, Posicion.PIVOT, 23, 205, 102, EstadoJugador.DISPONIBLE));
 
		//partidos de ejemplo
		List<Partido> partidos = new ArrayList<>();
		partidos.add(new Partido(1,"Bilbao Basket",LocalDate.of(2026, 11, 20),LocalizacionPartido.LOCAL));
		//pongo un resultado de ejemplo
		partidos.get(0).registrarResultado(65, 87);
		partidos.add(new Partido(2,"Real Madrid", LocalDate.of(2026, 11, 25), LocalizacionPartido.VISITANTE));
		partidos.add(new Partido(3,"Valencia", LocalDate.of(2026, 12, 2), LocalizacionPartido.LOCAL));
		
		
		SwingUtilities.invokeLater(() -> {
			VentanaDashboard dashboard = new VentanaDashboard(jugadores, partidos);
			dashboard.setVisible(true);
		});
	}
}
