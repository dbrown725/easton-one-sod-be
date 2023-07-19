package sod.eastonone.music.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sod.eastonone.music.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value="SELECT * FROM user where auth_provider_uid = ?1 LIMIT 1", nativeQuery=true)
	public User getUserByUid(String uid);

	@Query(value="SELECT * FROM user where email = ?1 LIMIT 1", nativeQuery=true)
	public User getUserByEmailAddress(String emailAddress);
}
