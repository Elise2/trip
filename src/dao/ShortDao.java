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

public class ShortDao {
	// 查找所有段游记
	public static List<Map<String, Object>> selectAll() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT user.*,shortcomment.* FROM shortcomment,user where shortComment_user_id=user_id order by shortComment_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("shortCommentId", rs.getInt("shortComment_id"));
				map.put("shortCommentUserId", rs.getInt("shortComment_user_id"));
				map.put("shortCommentFace", rs.getString("userimg"));
				map.put("shortCommentLevel", rs.getString("user_level"));
				map.put("shortCommentname", rs.getString("username"));
				map.put("shortCommentImg", rs.getString("shortComment_img"));
				map.put("shortCommentLocation",
						rs.getString("shortComment_location"));
				map.put("shortCommentTime", rs.getString("shortComment_time"));
				map.put("shortCommentContent",
						rs.getString("shortComment_content"));
				map.put("shortCommentLike", rs.getInt("shortCommnet_like_num"));
				map.put("shortCommentNum",
						countDiscuss(rs.getInt("shortComment_id")));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static int countDiscuss(int shortComment_discuss_id) {
		Connection con = null;
		PreparedStatement ps = null;
		int i = 0;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM shortCommentdiscussid where shortComment_discuss_id=? ";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, shortComment_discuss_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return i;
	}

	public static boolean addShort(int useId, String path, String time,
			String location, String content) {

		String sql = "select max(shortComment_id) from shortcomment ";
		int id = 0;
		int nameid = 0;
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
			sql = "insert into shortcomment values(?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, useId);
			ps.setString(2, path);
			ps.setInt(3, 0);
			ps.setString(4, time);
			ps.setString(5, location);
			ps.setInt(6, id + 1);
			ps.setString(7, content);
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
