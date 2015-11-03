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
import entity.Trace;

public class NoteDao {
	public static List<Map<String, Object>> selectNotes(int city_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT playnotes.*,notedetail.*, user.* FROM playnotes,notedetail,user where playnotes.city_id=? and playnotes.note_id=notedetail.note_id and user.user_id=playnotes.user_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("note_id", rs.getString("note_id"));
				map.put("note_img", rs.getString("note_img"));
				map.put("note_content", rs.getString("note_content"));
				map.put("notes_title", rs.getString("notes_title"));
				map.put("notes_go_time", rs.getInt("notes_go_time"));
				map.put("notes_cost", rs.getString("notes_cost"));
				map.put("notes_days", rs.getString("notes_days"));
				map.put("notes_type", rs.getString("notes_type"));
				map.put("notes_publish_time",
						rs.getString("notes_publish_time"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				map.put("see", rs.getInt("see"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selectAllNotes(int note_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT playnotes.*,notedetail.*, user.* FROM playnotes,notedetail,user where playnotes.note_id=? and playnotes.note_id=notedetail.note_id and user.user_id=playnotes.user_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, note_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("note_id", rs.getString("note_id"));
				map.put("note_img", rs.getString("note_img"));
				map.put("note_content", rs.getString("note_content"));
				map.put("notes_title", rs.getString("notes_title"));
				map.put("notes_go_time", rs.getInt("notes_go_time"));
				map.put("notes_cost", rs.getString("notes_cost"));
				map.put("notes_days", rs.getString("notes_days"));
				map.put("notes_type", rs.getString("notes_type"));
				map.put("notes_publish_time",
						rs.getString("notes_publish_time"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				map.put("see", rs.getInt("see"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> seletTop() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT playnotes.*,user.*,city.* from playnotes,user,city where playnotes.user_id = user.user_id and playnotes.city_id=city.city_id order by playnotes.note_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("note_id", rs.getInt("note_id"));
				map.put("city_id", rs.getInt("city_id"));
				map.put("city_name", rs.getString("city_name"));
				map.put("user_id", rs.getInt("user_id"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				map.put("user_level", rs.getString("user_level"));
				map.put("top_img", rs.getString("top_img"));
				map.put("notes_title", rs.getString("notes_title"));
				map.put("notes_go_time", rs.getInt("notes_go_time"));
				map.put("notes_cost", rs.getString("notes_cost"));
				map.put("notes_days", rs.getString("notes_days"));
				map.put("notes_type", rs.getString("notes_type"));
				map.put("notes_publish_time",
						rs.getString("notes_publish_time"));
				map.put("see", rs.getInt("see"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> seletTop(int city_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT playnotes.*,user.*,city.* from playnotes,user,city where playnotes.city_id = ? and playnotes.user_id=user.user_id and playnotes.city_id=city.city_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("note_id", rs.getInt("note_id"));
				map.put("city_id", rs.getInt("city_id"));
				map.put("city_name", rs.getString("city_name"));
				map.put("user_id", rs.getInt("user_id"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				map.put("user_level", rs.getString("user_level"));
				map.put("top_img", rs.getString("top_img"));
				map.put("notes_title", rs.getString("notes_title"));
				map.put("notes_go_time", rs.getInt("notes_go_time"));
				map.put("notes_cost", rs.getString("notes_cost"));
				map.put("notes_days", rs.getString("notes_days"));
				map.put("notes_type", rs.getString("notes_type"));
				map.put("notes_publish_time",
						rs.getString("notes_publish_time"));
				map.put("see", rs.getInt("see"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	// 新增方法
	public static List<Map<String, Object>> seletMyNotes(int user_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT playnotes.*,user.*,city.* from playnotes,user,city where playnotes.user_id = ? and playnotes.user_id = user.user_id and playnotes.city_id=city.city_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("note_id", rs.getInt("note_id"));
				map.put("city_id", rs.getInt("city_id"));
				map.put("city_name", rs.getString("city_name"));
				map.put("user_id", rs.getInt("user_id"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				map.put("user_level", rs.getString("user_level"));
				map.put("top_img", rs.getString("top_img"));
				map.put("notes_title", rs.getString("notes_title"));
				map.put("notes_go_time", rs.getInt("notes_go_time"));
				map.put("notes_cost", rs.getString("notes_cost"));
				map.put("notes_days", rs.getString("notes_days"));
				map.put("notes_type", rs.getString("notes_type"));
				map.put("notes_publish_time",
						rs.getString("notes_publish_time"));
				map.put("see", rs.getInt("see"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> seletTops(int note_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT playnotes.*,user.*,city.* from playnotes,user,city where  playnotes.note_id=? and playnotes.user_id = user.user_id and playnotes.city_id=city.city_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, note_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("note_id", rs.getInt("note_id"));
				map.put("city_id", rs.getInt("city_id"));
				map.put("city_name", rs.getString("city_name"));
				map.put("user_id", rs.getInt("user_id"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				map.put("user_level", rs.getString("user_level"));
				map.put("top_img", rs.getString("top_img"));
				map.put("notes_title", rs.getString("notes_title"));
				map.put("notes_go_time", rs.getInt("notes_go_time"));
				map.put("notes_cost", rs.getString("notes_cost"));
				map.put("notes_days", rs.getString("notes_days"));
				map.put("notes_type", rs.getString("notes_type"));
				map.put("notes_publish_time",
						rs.getString("notes_publish_time"));
				map.put("see", rs.getInt("see"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static boolean addPlayNoteNoId(int city_id, String top_img,
			int user_id, String notes_title, String notes_go_time,
			String notes_cost, String notes_days, String notes_type,
			String notes_publish_time, String notes_travel_type,
			List<Trace> trace) {

		String sql = "select max(note_id) from playnotes ";
		int id = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
			}
			sql = "insert into playnotes values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			ps.setInt(2, id + 1);
			ps.setString(3, top_img);
			ps.setInt(4, 0);
			ps.setInt(5, user_id);
			ps.setString(6, notes_title);
			ps.setString(7, notes_go_time);
			ps.setString(8, notes_cost);
			ps.setString(9, notes_days);
			ps.setString(10, notes_type);
			ps.setString(11, notes_publish_time);
			ps.setString(12, notes_travel_type);
			while (ps.executeUpdate() > 0) {
				for (int i = 0; i < trace.size(); i++) {
					addNoteDetail(city_id, user_id, id + 1, trace.get(i));
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return false;
	}

	public static boolean addPlayNotes(int city_id, String top_img,
			int user_id, String notes_title, String notes_go_time,
			String notes_cost, String notes_days, String notes_type,
			String notes_publish_time, String notes_travel_type,
			List<Trace> trace, int datedetailid) {

		String sql = "select max(note_id) from playnotes ";
		int id = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
			}
			sql = "insert into datedetail values(?,?,?)";
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, datedetailid);
			ps.setInt(2, 3);
			ps.setInt(3, id + 1);
			ps.executeUpdate();
			sql = "insert into playnotes values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			ps.setInt(2, id + 1);
			ps.setString(3, top_img);
			ps.setInt(4, 0);
			ps.setInt(5, user_id);
			ps.setString(6, notes_title);
			ps.setString(7, notes_go_time);
			ps.setString(8, notes_cost);
			ps.setString(9, notes_days);
			ps.setString(10, notes_type);
			ps.setString(11, notes_publish_time);
			ps.setString(12, notes_travel_type);
			while (ps.executeUpdate() > 0) {
				for (int i = 0; i < trace.size(); i++) {
					addNoteDetail(city_id, user_id, id + 1, trace.get(i));
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return false;
	}

	public static boolean addNoteDetail(int city_id, int user_id, int noteid,
			Trace trace) {

		String sql = "select max(note_detail_id) from notedetail ";
		int id = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
			}
			sql = "insert into notedetail values(?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			ps.setInt(2, user_id);
			ps.setInt(3, noteid);
			ps.setString(5, trace.getContent());
			ps.setString(4, trace.getImg());
			ps.setInt(6, id + 1);
			while (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return false;
	}

}
