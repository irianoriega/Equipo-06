package clase;

import java.util.ArrayList;
import java.util.List;

public class Club {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String ciudad;
    private List<Equipo> equipos;

    public Club() {
        equipos = new ArrayList<>();
    }

    public Club(int id, String nombre, String direccion,
                String telefono, String correo, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.ciudad = ciudad;
        this.equipos = new ArrayList<>();
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Equipo> getEquipos() {
        return new ArrayList<>(equipos);
    }

    public void agregarEquipo(Equipo equipo) {
        if (equipo == null) {
            return;
        }

        if (equipos.contains(equipo)) {
            return;
        }

        for (Equipo existente : equipos) {
            if (existente.getId() == equipo.getId()) {
                throw new IllegalArgumentException(
                        "Ya existe un equipo con ese identificador.");
            }
        }

        if (equipo.getClub() != null
                && equipo.getClub() != this) {
            throw new IllegalArgumentException(
                    "El equipo ya pertenece a otro club.");
        }

        equipos.add(equipo);
        equipo.setClub(this);
    }

    public boolean eliminarEquipo(Equipo equipo) {
        if (equipos.remove(equipo)) {
            equipo.setClub(null);
            return true;
        }

        return false;
    }

    public Equipo buscarEquipoPorId(int id) {
        for (Equipo equipo : equipos) {
            if (equipo.getId() == id) {
                return equipo;
            }
        }
        return null;
    }

    public List<Equipo> buscarEquiposPorCategoria(
            Categoria categoria) {
        List<Equipo> encontrados = new ArrayList<>();

        for (Equipo equipo : equipos) {
            if (equipo.getCategoria() == categoria) {
                encontrados.add(equipo);
            }
        }

        return encontrados;
    }

    public List<Equipo> buscarEquiposPorNivel(Nivel nivel) {
        List<Equipo> encontrados = new ArrayList<>();

        for (Equipo equipo : equipos) {
            if (equipo.getNivel() == nivel) {
                encontrados.add(equipo);
            }
        }

        return encontrados;
    }

    public List<Equipo> buscarEquiposPorTemporada(
            String temporada) {
        List<Equipo> encontrados = new ArrayList<>();

        if (temporada == null) {
            return encontrados;
        }

        for (Equipo equipo : equipos) {
            if (temporada.equalsIgnoreCase(
                    equipo.getTemporada())) {
                encontrados.add(equipo);
            }
        }

        return encontrados;
    }

    public int getNumeroEquipos() {
        return equipos.size();
    }

    public int getNumeroJugadores() {
        int total = 0;

        for (Equipo equipo : equipos) {
            total += equipo.getJugadores().size();
        }

        return total;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", numeroEquipos=" + getNumeroEquipos() +
                ", numeroJugadores=" + getNumeroJugadores() +
                '}';
    }
}
