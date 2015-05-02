package org.akhil.tvutils.season;

import java.util.List;

public class TvSeries {
	
	List<Episode> episodes;
	int year;
	String name;
	
	@Override
	public String toString() {
		return "TvSeries [episodes=" + episodes + ",\n year=" + year + ",\n name="
				+ name + "]";
	}
	public List<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
