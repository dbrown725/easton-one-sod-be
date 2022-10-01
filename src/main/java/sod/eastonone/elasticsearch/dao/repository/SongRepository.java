package sod.eastonone.elasticsearch.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sod.eastonone.elasticsearch.dao.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
}
