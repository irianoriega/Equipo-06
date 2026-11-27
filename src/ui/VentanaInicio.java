package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clase.Entrenamiento;
import clase.Equipo;
import clase.Jugador;
import clase.Partido;

 //Ventana de inicio  de HoopsManager.
 //Aqui tenemos una barra lateral con accesos rápidos a las distintas secciones.
 //Al elegir una sección, abre VentanaDashboard en la pestaña que se ha elegido.
 
public class VentanaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Indices para saber que ventana se debe abrir al elegir una seccion
	private static final int PESTANA_ROSTER = 1;
	private static final int PESTANA_CALENDARIO = 2;
	private static final int PESTANA_ENTRENAMIENTOS = 3;
	private static final int PESTANA_ESTADISTICAS = 4;
	private static final int PESTANA_ENFERMERIA = 5;

	// creamos los colores que vamos a usar:
	//He usado la IA para sacar los numeritos de los colores
	private static final Color AZUL = new Color(25, 118, 210);
	private static final Color FONDO_LATERAL = new Color(240, 242, 245);
	private static final Color BORDE_TARJETA = new Color(210, 214, 220);
	private static final Color TARJETA_RESALTADA = new Color(232, 240, 254);

	// creamos las variables con los datos que se pasan al Dashboard, 
	private List<Jugador> jugadores;
	private List<Partido> partidos;
	private Equipo equipo;
	private List<Entrenamiento> entrenamientos;
	
	
//creamos la ventana principal


	public VentanaInicio(List<Jugador> jugadores, List<Partido> partidos, Equipo equipo,
			List<Entrenamiento> entrenamientos) {
		super();
		this.jugadores = jugadores;
		this.partidos = partidos;
		this.equipo = equipo;
		this.entrenamientos = entrenamientos;
		
		//configuracion general de la ventana
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(950, 650);
		setLocationRelativeTo(null); 
		
		//Ahora usamos los metodos ya creados para añadirle tanto el panel lateral como el de las secciones al panel principal.
		setLayout(new BorderLayout());
		add(crearPanelLateral(), BorderLayout.WEST);
		add(crearPanelContenido(), BorderLayout.CENTER);
	}
	
