package sod.eastonone.music.model;

public class BandStats implements Comparable<BandStats>{
		String bandName;
		int songCount;
		
		public BandStats(String bandName, int songCount) {
			super();
			this.bandName = bandName;
			this.songCount = songCount;
		}
		
		public String getBandName() {
			return bandName;
		}
		public void setBandName(String bandName) {
			this.bandName = bandName;
		}
		public int getSongCount() {
			return songCount;
		}
		public void setSongCount(int songCount) {
			this.songCount = songCount;
		}
		@Override
		public String toString() {
			return "BandStats [bandName=" + bandName + ", songCount=" + songCount + "]";
		}
		
		@Override public int compareTo(BandStats a)
	    {
	        if (this.songCount > a.songCount) {
	            return -1;
	        }
	        else if (this.songCount < a.songCount) {
	            return 1;
	        }
	        else{
	            return 0;
	        }

	    }

	}
