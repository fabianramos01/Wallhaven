package persistence;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import controller.ConstantList;

public class FileManager {

	public static void loadImages(String image) throws IOException {
		// Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.0.73",
		// 8080));
		URL website = new URL(ConstantList.WEB_INIT_PATH + image);
		try (InputStream in = website.openStream()) {
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