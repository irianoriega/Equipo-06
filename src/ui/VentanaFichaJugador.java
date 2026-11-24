package ui;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import clase.EstadoJugador;
import clase.Jugador;
import clase.Posicion;
import util.GestorFotos;
 
/**
 * Ventana con la ficha de un jugador.
 * Sirve para tres cosas:
 *  - Anadir un jugador nuevo      -> new VentanaFichaJugador(padre, null, false)
 *  - Modificar un jugador         -> new VentanaFichaJugador(padre, jugador, false)
 *  - Ver la ficha (solo lectura)  -> new VentanaFichaJugador(padre, jugador, true)
 */
public class VentanaFichaJugador extends JDialog {
 
	private static final long serialVersionUID = 1L;
 
	// Campos del formulario
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtDorsal;
	private JComboBox<Posicion> cmbPosicion;
	private JTextField txtEdad;
	private JTextField txtAltura;
	private JTextField txtPeso;
	private JComboBox<EstadoJugador> cmbEstado;
	private JLabel lblFoto;
	private JButton btnSeleccionarFoto;
	private String rutaFotoActual = null;
 
	// Botones
	private JButton btnGuardar;
	private JButton btnCancelar;
 
	
	private Jugador resultado = null;
	// id del jugador que se esta modificando (0 si es nuevo)
	private int idOriginal = 0;
 
	public VentanaFichaJugador(JFrame padre, Jugador jugador, boolean soloLectura) {
		super(padre, true); // true = ventana modal (bloquea la de atrás)
 
		if (jugador == null) {
			setTitle("Añadir jugador");
		} else if (soloLectura) {
			setTitle("Ficha de " + jugador.getNombreCompleto());
		} else {
			setTitle("Modificar jugador");
		}
 
		crearComponentes();
 
		// Si nos pasan un jugador, rellenamos el formulario con sus datos
		if (jugador != null) {
			idOriginal = jugador.getId();
			rutaFotoActual = jugador.getRutaFoto();
			rellenarFormulario(jugador);
		}
		
		lblFoto.setIcon(GestorFotos.cargarIcono(rutaFotoActual, 120, 120));
 
		// En modo ficha, no se puede editar nada
		if (soloLectura) {
			bloquearCampos();
		}
 
		pack();
		setLocationRelativeTo(padre); // centrada respecto a la ventana principal
	}
 
	private void crearComponentes() {
		setLayout(new BorderLayout());
 
		// Panel de arriba: título
		JLabel lblTitulo = new JLabel("Ficha del jugador", JLabel.CENTER);
		lblTitulo.setBorder(new EmptyBorder(10, 10, 5, 10));
		add(lblTitulo, BorderLayout.NORTH);
		
		//foto
		JPanel panelFoto = new JPanel(new BorderLayout(5, 5));
		panelFoto.setBorder(new EmptyBorder(10, 20, 10, 0));

		lblFoto = new JLabel("", JLabel.CENTER);
		lblFoto.setPreferredSize(new Dimension(120, 120));
		lblFoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		btnSeleccionarFoto = new JButton("Seleccionar foto");
		btnSeleccionarFoto.addActionListener(e -> seleccionarFoto());

		panelFoto.add(lblFoto, BorderLayout.CENTER);
		panelFoto.add(btnSeleccionarFoto, BorderLayout.SOUTH);
		add(panelFoto, BorderLayout.WEST);
 
		// Panel central 7x2: formulario 
		JPanel panelForm = new JPanel(new GridLayout(8, 2, 5, 8));
		panelForm.setBorder(new EmptyBorder(10, 20, 10, 20));
 
		txtNombre = new JTextField(20);
		txtApellidos = new JTextField();
		txtDorsal = new JTextField();
		cmbPosicion = new JComboBox<>(Posicion.values());
		txtEdad = new JTextField();
		txtAltura = new JTextField();
		txtPeso = new JTextField();
		cmbEstado = new JComboBox<>(EstadoJugador.values());
 
		panelForm.add(new JLabel("Nombre:"));
		panelForm.add(txtNombre);
		panelForm.add(new JLabel("Apellidos:"));
		panelForm.add(txtApellidos);
		panelForm.add(new JLabel("Dorsal:"));
		panelForm.add(txtDorsal);
		panelForm.add(new JLabel("Posición:"));
		panelForm.add(cmbPosicion);
		panelForm.add(new JLabel("Edad:"));
		panelForm.add(txtEdad);
		panelForm.add(new JLabel("Altura (cm):"));
		panelForm.add(txtAltura);
		panelForm.add(new JLabel("Peso (kg):"));
		panelForm.add(txtPeso);
		panelForm.add(new JLabel("Estado:"));
		panelForm.add(cmbEstado);
 
		add(panelForm, BorderLayout.CENTER);
 
		// Panel de abajo: botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnGuardar = new JButton("Guardar cambios");
		btnCancelar = new JButton("Cancelar");
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCancelar);
		add(panelBotones, BorderLayout.SOUTH);
 
