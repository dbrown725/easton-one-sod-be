package sod.eastonone.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sod.eastonone.music.auth.models.User;
import sod.eastonone.music.service.SodSongService;

@Controller
@RequestMapping("/api/csv")
public class CSVSongController {

	@Autowired
	SodSongService sodSongService;

	private static final Logger logger = LoggerFactory.getLogger(CSVSongController.class);

	@GetMapping("/download")
	public ResponseEntity<Resource> getFile(@AuthenticationPrincipal User user) {
		logger.debug("Entering getFile for user " + user.getId());
		String filename = "songs.csv";
		InputStreamResource file = new InputStreamResource(sodSongService.loadAllSongs());

		ResponseEntity<Resource> response = null;
		try {
			response = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/csv")).body(file);
		} catch (Exception e) {
			logger.error("getFile: error caught retrieving download for user " + user.getId(), e);
			throw e;
		}
		logger.debug("Exiting getFile for user " + user.getId());
		return response;
	}
}
