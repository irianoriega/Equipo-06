package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import clase.Entrenamiento;
import clase.Jugador;
import clase.Partido;
import javax.swing.table.DefaultTableModel;


public class PanelEstadisticas extends JPanel {
	
	//PESTAÑA ESTADISTICAS DE LOS JUGADORES

	private static final long serialVersionUID = 1L;

	//hemos metido las mismas listas que contiene ventanaDashboard para las estadisticas
	private List<Jugador> jugadores;
	private List<Partido> partidos;
	private List<Entrenamiento> entrenamientos;

	private DefaultTableModel modeloTabla;
	private GraficoBarras grafico;
	private JLabel lblJugados;
	private JLabel lblVictorias;
	private JLabel lblDerrotas;
	private JLabel lblPuntosFavor;
	private JLabel lblPuntosContra;


	public PanelEstadisticas(List<Jugador> jugadores, List<Partido> partidos, List<Entrenamiento> entrenamientos) {
		this.jugadores = jugadores;
		this.partidos = partidos;
		this.entrenamientos = entrenamientos;

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		// Pestañas internas
		JTabbedPane pestanas = new JTabbedPane();
		pestanas.addTab("Estadísticas de jugadores", crearPanelJugadores());
		pestanas.addTab("Estadísticas del equipo", crearPanelEquipo());
		add(pestanas, BorderLayout.CENTER);
		actualizar();
	}

	//  pestaña de jugadores (tabla arriba, gráfico abajo)
	private JPanel crearPanelJugadores() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		// Tabla en el centro (ocupa el espacio que sobre)
		JScrollPane scrollTabla = new JScrollPane(crearTabla());
		scrollTabla.getViewport().setBackground(Color.WHITE);
		panel.add(scrollTabla, BorderLayout.CENTER);

		// Gráfico abajo, con altura fija
		JPanel panelGrafico = new JPanel(new BorderLayout());
		panelGrafico.setBackground(Color.WHITE);
		panelGrafico.setPreferredSize(new Dimension(0, 250));

		JLabel titulo = new JLabel("  Puntos por partido (según el calendario)");
		titulo.setFont(new Font("Calibri", Font.BOLD, 14));
		panelGrafico.add(titulo, BorderLayout.NORTH);

		grafico = new GraficoBarras();
		panelGrafico.add(grafico, BorderLayout.CENTER);

