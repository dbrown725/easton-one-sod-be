package sod.eastonone.elasticsearch.dao.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="myPotentialSongs")
public class MyPotentialSong implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public MyPotentialSong(String id) {
			super();
			this.id = Integer.parseInt(id);	
		}

	public MyPotentialSong() {
	    	super();
	    }

	@Column(name = "band_or_artist")
	private String bandOrArtist;
	
	@Column(name = "song")
	private String song;
	
	@Column(name = "youtube_title", nullable = false)
	private String youtubeTitle;
	
	@Column(name = "url", nullable = false)
	private String url;

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

	public String getBandOrArtist() {
		return bandOrArtist;
	}

	public void setBandOrArtist(String bandOrArtist) {
		this.bandOrArtist = bandOrArtist;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String getYoutubeTitle() {
		return youtubeTitle;
	}

	public void setYoutubeTitle(String youtubeTitle) {
		this.youtubeTitle = youtubeTitle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		return "MyPotentialSongs [id=" + id + ", bandOrArtist=" + bandOrArtist + ", song=" + song + ", youtubeTitle="
				+ youtubeTitle + ", url=" + url + ", createTime=" + createTime + ", modifyTime=" + modifyTime + "]";
	}

}