		// Eventos de los botones
		btnGuardar.addActionListener(e -> guardar());
		btnCancelar.addActionListener(e -> dispose());
	}
	
	private void seleccionarFoto() {
		JFileChooser selector = new JFileChooser();
		selector.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "jpeg", "png"));

		int opcion = selector.showOpenDialog(this);
		if (opcion == JFileChooser.APPROVE_OPTION) {
			try {
				File archivoElegido = selector.getSelectedFile();
				rutaFotoActual = GestorFotos.guardarFoto(archivoElegido);
				lblFoto.setIcon(GestorFotos.cargarIcono(rutaFotoActual, 120, 120));
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "No se pudo guardar la imagen.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
 
	private void rellenarFormulario(Jugador j) {
		txtNombre.setText(j.getNombre());
		txtApellidos.setText(j.getApellidos());
		txtDorsal.setText(String.valueOf(j.getDorsal()));
		cmbPosicion.setSelectedItem(j.getPosicion());
		txtEdad.setText(String.valueOf(j.getEdad()));
		txtAltura.setText(String.valueOf(j.getAltura()));
		txtPeso.setText(String.valueOf(j.getPeso()));
		cmbEstado.setSelectedItem(j.getEstado());
	}
 
	private void bloquearCampos() {
		txtNombre.setEditable(false);
		txtApellidos.setEditable(false);
		txtDorsal.setEditable(false);
		cmbPosicion.setEnabled(false);
		txtEdad.setEditable(false);
		txtAltura.setEditable(false);
		txtPeso.setEditable(false);
		cmbEstado.setEnabled(false);
		btnSeleccionarFoto.setVisible(false);
		btnGuardar.setVisible(false); // en modo ficha no hay nada que guardar
		btnCancelar.setText("Cerrar");
	}
 
	private void guardar() {
		String nombre = txtNombre.getText().trim();
		String apellidos = txtApellidos.getText().trim();
 
		if (nombre.isEmpty() || apellidos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre y los apellidos no pueden estar vacios.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
 
		try {
			int dorsal = Integer.parseInt(txtDorsal.getText().trim());
			int edad = Integer.parseInt(txtEdad.getText().trim());
			double altura = Double.parseDouble(txtAltura.getText().trim());
			double peso = Double.parseDouble(txtPeso.getText().trim());
 
			if (dorsal < 0 || dorsal > 99) {
				JOptionPane.showMessageDialog(this, "El dorsal debe estar entre 0 y 99.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (edad <= 0 || altura <= 0 || peso <= 0) {
				JOptionPane.showMessageDialog(this, "Edad, altura y peso deben ser mayores que 0.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
 
			Posicion posicion = (Posicion) cmbPosicion.getSelectedItem();
			EstadoJugador estado = (EstadoJugador) cmbEstado.getSelectedItem();
 
			resultado = new Jugador(idOriginal, nombre, apellidos, dorsal, posicion, edad, altura, peso, estado);
			resultado.setEstado(estado);
			resultado.setRutaFoto(rutaFotoActual);
			dispose(); // todo correcto: cerramos la ventana
 
		} catch (NumberFormatException ex) {
			// Se ejecuta si en un campo numérico hay letras o está vacío
			JOptionPane.showMessageDialog(this, "Dorsal, edad, altura y peso deben ser números.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
 
	public Jugador getResultado() {
		return resultado;
	}
}
 