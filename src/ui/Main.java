package ui;
 
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.SwingUtilities;

import clase.EstadoJugador;
import clase.Jugador;
import clase.Posicion;

 
public class Main {
 
	public static void main(String[] args) {
		// ejemplo
		List<Jugador> jugadores = new ArrayList<>();
		jugadores.add(new Jugador(1,"Carlos", "Ruiz", 1, Posicion.BASE, 20, 185, 78, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador(2,"Marcos", "Lopez", 5, Posicion.ESCOLTA, 22, 190, 82, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador(3,"David", "Garcia", 7, Posicion.ALERO, 19, 195, 88, EstadoJugador.LESIONADO));
		jugadores.add(new Jugador(4,"Pablo", "Martinez", 10, Posicion.ALA_PIVOT, 21, 200, 95, EstadoJugador.DISPONIBLE));
		jugadores.add(new Jugador(5,"Ivan", "Torres", 15, Posicion.PIVOT, 23, 205, 102, EstadoJugador.DISPONIBLE));
 
		SwingUtilities.invokeLater(() -> {
			VentanaDashboard dashboard = new VentanaDashboard(jugadores);
			dashboard.setVisible(true);
		});
	}
}
