package sod.eastonone.music.service.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sod.eastonone.music.auth.models.User;
import sod.eastonone.music.dao.entity.SongComment;
import sod.eastonone.music.es.model.Song;
import sod.eastonone.music.service.UserService;

@Service
public class GeneralHelper {
	
	@Autowired
	private UserService userService;
	
    public void setUserSubmitterAndPrivacy(Song song, User user) {
    	List<Song> songs = new ArrayList<Song>();
    	songs.add(song);
    	setUserSubmitterAndPrivacy(songs, user);
    	return;
    }

    public void setUserSubmitterAndPrivacy(List<Song> songs, User user) {
    	for (Song song : songs) {
	    	if(song.getUserId() == user.getId()) {
	    		song.setUserIsTheSubmitter(true);
	    	}
	    	if(song.getSongComments() != null && !song.getSongComments().isEmpty()) {
	    		setUserSubmitterAndPrivacyOnComment(song.getSongComments(), user);
	    	}
    	}
    	setSongsPrivacyOn(songs);
    	return;
    }

    public void setUserSubmitterAndPrivacyOnComment(List<SongComment> comments, User user) {
    	for (SongComment comment : comments) {
	    	if(comment.getUserId() == user.getId()) {
	    		comment.setUserIsTheSubmitter(true);
	    	}
    	}
    	setCommentsPrivacyOn(comments);
    	return;
    }
    
    public void setSongsPrivacyOn(List<Song> songs) {
    	userService.getAllUsers();
    	Map<Integer, Boolean> userMap = new HashMap<Integer, Boolean>();
    	for(sod.eastonone.music.dao.entity.User user: userService.getAllUsers()) {
    		userMap.put(Integer.valueOf(user.getId()), Boolean.valueOf(user.isPrivacyOn()));
    	}
    	
    	for (Song song : songs) {
    		song.setPrivacyOn(userMap.get(Integer.valueOf(song.getUserId())));
    	}
    	return;
    }
    
    public void setCommentsPrivacyOn(List<SongComment> comments) {
    	userService.getAllUsers();
    	Map<Integer, Boolean> userMap = new HashMap<Integer, Boolean>();
    	for(sod.eastonone.music.dao.entity.User user: userService.getAllUsers()) {
    		userMap.put(Integer.valueOf(user.getId()), Boolean.valueOf(user.isPrivacyOn()));
    	}
    	
    	for (SongComment songComment : comments) {
    		songComment.setPrivacyOn(userMap.get(Integer.valueOf(songComment.getUserId())));
    	}
    	return;
    }

}
