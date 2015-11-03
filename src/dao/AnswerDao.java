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

public class AnswerDao {
	// 通过城市id找问答内容
	public static List<Map<String, Object>> selectAskByCityId(int city_id,
			int ask_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT user.*,answer.* FROM answer,user where answer.city_id=?  and answer.ask_id=? and ask_answer_user_id=user_id order by is_best desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			ps.setInt(2, ask_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("img", rs.getString("userimg"));
				map.put("id", rs.getString("answer_id"));
				map.put("name", rs.getString("username"));
				map.put("level", rs.getString("user_level"));
				map.put("content", rs.getString("ask_answer"));
				map.put("time", rs.getString("ask_answer_time"));
				map.put("good", rs.getInt("ask_answer_support_num"));
				map.put("isBest", rs.getInt("is_best"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public boolean addSupport(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "update  answer set ask_answer_support_num=ask_answer_support_num+1 where where answer_id=?";
		String s = new String();
		int idnum = 0;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			if (ps.executeUpdate() > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return false;

	}
	public boolean downSupport(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "update  answer set ask_answer_support_num=ask_answer_support_num+1 where where answer_id=?";
		String s = new String();
		int idnum = 0;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			if (ps.executeUpdate() > 0) {
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
