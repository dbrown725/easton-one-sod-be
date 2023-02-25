package sod.eastonone.music.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.service.SodSongService;

@Controller
public class SodSongController {
	
	@Autowired
	private SodSongService sodSongService;
	
    @QueryMapping
    public List<Song> songBySearchText(@Argument String searchText) throws IOException{
    	Song song = new Song();
    	song.setTitle(searchText);
    	
    	List<Song> songs = new ArrayList<Song>();
    	try {
			songs = sodSongService.songsBySearchText(searchText);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
        return songs;
    }

    @QueryMapping
    public List<Song> getMostRecentSongs(@Argument int count) throws IOException{
    	List<Song> songs = new ArrayList<Song>();
    	try {
			songs = sodSongService.getMostRecentSongs(count);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        return songs;
    }

    @QueryMapping
    public int getSongsWithIssuesCount() throws IOException{
    	int count = 0;
    	try {
    		count = sodSongService.getAllSodSongsWithIssuesCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        return count;
    }

    @QueryMapping
    public List<Song> getSongsWithIssues(@Argument int count) throws IOException{
    	List<Song> songs = new ArrayList<Song>();
    	try {
			songs = sodSongService.getAllSodSongsWithIssues(count);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
        return songs;
    }

	@MutationMapping
	public Song insertSodSong(@Argument String title, @Argument String playlist, @Argument String link,
			@Argument String bandName, @Argument String songName, @Argument String message, @Argument int userId)
			throws Exception {

		return sodSongService.createSodSong(title, playlist, link, bandName, songName, message, userId);
	}

	@MutationMapping
	public Song updateSodSong(@Argument int id, @Argument String title, @Argument String playlist, @Argument String link,
			@Argument String bandName, @Argument String songName, @Argument String message, @Argument int userId)
			throws IOException {

		return sodSongService.updateSodSong(id, title, playlist, link, bandName, songName, message, userId);
	}

}