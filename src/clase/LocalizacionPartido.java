package clase;

public enum LocalizacionPartido {
	LOCAL("Local"),
    VISITANTE("Visitante");
	
    private final String displayName;

    LocalizacionPartido(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
