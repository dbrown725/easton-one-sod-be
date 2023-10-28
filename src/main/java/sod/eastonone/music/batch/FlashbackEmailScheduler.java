package sod.eastonone.music.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.music.dao.entity.SodSong;
import sod.eastonone.music.dao.repository.SodSongRepository;
import sod.eastonone.music.service.EmailService;

@Component
@Transactional
public class FlashbackEmailScheduler {

	@Autowired
	private SodSongRepository sodSongRepository;
	
    @Autowired
    private EmailService emailService;
    
    private static final Logger logger = LoggerFactory.getLogger(FlashbackEmailScheduler.class);

//    @Scheduled(cron = "0 0 12 * * ?")
	public void cronJobSch() {		
		logger.debug("Flashback email job starting");
		List<SodSong> sodSongs = sodSongRepository.getAllSodSongsSevenYearsOld();
		
		try {
			emailService.sendFlashbackSongs(sodSongs);
			logger.debug("Flashback email job ended");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Flashback email job failed with", e);
		}
		
	}
}