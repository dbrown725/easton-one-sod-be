package sod.eastonone.music.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	
	public String getThumbnailUrl(String videoUrl) throws Exception {
		
		String videoId = "";
		try {
			if (videoUrl.startsWith("https://www.youtube.com") && videoUrl.length() >= 43) {
				videoId = videoUrl.substring(32, 43);
			} else if (videoUrl.startsWith("https://youtu.be") && videoUrl.length() >= 28) {
				videoId = videoUrl.substring(17, 28);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed to parse video url", e);
			throw e;
		}
		
		return "https://i.ytimg.com/vi/" + videoId + "/hqdefault.jpg";
	}

}
