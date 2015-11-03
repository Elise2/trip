package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.regexp.internal.recompile;

import entity.Date;
import entity.Schedule;
import util.jdbc;

public class DateDao {

	public static List<Map<String, Object>> selectOlds(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM date where is_old=? and user_id=? ORDER BY datedetail_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("dateId", rs.getInt("date_id"));
				map.put("dateDetailId", rs.getInt("datedetail_id"));
				map.put("goDate", rs.getString("go_date"));
				map.put("arriveDate", rs.getString("arrive_date"));
				map.put("cityName", rs.getString("city_name"));
				map.put("cityId", rs.getInt("city_id"));
				map.put("isGo", rs.getInt("is_go"));
				map.put("isOld", rs.getInt("is_old"));
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

	public static List<Map<String, Object>> selectNews(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM date where is_old=? and user_id=? ORDER BY datedetail_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, 0);
			ps.setInt(2, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("dateId", rs.getInt("date_id"));
				map.put("dateDetailId", rs.getInt("datedetail_id"));
				map.put("goDate", rs.getString("go_date"));
				map.put("arriveDate", rs.getString("arrive_date"));
				map.put("cityName", rs.getString("city_name"));
				map.put("cityId", rs.getInt("city_id"));
				map.put("isGo", rs.getInt("is_go"));
				map.put("isOld", rs.getInt("is_old"));
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

	public static List<Map<String, Object>> selectCount(List<Date> ids) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < ids.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			int id = Integer.parseInt(ids.get(i).getId());
			map.put("datedetail_id", id);
			map.put("scenery", counts(id, 1));
			map.put("playnote", counts(id, 3));
			map.put("hotel", counts(id, 2));
			list.add(map);
		}

		return list;
	}

	public static List<Map<String, Object>> selectScenert(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM datedetail where datedetail_id=? and isType=?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, 1);
			rs = ps.executeQuery();
			while (rs.next()) {
				List<Map<String, Object>> map = SceneryDao
						.selecteSceneryByid(rs.getInt("type_id"));
				for (int i = 0; i < map.size(); i++) {
					list.add(map.get(i));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}

	public static List<Map<String, Object>> selectHotel(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM datedetail where datedetail_id=? and isType=?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, 2);
			rs = ps.executeQuery();
			while (rs.next()) {
				List<Map<String, Object>> map = HotelDao.selectHotelById(rs
						.getInt("type_id"));
				for (int i = 0; i < map.size(); i++) {
					list.add(map.get(i));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}

	public static List<Map<String, Object>> selectNotes(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM datedetail where datedetail_id=? and isType=?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, 3);
			rs = ps.executeQuery();
			while (rs.next()) {
				List<Map<String, Object>> map = NoteDao.seletTops(rs
						.getInt("type_id"));
				for (int i = 0; i < map.size(); i++) {
					list.add(map.get(i));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}

	public static boolean insertDate(int city_id, String city_name,
			int user_id, String go_date, String arrive_date,
			List<Schedule> traces) {
		updateNews(user_id);
		String sql = "select max(date_id) from date ";
		int id = 0;
		int datailid = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				id = id + 1;
			}
			sql = "select max(datedetail_id) from date ";
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				datailid = rs.getInt(1);
				datailid = datailid + 1;
			}
			sql = "insert into date values(?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, datailid);
			ps.setString(3, go_date);
			ps.setString(4, arrive_date);
			ps.setInt(5, city_id);
			ps.setString(6, city_name);
			ps.setInt(7, 1);
			ps.setInt(8, 0);
			ps.setInt(9, user_id);
			while (ps.executeUpdate() > 0) {
				for (int i = 0; i < traces.size(); i++) {
					datailid = datailid + 1;
					addDate(user_id, traces.get(i), id, datailid);
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

	public static boolean addDate(int user_id, Schedule schedule, int date_id,
			int datedetail_id) {
		String sql = "insert into date values(?,?,?,?,?,?,?,?,?)";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps = con.prepareStatement(sql);
			ps.setInt(1, date_id);
			ps.setInt(2, datedetail_id);
			ps.setString(3, schedule.getGoDate());
			ps.setString(4, schedule.getLeaveDate());
			ps.setInt(5, schedule.getCityId());
			ps.setString(6, schedule.getCityName());
			ps.setInt(7, 0);
			ps.setInt(8, 0);
			ps.setInt(9, user_id);
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

	public static boolean addDateDetail(int datedetailid, int type, int type_id) {
		String sql = "insert into datedetail values(?,?,?)";
		int id = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, datedetailid);
			ps.setInt(2, type);
			ps.setInt(3, type_id);
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

	public static boolean deleteDateDetail(int datedetailid, int type,
			int type_id) {
		String sql = "delete from datedetail where datedetail_id=? and isType=?  and type_id=?";
		int id = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, datedetailid);
			ps.setInt(2, type);
			ps.setInt(3, type_id);
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

	public static boolean updateNews(int id) {

		String sql = "update date set is_old=? where user_id=?";
		int nameid = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, id);
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

	public static int counts(int datedetail_id, int is) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM datedetail where datedetail_id=? and isType=?";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, datedetail_id);
			ps.setInt(2, is);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return 0;
	}

}
