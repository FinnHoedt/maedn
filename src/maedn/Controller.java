package maedn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.scene.media.Media;



public class Controller implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private File saveFile = new File("./Daten/daten.txt");
	
	private File saveFile2 = new File ("./Daten/daten2.txt");
	
	public static Playlist all;
	
	private Playlist selectedPlaylist;
	
	private ArrayList<Playlist> createdPlaylists;
	
	public void initialize() {
		
		all = new Playlist("all");
		
		createdPlaylists = new ArrayList<Playlist>();
		
		try {
			loadAll();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		
				
		loadMedia();
		
		selectedPlaylist = all;
		
		try {
			loadPlaylists();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Playlist> getPlaylists() {
		return createdPlaylists;
	}
	public Playlist getPlaylist() {
		return selectedPlaylist;
	}
	
	public Media getMedia(int currentTrack) {
		ArrayList<Song> songs = selectedPlaylist.getPlaylist();
		Media media = new Media(songs.get(currentTrack).getFile().toURI().toString());
		return media;
	}
	
	public ArrayList<Song> getAllSongList() {
		return all.getPlaylist();
	}
	
	public void saveAll() throws IOException {
		FileOutputStream fos = new FileOutputStream(saveFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(all);
		oos.flush();
		oos.close();
		fos.close();
		System.out.println("Saving successfull!");
	}
	
	public void savePlaylists() throws IOException {
		FileOutputStream fos = new FileOutputStream(saveFile2);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(createdPlaylists);
		oos.flush();
		oos.close();
		fos.close();
		System.out.println("Saving successfull!");
	}

	public void loadAll() throws ClassNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(saveFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		all = (Playlist) ois.readObject();
		ois.close();
		fis.close();
		System.out.println("Loading successfull!");
	}
	
	@SuppressWarnings("unchecked")
	public void loadPlaylists() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(saveFile2);
		ObjectInputStream ois = new ObjectInputStream(fis);
		createdPlaylists = (ArrayList<Playlist>) ois.readObject();
		ois.close();
		fis.close();
		System.out.println("Loading successfull!");
	}
	
	private void loadMedia() {
		
		File directory;
		File[] files;
		ArrayList<Song> songList = all.getPlaylist();
		
		directory = new File("music");
		
		files = directory.listFiles();
		
		if(files != null) {
			if(songList.isEmpty()) {
				for(File file: files) {
					Song song = new Song(file);
					all.addSong(song);
				}
			} else {
				for(File file: files) {
					Boolean checkElement = false;
					for(Song element: songList) {
						if(isEqual(element.getFile().toPath(), file.toPath())) {
							checkElement = true;
						}
					}
					if(!checkElement) {
						Song song = new Song(file);
						all.addSong(song);
					}
				}
			}
		}
	}
	
	public boolean isEqual(Path firstFile, Path secondFile)
    {
        try {
            if (Files.size(firstFile) != Files.size(secondFile)) {
                return false;
            }
 
            byte[] first = Files.readAllBytes(firstFile);
            byte[] second = Files.readAllBytes(secondFile);
            return Arrays.equals(first, second);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public void addPlaylist(Playlist playlist) {
		createdPlaylists.add(playlist);
	}
	
	public void removePlaylist(Playlist playlist) {
		createdPlaylists.remove(playlist);
	}
	
}
	
	

