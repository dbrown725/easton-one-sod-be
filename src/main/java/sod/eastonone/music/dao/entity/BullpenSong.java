package sod.eastonone.music.dao.entity;

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
@Table(name="bullpen_song")
public class BullpenSong implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public BullpenSong(String id) {
			super();
			this.id = Integer.parseInt(id);	
		}

	public BullpenSong() {
	    	super();
	    }

	@Column(name = "youtube_title", nullable = false)
	private String youtubeTitle;
	
	@Column(name = "youtube_url", nullable = false)
	private String youtubeUrl;
	
	@Column(name = "actual_band_name")
	private String actualBandName;
	
	@Column(name = "actual_song_name")
	private String actualSongName;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "sort_order")
	private int sortOrder;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

	@Column(name = "create_time")
	private LocalDateTime createTime;

	@Column(name = "modify_time")
	private LocalDateTime modifyTime;


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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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

	@Override
	public String toString() {
		return "BullpenSong [id=" + id + ", youtubeTitle=" + youtubeTitle + ", youtubeUrl=" + youtubeUrl
				+ ", actualBandName=" + actualBandName + ", actualSongName=" + actualSongName + ", message=" + message
				+ ", sortOrder=" + sortOrder + ", user=" + user + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + "]";
	}

}