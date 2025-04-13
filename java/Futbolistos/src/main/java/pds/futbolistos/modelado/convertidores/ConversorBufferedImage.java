package pds.futbolistos.modelado.convertidores;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import jakarta.persistence.AttributeConverter;

public class ConversorBufferedImage implements AttributeConverter<BufferedImage, Blob> {

	@Override
	public Blob convertToDatabaseColumn(BufferedImage bufferedImage) {
		if (bufferedImage == null) {
			return null;
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
			byte[] imageBytes = byteArrayOutputStream.toByteArray();
			return new javax.sql.rowset.serial.SerialBlob(imageBytes);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BufferedImage convertToEntityAttribute(Blob dbData) {
		if (dbData == null) {
			return null;
		}

		try (InputStream inputStream = dbData.getBinaryStream()) {
			return ImageIO.read(inputStream);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
