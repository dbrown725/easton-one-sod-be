package sod.eastonone.music.auth.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4408418647685225829L;
	private int id;
	private String uid;
	private String name;
	private String email;
	private String emailPreference;
	private boolean privacyOn;
	private boolean darkModeOn;
	private boolean isEmailVerified;
	private String issuer;
	private String picture;
    private String firstName;
    private String lastName;
    private String screenName;
    private String avatarColor;

}
