package org.akhil.tvutils.season;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FetchTvSeriesData {
	private String series;

	public static void main(String[] args) {
		String series = "how+i+met+%25+mother";
		List<Episode> epis = null;
		try {
			epis = new FetchTvSeriesData(series).fetchSortedEpisodes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(epis);
	}

	public FetchTvSeriesData(String series) {
		super();
		this.series = series;
	}

	public Map<String, TvSeries> fetchSeriesData() throws IOException {
		SendAPIRequest fej = new SendAPIRequest(series);
		StringBuffer sb = fej.sendImdbApiRequest();
		Map<String, TvSeries> map =  JsonParsing.parse(sb.toString());
		map.entrySet().iterator().next().getValue().setName(series);
		System.out.println(map);
		return map;
	}

	public List<Episode> fetchSortedEpisodes() throws IOException{
		Map<String, TvSeries> map = fetchSeriesData();
		//String tvSeries = map.entrySet().iterator().next().getKey();
		List<Episode> episodes = map.entrySet().iterator().next().getValue().getEpisodes();
		Collections.sort(episodes, new ValueComparator());
		removeIncorrectNames(episodes);
		return episodes;
	}
	
	public void removeIncorrectNames(List<Episode> episodes) {
		Iterator<Episode> it = episodes.listIterator();
		while(it.hasNext()) {
			Episode e = it.next();
			String incorrectName = "Episode #" + e.getSeason() + "." + e.getNumber();
			if(e.getName().equals(incorrectName)) {
				it.remove();			}
		}
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

}

class ValueComparator implements Comparator<Episode> {

	public int compare(Episode o1, Episode o2) {
		int seasonCompare = o1.getSeason() - o2.getSeason();
		if(seasonCompare == 0) {
			return o1.getNumber() - o2.getNumber();
		}
		return seasonCompare;
	}
	
			
}
