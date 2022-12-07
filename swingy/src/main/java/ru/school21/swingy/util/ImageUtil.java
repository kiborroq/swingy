package ru.school21.swingy.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.school21.swingy.exception.SwingyRuntimeException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageUtil {

	public static ImageIcon getImageIcon(String filepath) {
		try	{
			InputStream is = ImageUtil.class.getResourceAsStream(filepath);
			SwingyAssert.notNull(is, "Image not found: " + filepath);

			return new ImageIcon(ImageIO.read(is));
		} catch (Exception e) {
			throw new SwingyRuntimeException(e);
		}
	}

}
