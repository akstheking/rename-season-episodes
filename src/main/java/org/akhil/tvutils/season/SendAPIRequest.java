package org.akhil.tvutils.season;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendAPIRequest {
	private static final String IMDB_API_URL = "http://imdbapi.poromenos.org/js/";
	private String tvSeries;
	
	public static void main(String... args) {
		SendAPIRequest fej = new SendAPIRequest("how+i+met+%25+mother");
		try {
			System.out.println(fej.sendImdbApiRequest());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SendAPIRequest(String tvSeries) {
		super();
		this.tvSeries = tvSeries;
	}

	public StringBuffer sendImdbApiRequest() throws IOException {
		String parameter = "?name="+tvSeries;
		return sendGetRequest(IMDB_API_URL, parameter);
	}
	
	public static StringBuffer sendGetRequest(String urlString, String parameters) throws IOException {
		URL url = new URL(urlString+parameters);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		int responseCode = conn.getResponseCode();
		StringBuffer sb = new StringBuffer();	
		String line;		
		if(responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while((line = in.readLine()) != null) {
				sb.append(line);
			}
			/*in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			while((line = in.readLine()) != null) {
				sb.append(line);
			}*/
		} else {
			throw new IOException("NON-OK status : " + responseCode);
		}
		return sb;
	}

	public String getTvSeries() {
		return tvSeries;
	}

	public void setTvSeries(String tvSeries) {
		this.tvSeries = tvSeries;
	}
	
	

}
