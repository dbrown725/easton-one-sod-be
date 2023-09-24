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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import sod.eastonone.music.auth.models.User;
import sod.eastonone.music.dao.entity.SongComment;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.model.BandStats;
import sod.eastonone.music.service.SodSongService;
import sod.eastonone.music.service.helpers.GeneralHelper;

@Controller
public class SodSongController {

	@Autowired
	private SodSongService sodSongService;

	@Autowired
	GeneralHelper generalHelper;

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
			generalHelper.setUserSubmitterAndPrivacy(songs, user);
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
			generalHelper.setUserSubmitterAndPrivacy(songs, user);
		} catch (Exception e) {
			logger.error("getMostRecentSongs: error caught with count " + count + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getMostRecentSongs for user " + user.getId());
		return songs;
	}

	@QueryMapping
	public Song getSongById(@Argument int songId, @AuthenticationPrincipal User user) throws IOException {
		logger.debug("Entering getSongById for user " + user.getId());
		Song song = null;
		try {
			song = sodSongService.getSongById(songId);
			generalHelper.setUserSubmitterAndPrivacy(song, user);
		} catch (Exception e) {
			logger.error("getSongById: error caught for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getSongById for user " + user.getId());
		return song;
	}

	@QueryMapping
	public int getSongsWithIssuesCount(@AuthenticationPrincipal User user) throws Exception {
		logger.debug("Entering getSongsWithIssuesCount for user " + user.getId() + ". isAdmin(user): " + isAdmin(user));
		int count = 0;
		try {
			count = sodSongService.getAllSodSongsWithIssuesCount(user.getId(), isAdmin(user));
		} catch (Exception e) {
			logger.error("getSongsWithIssuesCount: error caught for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getSongsWithIssuesCount for user " + user.getId());
		return count;
	}

	@QueryMapping
	public List<Song> getSongsWithIssues(@Argument int count, @AuthenticationPrincipal User user) throws Exception {
		logger.debug("Entering getSongsWithIssues for user " + user.getId() + ". isAdmin(user): " + isAdmin(user));
		List<Song> songs = new ArrayList<Song>();
		try {
			songs = sodSongService.getAllSodSongsWithIssues(count, user.getId(), isAdmin(user));
			generalHelper.setUserSubmitterAndPrivacy(songs, user);
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
	public Song adminInsertSodSong(@Argument String title, @Argument String playlist, @Argument String link,
			@Argument String bandName, @Argument String songName, @Argument String message, @Argument int userId,
			@AuthenticationPrincipal User adminUser) throws Exception {

		logger.debug("Entering adminInsertSodSong: Admin user " + adminUser.getId() + " inserting for user " + userId);
    	if(!isAdmin(adminUser)) {
    		logger.debug("Exception in adminInsertSodSong: User " + adminUser.getId() + " does not have Admin access");
    		throw new Exception("Unauthorized access");
    	}

		Song insertedSong;
		try {
			insertedSong = sodSongService.createSodSong(title, playlist, link, bandName, songName, message,
					userId);
		} catch (Exception e) {
			logger.error("adminInsertSodSong: error caught while Admin user " + adminUser.getId() + "  inserting for user " + userId, e);
			throw e;
		}
		logger.debug("Exiting adminInsertSodSong: Admin user " + adminUser.getId() + " inserting for user " + userId);
		return insertedSong;
	}

	@MutationMapping
	public Song updateSodSong(@Argument int id, @Argument String title, @Argument String playlist,
			@Argument String link, @Argument String bandName, @Argument String songName,
			@AuthenticationPrincipal User user) throws IOException {
		logger.debug("Entering updateSodSong for user " + user.getId());
		Song updatedSong;
		try {
			updatedSong = sodSongService.updateSodSong(id, title, playlist, link, bandName, songName);
		} catch (Exception e) {
			logger.error("updateSodSong: error caught for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting updateSodSong for user " + user.getId());
		return updatedSong;
	}

	@QueryMapping
	public List<BandStats> getBandStats(@Argument int count, @Argument int userId, @AuthenticationPrincipal User user) throws IOException {
		logger.debug("Entering getBandStats for user " + user.getId());
		List<BandStats> bandStatsList = new ArrayList<BandStats>();
		try {
			bandStatsList = sodSongService.getBandStats(count, userId);
		} catch (Exception e) {
			logger.error("getBandStats: error caught with count " + count + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getBandStats for user " + user.getId());
		return bandStatsList;
	}

	@MutationMapping
	public SongComment insertSongComment(@Argument int songId, @Argument String comment,
			@AuthenticationPrincipal User user) throws Exception {
		logger.debug("Entering insertSongComment for user " + user.getId());
		SongComment insertedSongComment;
		try {
			insertedSongComment = sodSongService.createSodSongComment(songId, comment, user.getId(), true);
		} catch (Exception e) {
			logger.error("insertSongComment: error caught for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting insertSongComment for user " + user.getId());
		return insertedSongComment;
	}

	@MutationMapping
	public SongComment updateSongComment(@Argument int id, @Argument String comment,
			@AuthenticationPrincipal User user) {

		logger.debug("Entering updateSongComment");
		logger.info("updateSongComment: Updating song comment with id " + id + " for user " + user.getId());
		SongComment songComment;
		try {
			songComment = sodSongService.updateSodSongComment(id, comment);
		} catch (Exception e) {
			logger.error("updateSongComment: error caught updating song comment with id " + id + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting updateSongComment");
		return songComment;
	}

	@MutationMapping
	public boolean deleteSongComment(@Argument int id, @AuthenticationPrincipal User user) {
		logger.debug("Entering deleteSongComment");
		logger.info("deleteSongComment: Deleting song comment with id " + id + " for user " + user.getId());
		boolean deleteResponse = false;
		try {
			deleteResponse = sodSongService.deleteSodSongComment(id);
		} catch (Exception e) {
			logger.error("deleteSongComment: error caught deleting song comment with id " + id + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting deleteSongComment, deleteResponse " + deleteResponse);
		return deleteResponse;
	}

    private boolean isAdmin(User user) throws FirebaseAuthException {
    	String authProviderUid = user.getUid();
    	UserRecord userRecord = FirebaseAuth.getInstance().getUser(authProviderUid);
    	boolean isAdmin = false;
    	if(userRecord.getCustomClaims() != null  && userRecord.getCustomClaims().get("ADMIN") != null) {
    		isAdmin = (boolean)userRecord.getCustomClaims().get("ADMIN");
    	}
    	return isAdmin;
    }

}