package org.akhil.tvutils.season;

public class Episode {
	private int season;
	private String name;
	private int number;
	
	@Override
	public String toString() {
		return "\nEpisode [season=" + season + ", name=" + name + ", number="
				+ number + "]";
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
}
