package pds.futbolistos.modelado.convertidores;

import jakarta.persistence.AttributeConverter;
import pds.futbolistos.modelado.ImagenJPA;

public class ConversorImagenJPA implements AttributeConverter<ImagenJPA, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(ImagenJPA imagen) {
		if (imagen == null || imagen.getImagen() == null)
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
