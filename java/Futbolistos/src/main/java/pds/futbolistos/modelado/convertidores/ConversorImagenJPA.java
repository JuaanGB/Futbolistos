package pds.futbolistos.modelado.convertidores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import jakarta.persistence.AttributeConverter;
import pds.futbolistos.modelado.ImagenJPA;

public class ConversorImagenJPA implements AttributeConverter<ImagenJPA, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(ImagenJPA imagen) {
		if (imagen == null || imagen.getImagen() ==  null)
			return null;
		return imagen.getImagenBytes();
	}

	@Override
	public ImagenJPA convertToEntityAttribute(byte[] dbData) {
		if (dbData == null)
			return null;
		return ImagenJPA.fromByteArray(dbData);
	}
}
