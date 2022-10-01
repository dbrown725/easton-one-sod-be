package sod.eastonone.elasticsearch.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.elasticsearch.dao.entity.MyPotentialSong;
import sod.eastonone.elasticsearch.dao.repository.MyPotentialSongRepository;

@Service
public class MyPotentialSongService {

    private final MyPotentialSongRepository myPotentialSongsRepository ;

    public MyPotentialSongService(final MyPotentialSongRepository myPotentialSongsRepository) {
        this.myPotentialSongsRepository = myPotentialSongsRepository ;
    }
    
	@Column(name = "band_or_artist")
	private String bandOrArtist;
	
	@Column(name = "song")
	private String song;
	
	@Column(name = "youtube_title", nullable = false)
	private String youtubeTitle;
	
	@Column(name = "url", nullable = false)
	private String url;

    @Transactional
    public MyPotentialSong createMyPotentialSong(final String bandOrArtist, final String song, final String youtubeTitle, final String url) {
        final MyPotentialSong myPotentialSongs = new MyPotentialSong();
        myPotentialSongs.setBandOrArtist(bandOrArtist);
        myPotentialSongs.setSong(song);
        myPotentialSongs.setYoutubeTitle(youtubeTitle);
        myPotentialSongs.setUrl(url);
        return this.myPotentialSongsRepository.save(myPotentialSongs);
    }

    @Transactional(readOnly = true)
    public List<MyPotentialSong> getAllMyPotentialSongs(final int count) {
    	List<MyPotentialSong> myPotentialSongss = this.myPotentialSongsRepository.findAll().stream().limit(count).collect(Collectors.toList());
    	return myPotentialSongss;
    }

    @Transactional(readOnly = true)
    public Optional<MyPotentialSong> getMyPotentialSong(final int id) {
    	System.out.println("************************************** in MyPotentialSongsService getMyPotentialSongs");
        return this.myPotentialSongsRepository.findById(id);
    }
}

