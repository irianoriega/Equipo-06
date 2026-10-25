package clase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Entrenamiento {

    private int id;
    private LocalDate fecha;
    private TipoEntrenamiento tipo;
    private String descripcion;
    private List<Jugador> asistentes;

    public Entrenamiento() {
        asistentes = new ArrayList<>();
    }

    public Entrenamiento(int id, LocalDate fecha,
                         TipoEntrenamiento tipo,
                         String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.asistentes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoEntrenamiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEntrenamiento tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Jugador> getAsistentes() {
        return new ArrayList<>(asistentes);
    }

    public void registrarAsistencia(Jugador jugador) {
        if (jugador != null && !asistentes.contains(jugador)) {
            asistentes.add(jugador);
        }
    }

    public boolean quitarAsistencia(Jugador jugador) {
        return asistentes.remove(jugador);
    }

    public boolean haAsistido(Jugador jugador) {
        return asistentes.contains(jugador);
    }

    @Override
    public String toString() {
        return "Entrenamiento{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", tipo=" + tipo +
                ", descripcion='" + descripcion + '\'' +
                ", asistentes=" + asistentes.size() +
                '}';
    }
}
