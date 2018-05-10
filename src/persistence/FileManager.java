package persistence;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import controller.ConstantList;

public class FileManager {

	public static void downloadFile(String image) throws IOException {
		URLConnection website = new URL(ConstantList.WEB_INIT_PATH + image + ConstantList.WEB_END_PATH)
				.openConnection(ConstantList.PROXY);
		website.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		try (InputStream in = website.getInputStream()) {
			Files.copy(in, Paths.get(ConstantList.FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static void loadImages() throws IOException {
		Pattern pattern = Pattern.compile("(https://alpha\\.wallhaven\\.cc/wallpaper/)(\\d+)");
		Matcher matcher = pattern.matcher(Files.readAllLines(Paths.get(ConstantList.FILE_PATH)).get(0));
		HashSet<String> images = new HashSet<>();
		while (matcher.find()) {
			images.add(ConstantList.URL_IMG + matcher.group(2) + ConstantList.EXTENSION_JPG);
		}
		int count = 0;
		for (String string : images) {
			if (3 <= count) {
				break;
			}
			writeImg(string,
					new FileOutputStream(new File(ConstantList.FILE_IMG_PATH + count + ConstantList.EXTENSION_JPG)));
			System.out.println(string);
			count++;
		}
	}

	private static void writeImg(String img, FileOutputStream out) throws IOException {
		URLConnection image = new URL(img).openConnection(ConstantList.PROXY);
		image.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		InputStream in = image.getInputStream();
		int c;
		while ((c = in.read()) != -1) {
			out.write(c);
		}
		in.close();
		out.close();
	}

	public static void addFilter(String name, BufferedImage buffImage) throws IOException {
		for (int i = 0; i < buffImage.getWidth(); i++) {
			for (int j = 0; j < buffImage.getHeight(); j++) {
				Color pixel = new Color(buffImage.getRGB(i, j));
				int newPixel = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
				buffImage.setRGB(i, j, new Color(newPixel, newPixel, newPixel).getRGB());
			}
		}
		ImageIO.write(buffImage, "jpg", new File(ConstantList.FILE_IMG_PATH_F + name));
	}
}