package clase;

public enum Categoria {
	BABY_BASKET("Baby basket"),
	BENJAMIN("Benjamín"),
	ALEVIN("Alevín"),
	INFANTIL("Infantil"),
	CADETE("Cadete"),
	JUNIOR("Junior"),
	SUB_22("Sub-22"),
	SENIOR("Senior"),
	VETERANOS("Veteranos");
	
    private final String displayName;

    Categoria(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
