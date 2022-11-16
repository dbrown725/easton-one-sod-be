package sod.eastonone.music.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sod.eastonone.music.dao.entity.SodSong;

@Repository
public interface SodSongRepository extends JpaRepository<SodSong, Integer> {
	
	@Query(value="SELECT * FROM song", nativeQuery=true)
	public List<SodSong> getAllSodSongs();
	
	@Query(value="SELECT * FROM song ORDER BY id DESC LIMIT ?1", nativeQuery=true)
	public List<SodSong> getMostRecentSongs(int count);

}
