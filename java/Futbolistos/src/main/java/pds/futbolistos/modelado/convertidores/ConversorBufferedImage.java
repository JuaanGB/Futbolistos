package pds.futbolistos.modelado.convertidores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import jakarta.persistence.AttributeConverter;

public class ConversorBufferedImage implements AttributeConverter<BufferedImage, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(BufferedImage bufferedImage) {
		if (bufferedImage == null) {
			return null;
		}

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(bufferedImage, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BufferedImage convertToEntityAttribute(byte[] dbData) {
		if (dbData == null) {
			return null;
		}

		try (InputStream in = new java.io.ByteArrayInputStream(dbData)) {
			return ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
