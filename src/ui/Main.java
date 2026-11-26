package ui;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.SwingUtilities;

import clase.Categoria;
import clase.Entrenamiento;
import clase.Equipo;
import clase.EstadoJugador;
import clase.Jugador;
import clase.LocalizacionPartido;
import clase.Nivel;
import clase.Partido;
import clase.Posicion;
import clase.TipoEntrenamiento;

 
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
		
		Partido partido2 = new Partido(2, "CB Madrid", LocalDate.of(2026, 11, 29), LocalizacionPartido.VISITANTE);
		partido2.registrarResultado(62, 70);
		partidos.add(partido2);
		
		Partido partido3 = new Partido(3, "CB Sevilla", LocalDate.of(2026, 12, 6), LocalizacionPartido.LOCAL);
		partido3.registrarResultado(85, 80);
		partidos.add(partido3);
		
		partidos.add(new Partido(4, "CB Zaragoza", LocalDate.of(2026, 12, 13), LocalizacionPartido.VISITANTE));
		partidos.add(new Partido(5, "CB Canarias", LocalDate.of(2026, 12, 20), LocalizacionPartido.LOCAL));
		partidos.add(new Partido(6, "CB Burgos", LocalDate.of(2026, 12, 27), LocalizacionPartido.LOCAL));
		partidos.add(new Partido(7, "CB Valencia", LocalDate.of(2027, 1, 3), LocalizacionPartido.LOCAL));
		partidos.add(new Partido(8, "CB Murcia", LocalDate.of(2027, 1, 10), LocalizacionPartido.LOCAL));

		// Mi equipo
		Equipo equipo = new Equipo(1, "CB Bilbao", Categoria.SENIOR, Nivel.PROVINCIAL, "2026-2027");
		
		// Entrenamientos
		List<Entrenamiento> entrenamientos = new ArrayList<>();
		Entrenamiento entrenamiento1 = new Entrenamiento(1, LocalDate.of(2026, 11, 7), TipoEntrenamiento.TECNICO, "Trabajo de tiro");
		entrenamiento1.registrarAsistencia(jugadores.get(0));
		entrenamiento1.registrarAsistencia(jugadores.get(1));
		entrenamiento1.registrarAsistencia(jugadores.get(2));
		entrenamiento1.registrarAsistencia(jugadores.get(3));
		entrenamiento1.registrarAsistencia(jugadores.get(4));
		entrenamientos.add(entrenamiento1);
		
		Entrenamiento entrenamiento2 = new Entrenamiento(2, LocalDate.of(2026, 11, 14), TipoEntrenamiento.FISICO, "Circuito de resistencia");
		entrenamiento2.registrarAsistencia(jugadores.get(0));
		entrenamiento2.registrarAsistencia(jugadores.get(3));
		entrenamiento2.registrarAsistencia(jugadores.get(4));
		entrenamientos.add(entrenamiento2);
		
		Entrenamiento entrenamiento3 = new Entrenamiento(3, LocalDate.of(2026, 11, 21), TipoEntrenamiento.TACTICO, "Sistemas de ataque");
		entrenamiento3.registrarAsistencia(jugadores.get(1));
		entrenamiento3.registrarAsistencia(jugadores.get(2));
		entrenamiento3.registrarAsistencia(jugadores.get(4));
		entrenamientos.add(entrenamiento3);
		
		
		SwingUtilities.invokeLater(() -> {
			VentanaDashboard dashboard = new VentanaDashboard(jugadores, partidos, equipo, entrenamientos);
			dashboard.setVisible(true);
		});
	}
}
