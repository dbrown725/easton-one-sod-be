package sod.eastonone.elasticsearch.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sod.eastonone.elasticsearch.dao.entity.MyPotentialSong;

@Repository
public interface MyPotentialSongRepository extends JpaRepository<MyPotentialSong, Integer> {
}
