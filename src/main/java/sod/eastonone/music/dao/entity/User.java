package sod.eastonone.music.dao.entity;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //Needed for deserializing other classes that have User as a FK field
    public User(String strId) {
    	id = Integer.parseInt(strId);
    }
    
    public User() {
    	super();
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "screen_name", nullable = false)
    private String screenName;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "avatar_color", nullable = false)
    private String avatarColor;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

	@PrePersist
    protected void onCreate() {
    	createTime = Instant.ofEpochMilli( (new Date()).getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
    	
    	modifyTime = Instant.ofEpochMilli( (new Date()).getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
    }
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    private transient  String formattedCreateTime;

    // Getter and setter
    public String getFormattedCreateTime() {
        return getCreateTime().toString();
    }

	public void setFormattedCreateTime(String formattedCreateTime) {
		this.formattedCreateTime = formattedCreateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAvatarColor() {
		return avatarColor;
	}

	public void setAvatarColor(String avatarColor) {
		this.avatarColor = avatarColor;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", screenName=" + screenName
				+ ", email=" + email + ", password=" + password + ", avatarColor=" + avatarColor + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + "]";
	}

}
