package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Trace;
import util.jdbc;

public class HunterDao {
	public static boolean addHunter(String name, String img, int sex,
			String content, String indetify, String tel) {

		String sql = "select max(hunter_id) from hunter ";
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
			sql = "insert into hunter values(?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id + 1);
			ps.setString(2, name);
			ps.setString(3, img);
			ps.setInt(4, sex);
			ps.setString(5, tel);
			ps.setString(6, content);
			ps.setString(7, indetify);
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

	public static List<Map<String, Object>> updateHunter(String name,
			String img, int sex, String content, String indetify, String tel,
			int id, String pwd) {

		String sql = "delete  from hunter where hunter_id=?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

			sql = "insert into hunter values(?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, img);
			ps.setInt(4, sex);
			ps.setString(5, tel);
			ps.setString(6, content);
			ps.setString(7, indetify);
			ps.setString(8, pwd);
			while (ps.executeUpdate() > 0) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);
				map.put("name", name);
				map.put("img", img);
				map.put("sex", sex);
				map.put("tel", tel + "");
				map.put("detail", content);
				map.put("indedify", indetify);
				map.put("pwd", pwd);
				list.add(map);
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return null;
	}

	public static boolean addHunterMain(int hunter_id, String img, int price,
			String des, String location, String lable, List<Trace> traces,
			String hunter_time) {

		String sql = "select max(huntermain_id) from huntermain ";
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
			sql = "insert into huntermain values(?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id + 1);
			ps.setInt(2, hunter_id);
			ps.setString(3, img);
			ps.setInt(4, price);
			ps.setString(5, des);
			ps.setInt(6, 0);
			ps.setString(7, location);
			ps.setString(8, lable);
			ps.setString(9, hunter_time);
			ps.setInt(10, 35);

			while (ps.executeUpdate() > 0) {
				for (int i = 0; i < traces.size(); i++) {
					addHunterDetail(hunter_id, id + 1, traces.get(i));
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

	public static boolean addHunterDetail(int hunter_id, int huntermain_id,
			Trace trace) {

		String sql = "select max(hunterdetail_id) from hunterdetail ";
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
			sql = "insert into hunterdetail values(?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, hunter_id);
			ps.setInt(2, id + 1);
			ps.setString(3, trace.getContent());
			ps.setString(4, trace.getImg());
			ps.setInt(5, huntermain_id);
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

	public static List<Map<String, Object>> selectHunter(int city_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT huntermain.*, hunter.*,city.* FROM huntermain, hunter,city where huntermain.city_id = ? and huntermain.city_id=city.city_id and hunter.hunter_id=huntermain.hunter_id order by huntermain.huntermain_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, city_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("hunter_id", rs.getString("hunter_id"));
				map.put("hunter_img", rs.getString("hunter_img"));
				map.put("hunter_price", rs.getInt("hunter_price"));
				map.put("hunter_descript", rs.getString("hunter_descript"));
				map.put("like_num", rs.getInt("like_num"));
				map.put("hunter_name", rs.getString("hunter_name"));
				map.put("hunter_img", rs.getString("hunter_img"));
				map.put("hunter_sex", rs.getString("hunter_sex"));
				map.put("hunter_tel", rs.getString("hunter_tel"));
				map.put("hunter_detatils_info",
						rs.getString("hunter_detatils_info"));
				map.put("hunter_ identity_card",
						rs.getString("hunter_ identity_card"));
				map.put("hunter_title_img", rs.getString("hunter_title_img"));
				map.put("hunter_location", rs.getString("hunter_location"));
				map.put("hunter_label", rs.getString("hunter_label"));
				map.put("huntermain_id", rs.getString("huntermain_id"));
				map.put("hunter_label", rs.getString("hunter_label"));
				map.put("hunter_time", rs.getString("hunter_time"));
				map.put("hunter_location", rs.getString("hunter_location"));

				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}

	public static List<Map<String, Object>> selectHunterByHunter(int hunter) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT huntermain.*, hunter.*,city.* FROM huntermain, hunter,city where huntermain.hunter_id = ? and huntermain.city_id=city.city_id and hunter.hunter_id=huntermain.hunter_id order by huntermain.huntermain_id  desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, hunter);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("hunter_id", rs.getString("hunter_id"));
				map.put("hunter_img", rs.getString("hunter_img"));
				map.put("hunter_price", rs.getInt("hunter_price"));
				map.put("hunter_descript", rs.getString("hunter_descript"));
				map.put("like_num", rs.getInt("like_num"));
				map.put("hunter_name", rs.getString("hunter_name"));
				map.put("hunter_img", rs.getString("hunter_img"));
				map.put("hunter_sex", rs.getString("hunter_sex"));
				map.put("hunter_tel", rs.getString("hunter_tel"));
				map.put("hunter_detatils_info",
						rs.getString("hunter_detatils_info"));
				map.put("hunter_ identity_card",
						rs.getString("hunter_ identity_card"));
				map.put("hunter_title_img", rs.getString("hunter_title_img"));
				map.put("hunter_location", rs.getString("hunter_location"));
				map.put("hunter_label", rs.getString("hunter_label"));
				map.put("huntermain_id", rs.getString("huntermain_id"));
				map.put("hunter_label", rs.getString("hunter_label"));
				map.put("hunter_time", rs.getString("hunter_time"));
				map.put("hunter_location", rs.getString("hunter_location"));

				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}

	public static List<Map<String, Object>> selectHunterDetail(int huntermain_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT hunter.*,huntermain.*, hunterdetail.* FROM hunter,huntermain,hunterdetail where huntermain.huntermain_id=? and hunterdetail.huntermain_id = huntermain.huntermain_id and huntermain.hunter_id = hunter.hunter_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, huntermain_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("hunter_id", rs.getString("hunter_id"));
				map.put("hunter_img", rs.getString("hunter_img"));
				map.put("hunter_price", rs.getInt("hunter_price"));
				map.put("hunter_descript", rs.getString("hunter_descript"));
				map.put("like_num", rs.getInt("like_num"));
				map.put("hunter_name", rs.getString("hunter_name"));
				map.put("hunter_img", rs.getString("hunter_img"));
				map.put("hunter_sex", rs.getString("hunter_sex"));
				map.put("hunter_tel", rs.getString("hunter_tel"));
				map.put("hunter_detatils_info",
						rs.getString("hunter_detatils_info"));
				map.put("hunter_ identity_card",
						rs.getString("hunter_ identity_card"));
				map.put("hunter_title_img", rs.getString("hunter_title_img"));
				map.put("hunter_location", rs.getString("hunter_location"));
				map.put("hunter_label", rs.getString("hunter_label"));
				map.put("huntermain_id", rs.getString("huntermain_id"));
				map.put("hunter_label", rs.getString("hunter_label"));
				map.put("hunter_time", rs.getString("hunter_time"));
				map.put("hunter_location", rs.getString("hunter_location"));
				map.put("hunterdetail_content",
						rs.getString("hunterdetail_content"));
				map.put("hunterdetail_img", rs.getString("hunterdetail_img"));

				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}

	public static List<Map<String, Object>> login(String hunter_name,
			String hunter_pwd) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * from hunter where hunter_name=? and hunter_pwd =? ";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setString(1, hunter_name);
			ps.setString(2, hunter_pwd);
			rs = ps.executeQuery();
			if (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getString("hunter_id"));
				map.put("name", rs.getString("hunter_name"));
				map.put("img", rs.getString("hunter_img"));
				map.put("sex", rs.getString("hunter_sex"));
				map.put("tel", rs.getString("hunter_tel"));
				map.put("detail", rs.getString("hunter_detatils_info"));
				map.put("indedify", rs.getString("hunter_ identity_card"));
				map.put("pwd", rs.getString("hunter_pwd"));
				list.add(map);
			} else {
				list = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

}
