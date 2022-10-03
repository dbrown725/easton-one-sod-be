package sod.eastonone.elasticsearch.dao.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="song")
public class SodSong implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public SodSong(String id) {
			super();
			this.id = Integer.parseInt(id);	
		}

	public SodSong() {
	    	super();
	    }

	@Column(name = "youtube_title", nullable = false)
	private String youtubeTitle;
	
	@Column(name = "youtube_url", nullable = false)
	private String youtubeUrl;
	
	@Column(name = "youtube_playlist", nullable = false)
	private String youtubePlaylist;
	
	@Column(name = "actual_band_name")
	private String actualBandName;
	
	@Column(name = "actual_song_name")
	private String actualSongName;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	


	@Column(name = "create_time")
	private LocalDateTime createTime;

	@Column(name = "modify_time")
	private LocalDateTime modifyTime;

	@Override
	public String toString() {
		return "Song [id=" + id + ", youtubeTitle=" + youtubeTitle + ", youtubeUrl=" + youtubeUrl + ", youtubePlaylist="
				+ youtubePlaylist + ", actualBandName=" + actualBandName + ", actualSongName=" + actualSongName
				+ ", user=" + user + ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
	}

	@PrePersist
	protected void onCreate() {
		createTime = Instant.ofEpochMilli((new Date()).getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

		modifyTime = Instant.ofEpochMilli((new Date()).getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	private transient String formattedCreateTime;

	// Getter and setter
	public String getFormattedCreateTime() {
		return getCreateTime().toString();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYoutubeTitle() {
		return youtubeTitle;
	}

	public void setYoutubeTitle(String youtubeTitle) {
		this.youtubeTitle = youtubeTitle;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	public String getYoutubePlaylist() {
		return youtubePlaylist;
	}

	public void setYoutubePlaylist(String youtubePlaylist) {
		this.youtubePlaylist = youtubePlaylist;
	}

	public String getActualBandName() {
		return actualBandName;
	}

	public void setActualBandName(String actualBandName) {
		this.actualBandName = actualBandName;
	}

	public String getActualSongName() {
		return actualSongName;
	}

	public void setActualSongName(String actualSongName) {
		this.actualSongName = actualSongName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public void setFormattedCreateTime(String formattedCreateTime) {
		this.formattedCreateTime = formattedCreateTime;
	}

}