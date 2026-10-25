package clase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Partido {

    private int id;
    private String rival;
    private LocalDate fecha;
    private LocalizacionPartido localizacion;
    private Integer puntosEquipo;
    private Integer puntosRival;
    private List<Estadistica> estadisticas;

    public Partido() {
        estadisticas = new ArrayList<>();
    }

    public Partido(int id, String rival, LocalDate fecha,
                   LocalizacionPartido localizacion) {
        this.id = id;
        this.rival = rival;
        this.fecha = fecha;
        this.localizacion = localizacion;
        this.estadisticas = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRival() {
        return rival;
    }

    public void setRival(String rival) {
        this.rival = rival;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalizacionPartido getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(LocalizacionPartido localizacion) {
        this.localizacion = localizacion;
    }

    public Integer getPuntosEquipo() {
        return puntosEquipo;
    }

    public Integer getPuntosRival() {
        return puntosRival;
    }

    public void registrarResultado(int puntosEquipo, int puntosRival) {
        if (puntosEquipo < 0 || puntosRival < 0) {
            throw new IllegalArgumentException(
                    "El resultado no puede ser negativo.");
        }

        this.puntosEquipo = puntosEquipo;
        this.puntosRival = puntosRival;
    }

    public boolean tieneResultado() {
        return puntosEquipo != null && puntosRival != null;
    }

    public List<Estadistica> getEstadisticas() {
        return new ArrayList<>(estadisticas);
    }

    public void agregarEstadistica(Estadistica estadistica) {
        if (estadistica != null) {
            estadisticas.add(estadistica);
        }
    }

    public boolean eliminarEstadistica(Estadistica estadistica) {
        return estadisticas.remove(estadistica);
    }

    @Override
    public String toString() {
        String resultado = tieneResultado()
                ? puntosEquipo + " - " + puntosRival
                : "Pendiente";

        return "Partido{" +
                "id=" + id +
                ", rival='" + rival + '\'' +
                ", fecha=" + fecha +
                ", localizacion=" + localizacion +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}
