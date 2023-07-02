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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="song_comment")
public class SongComment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	public SongComment() {
	    	super();
	    }

	@Column(name = "song_id", nullable = false)
	private int songId;
	
	@Column(name = "comment", nullable = true)
	private String comment;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
	
	@Column(name = "create_time")
	private LocalDateTime createTime;

	@Column(name = "modify_time")
	private LocalDateTime modifyTime;

	@JsonIgnore
	@Transient
	private boolean userIsTheSubmitter;

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
	
	public int getUserId() {
		int userId = 0;
		if(user != null) {
			userId = user.getId();
		}
		return userId;
	}
	
	public String getUserFirstName() {
		String userFirstName = "";
		if(user != null) {
			userFirstName = user.getFirstName();
		}
		return userFirstName;
	}
	
	public String getUserLastName() {
		String userLastName = "";
		if(user != null) {
			userLastName = user.getLastName();
		}
		return userLastName;
	}

	public String getUserAvatarColor() {
		String userAvatarColor = "";
		if(user != null) {
			userAvatarColor = user.getAvatarColor();
		}
		return userAvatarColor;
	}

}