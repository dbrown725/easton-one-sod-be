package sod.eastonone.music.es.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import sod.eastonone.music.dao.entity.BullpenSong;
import sod.eastonone.music.dao.entity.SodSong;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Song {
	
	private int id;
	
	@JsonProperty("title")
    private String title;
	
	@JsonProperty("youtube_playlist")
    private String playlist;
	
	@JsonProperty("youtube_url")
    private String link;
	
	@JsonIgnore
	private Double score;
	
	@JsonIgnore
	private String titleHighlighted;
	
	@JsonIgnore
	private String bandNameHighlighted;

	@JsonIgnore
	private String songNameHighlighted;

	@JsonIgnore
	private String message;
	
	@JsonProperty("actual_band_name")
	private String bandName;

	@JsonProperty("actual_song_name")
	private String songName;

	@JsonIgnore
	private int sortOrder;
	
	@JsonProperty("user_id")
	private int userId;
	
	@JsonProperty("user_first_name")
	private String userFirstName;
	
	@JsonProperty("user_last_name")
	private String userLastName;
	
	@JsonProperty("user_avatar_color")
	private String userAvatarColor;

    @JsonProperty("@timestamp")
    public String timestamp;
	
	@JsonProperty("create_time")
	private String createTime;

	@JsonProperty("modify_time")
	private String modifyTime;
	
	public Song(BullpenSong bullpenSong) {
		id = bullpenSong.getId();
	    title = bullpenSong.getTitle();
	    link = bullpenSong.getYoutubeUrl();
	    message = bullpenSong.getMessage();
	    bandName = bullpenSong.getActualBandName();
	    songName = bullpenSong.getActualSongName();
	    sortOrder = bullpenSong.getSortOrder();
	    userId = bullpenSong.getUser().getId();
	    createTime = localDateTimeToString(bullpenSong.getCreateTime());
	    modifyTime = localDateTimeToString(bullpenSong.getModifyTime());
	}
	
	public Song(SodSong sodSong) {
		id = sodSong.getId();
	    title = sodSong.getTitle();
	    playlist = sodSong.getYoutubePlaylist();
	    link = sodSong.getYoutubeUrl();
	    bandName = sodSong.getActualBandName();
	    songName = sodSong.getActualSongName();
	    userId = sodSong.getUser().getId();
	    userFirstName = sodSong.getUser().getFirstName();
	    userLastName = sodSong.getUser().getLastName();
	    userAvatarColor = sodSong.getUser().getAvatarColor();
	    createTime = localDateTimeToString(sodSong.getCreateTime());
	    modifyTime = localDateTimeToString(sodSong.getModifyTime());
	}

	private String localDateTimeToString(LocalDateTime dateTime) {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
	}

	public Song() {
	}
	
}
