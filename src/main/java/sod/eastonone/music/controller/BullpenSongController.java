package sod.eastonone.music.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sod.eastonone.music.auth.models.User;
import sod.eastonone.music.dao.entity.BullpenSong;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.service.BullpenSongService;

@Controller
public class BullpenSongController {

	@Autowired
	private BullpenSongService bullpenSongService;

	private static final Logger logger = LoggerFactory.getLogger(BullpenSongController.class);

	@QueryMapping
	public Song bullpenSongById(@Argument int id, @AuthenticationPrincipal User user) {
		logger.debug("Entering bullpenSongById");
		logger.info("bullpenSongById: User " + user.getId() + "retrieving bullpen song " + id);
		Optional<BullpenSong> bullpenSongOpt = null;
		try {
			bullpenSongOpt = bullpenSongService.getBullpenSong(id);
		} catch (Exception e) {
			logger.error("bullpenSongById: error caught retrieving " + id + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting bullpenSongById");
		return new Song(bullpenSongOpt.get());
	}

	@QueryMapping
	public List<Song> getAllBullpenSongs(@Argument int count, @AuthenticationPrincipal User user) {
		logger.debug("Entering getAllBullpenSongs");

		List<Song> songs = new ArrayList<Song>();

		logger.info("getAllBullpenSongs: Getting all bullpen songs for user " + user.getId() + " count: " + count);
		List<BullpenSong> bullpenSongs = new ArrayList<BullpenSong>();
		try {
			bullpenSongs = bullpenSongService.getAllBullpenSongs(user.getId(), count);
		} catch (Exception e) {
			logger.error("getAllBullpenSongs: error caught retrieving for user: " + user.getId() + " count: " + count, e);
			throw e;
		}

		songs = new ArrayList<Song>();

		for (BullpenSong bpSong : bullpenSongs) {
			Song sng = new Song(bpSong);
			sng.setUserIsTheSubmitter(true);
			songs.add(sng);
		}
		logger.debug("Exiting getAllBullpenSongs");
		return songs;
	}

	@MutationMapping
	public Song addBullpenSong(@Argument String title, @Argument String link, @Argument String message,
			@Argument String bandName, @Argument String songName, @AuthenticationPrincipal User user) {

		logger.debug("Entering addBullpenSong");
		logger.info("addBullpenSong: Adding bullpen song with title " + title + " for user " + user.getId());
		BullpenSong bullpenSong = null;
		try {
			bullpenSong = bullpenSongService.createBullpenSong(title, link, bandName, songName, message,
					user.getId());
		} catch (Exception e) {
			logger.error("addBullpenSong: error caught adding song with title " + title + " for user " + user.getId(), e);
			throw e;
		}
		logger.info("addBullpenSong: bullpen song with title " + title + " and id " + bullpenSong.getId()
				+ " created for user " + user.getId());

		Song newSong = new Song(bullpenSong);
		logger.debug("Exiting addBullpenSong");
		return newSong;
	}

	@MutationMapping
	public Song updateBullpenSong(@Argument int id, @Argument String title, @Argument String link,
			@Argument String message, @Argument String bandName, @Argument String songName, @Argument int sortOrder,
			@AuthenticationPrincipal User user) {

		logger.debug("Entering updateBullpenSong");
		logger.info("updateBullpenSong: Updating bullpen song with id " + id + " for user " + user.getId());
		BullpenSong bullpenSong;
		try {
			bullpenSong = bullpenSongService.updateBullpenSong(id, title, link, bandName, songName, message,
					sortOrder);
		} catch (Exception e) {
			logger.error("updateBullpenSong: error caught updating song with id " + id + " for user " + user.getId(), e);
			throw e;
		}
		Song updated = new Song(bullpenSong);
		logger.debug("Exiting updateBullpenSong");
		return updated;
	}

	@MutationMapping
	public boolean deleteBullpenSong(@Argument int id, @AuthenticationPrincipal User user) {
		logger.debug("Entering deleteBullpenSong");
		logger.info("deleteBullpenSong: Deleting bullpen song with id " + id + " for user " + user.getId());
		boolean deleteResponse = false;
		try {
			deleteResponse = bullpenSongService.deleteBullpenSong(id);
		} catch (Exception e) {
			logger.error("deleteBullpenSong: error caught deleting song with id " + id + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting deleteBullpenSong, deleteResponse " + deleteResponse);
		return deleteResponse;
	}

}