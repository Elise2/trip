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

public class AskDao {
	// 通过城市id找旅游问答
	public static List<Map<String, Object>> selectAskByCityId(int city_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT city.*,user.*,ask.* FROM ask,city,user where ask.city_id=?  and city.city_id=ask.city_id and user.user_id=ask.ask_user_id ORDER BY ask_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("askTitle", rs.getString("ask_problem_title"));
				map.put("askContent", rs.getString("ask_problem_content"));
				map.put("askTime", rs.getString("ask_time"));
				map.put("askSee", rs.getInt("ask_see"));
				map.put("userId", rs.getInt("user_id"));
				map.put("askUserImg", rs.getString("userimg"));
				map.put("askUserName", rs.getString("username"));
				map.put("askId", rs.getInt("ask_id"));
				map.put("cityId", rs.getInt("city_id"));
				map.put("answerNum",
						countAnswer(rs.getInt("city_id"), rs.getInt("ask_id")));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static boolean addAsk(String city_id, String ask_problem_content,
			int ask_user_id, String ask_time, String ask_problem_title) {

		String sql = "select max(ask_id) from ask ";
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
			sql = "insert into ask values(?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, city_id);
			ps.setString(2, id + 1 + "");
			ps.setString(3, ask_problem_content);
			ps.setInt(4, ask_user_id);
			ps.setString(5, ask_time);
			ps.setInt(6, 0);
			ps.setString(7, ask_problem_title);
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

	public static int countAnswer(int city_id, int ask_id) {
		Connection con = null;
		PreparedStatement ps = null;
		int i = 0;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM answer where city_id=?  and ask_id=?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			ps.setInt(2, ask_id);
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
}
