package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import persistence.FileManager;
import view.FrameHome;

public class Controller implements ActionListener {

	private FrameHome frameHome;
	
	public Controller() {
		frameHome = new FrameHome(this);
	}
	
	private void init() {
		try {
			FileManager.downloadFile(frameHome.getSearch());
			FileManager.loadImages();
			imageFilter();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void imageFilter() throws IOException {
		File[] imgFiles = new File(ConstantList.FILE_IMG_PATH).listFiles();
		for (File file : imgFiles) {
			FileManager.addFilter(file, ImageIO.read(file));
		}
		ArrayList<String> images = new ArrayList<>();
		imgFiles = new File(ConstantList.FILE_IMG_PATH_F).listFiles();
		for (File files : imgFiles) {
			images.add(files.getAbsolutePath());
		}
		frameHome.loadImages(images);
	}
	
	public static void main(String[] args) {
		new Controller();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (CommandList.valueOf(e.getActionCommand())) {
		case COMMAND_SEARCH:
			init();
			break;
		}
		
	}
}