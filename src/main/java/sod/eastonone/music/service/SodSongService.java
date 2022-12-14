package sod.eastonone.music.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.music.dao.entity.SodSong;
import sod.eastonone.music.dao.repository.SodSongRepository;
import sod.eastonone.music.dao.repository.UserRepository;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.es.service.ESService;

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
		
		SodSong sodSong = new SodSong();
		SodSong sodSongSaved = null;
		try {
			// Insert DB
			populateAndCleanFields(title, playlist, link, bandName, songName, userId, sodSong);
			sodSongSaved = sodSongRepository.save(sodSong);
		} catch (Exception e) {
			// add logging
			e.printStackTrace();
			throw e;
		}

		Song esSong = new Song();
		try {
			// Insert ES
			esSong = new Song(sodSongSaved);
			esService.insertSong(esSong);
		} catch (IOException e) {
			// Add logging
			// Roll back DB insert?
			e.printStackTrace();
		}
		try {
			//Send email
		} catch (Exception e) {
			// Add logging
			// Should this be fatal? If so roll back DB and ES inserts?
			e.printStackTrace();
		} 
		return esSong;
	}

	@Transactional
	public Song updateSodSong(final int id, final String title, final String playlist, final String link,
			final String bandName, final String songName, final String message,
			final int userId) throws IOException {

		SodSong updatedSongData = new SodSong();
		SodSong sodSongSaved = null;
		try {
			//Update DB
			updatedSongData.setId(id);
			populateAndCleanFields(title, playlist, link, bandName, songName, userId, updatedSongData);

			Optional<SodSong> current = sodSongRepository.findById(id);
			if(!current.get().getYoutubeTitle().equals(updatedSongData.getYoutubeTitle())) {
				sodSongRepository.updateTitleById(updatedSongData.getYoutubeTitle(), String.valueOf(id));
			}
			if(!current.get().getActualBandName().equals(updatedSongData.getActualBandName())) {
				sodSongRepository.updateBandNameById(updatedSongData.getActualBandName(), String.valueOf(id));
			}
			if(!current.get().getActualSongName().equals(updatedSongData.getActualSongName())) {
				sodSongRepository.updateSongNameById(updatedSongData.getActualSongName(), String.valueOf(id));
			}
			if(!current.get().getYoutubeUrl().equals(updatedSongData.getYoutubeUrl())) {
				sodSongRepository.updateUrlById(updatedSongData.getYoutubeUrl(), String.valueOf(id));
			}

			sodSongSaved = updatedSongData;
		} catch (Exception e) {
			// add logging
			e.printStackTrace();
			throw e;
		}

		Song esSong = new Song();
		try {
			// Insert ES
			esSong = new Song(sodSongSaved);
			esService.updateSong(esSong);
		} catch (IOException e) {
			// Add logging
			// Roll back DB update?
			e.printStackTrace();
		}

		return esSong;
	}

	private void populateAndCleanFields(final String title, final String playlist, final String link,
			final String bandName, final String songName, final int userId, SodSong updatedSongData) {
		//Replace double quotes with single quotes and commas with blanks
		updatedSongData.setYoutubeTitle(title.replace("\"", "'").replace(",", ""));
		updatedSongData.setActualBandName(bandName.replace("\"", "'").replace(",", ""));
		updatedSongData.setActualSongName(songName.replace("\"", "'").replace(",", ""));

		updatedSongData.setYoutubePlaylist(playlist);
		updatedSongData.setYoutubeUrl(link);
		updatedSongData.setUser(userRepository.findById(userId).get());
	}
	
	public List<SodSong> getAllSodSongs() {
		return sodSongRepository.getAllSodSongs();
	}
	
	public List<Song> getAllSodSongsWithIssues(int count) {
		List<Song> songs = new ArrayList<Song>();
		for(SodSong sodSong: sodSongRepository.getAllSodSongsWithIssues(count)) {
			songs.add(new Song(sodSong));
		}
		return songs;
	}
	
	public List<Song> getMostRecentSongs(int count) {
		List<Song> songs = new ArrayList<Song>();
		for(SodSong sodSong: sodSongRepository.getMostRecentSongs(count)) {
			songs.add(new Song(sodSong));
		}
		return songs;
	}
	
    public List<Song> songsBySearchText(String searchText) throws IOException{
    	Song song = new Song();
    	song.setTitle(searchText);
    	song.setBandName(searchText);
    	song.setSongName(searchText);
    	
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
