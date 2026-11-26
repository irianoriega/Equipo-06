package clase;

public enum TipoEntrenamiento {
	FISICO("Físico"),
    TECNICO("Técnico"),
    TACTICO("Táctico"),
    TIRO("Tiro"),
    PARTIDO("Partido"),
    OTRO("Otro");
	
    private final String displayName;

    TipoEntrenamiento(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
