package sod.eastonone.music.dao.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sod.eastonone.music.model.EmailPreference;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //Needed for deserializing other classes that have User as a FK field
    public User(String strId) {
    	id = Integer.parseInt(strId);
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "auth_provider_uid", nullable = false)
    private String authProviderUid;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "screen_name", nullable = false)
    private String screenName;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name="email_preference", columnDefinition = "ENUM('ALL', 'NEW_SONG_ONLY', 'NONE')")
    private EmailPreference emailPreference;

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

    private transient  String formattedCreateTime;

    // Getter and setter
    public String getFormattedCreateTime() {
        return getCreateTime().toString();
    }
}
