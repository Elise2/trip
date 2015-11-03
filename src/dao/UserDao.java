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

public class UserDao {
	public static List<Map<String, Object>> selectUserInfo(String username,String userpwd) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT * from user where username=? and userpwd =? order by user_id";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			con = (Connection) jdbc.getcon();
			ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, userpwd);
			rs = ps.executeQuery();
			if (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("username", rs.getString("username"));
				map.put("userpwd", rs.getString("userpwd"));
				map.put("userimg", rs.getString("userimg"));
				map.put("userEmail", rs.getString("userEmail"));
				map.put("userPhoneNum", rs.getString("userPhoneNum"));
				map.put("userimg", rs.getString("userimg"));
				map.put("user_level", rs.getString("user_level"));
				map.put("user_id", rs.getString("user_id"));
				map.put("user_storage_id", rs.getString("user_storage_id"));
				map.put("user_order", rs.getString("user_order"));
				map.put("user_discount", rs.getString("user_discount"));
				list.add(map);
			}else{
				list=null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}
}
