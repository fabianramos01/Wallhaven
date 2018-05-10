package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.CommandList;
import controller.ConstantList;

public class FrameHome extends JFrame {

	private static final long serialVersionUID = 1L;

	private JProgressBar progressBar;
	private JTextField textField;
	private JPanel panelImage;

	public FrameHome(ActionListener listener) {
		setTitle(ConstantList.APP);
		setSize(ConstantList.WIDTH, ConstantList.HEIGHT);
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(getClass().getResource(ConstantList.APP_ICON)).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		init(listener);
		setResizable(false);
		setVisible(true);
	}

	private void init(ActionListener listener) {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(UtilityList.createJLabel(ConstantList.SEARCH, ConstantList.AGENCY_FB, Color.BLACK));
		textField = new JTextField();
		textField.setFont(ConstantList.AGENCY_FB);
		panel.add(textField);
		panel.add(UtilityList.createJButton(CommandList.COMMAND_SEARCH.getCommand(),
				CommandList.COMMAND_SEARCH.getTitle(), CommandList.COMMAND_SEARCH.getImage(), listener));
		add(panel, BorderLayout.NORTH);
		panelImage = new JPanel();
		add(new JScrollPane(panelImage), BorderLayout.CENTER);
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMaximum(ConstantList.MAX_PROGRESS);
		progressBar.setFont(ConstantList.AGENCY_FB);
		add(progressBar, BorderLayout.SOUTH);
	}

	public void loadImages(ArrayList<String> images) {
		panelImage.removeAll();
		panelImage.updateUI();
		panelImage.setLayout(new GridLayout(images.size() / 3, 3));
		for (String string : images) {
			panelImage.add(new JLabel(
					UtilityList.scaledImage(new ImageIcon(string), ConstantList.IMG_SIZE, ConstantList.IMG_SIZE)));
		}
		revalidate();
	}

	public void refreshProgress(int value, int all) {
		progressBar.setValue(value * 100 / all);
	}

	public String getSearch() {
		return textField.getText().replace(' ', '+');
	}
}
