package sod.eastonone.music.service.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import sod.eastonone.music.es.model.Song;

public class CSVHelper {

	public static ByteArrayInputStream songsToCSV(List<Song> songs) {

		Builder builder = CSVFormat.DEFAULT.builder().setQuoteMode(QuoteMode.MINIMAL);
		final CSVFormat format = builder.build();

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			
			List<String> headerData = Arrays.asList("id", "actual_band_name", 
					"actual_song_name", "title", "youtube_url", "youtube_playlist",
					"user_id", "user_first_name", "user_last_name", "user_avatar_color");
			csvPrinter.printRecord(headerData);
			
			for (Song song : songs) {
				List<String> data = Arrays.asList(
						String.valueOf(song.getId()),
						song.getBandName(),
						song.getSongName(),
						song.getTitle(),
						song.getLink(),
						song.getPlaylist(),
						String.valueOf(song.getUserId()),
						song.getUserFirstName(),
						song.getUserLastName(),
						song.getUserAvatarColor());

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
}
