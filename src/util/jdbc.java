package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class jdbc {
	private static String url = "jdbc:mysql://localhost:3306/trip?useUnicode=true&characterEncoding=UTF-8";
	private static String user = "root";
	private static String password= "ffffff";
	private jdbc() {
	}

	static {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);

		}

	}

	public static Connection getcon() throws SQLException {

		return (DriverManager.getConnection(url,user,password));

	}

	public static void free(ResultSet rs, Statement st, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (st != null)
					try {
						st.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (con != null)
							try {
								con.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					}

			}

		}

	}
	
	
	
	/**
	 * ��ݿ��������
	 * @author cxm
	 *
	 */
	public static int write(String sql)
	{
	    Connection con=null;
	    Statement sm=null;
	    ResultSet rs=null;
	    int rows=0;
	    try {
	        con=jdbc.getcon();
			   sm= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			   System.out.println(sql);
			   rows=sm.executeUpdate(sql);
			   System.out.println(rows+"rows effect.");		   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally
	    {
	 	   jdbc.free(rs,sm,con);
	    }
	    return rows;
	}
	
	
	public static List<Map<String, Object>> readDB(String sql, Object[] params) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// map<>�string �Ǽ��������object��ֵ���Ǽ��ϣ�һ��map�õ�һ�м�¼

		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);

			// sql������
			if (params!= null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();//һ����ü���
			String[] columName=new String[count];
			for (int i = 0; i < count; i++) {
			    columName[i]=rsmd.getColumnName(i+1);//�������
			}
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
			   for(int i=0;i< count;i++){
				map.put( columName[i],rs.getObject(i+1));
			   }
			   list.add(map);
				
			   }
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return list;
	}
	
	public static int writeDB(String sql, Object[] params) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int row = 0;
		try {
			con = jdbc.getcon();
			ps = con.prepareStatement(sql);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			row = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			jdbc.free(rs, ps, con);
		}
		return row;
	}	
	
}
