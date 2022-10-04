package sod.eastonone.elasticsearch.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import sod.eastonone.elasticsearch.dao.entity.BullpenSong;
import sod.eastonone.elasticsearch.es.model.Song;
import sod.eastonone.elasticsearch.es.service.ESService;
import sod.eastonone.elasticsearch.service.BullpenSongService;
import sod.eastonone.elasticsearch.service.SodSongService;

@Controller
public class SongController {
	
	@Autowired
	private BullpenSongService bullpenSongService;
	
	@Autowired
	private SodSongService sodSongService;
	
    @QueryMapping
    public Song bullpenSongById(@Argument int id) {
    	Optional<BullpenSong> bullpenSongOpt = bullpenSongService.getBullpenSong(id);
        return new Song(bullpenSongOpt.get());
    }
    
    @QueryMapping
    public List<Song> getAllBullpenSongs(@Argument int count) {
    	
    	System.out.println("in getAllBullpenSongs count: " + count);
    	
    	List<Song> songs = new ArrayList<Song>();

			List<BullpenSong> bullpenSongs = bullpenSongService.getAllBullpenSongs(count);
			
			songs = new ArrayList<Song>();
			
			for(BullpenSong bpSong: bullpenSongs) {
				songs.add(new Song(bpSong));
			}
        return songs;
    }
    
    @MutationMapping
    public Song addBullpenSong(
        @Argument String title,
        @Argument String link,
        @Argument String message,
        @Argument String bandName,
        @Argument String songName,
        @Argument int sortOrder,
        @Argument int userId) {

      BullpenSong bullpenSong = bullpenSongService.createBullpenSong(title, link, bandName, songName, message, sortOrder, userId);
      return new Song(bullpenSong);
    }
    
    @MutationMapping
    public Song updateBullpenSong(
    	@Argument int id,	
        @Argument String title,
        @Argument String link,
        @Argument String message,
        @Argument String bandName,
        @Argument String songName,
        @Argument int sortOrder) {

      BullpenSong bullpenSong = bullpenSongService.updateBullpenSong(id, title, link, bandName, songName, message, sortOrder);
      return new Song(bullpenSong);
    }
    
    @MutationMapping
    public boolean deleteBullpenSong(
    	@Argument int id) {
      return bullpenSongService.deleteBullpenSong(id);
    }
    
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

	@MutationMapping
	public Song insertSodSong(@Argument String title, @Argument String playlist, @Argument String link,
			@Argument String bandName, @Argument String songName, @Argument String message, @Argument int userId)
			throws IOException {

		return sodSongService.createSodSong(title, playlist, link, bandName, songName, message, userId);
	}

//    @SchemaMapping
//    public Author author(Book book) {
//        return Author.getById(book.getAuthorId());
//    }
}