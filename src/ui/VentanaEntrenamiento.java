package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import clase.Entrenamiento;
import clase.TipoEntrenamiento;


// Formulario de un entrenamiento. Sirve para dos cosas:
//   - Añadir un entrenamiento nuevo  -> new VentanaEntrenamiento(padre, null)
//   - Modificar un entrenamiento     -> new VentanaEntrenamiento(padre, entrenamiento)
// Al cerrarse, getResultado() devuelve el entrenamiento con los datos escritos,
// o null si se ha cancelado.
 
public class VentanaEntrenamiento extends JDialog {

	private static final long serialVersionUID = 1L;

	// CREAMOS LAS PARTES QUE APARECEN EN EL FORMULARIO
	private JTextField txtFecha;
	private JComboBox<TipoEntrenamiento> cmbTipo;
	private JTextArea txtObservaciones;

	private Entrenamiento resultado = null; //en esta variable es donde vamos a guardar el entreno cuando el usuario lo guarde
	// id del entrenamiento que se está modificando (0 si es nuevo porque ya lo va a poner la base de datos)
	private int idOriginal = 0;

	public VentanaEntrenamiento(JFrame padre, Entrenamiento entrenamiento) {
		super(padre, true); //HACE QUE NO SE PUEDA TOCAR LA VENTANA DE ATRAS MIENTRAS QUE ESTA EL FORMULARIO ABIERTO, PPORQUE SINO TE DEJABA ABRIR MUCHOS. HEMOS USADO LA IA PARA SABER COMO HACER ESTO

		if (entrenamiento == null) { 
			setTitle("Añadir entrenamiento");
		} else {
			setTitle("Modificar entrenamiento");
		}
		crearComponentes();

		if (entrenamiento == null) {
			txtFecha.setText(LocalDate.now().toString()); // pone por defecto la fecha d hoy
		} else {
			// Si nos pasan un entrenamiento, el formulario copia los datos para que el usuario vea lo que va a modificar
			idOriginal = entrenamiento.getId();
			txtFecha.setText(entrenamiento.getFecha().toString());
			cmbTipo.setSelectedItem(entrenamiento.getTipo());
			txtObservaciones.setText(entrenamiento.getObservaciones());
		}

		setSize(420, 300);
		setLocationRelativeTo(padre); // centrada respecto a la ventana de detras
	}

	private void crearComponentes() {
		setLayout(new BorderLayout());
		//arriba  etiquetas de fecha y tipo  
		JPanel panelDatos = new JPanel(new GridLayout(2, 2, 5, 8));
		panelDatos.setBorder(new EmptyBorder(15, 20, 10, 20));

		//el recuadro de texto y el combo box
		txtFecha = new JTextField();
		cmbTipo = new JComboBox<>(TipoEntrenamiento.values());
		//añadimos en orden las etiquetas con sus correspondientes partes del formulario
		panelDatos.add(new JLabel("Fecha (aaaa-mm-dd):"));
		panelDatos.add(txtFecha);
		panelDatos.add(new JLabel("Tipo:"));
		panelDatos.add(cmbTipo);
		add(panelDatos, BorderLayout.NORTH);

		// Centro: observaciones 
		JPanel panelObservaciones = new JPanel(new BorderLayout(5, 5));
		panelObservaciones.setBorder(new EmptyBorder(0, 20, 0, 20));
		txtObservaciones = new JTextArea();
		panelObservaciones.add(new JLabel("Observaciones:"), BorderLayout.NORTH);
		panelObservaciones.add(txtObservaciones, BorderLayout.CENTER);
		add(panelObservaciones, BorderLayout.CENTER);

		// Abajo: botones
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnGuardar = new JButton("Guardar");
		JButton btnCancelar = new JButton("Cancelar");
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCancelar);
		add(panelBotones, BorderLayout.SOUTH);
		// Eventos que usan metodos definidps abajo
		btnGuardar.addActionListener(e -> guardar());
		btnCancelar.addActionListener(e -> dispose()); // resultado se queda a null
	}

	private void guardar() {
		LocalDate fecha;
		try {
			fecha = LocalDate.parse(txtFecha.getText().trim());
		} catch (DateTimeParseException ex) {
			// Se ejecuta si la fecha no tiene el formato aaaa-mm-dd o no existe (p. ej. 2026-02-30)
			JOptionPane.showMessageDialog(this, "La fecha debe tener el formato aaaa-mm-dd (por ejemplo 2026-11-27).",
					"Error", JOptionPane.ERROR_MESSAGE);
			return; // no cerramos la ventana para que pueda corregirla
		}

		TipoEntrenamiento tipo = (TipoEntrenamiento) cmbTipo.getSelectedItem();
		String observaciones = txtObservaciones.getText().trim();

		resultado = new Entrenamiento(idOriginal, fecha, tipo, observaciones);
		dispose(); // todo correcto: cerramos la ventana
	}

	public Entrenamiento getResultado() {
		return resultado;
	}
}
