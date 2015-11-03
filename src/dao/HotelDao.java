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

public class HotelDao {
	public static List<Map<String, Object>> selectHotel(String city_name) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT hotel.*, user.*,city.* FROM hotel,user,city where hotel.city_name=?  and user.user_id=hotel.user_id and hotel.city_name=city.city_name ";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setString(1, city_name);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("city_id", rs.getString("city_id"));
				map.put("city_name", rs.getString("city_name"));
				map.put("order_id", rs.getInt("order_id"));
				map.put("order_name", rs.getString("order_name"));
				map.put("order_price", rs.getString("order_price"));
				map.put("order_img", rs.getString("order_img"));
				map.put("order_type", rs.getString("order_type"));
				map.put("order_descript", rs.getString("order_descript"));
				map.put("order_recommentNum",
						rs.getString("order_recommentNum"));
				// map.put("usercotent", rs.getString("usercotent"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}
	public static List<Map<String, Object>> selectHotelById(int order_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT hotel.*, user.*,city.* FROM hotel,user,city where hotel.order_id=?  and user.user_id=hotel.user_id and hotel.city_name=city.city_name ";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, order_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("city_id", rs.getString("city_id"));
				map.put("city_name", rs.getString("city_name"));
				map.put("order_id", rs.getInt("order_id"));
				map.put("order_name", rs.getString("order_name"));
				map.put("order_price", rs.getString("order_price"));
				map.put("order_img", rs.getString("order_img"));
				map.put("order_type", rs.getString("order_type"));
				map.put("order_descript", rs.getString("order_descript"));
				map.put("order_recommentNum",
						rs.getString("order_recommentNum"));
				// map.put("usercotent", rs.getString("usercotent"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;

	}

	public static List<Map<String, Object>> selectHotel(int order_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT hotel.*,hoteldetail.*, user.* FROM hotel,hoteldetail,user where hotel.order_id=? and hoteldetail.order_id = hotel.order_id and hotel.user_id = user.user_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, order_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("order_id", rs.getInt("order_id"));
				map.put("order_name", rs.getString("order_name"));
				map.put("order_price", rs.getString("order_price"));
				map.put("order_img", rs.getString("order_img"));
				map.put("order_type", rs.getString("order_type"));
				map.put("order_descript", rs.getString("order_descript"));
				map.put("order_recommentNum",
						rs.getString("order_recommentNum"));
				map.put("usercotent", rs.getString("usercotent"));
				map.put("username", rs.getString("username"));
				map.put("userimg", rs.getString("userimg"));
				map.put("user_levle", rs.getString("user_level"));
				map.put("publishtime", rs.getString("publishtime"));
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
