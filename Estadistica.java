package clase;

public class Estadistica {

    private int id;
    private Jugador jugador;
    private int puntos;
    private int rebotes;
    private int asistencias;
    private int faltas;
    private int minutosJugados;

    public Estadistica() {
    }

    public Estadistica(int id, Jugador jugador, int puntos,
                       int rebotes, int asistencias,
                       int faltas, int minutosJugados) {
        this.id = id;
        this.jugador = jugador;
        this.puntos = puntos;
        this.rebotes = rebotes;
        this.asistencias = asistencias;
        this.faltas = faltas;
        this.minutosJugados = minutosJugados;
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

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getMinutosJugados() {
        return minutosJugados;
    }

    public void setMinutosJugados(int minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    @Override
    public String toString() {
        return "Estadistica{" +
                "jugador=" +
                (jugador != null
                    ? jugador.getNombreCompleto()
                    : "Sin jugador") +
                ", puntos=" + puntos +
                ", rebotes=" + rebotes +
                ", asistencias=" + asistencias +
                ", faltas=" + faltas +
                ", minutosJugados=" + minutosJugados +
                '}';
    }
}
