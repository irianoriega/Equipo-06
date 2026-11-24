package clase;

public enum Posicion {
    BASE("Base"),
    ESCOLTA("Escolta"),
    ALERO("Alero"),
    ALA_PIVOT("Ala-pívot"),
    PIVOT("Pívot");
    
    private final String displayName;

    Posicion(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}


