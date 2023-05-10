package sod.eastonone.music.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.music.dao.entity.BullpenSong;
import sod.eastonone.music.dao.entity.User;
import sod.eastonone.music.dao.repository.BullpenSongRepository;
import sod.eastonone.music.dao.repository.UserRepository;

@Service
public class BullpenSongService {

	@Autowired
	private BullpenSongRepository bullpenSongRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public BullpenSong createBullpenSong(final String title, final String youtubeUrl,
			final String actualBandName, final String actualSongName, final String message,
			final int userId) {
		BullpenSong bullpenSong = new BullpenSong();
		bullpenSong.setTitle(title);
		bullpenSong.setYoutubeUrl(youtubeUrl);
		bullpenSong.setActualBandName(actualBandName);
		bullpenSong.setActualSongName(actualSongName);
		bullpenSong.setMessage(message);
		
		int currentSongCount = bullpenSongRepository.getSongCountByUserId(userId);
		
		if(currentSongCount == 0) {
			//First song in the bullpen
			bullpenSong.setSortOrder(100000);
		}else {
			int maxSortOrder = bullpenSongRepository.getMaxSortOrderByUserId(userId);
			bullpenSong.setSortOrder(maxSortOrder + 10000);
		}

		Optional<User> user = this.userRepository.findById(userId);
		bullpenSong.setUser(user.get());
		return this.bullpenSongRepository.save(bullpenSong);
	}
	
	@Transactional
	public BullpenSong updateBullpenSong(final int id, final String title, final String youtubeUrl,
			final String actualBandName, final String actualSongName, final String message, final int sortOrder) {
		
		Optional<BullpenSong> bullpenSongOpt = bullpenSongRepository.findById(id);
		
		BullpenSong bullpenSong = bullpenSongOpt.get();
		if (title != null)
			bullpenSong.setTitle(title);
		if (youtubeUrl != null)
			bullpenSong.setYoutubeUrl(youtubeUrl);
		if (actualBandName != null)
			bullpenSong.setActualBandName(actualBandName);
		if (actualSongName != null)
			bullpenSong.setActualSongName(actualSongName);
		if (message != null)	
			bullpenSong.setMessage(message);
		if (sortOrder != 0)
			bullpenSong.setSortOrder(sortOrder);

		return this.bullpenSongRepository.save(bullpenSong);
	}
	
	@Transactional
	public boolean deleteBullpenSong(final int id) {
		Optional<BullpenSong> bullpenSongOpt = bullpenSongRepository.findById(id);
		
		BullpenSong bullpenSong = bullpenSongOpt.get();
		this.bullpenSongRepository.delete(bullpenSong);
		return true;
	}


	@Transactional(readOnly = true)
	public List<BullpenSong> getAllBullpenSongs(final int userId, final int count) {
		List<BullpenSong> bullpenSongs = bullpenSongRepository.getSongsByUserIdLimitCount(userId, count).stream().limit(count)
				.sorted((a, b) -> Integer.compare(b.getSortOrder(), a.getSortOrder())).collect(Collectors.toList());
		return bullpenSongs;
	}

	@Transactional(readOnly = true)
	public Optional<BullpenSong> getBullpenSong(final int id) {
		return this.bullpenSongRepository.findById(id);
	}
}
