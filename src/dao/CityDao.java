package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.City;
import util.jdbc;

public class CityDao {

	public static List<Map<String, Object>> selectCitys() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM city ORDER BY namesort";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("city_name", rs.getString("city_name"));
				map.put("city_traver_num", rs.getInt("city_traver_num"));
				map.put("iscity", rs.getInt("iscity"));
				map.put("city_category", rs.getInt("city_category"));
				map.put("namesort", rs.getString("namesort"));
				map.put("city_id", rs.getInt("city_id"));
				list.add(map);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selectProvince() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM city where iscity=? ORDER BY namesort";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("city_name", rs.getString("city_name"));
				map.put("city_traver_num", rs.getInt("city_traver_num"));
				map.put("iscity", rs.getInt("iscity"));
				map.put("city_category", rs.getInt("city_category"));
				map.put("namesort", rs.getString("namesort"));
				map.put("city_id", rs.getInt("city_id"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> seekProvince(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM city where city_category=? ORDER BY city_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("cityName", rs.getString("city_name"));
				map.put("cityTraverNum", rs.getInt("city_traver_num"));
				map.put("isCity", rs.getInt("iscity"));
				map.put("cityCategory", rs.getInt("city_category"));
				map.put("nameSort", rs.getString("namesort"));
				map.put("cityId", rs.getInt("city_id"));
				map.put("cityImg", rs.getString("img"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selectCity(String cityName) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM city where city_name= ? ";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setString(1, cityName);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("cityName", rs.getString("city_name"));
				map.put("cityTraverNum", rs.getInt("city_traver_num"));
				map.put("isCity", rs.getInt("iscity"));
				map.put("cityCategory", rs.getInt("city_category"));
				map.put("nameSort", rs.getString("namesort"));
				map.put("cityId", rs.getInt("city_id"));
				map.put("cityImg", rs.getString("img"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

}
