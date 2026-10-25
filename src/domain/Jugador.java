package domain;

import domain.Jugador.Estado;
import domain.Jugador.Posicion;

public class Jugador {
	public enum Estado {
		DISPONIBLE, LESIONADO
	}
	public enum Posicion {
		BASE, ESCOLTA, ALERO, ALA_PIVOT, PIVOT
	}
	
	private String nombre;
	private int dorsal;
	private Posicion posicion;
	private int edad;
	private double altura;
	private double peso;
	private Estado estado;
	
	public Jugador(String nombre, int dorsal, Posicion posicion, int edad, double altura, double peso, Estado estado) {
		super();
		this.nombre = nombre;
		this.dorsal = dorsal;
		this.posicion = posicion;
		this.edad = edad;
		this.altura = altura;
		this.peso = peso;
		this.estado = estado;
	}
	
	public Jugador() {
		super();
		this.nombre = "";
		this.dorsal = 0;
		this.posicion = Posicion.BASE;
		this.edad = 0;
		this.altura = 0.0;
		this.peso = 0.0;
		this.estado = Estado.DISPONIBLE;
	}
	
	public Jugador(Jugador j) {
		super();
		this.nombre = j.nombre;
		this.dorsal = j.dorsal;
		this.posicion = j.posicion;
		this.edad = j.edad;
		this.altura = j.altura;
		this.peso = j.peso;
		this.estado = j.estado;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", dorsal=" + dorsal + ", posicion=" + posicion + ", edad=" + edad
				+ ", altura=" + altura + ", peso=" + peso + ", estado=" + estado + "]";
	}
	
}
