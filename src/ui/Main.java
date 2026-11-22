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
		// Jugadores
		List<Jugador> jugadores = new ArrayList<>();
		jugadores.add(new Jugador(1,"Carlos", "Ruiz", 1, Posicion.BASE, 20, 185, 78, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador(2,"Marcos", "Lopez", 5, Posicion.ESCOLTA, 22, 190, 82, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador(3,"David", "Garcia", 7, Posicion.ALERO, 19, 195, 88, EstadoJugador.LESIONADO));
		jugadores.add(new Jugador(4,"Pablo", "Martinez", 10, Posicion.ALA_PIVOT, 21, 200, 95, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador(5,"Ivan", "Torres", 15, Posicion.PIVOT, 23, 205, 102, EstadoJugador.DISPONIBLE));
 
		// Partidos
		List<Partido> partidos = new ArrayList<>();
		Partido partido1 = new Partido(1, "CB Valencia", LocalDate.of(2026, 11, 22), LocalizacionPartido.LOCAL);
		partido1.registrarResultado(78, 65);
		partidos.add(partido1 );
		
		Partido partido2 = new Partido(2, "Basket Madrid", LocalDate.of(2026, 11, 29), LocalizacionPartido.VISITANTE);
		partido2.registrarResultado(62, 70);
		partidos.add(partido2);
		
		Partido partido3 = new Partido(3, "CB Sevilla", LocalDate.of(2026, 12, 6), LocalizacionPartido.LOCAL);
		partido3.registrarResultado(85, 80);
		partidos.add(partido3);
		
		partidos.add(new Partido(4, "CB Zaragoza", LocalDate.of(2026, 12, 13), LocalizacionPartido.VISITANTE));
		partidos.add(new Partido(5, "CB Bilbao", LocalDate.of(2026, 12, 20), LocalizacionPartido.LOCAL));
		
		SwingUtilities.invokeLater(() -> {
			VentanaDashboard dashboard = new VentanaDashboard(jugadores, partidos);
			dashboard.setVisible(true);
		});
	}
}
