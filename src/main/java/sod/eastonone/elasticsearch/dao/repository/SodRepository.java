package sod.eastonone.elasticsearch.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sod.eastonone.elasticsearch.dao.entity.SodSong;

@Repository
public interface SodRepository extends JpaRepository<SodSong, Integer> {
}
