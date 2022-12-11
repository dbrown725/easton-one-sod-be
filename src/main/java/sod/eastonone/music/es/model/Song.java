package sod.eastonone.music.es.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import sod.eastonone.music.dao.entity.BullpenSong;
import sod.eastonone.music.dao.entity.SodSong;

@Data
//@NoArgsConstructor //not recognized by Eclipse, had to add a default constructor. Maven build works fine, just Eclipse has the problem.
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Song {
	
	private int id;
	
	@JsonProperty("youtube_title")
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
	
	@JsonIgnore
	private LocalDateTime createTime;

	@JsonIgnore
	private LocalDateTime modifyTime;
	
	public Song(BullpenSong bullpenSong) {
		id = bullpenSong.getId();
	    title = bullpenSong.getYoutubeTitle();
	    link = bullpenSong.getYoutubeUrl();
	    message = bullpenSong.getMessage();
	    bandName = bullpenSong.getActualBandName();
	    songName = bullpenSong.getActualSongName();
	    sortOrder = bullpenSong.getSortOrder();
	    userId = bullpenSong.getUser().getId();
	    createTime = bullpenSong.getCreateTime();
	    modifyTime = bullpenSong.getModifyTime();
	}
	
	public Song(SodSong sodSong) {
		id = sodSong.getId();
	    title = sodSong.getYoutubeTitle();
	    playlist = sodSong.getYoutubePlaylist();
	    link = sodSong.getYoutubeUrl();
	    bandName = sodSong.getActualBandName();
	    songName = sodSong.getActualSongName();
	    userId = sodSong.getUser().getId();
	    userFirstName = sodSong.getUser().getFirstName();
	    userLastName = sodSong.getUser().getLastName();
	    userAvatarColor = sodSong.getUser().getAvatarColor();
	    createTime = sodSong.getCreateTime();
	    modifyTime = sodSong.getModifyTime();
	}

	public Song() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlaylist() {
		return playlist;
	}
	public void setPlaylist(String playlist) {
		this.playlist = playlist;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getTitleHighlighted() {
		return titleHighlighted;
	}
	public void setTitleHighlighted(String titleHighlighted) {
		this.titleHighlighted = titleHighlighted;
	}

	public String getBandNameHighlighted() {
		return bandNameHighlighted;
	}

	public void setBandNameHighlighted(String bandNameHighlighted) {
		this.bandNameHighlighted = bandNameHighlighted;
	}

	public String getSongNameHighlighted() {
		return songNameHighlighted;
	}

	public void setSongNameHighlighted(String songNameHighlighted) {
		this.songNameHighlighted = songNameHighlighted;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBandName() {
		return bandName;
	}
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserAvatarColor() {
		return userAvatarColor;
	}

	public void setUserAvatarColor(String userAvatarColor) {
		this.userAvatarColor = userAvatarColor;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public LocalDateTime getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", playlist=" + playlist + ", link=" + link + ", score=" + score
				+ ", titleHighlighted=" + titleHighlighted + ", bandNameHighlighted=" + bandNameHighlighted
				+ ", songNameHighlighted=" + songNameHighlighted + ", message=" + message + ", bandName=" + bandName
				+ ", songName=" + songName + ", sortOrder=" + sortOrder + ", userId=" + userId + ", userFirstName="
				+ userFirstName + ", userLastName=" + userLastName + ", userAvatarColor=" + userAvatarColor
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
	}
	
}
