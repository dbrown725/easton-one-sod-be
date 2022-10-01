package sod.eastonone.elasticsearch.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sod.eastonone.elasticsearch.dao.entity.MyPotentialSong;
import sod.eastonone.elasticsearch.service.MyPotentialSongService;

@Controller
public class SampleController {

	@Autowired
	private MyPotentialSongService myPotentialSongService;

	@RequestMapping("/hello")
	@ResponseBody
	public String helloWorld() {

		// Print statement
		System.out.println("*********************** inn SampleController helloWorld");
		Optional<MyPotentialSong> song = myPotentialSongService.getMyPotentialSong(2);
		System.out.println("*********************** song.toString(): " + song.toString());

		MyPotentialSong createdSong = myPotentialSongService.createMyPotentialSong("Billy Bragg",
				"Waiting For The Great Leap Forward", "Billy Bragg - Waiting For The Great Leap Forward",
				"https://www.youtube.com/watch?v=mHbfZiE1D50");
		System.out.println("*********************** createdSong.toString(): " + createdSong.toString());

		List<MyPotentialSong> songs = myPotentialSongService.getAllMyPotentialSongs(4);

		for (MyPotentialSong sng : songs) {
			System.out.println("******** one of list: " + sng);
		}

		return song.toString();
	}

}
