package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import persistence.FileManager;
import view.FrameHome;

public class Controller implements ActionListener {

	private FrameHome frameHome;
	private long time;

	public Controller() {
		new File(ConstantList.FILE_IMG_PATH).mkdir();
		new File(ConstantList.FILE_IMG_PATH_F).mkdir();
		frameHome = new FrameHome(this);
	}

	private void init() {
		time = System.currentTimeMillis();
		try {
			FileManager.downloadFile(frameHome.getSearch());
			int count = 0;
			int files = 0;
			frameHome.refreshProgress(files);
			for (String image : FileManager.getImagesURL()) {
				FileManager.writeImg(image, new FileOutputStream(
						new File(ConstantList.FILE_IMG_PATH + count + ConstantList.EXTENSION_JPG)));
				frameHome.refreshProgress(++files);
				FileManager.addFilter(String.valueOf(count),
						ImageIO.read(new File(ConstantList.FILE_IMG_PATH + count + ConstantList.EXTENSION_JPG)));
				frameHome.refreshProgress(++files);
				count++;
			}
			paintImages();
			JOptionPane.showMessageDialog(null,
					"Tiempo transcurrido: " + (System.currentTimeMillis() - time) / 1000 + "seg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void paintImages() throws IOException {
		ArrayList<String> images = new ArrayList<>();
		File[] imgFiles = new File(ConstantList.FILE_IMG_PATH_F).listFiles();
		for (File files : imgFiles) {
			images.add(files.getAbsolutePath());
		}
		frameHome.loadImages(images);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (CommandList.valueOf(e.getActionCommand())) {
		case COMMAND_SEARCH:
			init();
			break;
		}
	}

	public static void main(String[] args) {
		new Controller();
	}
}