package sod.eastonone.music.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sod.eastonone.music.dao.entity.SongComment;

@Repository
public interface SongCommentRepository extends JpaRepository<SongComment, Integer> {

	@Query(value="SELECT * FROM song_comment sc where sc.song_id = ?1", nativeQuery=true)
	public List<SongComment> getSongCommentsBySongId();

}
