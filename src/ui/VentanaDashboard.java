package ui;
 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import clase.Entrenamiento;
import clase.Equipo;
import clase.EstadoJugador;
import clase.Jugador;
import clase.Partido;
import clase.Posicion;
import javax.swing.BorderFactory;

public class VentanaDashboard extends JFrame {
 
	private static final long serialVersionUID = 1L;
 
	private List<Jugador> jugadores;
	private List<Partido> partidos;
	private Equipo equipo;
	private List<Entrenamiento> entrenamientos;
	 
	// Inicio
	private JLabel lblPartidoEquipos;
	private JLabel lblPartidoFecha;
	private JLabel lblPartidoLugar;
	private JLabel lblTotal;
	private JLabel lblDisponibles;
	private JLabel lblLesionados;
 
	// Roster
	private JTextField txtBuscar;
	private JComboBox<String> cmbPosicion;
	private JTable tabla;
	private DefaultTableModel modeloTabla;
	// Jugadores que se ven ahora mismo en la tabla (después de buscar/filtrar)
	private List<Jugador> jugadoresMostrados;
 
	// Calendario
	private DefaultTableModel modeloPartidos;
	private JTable tablaPartidos;

	// Enfermería
	private DefaultListModel<String> modeloLesionados;
 
	
	public VentanaDashboard(List<Jugador> jugadores, List<Partido> partidos, Equipo equipo, List<Entrenamiento> entrenamientos) {
		super("HoopsManager - Equipo-06");
		this.jugadores = jugadores;
		this.jugadoresMostrados = new ArrayList<>();
		this.partidos = partidos;
		this.equipo = equipo;
		this.entrenamientos = entrenamientos;
 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 520);
		setLocationRelativeTo(null); // centrada en la pantalla
 
		// Las pestañas
		JTabbedPane pestanas = new JTabbedPane();
		pestanas.addTab("Inicio", crearPanelInicio());
		pestanas.addTab("Roster", crearPanelRoster());
		pestanas.addTab("Calendario", crearPanelCalendario());
		pestanas.addTab("Estadísticas", crearPanelPendiente("Estadísticas del equipo"));
		pestanas.addTab("Enfermería", crearPanelEnfermeria());
 
		add(pestanas);
 
