package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.jdbc;

import com.mysql.jdbc.Connection;

public class SceneryDao {
	public static List<Map<String, Object>> selecteSceneryByType(int city_id,
			int isType) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT city.*,user.*,scenery.*,recommentor.* FROM scenery,city,user,recommentor where scenery.city_id=? and scenery.isType=? and city.city_id=scenery.city_id  and recommentor.scenery_id=scenery.scenery_id and recommentor.user_id=user.user_id and recommentor.city_id= city.city_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			ps.setInt(2, isType);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("scenery_id", rs.getString("scenery_id"));

				map.put("scenery_title", rs.getString("scenery_title"));
				map.put("scenery_img", rs.getString("scenery_img"));
				map.put("scenery_type", rs.getString("scenery_type"));
				map.put("scenery_talkNum", rs.getInt("scenery_talkNum"));
				map.put("username", rs.getString("username"));
				// 新增属性
				map.put("userimg", rs.getString("userimg"));
				map.put("scenery_type", rs.getString("scenery_type"));
				map.put("talkNum", rs.getString("city_traver_num"));
				map.put("hotel_price", rs.getString("hotel_price"));
				map.put("sceneryname", rs.getString("sceneryname"));
				map.put("comment", rs.getString("comment"));

				// 新增属性
				map.put("title_comment", rs.getString("title_comment"));// 这是每个景点下的短评
				map.put("contact_tel", rs.getString("contact_tel"));
				map.put("open_time", rs.getString("open_time"));
				map.put("scenery_cost", rs.getString("scenery_cost"));
				map.put("strategy_descript", rs.getString("strategy_descript"));
				map.put("isTop", rs.getInt("isTop"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selecteSceneryByid(int scenery_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT city.*,user.*,scenery.*,recommentor.* FROM scenery,city,user,recommentor where scenery.scenery_id=?  and city.city_id=scenery.city_id  and recommentor.scenery_id=scenery.scenery_id and recommentor.user_id=user.user_id and recommentor.city_id= city.city_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, scenery_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("scenery_id", rs.getString("scenery_id"));

				map.put("scenery_title", rs.getString("scenery_title"));
				map.put("scenery_img", rs.getString("scenery_img"));
				map.put("scenery_type", rs.getString("scenery_type"));
				map.put("scenery_talkNum", rs.getInt("scenery_talkNum"));
				map.put("username", rs.getString("username"));
				// 新增属性
				map.put("userimg", rs.getString("userimg"));
				map.put("scenery_type", rs.getString("scenery_type"));
				map.put("talkNum", rs.getString("city_traver_num"));
				map.put("hotel_price", rs.getString("hotel_price"));
				map.put("sceneryname", rs.getString("sceneryname"));
				map.put("comment", rs.getString("comment"));

				// 新增属性
				map.put("title_comment", rs.getString("title_comment"));// 这是每个景点下的短评
				map.put("contact_tel", rs.getString("contact_tel"));
				map.put("open_time", rs.getString("open_time"));
				map.put("scenery_cost", rs.getString("scenery_cost"));
				map.put("strategy_descript", rs.getString("strategy_descript"));
				map.put("isTop", rs.getInt("isTop"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selecteSceneryByCity(int city_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT city.*,user.*,scenery.*,recommentor.* FROM scenery,city,user,recommentor where scenery.city_id=?  and city.city_id=scenery.city_id  and recommentor.scenery_id=scenery.scenery_id and recommentor.user_id=user.user_id and recommentor.city_id= city.city_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("scenery_id", rs.getString("scenery_id"));

				map.put("scenery_title", rs.getString("scenery_title"));
				map.put("scenery_img", rs.getString("scenery_img"));
				map.put("scenery_type", rs.getString("scenery_type"));
				map.put("scenery_talkNum", rs.getInt("scenery_talkNum"));
				map.put("username", rs.getString("username"));
				// 新增属性
				map.put("userimg", rs.getString("userimg"));
				map.put("scenery_type", rs.getString("scenery_type"));
				map.put("talkNum", rs.getString("city_traver_num"));
				map.put("hotel_price", rs.getString("hotel_price"));
				map.put("sceneryname", rs.getString("sceneryname"));
				map.put("comment", rs.getString("comment"));

				// 新增属性
				map.put("title_comment", rs.getString("title_comment"));// 这是每个景点下的短评
				map.put("contact_tel", rs.getString("contact_tel"));
				map.put("open_time", rs.getString("open_time"));
				map.put("scenery_cost", rs.getString("scenery_cost"));
				map.put("strategy_descript", rs.getString("strategy_descript"));
				map.put("isTop", rs.getInt("isTop"));
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
