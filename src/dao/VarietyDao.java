package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.jdbc;

public class VarietyDao {
	public static List<Map<String, Object>> selectType(int type) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT city.*,my.*,topImg,shareReason FROM varietytraveling,city,my where isTravelType=? and city.city_id=varietytraveling.city_id and my.my_id=varietytraveling.my_id ORDER BY travelTypeId";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, type);
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
				map.put("topImg", rs.getString("topImg"));
				map.put("shareReason", rs.getString("shareReason"));
				map.put("myId", rs.getInt("my_id"));
				map.put("myName", rs.getString("my_name"));
				map.put("myImg", rs.getString("my_img"));
				map.put("myLevel", rs.getString("my_level"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selectsType(int type) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT topImg,shareReason FROM varietytraveling where isTravelType=?  ORDER BY travelTypeId";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, type);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("topImg", rs.getString("topImg"));
				map.put("shareReason", rs.getString("shareReason"));
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
