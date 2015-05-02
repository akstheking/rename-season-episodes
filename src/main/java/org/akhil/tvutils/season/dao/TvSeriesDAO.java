package org.akhil.tvutils.season.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;

public class TvSeriesDAO {
	Logger LOGGER = Logger.getLogger(TvSeriesDAO.class);

	private final static String INSERT_SERIES = "INSERT INTO series(name, year) VALUES (?, ?)";
	private final static String INSERT_EPISODE = "INSERT INTO episodes(series_id, season, number, name) VALUES (?, ?, ?, ?)";
	private final static String FETCH_EPISODES = "SELECT e.number AS num, e.name AS e_name FROM series s, episodes e WHERE e.series_id = s.id AND s.name = ? AND season = ?";

	public int insertSeries(String name, int year) throws SQLException {
		try (Connection conn = MySqlConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_SERIES,
						Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, name);
			ps.setInt(2, year);
			int rows = ps.executeUpdate();
			LOGGER.info(rows + " updated by " + INSERT_SERIES);
			int id = -1;
			try (ResultSet rs = ps.getGeneratedKeys()) {
				rs.next();
				id = rs.getInt(1);
			}
			return id;
		}
	}

	public void insertEpisode(int seriesID, int season, int number, String name)
			throws SQLException {
		try (Connection conn = MySqlConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_EPISODE)) {
			ps.setInt(1, seriesID);
			ps.setInt(2, season);
			ps.setInt(3, number);
			ps.setString(4, name);
			int rows = ps.executeUpdate();
			LOGGER.info(rows + " updated by " + INSERT_EPISODE);
		}
	}

	public Map<Integer, String> fetchEpisodes(String series, int season)
			throws SQLException {
		try (Connection conn = MySqlConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(FETCH_EPISODES)) {
			ps.setString(1, series);
			ps.setInt(2, season);
			Map<Integer, String> episodes = new HashMap<>();
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					episodes.put(rs.getInt("num"), rs.getString("e_name"));
				}
			}
			return episodes;
		}
	}

}
