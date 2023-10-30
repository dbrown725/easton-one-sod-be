package sod.eastonone.music.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sod.eastonone.music.dao.entity.SodSong;

@Repository
public interface SodSongRepository extends JpaRepository<SodSong, Integer> {

	@Query(value="SELECT * FROM song", nativeQuery=true)
	public List<SodSong> getAllSodSongs();

	@Query(value="SELECT * FROM song s where s.id = ?1", nativeQuery=true)
	public SodSong getSongById(int songId);

	@Query(value = "SELECT * FROM song s where s.user_id = ?1", nativeQuery = true)
	public List<SodSong> getAllSodSongsByUserId(int userId);

	@Query(value="SELECT * FROM song ORDER BY id DESC LIMIT ?1", nativeQuery=true)
	public List<SodSong> getMostRecentSongs(int count);

	@Query(value="SELECT * FROM song where song.id IN (:songIds) ORDER BY song.id", nativeQuery=true)
	public List<SodSong> getAllSodSongsWithIDsIn(@Param("songIds") int[] songIds);

	@Query(value="SELECT * FROM song WHERE SUBSTRING(create_time, 1, 10) = SUBSTRING(DATE_SUB(NOW(),INTERVAL 7 YEAR), 1, 10) ORDER BY song.id", nativeQuery=true)
	public List<SodSong> getAllSodSongsSevenYearsOld();

	@Query(value="select * from song where youtube_url_valid = false", nativeQuery=true)
	public List<SodSong> getAllSodSongsWithInvalidUrls();

	@Query(value="select * from song where youtube_url_valid = false and user_id = ?1", nativeQuery=true)
	public List<SodSong> getAllSodSongsWithInvalidUrlsByUserId(int userId);

	@Query(value="select count(*) from song where youtube_url_valid = false", nativeQuery=true)
	public int getAllSodSongsWithInvalidUrlsCount();

	@Query(value="select count(*) from song where youtube_url_valid = false and user_id = ?1", nativeQuery=true)
	public int getAllSodSongsWithInvalidUrlsByUserIdCount(int userId);

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
	@Query(value = "UPDATE song s set youtube_url_valid =?1 where s.id = ?2", nativeQuery = true)
	void updateUrlValidById(boolean urlValid, String id);

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

	String songsWithIssuesByUserQuery = "select * from song where youtube_url = '' and user_id = ?2\n"
			+ "	   union \n"
			+ "    select * from song where title = '' and user_id = ?2\n"
			+ "    union\n"
			+ "    select * from song where actual_band_name = '' and user_id = ?2\n"
			+ "    union\n"
			+ "    select * from song where actual_song_name = '' and user_id = ?2\n"
			+ "    union\n"
			+ "    select * from song where title like '%Deleted video%' and user_id = ?2\n"
			+ "    union\n"
			+ "    select * from song where title like '%Private video%' and user_id = ?2\n"
			+ "    order by id desc LIMIT ?1";

	@Query(value=songsWithIssuesByUserQuery, nativeQuery=true)
	public List<SodSong> getAllSodSongsWithIssuesByUser(int count, int userId);

	String songsWithIssuesCountQuery = "select count(*) from\n"
			+ "	(\n"
			+ "	select * from song where youtube_url = ''\n"
			+ "	union \n"
			+ "    select * from song where title = ''\n"
			+ "    union\n"
			+ "    select * from song where actual_band_name = ''\n"
			+ "    union\n"
			+ "    select * from song where actual_song_name = ''\n"
			+ "    union\n"
			+ "    select * from song where title like '%Deleted video%'\n"
			+ "    union\n"
			+ "    select * from song where title like '%Private video%'\n"
			+ "	) x";

	@Query(value=songsWithIssuesCountQuery, nativeQuery=true)
	public int getAllSodSongsWithIssuesCount();

	String songsWithIssuesCountByUserQuery = "select count(*) from\n"
			+ "	(\n"
			+ "	select * from song where youtube_url = '' and user_id = ?1\n"
			+ "	   union \n"
			+ "    select * from song where title = '' and user_id = ?1\n"
			+ "    union\n"
			+ "    select * from song where actual_band_name = '' and user_id = ?1\n"
			+ "    union\n"
			+ "    select * from song where actual_song_name = '' and user_id = ?1\n"
			+ "    union\n"
			+ "    select * from song where title like '%Deleted video%' and user_id = ?1\n"
			+ "    union\n"
			+ "    select * from song where title like '%Private video%' and user_id = ?1\n"
			+ "	) x";

	@Query(value=songsWithIssuesCountByUserQuery, nativeQuery=true)
	public int getAllSodSongsWithIssuesByUserCount(int userId);

}
