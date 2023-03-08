package sod.eastonone.music.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import sod.eastonone.music.auth.models.User;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.service.SodSongService;

@Controller
public class SodSongController {

	@Autowired
	private SodSongService sodSongService;

	private static final Logger logger = LoggerFactory.getLogger(SodSongController.class);

	@QueryMapping
	public List<Song> songBySearchText(@Argument String searchText, @AuthenticationPrincipal User user)
			throws IOException {
		logger.debug("Entering songBySearchText for user " + user.getId());
		Song song = new Song();
		song.setTitle(searchText);

		List<Song> songs = new ArrayList<Song>();
		try {
			songs = sodSongService.songsBySearchText(searchText);
		} catch (Exception e) {
			logger.error("songBySearchText: error caught while searching " + searchText + " for user " + user.getId(),
					e);
			throw e;
		}
		logger.debug("Exiting songBySearchText for user " + user.getId());
		return songs;
	}

	@QueryMapping
	public List<Song> getMostRecentSongs(@Argument int count, @AuthenticationPrincipal User user) throws IOException {
		logger.debug("Entering getMostRecentSongs for user " + user.getId());
		List<Song> songs = new ArrayList<Song>();
		try {
			songs = sodSongService.getMostRecentSongs(count);
		} catch (Exception e) {
			logger.error("getMostRecentSongs: error caught with count " + count + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getMostRecentSongs for user " + user.getId());
		return songs;
	}

	@QueryMapping
	public int getSongsWithIssuesCount(@AuthenticationPrincipal User user) throws IOException {
		logger.debug("Entering getSongsWithIssuesCount for user " + user.getId());
		int count = 0;
		try {
			count = sodSongService.getAllSodSongsWithIssuesCount();
		} catch (Exception e) {
			logger.error("getSongsWithIssuesCount: error caught for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getSongsWithIssuesCount for user " + user.getId());
		return count;
	}

	@QueryMapping
	public List<Song> getSongsWithIssues(@Argument int count, @AuthenticationPrincipal User user) throws IOException {
		logger.debug("Entering getSongsWithIssues for user " + user.getId());
		List<Song> songs = new ArrayList<Song>();
		try {
			songs = sodSongService.getAllSodSongsWithIssues(count);
		} catch (Exception e) {
			logger.error("getSongsWithIssues: error caught with count " + count + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getSongsWithIssues for user " + user.getId());
		return songs;
	}

	@MutationMapping
	public Song insertSodSong(@Argument String title, @Argument String playlist, @Argument String link,
			@Argument String bandName, @Argument String songName, @Argument String message,
			@AuthenticationPrincipal User user) throws Exception {
		logger.debug("Entering insertSodSong for user " + user.getId());
		Song insertedSong;
		try {
			insertedSong = sodSongService.createSodSong(title, playlist, link, bandName, songName, message,
					user.getId());
		} catch (Exception e) {
			logger.error("insertSodSong: error caught for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting insertSodSong for user " + user.getId());
		return insertedSong;
	}

	@MutationMapping
	public Song updateSodSong(@Argument int id, @Argument String title, @Argument String playlist,
			@Argument String link, @Argument String bandName, @Argument String songName,
			@AuthenticationPrincipal User user) throws IOException {
		logger.debug("Entering updateSodSong for user " + user.getId());
		Song updatedSong;
		try {
			updatedSong = sodSongService.updateSodSong(id, title, playlist, link, bandName, songName, user.getId());
		} catch (Exception e) {
			logger.error("updateSodSong: error caught for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting updateSodSong for user " + user.getId());
		return updatedSong;
	}

}