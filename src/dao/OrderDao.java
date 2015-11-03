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

public class OrderDao {
	public static boolean addOrder(String name, String time, int num,
			int price, String img, int user_id, String cName, String Ctel,
			int top, int sale_id, int isReceiver) {

		String sql = "select max(order_id) from orders";
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
				id = id + 1;
			}
			sql = "insert into orders values(?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, time);
			ps.setInt(4, num);
			ps.setInt(5, price);
			ps.setInt(6, top);
			ps.setString(7, img);
			ps.setInt(8, user_id);
			ps.setString(9, cName);
			ps.setString(10, Ctel);
			ps.setInt(11, isReceiver);
			ps.setInt(12, sale_id);

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

	public static List<Map<String, Object>> selectOrder(int top, int user_id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT orders.* FROM orders where user_id=? and isTop=? order by order_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, user_id);
			ps.setInt(2, top);

			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("order_id", rs.getInt("order_id"));
				map.put("hotelName", rs.getString("hotelName"));
				map.put("inTime", rs.getString("inTime"));
				map.put("orderNum", rs.getInt("orderNum"));
				map.put("orderPrice", rs.getInt("orderPrice"));
				map.put("name", rs.getString("contract_name"));
				map.put("phonenum", rs.getString("contract_tel"));
				map.put("isTop", top);
				map.put("hotelImg", rs.getString("hotelImg"));
				map.put("isReceiver", rs.getInt("is_receive"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public static List<Map<String, Object>> selectOrderByHunter(int hunter_id,
			int is_receive) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT orders.* FROM orders,huntermain where hunter_id=? and isTop=2 and is_receive=? and huntermain.huntermain_id=orders.sale_id order by order_id desc";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, hunter_id);
			ps.setInt(2, is_receive);
			rs = ps.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("order_id", rs.getInt("order_id"));
				map.put("hotelName", rs.getString("hotelName"));
				map.put("inTime", rs.getString("inTime"));
				map.put("orderNum", rs.getInt("orderNum"));
				map.put("orderPrice", rs.getInt("orderPrice"));
				map.put("name", rs.getString("contract_name"));
				map.put("phonenum", rs.getString("contract_tel"));
				map.put("isTop", 2);
				map.put("hotelImg", rs.getString("hotelImg"));
				map.put("huntermain_id", rs.getString("sale_id"));
				map.put("isReceiver", rs.getInt("is_receive"));

				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}

	public boolean updateOrder(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "update  orders set is_receive=1 where order_id=?";
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

	public boolean cancelOrder(int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "update  orders set is_receive=0 where order_id=?";
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
