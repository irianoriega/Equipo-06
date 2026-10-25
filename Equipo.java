package clase;

import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private int id;
    private String nombre;
    private Categoria categoria;
    private Nivel nivel;
    private String temporada;
    private Club club;
    private Entrenador entrenador;

    private List<Jugador> jugadores;
    private List<Partido> partidos;
    private List<Entrenamiento> entrenamientos;
    private List<Lesion> lesiones;

    public Equipo() {
        inicializarListas();
    }

    public Equipo(int id, String nombre, Categoria categoria,
                  Nivel nivel, String temporada) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.nivel = nivel;
        this.temporada = temporada;
        inicializarListas();
    }

    private void inicializarListas() {
        jugadores = new ArrayList<>();
        partidos = new ArrayList<>();
        entrenamientos = new ArrayList<>();
        lesiones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<Jugador> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    public List<Partido> getPartidos() {
        return new ArrayList<>(partidos);
    }

    public List<Entrenamiento> getEntrenamientos() {
        return new ArrayList<>(entrenamientos);
    }

    public List<Lesion> getLesiones() {
        return new ArrayList<>(lesiones);
    }

    public void agregarJugador(Jugador jugador) {
        if (jugador == null) {
            return;
        }

        if (buscarJugadorPorId(jugador.getId()) != null) {
            return;
        }

        for (Jugador existente : jugadores) {
            if (existente.getDorsal() == jugador.getDorsal()) {
                throw new IllegalArgumentException(
                        "Ya existe un jugador con ese dorsal.");
            }
        }

        jugadores.add(jugador);
    }

    public boolean eliminarJugador(Jugador jugador) {
        return jugadores.remove(jugador);
    }

    public Jugador buscarJugadorPorId(int id) {
        for (Jugador jugador : jugadores) {
            if (jugador.getId() == id) {
                return jugador;
            }
        }
        return null;
    }

    public Jugador buscarJugadorPorDorsal(int dorsal) {
        for (Jugador jugador : jugadores) {
            if (jugador.getDorsal() == dorsal) {
                return jugador;
            }
        }
        return null;
    }

    public List<Jugador> buscarJugadoresPorNombre(String texto) {
        List<Jugador> encontrados = new ArrayList<>();

        if (texto == null) {
            return encontrados;
        }

        String busqueda = texto.toLowerCase();

        for (Jugador jugador : jugadores) {
            if (jugador.getNombreCompleto()
                    .toLowerCase().contains(busqueda)) {
                encontrados.add(jugador);
            }
        }

        return encontrados;
    }

    public List<Jugador> getJugadoresDisponibles() {
        List<Jugador> disponibles = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            if (jugador.getEstado() == EstadoJugador.DISPONIBLE) {
                disponibles.add(jugador);
            }
        }

        return disponibles;
    }

    public void agregarPartido(Partido partido) {
        if (partido != null && !partidos.contains(partido)) {
            partidos.add(partido);
        }
    }

    public boolean eliminarPartido(Partido partido) {
        return partidos.remove(partido);
    }

    public void agregarEntrenamiento(Entrenamiento entrenamiento) {
        if (entrenamiento != null
                && !entrenamientos.contains(entrenamiento)) {
            entrenamientos.add(entrenamiento);
        }
    }

    public boolean eliminarEntrenamiento(
            Entrenamiento entrenamiento) {
        return entrenamientos.remove(entrenamiento);
    }

    public void agregarLesion(Lesion lesion) {
        if (lesion != null && !lesiones.contains(lesion)) {
            lesiones.add(lesion);
        }
    }

    public boolean eliminarLesion(Lesion lesion) {
        return lesiones.remove(lesion);
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", nivel=" + nivel +
                ", temporada='" + temporada + '\'' +
                ", jugadores=" + jugadores.size() +
                '}';
    }
}
