package sod.eastonone.music.service;

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

}
