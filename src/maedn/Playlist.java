package maedn;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;


public class Playlist implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Song> playlist;
	private String playlistName;
	
	public Playlist(String playlistName) {
		this.setPlaylistName(playlistName);
		playlist = new ArrayList<Song>();
	}
	
	public void addSong(Song song) {
			playlist.add(song);
	}
	
	public ArrayList<Song> getPlaylist() {
		return playlist;
	}
	
	public ArrayList<Song> searchName(String searchedName) {
		
		ArrayList<Song> temp = new ArrayList<Song>();
		
		for(Song song: playlist) {
			if(searchedName.toLowerCase().contains(song.getName().toLowerCase()) || song.getName().toLowerCase().contains(searchedName.toLowerCase())) {
				temp.add(song);
			}
		}
		
		return temp;
	}
	
	public ArrayList<Song> searchInterpret(String searchedInterpret) {		
		ArrayList<Song> temp = new ArrayList<Song>();
		
		for(Song song: playlist) {
			if(searchedInterpret.toLowerCase().contains(song.getInterpret().toLowerCase()) || song.getInterpret().toLowerCase().contains(searchedInterpret.toLowerCase())) {
				temp.add(song);
			}
		}
		
		return temp;
	}
	
	public ArrayList<Song> searchGenre(String searchedGenre) {		
		
		ArrayList<Song> temp = new ArrayList<Song>();
	
		for(Song song: playlist) {
			if(searchedGenre.toLowerCase().contains(song.getGenre().toLowerCase()) || song.getGenre().toLowerCase().contains(searchedGenre.toLowerCase())) {
				temp.add(song);
			}
		}
		
		return temp;
	}
   
	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public void changeSong(int index, String titel, String interpret, String genre, File img) {
		playlist.get(index).setName(titel);
		playlist.get(index).setInterpret(interpret);
		playlist.get(index).setGenre(genre);
		playlist.get(index).setImage(img);
	}
	
	public void setPlaylist(ArrayList<Song> playlist_songs) {
		this.playlist = playlist_songs;
	}

	
	
	
}