		actualizarTodo();
	}
 
	// PESTAÑA INICIO
	private JPanel crearPanelInicio() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(15, 15, 15, 15));
 
		// bienvenida
		JLabel lblBienvenida = new JLabel("Bienvenido a HoopsManager - Gestión de tu equipo de baloncesto",
				JLabel.CENTER);
		panel.add(lblBienvenida, BorderLayout.NORTH);
 
		JPanel centro = new JPanel(new GridLayout(1, 3, 10, 10));
 
		// Columna 1: próximo partido
		JPanel panelPartido = new JPanel(new GridLayout(3,1));
		panelPartido.setBorder(BorderFactory.createTitledBorder("Próximo partido"));
		lblPartidoEquipos = new JLabel("", JLabel.CENTER);
		lblPartidoFecha = new JLabel("", JLabel.CENTER);
		lblPartidoLugar = new JLabel("", JLabel.CENTER);
		panelPartido.add(lblPartidoEquipos, BorderLayout.CENTER);
		panelPartido.add(lblPartidoFecha, BorderLayout.CENTER);
		panelPartido.add(lblPartidoLugar, BorderLayout.CENTER);
		centro.add(panelPartido);
 
		// Columna 2: líderes
		JPanel panelLideres = new JPanel(new GridLayout(3, 1));
		panelLideres.setBorder(BorderFactory.createTitledBorder("Líderes del equipo"));
		// TODO: calcular los líderes de verdad
		panelLideres.add(new JLabel("Líderes en puntos: -", JLabel.CENTER));
		panelLideres.add(new JLabel("Líderes en rebotes: -", JLabel.CENTER));
		panelLideres.add(new JLabel("Líderes en asistencias: -", JLabel.CENTER));
		centro.add(panelLideres);
 
		// Columna 3: resumen de la plantilla
		JPanel panelResumen = new JPanel(new GridLayout(3, 1));
		panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen de la plantilla"));
		lblTotal = new JLabel("", JLabel.CENTER);
		lblDisponibles = new JLabel("", JLabel.CENTER);
		lblLesionados = new JLabel("", JLabel.CENTER);
		panelResumen.add(lblTotal);
		panelResumen.add(lblDisponibles);
		panelResumen.add(lblLesionados);
		centro.add(panelResumen);
 
		panel.add(centro, BorderLayout.CENTER);
		return panel;
	}
 
	// PESTAÑA ROSTER
	private JPanel crearPanelRoster() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
 
		// Arriba: buscador y filtro por posición
		JPanel panelArriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelArriba.add(new JLabel("Buscar (nombre o dorsal):"));
		txtBuscar = new JTextField(15);
		panelArriba.add(txtBuscar);
 
		JButton btnBuscar = new JButton("Buscar");
		panelArriba.add(btnBuscar);
 
		panelArriba.add(new JLabel("   Posición:"));
		cmbPosicion = new JComboBox<>();
		cmbPosicion.addItem("Todas");
		for (Posicion p : Posicion.values()) {
			cmbPosicion.addItem(p.toString());
		}
		panelArriba.add(cmbPosicion);
 
		panel.add(panelArriba, BorderLayout.NORTH);
 
		// Centro: tabla de jugadores
		String[] columnas = { "Dorsal", "Nombre", "Posición", "Edad", "Altura (cm)", "Estado" };
		modeloTabla = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;
 
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false; // la tabla es solo para ver, no se edita escribiendo en ella
			}
		};
		tabla = new JTable(modeloTabla);
		panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
 
		// Abajo: botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btnAnadir = new JButton("Añadir");
		JButton btnModificar = new JButton("Modificar");
		JButton btnEliminar = new JButton("Eliminar");
		JButton btnVerFicha = new JButton("Ver ficha");
		panelBotones.add(btnAnadir);
		panelBotones.add(btnModificar);
		panelBotones.add(btnEliminar);
		panelBotones.add(btnVerFicha);
		panel.add(panelBotones, BorderLayout.SOUTH);
 
		// Eventos
		btnBuscar.addActionListener(e -> actualizarTabla());
		txtBuscar.addActionListener(e -> actualizarTabla()); // al pulsar Enter
		cmbPosicion.addActionListener(e -> actualizarTabla());
		btnAnadir.addActionListener(e -> anadirJugador());
		btnModificar.addActionListener(e -> modificarJugador());
		btnEliminar.addActionListener(e -> eliminarJugador());
		btnVerFicha.addActionListener(e -> verFicha());
 
		return panel;
	}
	
	// PESTAÑA CALENDARIO
	private JPanel crearPanelCalendario() {
	    JPanel panel = new JPanel(new BorderLayout(5, 5));
	    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

	    String[] columnas = { "Fecha", "Rival", "Localización", "Resultado", "Estado" };
	    modeloPartidos = new DefaultTableModel(columnas, 0) {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public boolean isCellEditable(int fila, int columna) {
	            return false;
	        }
	    };
	    tablaPartidos = new JTable(modeloPartidos);
	    panel.add(new JScrollPane(tablaPartidos), BorderLayout.CENTER);

	    return panel;
	}
 
	// PESTAÑA ENFERMERÍA
	private JPanel crearPanelEnfermeria() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
 
		panel.add(new JLabel("Jugadores lesionados:"), BorderLayout.NORTH);
 
		modeloLesionados = new DefaultListModel<>();
		JList<String> lista = new JList<>(modeloLesionados);
		panel.add(new JScrollPane(lista), BorderLayout.CENTER);
 
		// TODO: cuando exista la clase Lesion, mostrar tipo de lesión y fecha de recuperación
		return panel;
	}
 
	// PESTAÑAS TODAVÍA SIN HACER
	private JPanel crearPanelPendiente(String texto) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel(texto, JLabel.CENTER), BorderLayout.CENTER);
		return panel;
	}
 
	// ACCIONES DE LOS BOTONES
	private void anadirJugador() {
		VentanaFichaJugador ficha = new VentanaFichaJugador(this, null, false);
		ficha.setVisible(true); // el programa se queda aquí hasta que se cierra la ficha
 
		Jugador nuevo = ficha.getResultado();
		if (nuevo != null) {
			if (existeDorsal(nuevo.getDorsal(), null)) {
				JOptionPane.showMessageDialog(this, "Ya hay un jugador con el dorsal " + nuevo.getDorsal() + ".",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			nuevo.setId(siguienteId());
			jugadores.add(nuevo);
			actualizarTodo();
		}
	}
 
	private void modificarJugador() {
		Jugador seleccionado = getJugadorSeleccionado();
		if (seleccionado == null) {
			return;
		}
 
		VentanaFichaJugador ficha = new VentanaFichaJugador(this, seleccionado, false);
		ficha.setVisible(true);
 
		Jugador modificado = ficha.getResultado();
		if (modificado != null) {
			if (existeDorsal(modificado.getDorsal(), seleccionado)) {
				JOptionPane.showMessageDialog(this, "Ya hay otro jugador con el dorsal " + modificado.getDorsal() + ".",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Copiamos los datos nuevos al jugador original
			seleccionado.setNombre(modificado.getNombre());
			seleccionado.setApellidos(modificado.getApellidos());
			seleccionado.setDorsal(modificado.getDorsal());
			seleccionado.setPosicion(modificado.getPosicion());
			seleccionado.setEdad(modificado.getEdad());
			seleccionado.setAltura(modificado.getAltura());
			seleccionado.setPeso(modificado.getPeso());
			seleccionado.setEstado(modificado.getEstado());
			actualizarTodo();
		}
	}
 
	private void eliminarJugador() {
		Jugador seleccionado = getJugadorSeleccionado();
		if (seleccionado == null) {
			return;
		}
 
		int respuesta = JOptionPane.showConfirmDialog(this,
				"¿Seguro que quieres eliminar a " + seleccionado.getNombreCompleto() + "?", "Confirmar",
				JOptionPane.YES_NO_OPTION);
 
		if (respuesta == JOptionPane.YES_OPTION) {
			jugadores.remove(seleccionado);
			actualizarTodo();
		}
	}
 
	private void verFicha() {
		Jugador seleccionado = getJugadorSeleccionado();
		if (seleccionado == null) {
			return;
		}
		VentanaFichaJugador ficha = new VentanaFichaJugador(this, seleccionado, true);
		ficha.setVisible(true);
	}
 
	// MÉTODOS AUXILIARES

	/** Devuelve el jugador de la fila seleccionada, o null (con aviso) si no hay ninguna. */
	private Jugador getJugadorSeleccionado() {
		int fila = tabla.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Selecciona primero un jugador de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		return jugadoresMostrados.get(fila);
	}
 
	/** Comprueba si ya existe un jugador con ese dorsal (ignorando al jugador "excepto"). */
	private boolean existeDorsal(int dorsal, Jugador excepto) {
		for (Jugador j : jugadores) {
			if (j != excepto && j.getDorsal() == dorsal) {
				return true;
			}
		}
		return false;
	}
 
	/** Devuelve un id nuevo (el mayor que existe + 1). */
	private int siguienteId() {
		int max = 0;
		for (Jugador j : jugadores) {
			if (j.getId() > max) {
				max = j.getId();
			}
		}
		return max + 1;
	}
 
	/** Actualiza todas las pestañas. */
	private void actualizarTodo() {
		actualizarTabla();
		actualizarResumen();
		actualizarPartidos();
		actualizarEnfermeria();
		
	}

	/** Rellena la tabla aplicando el buscador y el filtro de posición. */
	private void actualizarTabla() {
		modeloTabla.setRowCount(0); // vaciamos la tabla
		jugadoresMostrados.clear();
 
		String texto = txtBuscar.getText().trim().toLowerCase();
		String posicionElegida = (String) cmbPosicion.getSelectedItem();
 
		for (Jugador j : jugadores) {
			// Filtro por posición
			boolean cumplePosicion = posicionElegida.equals("Todas")
					|| j.getPosicion().toString().equals(posicionElegida);
 
			// Busqueda por nombre o dorsal
			boolean cumpleBusqueda = texto.isEmpty() || j.getNombreCompleto().toLowerCase().contains(texto)
					|| String.valueOf(j.getDorsal()).equals(texto);
 
			if (cumplePosicion && cumpleBusqueda) {
				jugadoresMostrados.add(j);
				modeloTabla.addRow(new Object[] { j.getDorsal(), j.getNombreCompleto(), j.getPosicion(), j.getEdad(),
						j.getAltura(), j.getEstado() });
			}
		}
	}
 
	private void actualizarResumen() {
		int disponibles = 0;
		int lesionados = 0;
		for (Jugador j : jugadores) {
			if (j.getEstado() == EstadoJugador.DISPONIBLE) {
				disponibles++;
			} else {
				lesionados++;
			}
		}
		lblTotal.setText("Jugadores en plantilla: " + jugadores.size());
		lblDisponibles.setText("Disponibles: " + disponibles);
		lblLesionados.setText("Lesionados: " + lesionados);
	}
	
	private void actualizarPartidos() {
		modeloPartidos.setRowCount(0);
	    Partido proximoPartido = null;

	    for (Partido p : partidos) {
	    	String resultado = "-";
			String estado = "Próximo";

			if (p.tieneResultado()) {
				resultado = p.getPuntosEquipo() + " - " + p.getPuntosRival();
				estado = "Finalizado";
			}

	        modeloPartidos.addRow(new Object[] {
	            p.getFecha(), p.getRival(), p.getLocalizacion(), resultado, estado
	        });

	        if (!p.tieneResultado() && proximoPartido == null) {
	            proximoPartido = p;
	        }
	    }

	    if (proximoPartido != null) {
	    	lblPartidoEquipos.setText(equipo.getNombre() + " VS " + proximoPartido.getRival());
	    	lblPartidoFecha.setText("Fecha: " + proximoPartido.getFecha());
	    	lblPartidoLugar.setText("Lugar: " + proximoPartido.getLocalizacion());
	    } else {
	    	lblPartidoEquipos.setText("No hay partidos pendientes");
	    	lblPartidoFecha.setText("");
	    	lblPartidoLugar.setText("");
	    }
	}
	
	private void actualizarEnfermeria() {
		modeloLesionados.clear();
		for (Jugador j : jugadores) {
			if (j.getEstado() == EstadoJugador.LESIONADO) {
				modeloLesionados.addElement("#" + j.getDorsal() + " - " + j.getNombreCompleto() + " (" + j.getPosicion() + ")");
			}
		}
	}
}
 