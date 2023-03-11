package maedn;

import java.io.File;
import java.io.Serializable;

public class Song implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	private String interpret;
	private String genre;
	private File file;
	private File image;
	
	public Song(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setImage(File image) {
		this.image = image;
	}
	
	public File getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterpret() {
		return interpret;
	}

	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
}
