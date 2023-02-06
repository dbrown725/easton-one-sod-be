package sod.eastonone.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sod.eastonone.music.service.SodSongService;

@CrossOrigin("http://localhost:3000")
@Controller
@RequestMapping("/api/csv")
public class CSVSongController {

	@Autowired
	SodSongService sodSongService;

	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		String filename = "songs.csv";
		InputStreamResource file = new InputStreamResource(sodSongService.loadAllSongs());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
}