// METODOS
	// configuracion de la barra lateral
	private JPanel crearPanelLateral() {
		//creamos el panel lateral
		JPanel panel = new JPanel(new BorderLayout());
		
		//configuracion general del panel lateral 
		panel.setBackground(FONDO_LATERAL);
		panel.setBorder(new EmptyBorder(15, 10, 15, 10));
		panel.setPreferredSize(new Dimension(190, 0)); //le ponemos un ancho fijo
		//arriba queremos que aparezca el nombre de la app y los botones de navegacion;
		
		//creamos la parte de arriba donde iran los botones de las secciones
		JPanel panelArriba = new JPanel(new GridLayout(8, 1, 0, 5));
		panelArriba.setOpaque(false); // para poder ver ek color de fondo
		
			//nombre app
			JLabel lblApp = new JLabel("HoopsManager", JLabel.CENTER);
			lblApp.setFont(new Font("Arial", Font.BOLD, 18));
		//le añadimos la etiqueta con el nombre al panel	
		panelArriba.add(lblApp);
		
		//usando el metodo descrito abajo crearBotonlateral (para que todos sean iguales) creamos los botones de las secciones
		
		//botones 
		JButton btnInicio = crearBotonLateral("Inicio");
		JButton btnPlantilla = crearBotonLateral("Plantilla");
		JButton btnPartidos = crearBotonLateral("Partidos");
		JButton btnEntrenamientos = crearBotonLateral("Entrenamientos");
		JButton btnEstadisticas = crearBotonLateral("Estadísticas");
		JButton btnLesiones = crearBotonLateral("Lesiones");
		
		//añadimos los botones
		panelArriba.add(btnInicio);
		panelArriba.add(btnPlantilla);
		panelArriba.add(btnPartidos);
		panelArriba.add(btnEntrenamientos);
		panelArriba.add(btnEstadisticas);
		panelArriba.add(btnLesiones);
		panel.add(panelArriba, BorderLayout.NORTH);

		// Resultamos el boton de inicio porque estamos ahi
		btnInicio.setBackground(AZUL);
		btnInicio.setForeground(Color.WHITE);

		// en la parte de ABAJO queremos poner los botones de configuracion y salir
		//creamos un panel
		JPanel panelAbajo = new JPanel(new GridLayout(2, 1, 0, 5));
		panelAbajo.setOpaque(false);

		JButton btnConfiguracion = crearBotonLateral("Configuración");
		JButton btnSalir = crearBotonLateral("Salir");
		panelAbajo.add(btnConfiguracion);
		panelAbajo.add(btnSalir);
		
		//añadimos el panel que contiene los botones d conf y salir abajo del panel lateral
		panel.add(panelAbajo, BorderLayout.SOUTH);

		//Aqui añado los eventos de los botones. 
		// (cuando pulso un boton melleva a esa pestaña)
		//uso el metodo abrirSeccion que esta definido abajo en ACCIONES
		btnPlantilla.addActionListener(e -> abrirSeccion(PESTANA_ROSTER));
		btnPartidos.addActionListener(e -> abrirSeccion(PESTANA_CALENDARIO));
		btnEntrenamientos.addActionListener(e -> abrirSeccion(PESTANA_ENTRENAMIENTOS));
		btnEstadisticas.addActionListener(e -> abrirSeccion(PESTANA_ESTADISTICAS));
		btnLesiones.addActionListener(e -> abrirSeccion(PESTANA_ENFERMERIA));
		btnConfiguracion.addActionListener(e -> JOptionPane.showMessageDialog(this,
				"La configuración estará disponible próximamente.", "Configuración",
				JOptionPane.INFORMATION_MESSAGE));  //este parte todavia no esta creada asique he añadio un mensaje informativo
		btnSalir.addActionListener(e -> salir()); //el metodo esta implementado abajo

		return panel;
	}
	
	//configuracion panelprincipal de la ventana
		private JPanel crearPanelContenido() {
			//creamos el panel
			JPanel panel = new JPanel(new BorderLayout(0, 20));
			//configuracion general
			panel.setBackground(Color.WHITE);
			panel.setBorder(new EmptyBorder(25, 30, 25, 30));

			//Arriba tenemos que poner el titulo y el subtitulo
			JPanel panelCabecera = new JPanel(new GridLayout(2, 1));
			panelCabecera.setOpaque(false);
			//creamos las etiquetas
			JLabel lblTitulo = new JLabel("Bienvenido a HoopsManager", JLabel.CENTER);
			lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
			JLabel lblSubtitulo = new JLabel("Gestión de tu equipo de baloncesto", JLabel.CENTER);
			lblSubtitulo.setForeground(Color.GRAY);
			//añadimos las etiquetas a un panel cabecera que añadimos arriba del panel
			panelCabecera.add(lblTitulo);
			panelCabecera.add(lblSubtitulo);
			panel.add(panelCabecera, BorderLayout.NORTH);

			// en el centro vamos a añadir los recuadros con las secciones
			//para ello creamos un panel de secciones que es el que luego vamos a añadir al centro del panel general
			JPanel panelTarjetas = new JPanel(new BorderLayout(0, 15));
			panelTarjetas.setOpaque(false);

			// Las 4 secciones iguales, en cuadricula de 2 x 2
			JPanel cuadricula = new JPanel(new GridLayout(2, 2, 15, 15));
			cuadricula.setOpaque(false);
			//usamos el metodo crearTarjeta que he definido abajo
			cuadricula.add(crearTarjeta("Plantilla", "Gestiona los jugadores", PESTANA_ROSTER));
			cuadricula.add(crearTarjeta("Partidos", "Consulta y registra los partidos", PESTANA_CALENDARIO));
			cuadricula.add(crearTarjeta("Entrenamientos", "Organiza las sesiones de entrenamiento",
					PESTANA_ENTRENAMIENTOS));
			cuadricula.add(crearTarjeta("Estadísticas", "Consulta el rendimiento del equipo", PESTANA_ESTADISTICAS));
			panelTarjetas.add(cuadricula, BorderLayout.CENTER);

			// La seccion lesiones abajo ocupa todo el ancho por eso no la añadimos
			// a la rejilla sino que la añadimos directamente a la parte de abajo del panel de secciones
			
			JPanel tarjetaLesiones = crearTarjeta("Lesiones", "Controla el estado de los jugadores", PESTANA_ENFERMERIA); 
			//ahora añadimos tanto el panel con la seccion lesion como el panel con la cuadricula del resto de secciones
			panelTarjetas.add(tarjetaLesiones, BorderLayout.SOUTH);
			panel.add(panelTarjetas, BorderLayout.CENTER);
			return panel;
		}

	//metodo para crear las tarjetas/ secciones del panel de secciones:
	private JPanel crearTarjeta(String titulo, String descripcion, int pestana) {
		JPanel tarjeta = new JPanel(new GridLayout(2, 1));
		tarjeta.setBackground(Color.WHITE);
		//Aqui he usado IA PARA SABER COMO PONER EL BORDE 
		tarjeta.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(BORDE_TARJETA), new EmptyBorder(10, 10, 10, 10)));
		
		tarjeta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 17));
		lblTitulo.setForeground(AZUL);
		lblTitulo.setVerticalAlignment(JLabel.BOTTOM); 
		JLabel lblDescripcion = new JLabel(descripcion, JLabel.CENTER);
		lblDescripcion.setForeground(Color.GRAY);
		lblDescripcion.setVerticalAlignment(JLabel.TOP);

		tarjeta.add(lblTitulo);
		tarjeta.add(lblDescripcion);

		//Ahora le tenemos que añadir el mousse listener para cuando lo pulsemos nos abra la seccion
		tarjeta.addMouseListener(new MouseAdapter() {
			
			//para ello cuando lo clickemos abrimos la seccion como en los botones con el metodo ya creado previamente.
			@Override
			public void mouseClicked(MouseEvent e) {
				abrirSeccion(pestana);
			}
			//cuando pasa el raton hacemos que se resalte
			@Override
			public void mouseEntered(MouseEvent e) {
				tarjeta.setBackground(TARJETA_RESALTADA); 
			}
			//cuando sale el raton volvemos a su color 
			@Override
			public void mouseExited(MouseEvent e) {
				tarjeta.setBackground(Color.WHITE);
			}
		});

		return tarjeta;
	}
	//metodo para crear botones de la barra lateral:
	private JButton crearBotonLateral(String texto) {
		JButton boton = new JButton(texto);
		boton.setHorizontalAlignment(JButton.LEFT); // para que el texto aparezca alineado a la izquierda
		boton.setBackground(FONDO_LATERAL);
		boton.setBorderPainted(false);
		boton.setOpaque(true);		
		return boton;
	}
	// este metodo hace que cuando pulses un boton o una seccion te lleve a la seccion que corresponde. 
	private void abrirSeccion(int pestana) {
		VentanaDashboard dashboard = new VentanaDashboard(jugadores, partidos, equipo, entrenamientos);
		dashboard.seleccionarPestana(pestana);
		
		// Cuando cerramos el dashbord, solo cerramos esa seccion, volvemos al inicio no se cierra todo
		dashboard.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		dashboard.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				VentanaInicio.this.setVisible(true);
			}
		});
		setVisible(false);
		dashboard.setVisible(true);
	}
	//metodo que muestra el panel de dialogo de confirmacion al ir a cerrar
	private void salir() {
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir de HoopsManager?",
				"Salir", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
}





