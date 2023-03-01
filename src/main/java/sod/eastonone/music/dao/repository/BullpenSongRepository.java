package sod.eastonone.music.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sod.eastonone.music.dao.entity.BullpenSong;

@Repository
public interface BullpenSongRepository extends JpaRepository<BullpenSong, Integer> {
	
	@Query(value="SELECT count(*) FROM bullpen_song WHERE user_id = ?1", nativeQuery=true)
	public int getSongCountByUserId(int userId);
	
	@Query(value="SELECT MAX(sort_order) FROM bullpen_song WHERE user_id = ?1", nativeQuery=true)
	public int getMaxSortOrderByUserId(int userId);

	@Query(value="SELECT * FROM bullpen_song WHERE user_id = ?1 limit ?2" , nativeQuery=true)
	public List<BullpenSong> getSongsByUserIdLimitCount(int userId, int count);
}
