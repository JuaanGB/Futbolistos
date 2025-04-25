package pds.futbolistos.modelado;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImagenJPA {

	private BufferedImage imagen;
	private byte[] imagenBytes;
	
	public ImagenJPA(BufferedImage imagen) {
		this.imagen = imagen;
		this.imagenBytes = toByteArray(imagen);
	}
	
	public ImagenJPA() {
		this(null);
	}
	
	public BufferedImage getImagen() {
		return imagen;
	}
	
	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
		this.imagenBytes = toByteArray(imagen);
	}
	
	public byte[] getImagenBytes() {
		return imagenBytes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ImagenJPA))
			return false;
		return Arrays.equals(this.imagenBytes, ((ImagenJPA)obj).imagenBytes);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(imagenBytes);
	}
	
	public static byte[] toByteArray(BufferedImage imagen) {
		if (imagen == null)
			return null;
		
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(imagen, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			System.err.println("No se pudo convertir la imagen.");
			return null;
		}
	}
	
	public static ImagenJPA fromByteArray(byte[] imagen) {
		if (imagen == null)
			return null;
		
		try {
			BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imagen));
			ImagenJPA imgJPA = new ImagenJPA(bufImg);
			imgJPA.imagenBytes = imagen;
			return imgJPA;
		} catch (IOException e) {
			System.err.println("Error al convertir bytes a imagen");
			return null;
		}
	}
	
}
