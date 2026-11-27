package util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.ImageIcon;

public class GestorFotos {

	private static final String RUTA_PLACEHOLDER = "resources/images/player.png"; //la imagen que sale cuando no la especificas
	private static final String CARPETA_FOTOS = "data/img";

	public static String guardarFoto(File archivoOrigen) throws IOException {
		File carpetaDestino = new File(CARPETA_FOTOS);
		if (!carpetaDestino.exists()) {
			carpetaDestino.mkdirs();
		}

		String nombreUnico = System.currentTimeMillis() + "_" + archivoOrigen.getName();
		File archivoDestino = new File(carpetaDestino, nombreUnico);

		Files.copy(archivoOrigen.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return nombreUnico;
	}

	public static ImageIcon cargarIcono(String nombreArchivo, int ancho, int alto) {
		ImageIcon icono = null;

		if (nombreArchivo != null && !nombreArchivo.trim().isEmpty()) {
			File archivoFoto = new File(CARPETA_FOTOS, nombreArchivo);
			if (archivoFoto.exists()) {
				icono = new ImageIcon(archivoFoto.getAbsolutePath());
			}
		}

		if (icono == null || icono.getIconWidth() <= 0) {
			File archivoPlaceholder = new File(RUTA_PLACEHOLDER);
			if (archivoPlaceholder.exists()) {
				icono = new ImageIcon(archivoPlaceholder.getAbsolutePath());
			} else {
				return null;
			}
		}

		Image imgEscalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		return new ImageIcon(imgEscalada);
	}
}