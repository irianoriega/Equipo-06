package clase;

public class Jugador {
	 private int id;
	    private String nombre;
	    private String apellidos;
	    private int dorsal;
	    private Posicion posicion;
	    private int edad;
	    private double altura;
	    private double peso;
	    private EstadoJugador estado;

	    public Jugador() {
	        estado = EstadoJugador.DISPONIBLE;
	    }

	    public Jugador(int id, String nombre, String apellidos,
	                   int dorsal, Posicion posicion, int edad,
	                   double altura, double peso) {
	        this.id = id;
	        this.nombre = nombre;
	        this.apellidos = apellidos;
	        this.dorsal = dorsal;
	        this.posicion = posicion;
	        this.edad = edad;
	        this.altura = altura;
	        this.peso = peso;
	        this.estado = EstadoJugador.DISPONIBLE;
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

	    public String getApellidos() {
	        return apellidos;
	    }

	    public void setApellidos(String apellidos) {
	        this.apellidos = apellidos;
	    }

	    public int getDorsal() {
	        return dorsal;
	    }

	    public void setDorsal(int dorsal) {
	        this.dorsal = dorsal;
	    }

	    public Posicion getPosicion() {
	        return posicion;
	    }

	    public void setPosicion(Posicion posicion) {
	        this.posicion = posicion;
	    }

	    public int getEdad() {
	        return edad;
	    }

	    public void setEdad(int edad) {
	        this.edad = edad;
	    }

	    public double getAltura() {
	        return altura;
	    }

	    public void setAltura(double altura) {
	        this.altura = altura;
	    }

	    public double getPeso() {
	        return peso;
	    }

	    public void setPeso(double peso) {
	        this.peso = peso;
	    }

	    public EstadoJugador getEstado() {
	        return estado;
	    }

	    public void setEstado(EstadoJugador estado) {
	        this.estado = estado;
	    }

	    public String getNombreCompleto() {
	        return nombre + " " + apellidos;
	    }

	    @Override
	    public String toString() {
	        return "Jugador{" +
	                "id=" + id +
	                ", nombre='" + getNombreCompleto() + '\'' +
	                ", dorsal=" + dorsal +
	                ", posicion=" + posicion +
	                ", estado=" + estado +
	                '}';
	    }
}
