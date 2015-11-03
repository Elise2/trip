package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import util.jdbc;
import dao.CityDao;
import entity.City;

/**
 * Servlet implementation class CityServlet
 */
@WebServlet("/CityServlet")
public class CityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CityServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.setCharacterEncoding("gb2312");

		String a = request.getParameter("action");
		try {
			if (a.equals("seek")) {
				this.seekProvince(request, response);
			} else if ("select".equals(a)) {
				this.selectCity(request, response);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void selectCity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String cityName = request.getParameter("cityName");
		
		CityDao dao = new CityDao();
		//cityName = new String(cityName.getBytes(), "gb2312");
		// cityName = URLEncoder.encode(cityName, "gb2312");
		List<Map<String, Object>> citys = dao.selectCity(cityName);
		if (citys == null) {
			out.print("error");
		} else {
			City tm = new City();
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < citys.size(); i++) {
				Map<String, Object> mapPlace = citys.get(i);
				try {
					stringer.object();
					Iterator it = mapPlace.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						stringer.key((String) key).value(mapPlace.get(key));
					}
					stringer.endObject();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			stringer.endArray();

			out.print(stringer.toString());
		}
		out.flush();
		out.close();

	}

	public void seekProvince(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int category = Integer.parseInt(request.getParameter("category"));
		CityDao dao = new CityDao();
		List<Map<String, Object>> citys = dao.seekProvince(category);
		if (citys == null) {
			out.print("error");
		} else {
			City tm = new City();
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < citys.size(); i++) {
				Map<String, Object> mapPlace = citys.get(i);
				try {
					stringer.object();
					Iterator it = mapPlace.keySet().iterator();
					while (it.hasNext()) {
						Object key = it.next();
						stringer.key((String) key).value(mapPlace.get(key));
					}
					stringer.endObject();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			stringer.endArray();

			out.print(stringer.toString());
		}
		out.flush();
		out.close();

	}

}
