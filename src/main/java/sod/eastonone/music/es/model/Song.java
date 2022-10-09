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
	
	@JsonProperty("YouTube Title")
    private String title;
	
	@JsonProperty("YouTube Playlist")
    private String playlist;
	
	@JsonProperty("YouTube Link")
    private String link;
	
	@JsonIgnore
	private Double score;
	
	@JsonIgnore
	private String titleHighlighted;
	
	@JsonIgnore
	private String message;
	
	private String bandName;
	
	private String songName;
	
	@JsonIgnore
	private int sortOrder;
	
	private int userId;
	
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
				+ ", titleHighlighted=" + titleHighlighted + ", message=" + message + ", bandName=" + bandName
				+ ", songName=" + songName + ", sortOrder=" + sortOrder + ", userId=" + userId + "]";
	}
	
}
