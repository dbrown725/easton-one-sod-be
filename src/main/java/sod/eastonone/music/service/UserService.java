package sod.eastonone.music.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.music.dao.entity.User;
import sod.eastonone.music.dao.repository.UserRepository;
import sod.eastonone.music.model.EmailPreference;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public User getUser(final int id) {
		Optional<User> user = this.userRepository.findById(id);
		return user.get();
	}

	@Transactional
	public User updateEmailPreference(final String emailPreference, final int userId) {

		User user = getUser(userId);
		user.setEmailPreference(EmailPreference.valueOf(emailPreference));

		return this.userRepository.save(user);
	}

	@Transactional
	public User updatePrivacyOn(final boolean privacyOn, final int userId) {

		User user = getUser(userId);
		user.setPrivacyOn(privacyOn);

		return this.userRepository.save(user);
	}
	
	@Transactional
	public User updateDarkModeOn(final boolean darkModeOn, final int userId) {

		User user = getUser(userId);
		user.setDarkModeOn(darkModeOn);

		return this.userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return this.userRepository.getAllUsers();
	}

	@Transactional(readOnly = true)
	public List<sod.eastonone.music.auth.models.User> getUsersForDropDown() {
		List<sod.eastonone.music.auth.models.User> users = new ArrayList<sod.eastonone.music.auth.models.User>();
		for(User user: this.userRepository.getAllUsers()) {
			sod.eastonone.music.auth.models.User modelUser = new sod.eastonone.music.auth.models.User();
			modelUser.setId(user.getId());
			modelUser.setFirstName(user.getFirstName());
			modelUser.setLastName(user.getLastName());
			modelUser.setPrivacyOn(user.isPrivacyOn());
			users.add(modelUser);
		}

		return users;
	}

}
