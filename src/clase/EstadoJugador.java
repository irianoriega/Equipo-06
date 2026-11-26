package clase;

public enum EstadoJugador {
	DISPONIBLE("Disponible"),
    LESIONADO("Lesionado");
	
    private final String displayName;

    EstadoJugador(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
