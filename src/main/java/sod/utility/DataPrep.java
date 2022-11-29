package sod.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import sod.eastonone.music.es.model.Song;

public class DataPrep {
	
	//Attempts to extract the band name and song name from the song's title property

	//	5329 rows 11/19/2022
	// #914 deleted song, has Band name but no song name
	// 1189	Phil Lynott 	Kings Call - nominator "Frank"?

	public static void main(String[] args) {

		int countDelete = 0;

		List<SongExt> songs = readMasterCSV();
		for (SongExt song : songs) {

			String cleanTitle = "";

			boolean skipProcessing = false;
			if (!StringUtils.hasLength(song.getTitle())) {
				skipProcessing = true;
			} else {
				if (song.getTitle().equals("Deleted video")) {
					countDelete++;
				}

				cleanTitle = song.getTitle().replaceAll("\\([^()]*\\)", "") // Strip out any round brackets and contents
						.replaceAll("\\[[^]]*\\]", "") // Strip out any square brackets and contents
						.replaceAll("---", "-").replaceAll("--", "-").replaceAll("Sleater-Kinney", "Sleater Kinney")
						.replaceAll("Drive-By", "Drive By").replaceAll("~", "-").replaceAll("–", "-")
						.replaceAll(":", "-");

				long count = cleanTitle.chars().filter(ch -> ch == '/').count();
				long count2 = cleanTitle.chars().filter(ch -> ch == '-').count();
				if (count == 1 && count2 == 0) {
					cleanTitle = cleanTitle.replaceAll("/", "-");
				}

				long count3 = cleanTitle.chars().filter(ch -> ch == '|').count();
				long count4 = cleanTitle.chars().filter(ch -> ch == '-').count();
				if (count3 == 1 && count4 == 0) {
					cleanTitle = cleanTitle.replaceAll("|", "-");
				}
			}
			if (StringUtils.hasLength(song.getBandName())) {
				song.setDerivedBandName("******* Already GOOD *******");
				skipProcessing = true;
			}
			if (StringUtils.hasLength(song.getSongName())) {
				song.setDerivedSongName("******* Already GOOD *******");
				skipProcessing = true;
			}

			if (!skipProcessing) {
				String[] result = cleanTitle.split("-");
				if (result.length >= 2) {
					song.setDerivedBandName(result[0].trim());
					song.setDerivedSongName(result[1].trim());

					if (song.getDerivedBandName().startsWith("'") && song.getDerivedBandName().endsWith("'")) {
						song.setDerivedBandName(song.getDerivedBandName().replaceAll("'$|^'", ""));
					}
					if (song.getDerivedSongName().startsWith("'") && song.getDerivedSongName().endsWith("'")) {
						song.setDerivedSongName(song.getDerivedSongName().replaceAll("'$|^'", ""));
					}
				}
			}

		}

		printCountsBlankBandsAndSongs(songs);
		System.out.println("countDelete: " + countDelete);
		writeNewCSV(songs);

	}

	private static void printCountsBlankBandsAndSongs(List<SongExt> songs) {
		int blankBandNameCount = 0;
		int blankSongNameCount = 0;
		for (SongExt song : songs) {
			if (song.getDerivedBandName() == null || song.getDerivedBandName().isEmpty()) {
				blankBandNameCount++;
			}
			if (song.getDerivedSongName() == null || song.getDerivedSongName().isEmpty()) {
				blankSongNameCount++;
			}
		}
		System.out.println("blankBandNameCount: " + blankBandNameCount);
		System.out.println("blankSongNameCount: " + blankSongNameCount);
	}

	private static List<SongExt> readMasterCSV() {
		List<SongExt> songs = new ArrayList<SongExt>();

		String pathToCsv = "/home/example/Master_Song_List.csv";
		BufferedReader csvReader = null;
		try {
			csvReader = new BufferedReader(new FileReader(pathToCsv));

			String row;
			boolean firstLine = true;
			while ((row = csvReader.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue;
				}
				String[] data = row.split(",");
				SongExt songExt = new SongExt();
				songExt.setId(Integer.parseInt(data[0]));
				songExt.setBandName(data[1]);
				songExt.setSongName(data[2]);
				songExt.setTitle(data[3]);
				songs.add(songExt);
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return songs;
	}

	private static void writeNewCSV(List<SongExt> songs) {
		try {
			FileWriter csvWriter = new FileWriter("/home/example/new.csv");
			csvWriter.append("Id");
			csvWriter.append(",");
			csvWriter.append("youtube_title");
			csvWriter.append(",");
			csvWriter.append("derivedBandName");
			csvWriter.append(",");
			csvWriter.append("derivedSongName");
			csvWriter.append("\n");

			for (SongExt song : songs) {
				csvWriter.append(String.valueOf(song.getId()));
				csvWriter.append(",");
				csvWriter.append(song.getTitle());
				csvWriter.append(",");
				csvWriter.append(song.getDerivedBandName());
				csvWriter.append(",");
				csvWriter.append(song.getDerivedSongName());
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static class SongExt extends Song {
		String derivedBandName;
		String derivedSongName;

		public String getDerivedBandName() {
			return derivedBandName;
		}

		public void setDerivedBandName(String derivedBandName) {
			this.derivedBandName = derivedBandName;
		}

		public String getDerivedSongName() {
			return derivedSongName;
		}

		public void setDerivedSongName(String derivedSongName) {
			this.derivedSongName = derivedSongName;
		}

		@Override
		public String toString() {
			return "SongExt [derivedBandName=" + derivedBandName + ", derivedSongName=" + derivedSongName + ", getId()="
					+ getId() + ", getTitle()=" + getTitle() + "]";
		}
	}

}
