package clase;


import java.time.LocalDate;

public class Lesion {

    private int id;
    private Jugador jugador;
    private String tipoLesion;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaRecuperacion;
    private String observaciones;

    public Lesion() {
    }

    public Lesion(int id, Jugador jugador, String tipoLesion,
                  LocalDate fechaInicio,
                  LocalDate fechaEstimadaRecuperacion,
                  String observaciones) {
        this.id = id;
        this.jugador = jugador;
        this.tipoLesion = tipoLesion;
        this.fechaInicio = fechaInicio;
        this.fechaEstimadaRecuperacion =
                fechaEstimadaRecuperacion;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public String getTipoLesion() {
        return tipoLesion;
    }

    public void setTipoLesion(String tipoLesion) {
        this.tipoLesion = tipoLesion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaEstimadaRecuperacion() {
        return fechaEstimadaRecuperacion;
    }

    public void setFechaEstimadaRecuperacion(LocalDate fecha) {
        this.fechaEstimadaRecuperacion = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean estaEnPeriodo(LocalDate fecha) {
        if (fecha == null || fechaInicio == null) {
            return false;
        }

        return !fecha.isBefore(fechaInicio)
                && (fechaEstimadaRecuperacion == null
                || fecha.isBefore(fechaEstimadaRecuperacion));
    }

    @Override
    public String toString() {
        return "Lesion{" +
                "id=" + id +
                ", jugador=" +
                (jugador != null
                    ? jugador.getNombreCompleto()
                    : "Sin jugador") +
                ", tipoLesion='" + tipoLesion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaEstimadaRecuperacion=" +
                fechaEstimadaRecuperacion +
                '}';
    }
}

