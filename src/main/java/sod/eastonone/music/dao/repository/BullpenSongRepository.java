package sod.eastonone.music.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sod.eastonone.music.dao.entity.BullpenSong;

@Repository
public interface BullpenSongRepository extends JpaRepository<BullpenSong, Integer> {
}
