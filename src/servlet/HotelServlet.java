package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;
import dao.HotelDao;

public class HotelServlet extends HttpServlet {
	public HotelServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		try {
			if (action.equals("tophotel")) {
				this.selectHotelTop(request, response);
			} else if (action.equals("detailhotel")) {
				this.selectHotelDetail(request, response);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void selectHotelTop(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String city_name = request.getParameter("city_name");
		HotelDao hotelDao = new HotelDao();
		List<Map<String, Object>> hotels = hotelDao.selectHotel(city_name);
		if (hotels == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < hotels.size(); i++) {
				Map<String, Object> mapPlace = hotels.get(i);
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

	private void selectHotelDetail(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int order_id = Integer.parseInt(request.getParameter("order_id"));
		HotelDao hotelDao = new HotelDao();
		List<Map<String, Object>> hotels = hotelDao.selectHotel(order_id);
		if (hotels == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < hotels.size(); i++) {
				Map<String, Object> mapPlace = hotels.get(i);
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