		panel.add(panelGrafico, BorderLayout.SOUTH);
		return panel;
	}

	//  crear la tabla de jugadores (vacía; se rellena en actualizar)
	private JTable crearTabla() {
		String[] columnas = { "Dorsal", "Nombre", "Posición", "Edad", "Altura (cm)", "Estado", "Entrenamientos" };

		modeloTabla = new DefaultTableModel(columnas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};

		JTable tabla = new JTable(modeloTabla);
		tabla.setRowHeight(30);
		tabla.setGridColor(new Color(220, 220, 220));
		tabla.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));

		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(SwingConstants.CENTER);
		tabla.setDefaultRenderer(Object.class, centrado); //para centrar el texto de todas las columnas

		tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(170);

		return tabla;
	}

	
	//metodo para volver a leer las listas y actualizar todo
	public void actualizar() {
		actualizarTablaJugadores();
		actualizarPartidos();
	}

	// metodo para rellenar la tabla con los jugadores reales
	private void actualizarTablaJugadores() {
		modeloTabla.setRowCount(0); 

		for (Jugador j : jugadores) {
			int asistidos = 0;
			for (Entrenamiento e : entrenamientos) {
				if (e.getAsistentes().contains(j)) {
					asistidos++;
				}
			}
			//en este metodo primero vaciamos la tabla y luego contamos cuantas veces a entrenado cada jugador
			modeloTabla.addRow(new Object[] { j.getDorsal(), j.getNombreCompleto(), j.getPosicion(), j.getEdad(),
					j.getAltura(), j.getEstado(), asistidos + "/" + entrenamientos.size() });
		}
	}

	
	
	//metodo para calcular el resumen del equipo y los datos del grafico a partir de los partidos que ya tienen resultado
	private void actualizarPartidos() {
		List<String> rivales = new ArrayList<>();
		List<Integer> puntosEquipo = new ArrayList<>();

		int victorias = 0;
		int derrotas = 0;
		int totalFavor = 0;
		int totalContra = 0;

		for (Partido p : partidos) {
			if (p.tieneResultado()) { // solo los partidos ya jugados
				int favor = p.getPuntosEquipo();
				int contra = p.getPuntosRival();

				rivales.add(String.valueOf(p.getRival()));
				puntosEquipo.add(favor);

				totalFavor += favor;
				totalContra += contra;
				if (favor > contra) {
					victorias++;
				} else if (favor < contra) {
					derrotas++;
				}
			}
		}
		

		int jugados = rivales.size();
		
		actualizarEstadisticasEquipo(jugados, victorias, derrotas, totalFavor, totalContra);

		// Le pasamos los datos al gráfico y le decimos que se redibuje
		grafico.setDatos(rivales, puntosEquipo);
	}

	//clase interna para dibujar el grafico a mano
	//PARA REALIZAR ESTE PASO HECHO USO DE CLAUDE
	// el uso de la ia va desde este punto del codigo hasta donde se indica el final.
	private class GraficoBarras extends JPanel {

		private static final long serialVersionUID = 1L;

		private List<String> nombres = new ArrayList<>();
		private List<Integer> valores = new ArrayList<>();

		public GraficoBarras() {
			setBackground(Color.WHITE);
		}

		/** Guarda los datos nuevos y pide a Swing que vuelva a pintar. */
		public void setDatos(List<String> nombres, List<Integer> valores) {
			this.nombres = nombres;
			this.valores = valores;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Si no hay partidos con resultado, mostramos un mensaje
			if (valores.isEmpty()) {
				g.setColor(Color.GRAY);
				String mensaje = "Todavía no hay partidos con resultado";
				int anchoMensaje = g.getFontMetrics().stringWidth(mensaje);
				g.drawString(mensaje, (getWidth() - anchoMensaje) / 2, getHeight() / 2);
				return;
			}

			int margenIzq = 45;
			int margenAbajo = 30;
			int margenArriba = 15;
			int anchoZona = getWidth() - margenIzq - 10;
			int altoZona = getHeight() - margenAbajo - margenArriba;

			// Escala del eje Y: se adapta al mayor valor (redondeado hacia arriba)
			int mayor = 0;
			for (int v : valores) {
				if (v > mayor) {
					mayor = v;
				}
			}
			int paso = (mayor <= 50) ? 10 : 20;
			int maximo = Math.max(paso, ((mayor + paso - 1) / paso) * paso);

			// Eje Y: líneas grises y números
			for (int valor = 0; valor <= maximo; valor += paso) {
				int y = margenArriba + altoZona - (int) ((double) valor / maximo * altoZona);
				g.setColor(new Color(225, 225, 225));
				g.drawLine(margenIzq, y, margenIzq + anchoZona, y);
				g.setColor(Color.DARK_GRAY);
				g.drawString(String.valueOf(valor), margenIzq - 30, y + 5);
			}

			// Barras
			int numBarras = valores.size();
			int anchoHueco = anchoZona / numBarras;
			int anchoBarra = (int) (anchoHueco * 0.5);

			for (int i = 0; i < numBarras; i++) {
				int altoBarra = (int) ((double) valores.get(i) / maximo * altoZona);
				int x = margenIzq + i * anchoHueco + (anchoHueco - anchoBarra) / 2;
				int y = margenArriba + altoZona - altoBarra;

				g.setColor(new Color(38, 132, 245));
				g.fillRect(x, y, anchoBarra, altoBarra);

				// Valor encima de la barra
				g.setColor(Color.DARK_GRAY);
				String valorTexto = String.valueOf(valores.get(i));
				int anchoValor = g.getFontMetrics().stringWidth(valorTexto);
				g.drawString(valorTexto, x + (anchoBarra - anchoValor) / 2, y - 4);

				// Nombre del rival debajo
				String nombre = nombres.get(i);
				int anchoNombre = g.getFontMetrics().stringWidth(nombre);
				g.drawString(nombre, margenIzq + i * anchoHueco + (anchoHueco - anchoNombre) / 2,
						margenArriba + altoZona + 20);
			}
		}
	}
	//aqui finaliza la clase interna para crear el grafico, y el uso de claude
	
	
	// PESTAÑA ESTADISTICAS DEL EQUIPO
    private DefaultTableModel modeloEquipo;

    // pestaña del equipo (resumen con los resultados del calendario)
    private JPanel crearPanelEquipo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Resumen de la temporada"));

        modeloEquipo = new DefaultTableModel(new Object[]{"Estadística", "Valor"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modeloEquipo.addRow(new Object[]{"Partidos jugados", "-"});
        modeloEquipo.addRow(new Object[]{"Victorias", "-"});
        modeloEquipo.addRow(new Object[]{"Derrotas", "-"});
        modeloEquipo.addRow(new Object[]{"Puntos a favor por partido", "-"});
        modeloEquipo.addRow(new Object[]{"Puntos en contra por partido", "-"});

        JTable tabla = new JTable(modeloEquipo);
        tabla.setRowHeight(28);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    // llama a este método donde antes hacías los setText de las etiquetas
    private void actualizarEstadisticasEquipo(int jugados, int victorias, int derrotas,
                                              double totalFavor, double totalContra) {
        double mediaFavor = jugados == 0 ? 0 : totalFavor / jugados;
        double mediaContra = jugados == 0 ? 0 : totalContra / jugados;

        modeloEquipo.setValueAt(jugados, 0, 1);
        modeloEquipo.setValueAt(victorias, 1, 1);
        modeloEquipo.setValueAt(derrotas, 2, 1);
        modeloEquipo.setValueAt(String.format("%.1f", mediaFavor), 3, 1);
        modeloEquipo.setValueAt(String.format("%.1f", mediaContra), 4, 1);
    }
}
