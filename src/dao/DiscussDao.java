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

public class DiscussDao {
	public static List<Map<String, Object>> selectDiscuss(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT user.*,shortCommentdiscussid.* FROM shortCommentdiscussid,user where shortCommentdiscussid.shortComment_discuss_id=?  and discuss_user_id=user_id order by discuss_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("img", rs.getString("userimg"));
				map.put("id", rs.getString("discuss_id"));
				map.put("name", rs.getString("username"));
				map.put("level", rs.getString("user_level"));
				map.put("content", rs.getString("discuss_content"));
				map.put("time", rs.getString("discuss_time"));
				map.put("shortCommentId", rs.getInt("shortComment_discuss_id"));
				map.put("userId", rs.getInt("user_id"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selectNoteDiscuss(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT user.*,playnotesdiscuss.* FROM playnotesdiscuss,user where playnotesdiscuss.note_id=?  and discuss_user_id=user_id order by discuss_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("img", rs.getString("userimg"));
				map.put("id", rs.getString("discuss_id"));
				map.put("name", rs.getString("username"));
				map.put("level", rs.getString("user_level"));
				map.put("content", rs.getString("discuss_content"));
				map.put("time", rs.getString("discuss_time"));
				map.put("shortCommentId", rs.getInt("note_id"));
				map.put("userId", rs.getInt("user_id"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static boolean addDiscuss(int useId, String time,
			int shortComment_discuss_id, String content) {

		String sql = "select max(discuss_id) from shortCommentdiscussid ";
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
			sql = "insert into shortCommentdiscussid values(?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id + 1);
			ps.setString(2, content);
			ps.setInt(3, useId);
			ps.setString(4, time);
			ps.setInt(5, shortComment_discuss_id);
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

	public static boolean addNoteDiscuss(int useId, String time, int note_id,
			String content) {

		String sql = "select max(discuss_id) from playnotesdiscuss ";
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
			sql = "insert into playnotesdiscuss values(?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id + 1);
			ps.setString(2, content);
			ps.setInt(3, useId);
			ps.setString(4, time);
			ps.setInt(5, note_id);
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
