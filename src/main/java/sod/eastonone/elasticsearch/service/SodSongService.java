package sod.eastonone.elasticsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.elasticsearch.dao.entity.SodSong;
import sod.eastonone.elasticsearch.dao.repository.SodSongRepository;
import sod.eastonone.elasticsearch.dao.repository.UserRepository;
import sod.eastonone.elasticsearch.es.model.Song;
import sod.eastonone.elasticsearch.es.service.ESService;

@Service
public class SodSongService {

	@Autowired
	private SodSongRepository sodSongRepository;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private ESService esService;

	@Transactional
	public Song createSodSong(final String title, final String playlist, final String link,
			final String bandName, final String songName, final String message,
			final int userId) throws IOException {
		
		Song song = new Song();
		
		
		SodSong sodSong = new SodSong();
		SodSong sodSongSaved = null;
		
		try {
			//Insert DB
			sodSong.setYoutubeTitle(title);
			sodSong.setYoutubePlaylist(playlist);
			sodSong.setYoutubeUrl(link);
			sodSong.setActualBandName(bandName);
			sodSong.setActualSongName(songName);
			sodSong.setUser(userRepository.findById(userId).get());
			sodSongSaved = sodSongRepository.save(sodSong);
		} catch (Exception e) {
			// add logging
			e.printStackTrace();
			throw e;
		}
		try {
			// Insert ES
			song = new Song(sodSongSaved);
			song.setId(sodSongSaved.getId());
			esService.insertSong(song);
		} catch (IOException e) {
			// Add logging
			// Roll back DB insert?
			e.printStackTrace();
		}
		try {
			//Send email
			System.out.println("TBD: Use song data and message to generate group email");
		} catch (Exception e) {
			// Add logging
			// Should this be fatal? If so roll back DB and ES inserts?
			e.printStackTrace();
		} 
		return song;
	}
	
    public List<Song> songsBySearchText(String searchText) throws IOException{
    	Song song = new Song();
    	song.setTitle(searchText);
    	
    	List<Song> songs = new ArrayList<Song>();
    	try {
			songs = esService.fetchSongsWithShouldQuery(song);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
        return songs;
    }
}
