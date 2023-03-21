package sod.eastonone.music.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.music.dao.entity.SodSong;

@Repository
public interface SodSongRepository extends JpaRepository<SodSong, Integer> {

	@Query(value="SELECT * FROM song", nativeQuery=true)
	public List<SodSong> getAllSodSongs();

	@Query(value="SELECT * FROM song ORDER BY id DESC LIMIT ?1", nativeQuery=true)
	public List<SodSong> getMostRecentSongs(int count);

	@Transactional
	@Modifying
	@Query(value = "UPDATE song s set title =?1 where s.id = ?2", nativeQuery = true)
	void updateTitleById(String title, String id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE song s set actual_band_name =?1 where s.id = ?2", nativeQuery = true)
	void updateBandNameById(String bandName, String id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE song s set actual_song_name =?1 where s.id = ?2", nativeQuery = true)
	void updateSongNameById(String songName, String id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE song s set youtube_url =?1 where s.id = ?2", nativeQuery = true)
	void updateUrlById(String url, String id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE song s set user_id =?1 where s.id = ?2", nativeQuery = true)
	void updateUserById(int userId, String id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE song s set modify_time =?1 where s.id = ?2", nativeQuery = true)
	void updateModifyTimeById(LocalDateTime modifyTime, String id);

	String songsWithIssuesQuery = "select * from song where youtube_url = ''\n"
			+ "	   union \n"
			+ "    select * from song where title = ''\n"
			+ "    union\n"
			+ "    select * from song where actual_band_name = ''\n"
			+ "    union\n"
			+ "    select * from song where actual_song_name = ''\n"
			+ "    union\n"
			+ "    select * from song where title like '%Deleted video%'\n"
			+ "    union\n"
			+ "    select * from song where title like '%Private video%'\n"
			+ "    order by id desc LIMIT ?1";

	@Query(value=songsWithIssuesQuery, nativeQuery=true)
	public List<SodSong> getAllSodSongsWithIssues(int count);

	String songsWithIssuesCountQuery = "select count(*) from\n"
			+ "	(\n"
			+ "	select * from eastonOneSOD.song where youtube_url = ''\n"
			+ "	union \n"
			+ "    select * from eastonOneSOD.song where title = ''\n"
			+ "    union\n"
			+ "    select * from eastonOneSOD.song where actual_band_name = ''\n"
			+ "    union\n"
			+ "    select * from eastonOneSOD.song where actual_song_name = ''\n"
			+ "    union\n"
			+ "    select * from eastonOneSOD.song where title like '%Deleted video%'\n"
			+ "    union\n"
			+ "    select * from eastonOneSOD.song where title like '%Private video%'\n"
			+ "	) x";

	@Query(value=songsWithIssuesCountQuery, nativeQuery=true)
	public int getAllSodSongsWithIssuesCount();

}
