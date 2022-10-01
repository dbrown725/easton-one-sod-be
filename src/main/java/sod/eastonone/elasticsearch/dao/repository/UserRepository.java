package sod.eastonone.elasticsearch.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sod.eastonone.elasticsearch.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
