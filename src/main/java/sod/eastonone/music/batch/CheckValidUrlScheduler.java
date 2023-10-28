package sod.eastonone.music.batch;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import sod.eastonone.music.dao.entity.SodSong;
import sod.eastonone.music.dao.repository.SodSongRepository;
import sod.eastonone.music.service.SodSongService;
import sod.eastonone.music.utils.CommonUtils;

@Component
public class CheckValidUrlScheduler {

	@Autowired
	private SodSongRepository sodSongRepository;
	
	@Autowired
	private SodSongService sodSongService;
    
    private static final Logger logger = LoggerFactory.getLogger(CheckValidUrlScheduler.class);

	@Scheduled(cron = "0 0 2 * * SAT")
	public void cronJobSch() {		
		logger.debug("CheckValidUrlScheduler job starting");
		List<SodSong> sodSongs = sodSongRepository.getAllSodSongs();
		
		CommonUtils utils = new CommonUtils();
		
		int curentSongId  = 0;
		
		for(SodSong song: sodSongs) {
			try {
				Thread.sleep(2000);

				curentSongId = song.getId();
				//logger.debug("On song: " + curentSongId);
				
				String songImageUrl = utils.getThumbnailUrl(song.getYoutubeUrl());
				
				var uri = URI.create(songImageUrl);
				var client = HttpClient.newHttpClient();
				var request = HttpRequest
				        .newBuilder()
				        .uri(uri)
				        .header("accept", "image/jpeg")
				        .GET()
				        .build();
				var response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				if(song.isYoutubeUrlValid() && response.statusCode() == 404) {
					sodSongService.updateUrlValid(song, false);
				} else if(!song.isYoutubeUrlValid() && response.statusCode() == 200) {
					sodSongService.updateUrlValid(song, true);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error("CheckValidUrlScheduler job failed with InterruptedException on song id: " + curentSongId, e);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("CheckValidUrlScheduler job failed with IOException on song id: " + curentSongId, e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("CheckValidUrlScheduler job failed with Exception on song id: " + curentSongId, e);
			}
		}
		logger.debug("CheckValidUrlScheduler job ending");
	}
}