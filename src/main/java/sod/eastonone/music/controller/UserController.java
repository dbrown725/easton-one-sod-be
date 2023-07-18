package sod.eastonone.music.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import sod.eastonone.music.auth.models.User;
import sod.eastonone.music.dao.entity.SongComment;
import sod.eastonone.music.service.UserService;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

    @QueryMapping
    public User getUserInfo(@AuthenticationPrincipal User user) {
    	logger.debug("Entering/Exiting getUserInfo for user " + user.getId());
        return user;
    }

    @MutationMapping
    public Boolean setUserRole(@Argument String role, @Argument int userId, @AuthenticationPrincipal User adminUser) throws Exception {
    	logger.debug("Entering setUserRole: Admin user " + adminUser.getId() + " setting role for user " + userId);
    	if(!isAdmin(adminUser)) {
    		logger.debug("Exception in setUserRole: User " + adminUser.getId() + " does not have Admin access");
    		throw new Exception("Unauthorized access");
    	}

    	sod.eastonone.music.dao.entity.User toBeModifiedUser = userService.getUser(userId);

	    Map<String, Object> roles = new HashMap<>();
	    roles.put(role, true);
	    FirebaseAuth.getInstance().setCustomUserClaims(toBeModifiedUser.getAuthProviderUid(), roles);
	    logger.debug("Exiting setUserRole: Admin user " + adminUser.getId() + " setting role for user " + userId);
        return Boolean.TRUE;
    }

    @QueryMapping
    public String getUserRole(@Argument int userId, @AuthenticationPrincipal User adminUser) throws Exception {
    	logger.debug("Entering getUserRole: Admin user " + adminUser.getId() + " getting role for user " + userId);

    	if(!isAdmin(adminUser)) {
    		logger.debug("Exception in getUserRole: User " + adminUser.getId() + " does not have Admin access");
    		throw new Exception("Unauthorized access");
    	}
    	sod.eastonone.music.dao.entity.User usersRoleRequested = userService.getUser(userId);

    	UserRecord user = FirebaseAuth.getInstance().getUser(usersRoleRequested.getAuthProviderUid());
    	Set<String> claimsKeys = user.getCustomClaims().keySet();
    	List<String> claimsList = new ArrayList<String>(claimsKeys);
    	String response = "NONE";
    	if(claimsList.size() > 0) {
    		response = claimsList.get(0);
    	}
	    logger.debug("Exiting getUserRole: Admin user " + adminUser.getId() + " getting role for user " + userId);
        return response;
    }

	@MutationMapping
	public User updateEmailPreference(@Argument String emailPreference,
			@AuthenticationPrincipal User user) {

		logger.debug("Entering updateEmailPreference");
		logger.info("updateEmailPreference: Updating user emailPreference with value " + emailPreference + " for user " + user.getId());

		try {
			sod.eastonone.music.dao.entity.User dbUser = userService.updateEmailPreference(emailPreference, user.getId());
			user.setEmailPreference(dbUser.getEmailPreference().name());
		} catch (Exception e) {
			logger.error("updateEmailPreference: error caught updating user with emailPreference " + emailPreference + " for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting updateEmailPreference");
		return user;
	}

    private boolean isAdmin(User user) throws FirebaseAuthException {
    	String authProviderUid = user.getUid();
    	UserRecord userRecord = FirebaseAuth.getInstance().getUser(authProviderUid);
    	return (boolean) userRecord.getCustomClaims().get("ADMIN");
    }

}