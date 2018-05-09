package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;

import controller.ConstantList;

public class FileManager {

	public static void loadImages(String image) throws IOException {
		URLConnection website = new URL(ConstantList.WEB_INIT_PATH + image + ConstantList.WEB_END_PATH)
				.openConnection();
		website.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		try (InputStream in = website.getInputStream()) {
			Files.copy(in, Paths.get(ConstantList.FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static void main(String[] args) {
		String image = JOptionPane.showInputDialog("Ingrese el nombre de la imagen");
		try {
			loadImages(image);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}