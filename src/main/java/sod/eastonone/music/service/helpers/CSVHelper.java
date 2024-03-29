package sod.eastonone.music.service.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import sod.eastonone.music.auth.models.User;
import sod.eastonone.music.es.model.Song;

@Service
public class CSVHelper {

	@Autowired
	GeneralHelper generalHelper;

	public ByteArrayInputStream songsToCSV(List<Song> songs, User user) {

		Builder builder = CSVFormat.DEFAULT.builder().setQuoteMode(QuoteMode.MINIMAL);
		final CSVFormat format = builder.build();

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			
			List<String> headerData = Arrays.asList("id", "actual_band_name", 
					"actual_song_name", "title", "youtube_url", "youtube_playlist",
					"user_id", "user_first_name", "user_last_name", "user_avatar_color",
					"create_time", "modify_time");
			csvPrinter.printRecord(headerData);
			
			List<Song> mutableSongs = new ArrayList<Song>(songs);

			generalHelper.setUserSubmitterAndPrivacy(mutableSongs, user);

			boolean userIsGuest = isGuest(user);

			for (Song song : mutableSongs) {
				List<String> data = Arrays.asList(
						String.valueOf(song.getId()),
						song.getBandName(),
						song.getSongName(),
						song.getTitle(),
						song.getLink(),
						song.getPlaylist(),
						String.valueOf(song.getUserId()),
						userIsGuest?song.isPrivacyOn()?"anonymous":song.getUserFirstName():song.getUserFirstName(),
						userIsGuest?song.isPrivacyOn()?"anonymous":song.getUserLastName():song.getUserLastName(),
						song.getUserAvatarColor(),
						song.getCreateTime(),
						song.getModifyTime());

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (FirebaseAuthException e) {
			throw new RuntimeException("fail to import data to CSV file due to FirebaseAuthException: " + e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file due to IOException: " + e.getMessage());
		}
	}

    private boolean isGuest(User user) throws FirebaseAuthException {
    	String authProviderUid = user.getUid();
    	UserRecord userRecord = FirebaseAuth.getInstance().getUser(authProviderUid);
    	boolean isGuest = false;
    	if(userRecord.getCustomClaims() != null  && userRecord.getCustomClaims().get("GUEST") != null) {
    		isGuest = (boolean)userRecord.getCustomClaims().get("GUEST");
    	}
    	return isGuest;
    }
}
