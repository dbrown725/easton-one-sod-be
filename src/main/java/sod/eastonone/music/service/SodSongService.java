package sod.eastonone.music.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.music.dao.entity.SodSong;
import sod.eastonone.music.dao.repository.SodSongRepository;
import sod.eastonone.music.dao.repository.UserRepository;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.es.service.ESService;
import sod.eastonone.music.model.BandStats;
import sod.eastonone.music.service.helpers.CSVHelper;

@Service
public class SodSongService {

	@Autowired
	private SodSongRepository sodSongRepository;

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private ESService esService;

    @Autowired
    private EmailService emailService;

	@Transactional
	public Song createSodSong(final String title, final String playlist, final String link,
			final String bandName, final String songName, final String message,
			final int userId) throws Exception {
		
		SodSong sodSong = new SodSong();
		SodSong sodSongSaved = null;
		try {
			// Insert DB
			populateAndCleanFields(title, playlist, link, bandName, songName, sodSong);
			sodSong.setUser(userRepository.findById(userId).get());
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
			throw e;
		}
		try {
			emailService.sendSODNotification(sodSongSaved, message);
		} catch (Exception e) {
			// Add logging
			// Should this be fatal? If so roll back DB and ES inserts?
			e.printStackTrace();
			//throw e;
		} 
		return esSong;
	}

	@Transactional
	public Song updateSodSong(final int id, final String title, final String playlist, final String link,
			final String bandName, final String songName) throws IOException {

		SodSong updatedSongData = new SodSong();
		SodSong sodSongSaved = null;
		try {
			//Update DB
			updatedSongData.setId(id);
			populateAndCleanFields(title, playlist, link, bandName, songName, updatedSongData);

			Optional<SodSong> current = sodSongRepository.findById(id);
			updatedSongData.setUser(current.get().getUser());

			boolean dataUpdated = false;
			if(!current.get().getTitle().equals(updatedSongData.getTitle())) {
				sodSongRepository.updateTitleById(updatedSongData.getTitle(), String.valueOf(id));
				dataUpdated = true;
			}
			if(!current.get().getActualBandName().equals(updatedSongData.getActualBandName())) {
				sodSongRepository.updateBandNameById(updatedSongData.getActualBandName(), String.valueOf(id));
				dataUpdated = true;
			}
			if(!current.get().getActualSongName().equals(updatedSongData.getActualSongName())) {
				sodSongRepository.updateSongNameById(updatedSongData.getActualSongName(), String.valueOf(id));
				dataUpdated = true;
			}
			if(!current.get().getYoutubeUrl().equals(updatedSongData.getYoutubeUrl())) {
				sodSongRepository.updateUrlById(updatedSongData.getYoutubeUrl(), String.valueOf(id));
				dataUpdated = true;
			}
			if(dataUpdated) {
				updatedSongData.setModifyTime(LocalDateTime.now());
				updatedSongData.setCreateTime(current.get().getCreateTime());
				sodSongRepository.updateModifyTimeById(updatedSongData.getModifyTime(), String.valueOf(id));
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
			throw e;
		}

		return esSong;
	}

	private void populateAndCleanFields(final String title, final String playlist, final String link,
			final String bandName, final String songName, SodSong updatedSongData) {
		//Replace double quotes with single quotes and commas with blanks
		updatedSongData.setTitle(title.replace("\"", "'").replace(",", ""));
		updatedSongData.setActualBandName(bandName.replace("\"", "'").replace(",", ""));
		updatedSongData.setActualSongName(songName.replace("\"", "'").replace(",", ""));

		updatedSongData.setYoutubePlaylist(playlist);
		updatedSongData.setYoutubeUrl(link);
	}
	
	public ByteArrayInputStream loadAllSongs() {
		List<Song> songs = new ArrayList<Song>();
		for(SodSong sodSong: sodSongRepository.getAllSodSongs()) {
			songs.add(new Song(sodSong));
		}
		return CSVHelper.songsToCSV(songs);
	}
	
	public int getAllSodSongsWithIssuesCount() {
		return sodSongRepository.getAllSodSongsWithIssuesCount();
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

	public List<BandStats> getBandStats(int count) {
		List<BandStats> bandStatsList = new ArrayList<BandStats>();
		Map<String, BandStats> masterBandSongs = new HashMap<String, BandStats>();

		for (SodSong sodSong : sodSongRepository.getAllSodSongs()) {
			String bandStripped = sodSong.getActualBandName().replace("\"", "").replace("The ", "").replace("the ", "")
					.replace(" & ", " and ").replace(".", "").replace("'", "").replace("-", "").replaceAll("\\s+", "")
					.toLowerCase();

			BandStats bandMetrics = masterBandSongs.get(bandStripped.toLowerCase());
			if (bandMetrics == null) {
				String bandName = sodSong.getActualBandName().replace("\"", "").strip();
				bandMetrics = new BandStats(bandName, 1);
				masterBandSongs.put(bandStripped, bandMetrics);
			} else {
				int songCount = bandMetrics.getSongCount();
				songCount++;
				bandMetrics.setSongCount(songCount);
			}
		}

		Map<String, BandStats> sortedMap = masterBandSongs.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		int bandsAdded = 0;
		for (Map.Entry<String, BandStats> entry : sortedMap.entrySet()) {
			if(entry.getValue().getBandName() == null || entry.getValue().getBandName().isBlank()) {
				continue;
			}
			if(++bandsAdded > count) {break;}
	        BandStats bandStats = new BandStats(entry.getValue().getBandName(), entry.getValue().getSongCount());
	        bandStatsList.add(bandStats);
	    }

		return bandStatsList;
	}
}
