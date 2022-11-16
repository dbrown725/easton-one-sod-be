package sod.eastonone.music.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sod.eastonone.music.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
