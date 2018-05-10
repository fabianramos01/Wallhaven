package controller;

import javax.swing.ImageIcon;

public enum CommandList {

	COMMAND_SEARCH("COMMAND_SEARCH" , "Buscar",  "/data/search.png");
	
	private String command;
	private String title;
	private String image;
	
	private CommandList(String command, String title, String image) {
		this.command = command;
		this.title = title;
		this.image = image;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getTitle() {
		return title;
	}
	
	public ImageIcon getImage() {
		return new ImageIcon(getClass().getResource(image));
	}
}