package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import persistence.FileManager;
import view.FrameHome;

public class Controller implements ActionListener {

	private FrameHome frameHome;
	private Timer timer;
	private long time;
	private File copy;

	public Controller() {
		frameHome = new FrameHome(this);
		createTimer();
	}

	private void init() {
		time = System.currentTimeMillis();
		copy = new File(ConstantList.FILE_IMG_PATH_F);
		timer.start();
		try {
			FileManager.downloadFile(frameHome.getSearch());
			FileManager.loadImages();
			imageFilter();
			timer.stop();
			JOptionPane.showMessageDialog(null, "Tiempo transcurrido: " + (System.currentTimeMillis()-time)/1000 + "seg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void imageFilter() throws IOException {
		File[] imgFiles = new File(ConstantList.FILE_IMG_PATH).listFiles();
		for (File file : imgFiles) {
			FileManager.addFilter(file.getName(), ImageIO.read(file));
		}
		ArrayList<String> images = new ArrayList<>();
		imgFiles = new File(ConstantList.FILE_IMG_PATH_F).listFiles();
		for (File files : imgFiles) {
			images.add(files.getAbsolutePath());
		}
		frameHome.loadImages(images);
	}

	private void createTimer() {
		timer = new Timer(ConstantList.TIME_REFRESH, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("------------------");
				time++;
				frameHome.refreshProgress(copy.list().length, 23);
				if (24 == copy.list().length) {
					timer.stop();
				}
			}
		});
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