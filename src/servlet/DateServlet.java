package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;
import dao.CityDao;
import dao.DateDao;
import dao.HunterDao;
import dao.NoteDao;
import dao.SceneryDao;
import entity.City;
import entity.Date;
import entity.Schedule;
import entity.Trace;

public class DateServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DateServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String a = request.getParameter("action");
		try {
			if (a.equals("select")) {
				this.selectOld(request, response);
			} else if (a.equals("selectdetail")) {
				this.selectDetail(request, response);
			} else if (a.equals("selectdetailscenery")) {
				this.selectdetailscenery(request, response);
			} else if (a.equals("selectdetailhotel")) {
				this.selectdetailhotel(request, response);
			} else if (a.equals("selectdetailnote")) {
				this.selectdetailnote(request, response);
			} else if (a.equals("selectallscenery")) {
				this.selectallscenery(request, response);
			} else if (a.equals("adddetailscenery")) {
				this.adddetail(request, response);
			} else if (a.equals("adddetailnote")) {
				this.adddetailnote(request, response);
			} else if (a.equals("addDate")) {
				this.addDate(request, response);
			} else if (a.equals("selectNew")) {
				this.selectNew(request, response);
			} else if (a.equals("deleteDate")) {
				this.deleteDate(request, response);
			} else if (a.equals("adddetailnoteNo")) {
				this.adddetailnoteNo(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void adddetailnoteNo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

		NoteDao dao = new NoteDao();
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String notes_title = request.getParameter("notes_title");
		String notes_go_time = request.getParameter("notes_go_time");
		String notes_cost = request.getParameter("notes_cost");
		String notes_days = request.getParameter("notes_days");
		String notes_type = request.getParameter("notes_type");
		String notes_travel_type = request.getParameter("notes_travel_type");

		String details = request.getParameter("details");
		Gson gson = new Gson();
		List<Trace> traces = new ArrayList<Trace>();
		traces = gson.fromJson(details, new TypeToken<ArrayList<Trace>>() {
		}.getType());
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String notes_publish_time = sDateFormat.format(new java.util.Date());

		for (int j = 0; j < traces.size(); j++) {
			sDateFormat = new SimpleDateFormat("yyMMddss");
			String headName = sDateFormat.format(new java.util.Date()) + j;
			String uhead = traces.get(j).getImg(), path = "";
			uhead = uhead.replaceAll(" ", "+");
			if (uhead != null) {
				String temp = uhead.substring(0, 4);
				if (temp.equals("http")) {
					path = uhead;
				} else {
					// 将base64转化为图片并存在指定路径下
					BASE64Decoder decoder = new BASE64Decoder();
					byte[] b = decoder.decodeBuffer(uhead);
					for (int i = 0; i < b.length; ++i) {
						if (b[i] < 0) {
							// 调整异常数据
							b[i] += 256;
						}
					}

					String imgFilePath = super.getServletContext().getRealPath(
							"/img/note/" + headName + ".png");// 新生成的图片
					OutputStream os = new FileOutputStream(imgFilePath);
					os.write(b);
					os.flush();
					os.close();
					path = "img/note/" + headName + ".png";
					traces.get(j).setImg(path);
				}
			}
		}
		sDateFormat = new SimpleDateFormat("yyMMddss");
		String headName = sDateFormat.format(new java.util.Date());
		String uhead = request.getParameter("top_img"), path = "";
		uhead = uhead.replaceAll(" ", "+");
		if (uhead != null) {
			String temps = uhead.substring(0, 4);
			if (temps.equals("http")) {
				path = uhead;
			} else {
				// 将base64转化为图片并存在指定路径下
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] b = decoder.decodeBuffer(uhead);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						// 调整异常数据
						b[i] += 256;
					}
				}

				String imgFilePath = super.getServletContext().getRealPath(
						"/img/note/" + headName + ".png");// 新生成的图片
				OutputStream os = new FileOutputStream(imgFilePath);
				os.write(b);
				os.flush();
				os.close();

				path = "img/note/" + headName + ".png";
			}
			// 链接更新到数据库
			if (dao.addPlayNoteNoId(city_id, path, user_id, notes_title,
					notes_go_time, notes_cost, notes_days, notes_type,
					notes_publish_time, notes_travel_type, traces)) {
				response.getWriter().print(path);
			} else {
				response.getWriter().print("");
			}
		}

	}

	public void deleteDate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int datedetailid = Integer.parseInt(request
				.getParameter("datedetailid"));
		int type = Integer.parseInt(request.getParameter("type"));
		int type_id = Integer.parseInt(request.getParameter("type_id"));
		DateDao dao = new DateDao();
		boolean flag = dao.deleteDateDetail(datedetailid, type, type_id);
		if (!flag) {
			out.print("{info:\"error\"}");
		} else {
			out.print("{info:\"suc\"}");
		}
		out.flush();
		out.close();
	}

	public void selectdetailhotel(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		DateDao dao = new DateDao();
		List<Map<String, Object>> dates = dao.selectHotel(Integer.parseInt(id));
		if (dates == null) {
			out.print("{info:\"error\"}");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < dates.size(); i++) {
				Map<String, Object> mapPlace = dates.get(i);
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

	public void addDate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		DateDao dao = new DateDao();
		String go_date = request.getParameter("go_date");
		String arrive_date = request.getParameter("arrive_date");
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		String city_name = request.getParameter("city_name");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		PrintWriter out = response.getWriter();

		String details = request.getParameter("details");
		Gson gson = new Gson();
		List<Schedule> traces = new ArrayList<Schedule>();
		traces = gson.fromJson(details, new TypeToken<ArrayList<Schedule>>() {
		}.getType());
		// 链接更新到数据库
		if (dao.insertDate(city_id, city_name, user_id, go_date, arrive_date,
				traces)) {
			out.print("{info:\"suc\"}");
		} else {
			out.print("{info:\"error\"}");
		}

	}

	public void selectNew(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int user_id = Integer.parseInt(request.getParameter("user_id"));

		DateDao dao = new DateDao();
		List<Map<String, Object>> dates = dao.selectNews(user_id);
		if (dates == null) {
			out.print("{info:\"error\"}");
		} else {
			City tm = new City();
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < dates.size(); i++) {
				Map<String, Object> mapPlace = dates.get(i);
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

	public void adddetailnote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

		NoteDao dao = new NoteDao();
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String notes_title = request.getParameter("notes_title");
		String notes_go_time = request.getParameter("notes_go_time");
		String notes_cost = request.getParameter("notes_cost");
		String notes_days = request.getParameter("notes_days");
		String notes_type = request.getParameter("notes_type");
		String notes_travel_type = request.getParameter("notes_travel_type");

		int datedetailid = Integer.parseInt(request
				.getParameter("datedetailid"));

		String details = request.getParameter("details");
		Gson gson = new Gson();
		List<Trace> traces = new ArrayList<Trace>();
		traces = gson.fromJson(details, new TypeToken<ArrayList<Trace>>() {
		}.getType());
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String notes_publish_time = sDateFormat.format(new java.util.Date());

		for (int j = 0; j < traces.size(); j++) {
			sDateFormat = new SimpleDateFormat("yyMMddss");
			String headName = sDateFormat.format(new java.util.Date()) + j;
			String uhead = traces.get(j).getImg(), path = "";
			uhead = uhead.replaceAll(" ", "+");
			if (uhead != null) {
				String temp = uhead.substring(0, 4);
				if (temp.equals("http")) {
					path = uhead;
				} else {
					// 将base64转化为图片并存在指定路径下
					BASE64Decoder decoder = new BASE64Decoder();
					byte[] b = decoder.decodeBuffer(uhead);
					for (int i = 0; i < b.length; ++i) {
						if (b[i] < 0) {
							// 调整异常数据
							b[i] += 256;
						}
					}

					String imgFilePath = super.getServletContext().getRealPath(
							"/img/note/" + headName + ".png");// 新生成的图片
					OutputStream os = new FileOutputStream(imgFilePath);
					os.write(b);
					os.flush();
					os.close();
					path = "img/note/" + headName + ".png";
					traces.get(j).setImg(path);
				}
			}
		}
		sDateFormat = new SimpleDateFormat("yyMMddss");
		String headName = sDateFormat.format(new java.util.Date());
		String uhead = request.getParameter("top_img"), path = "";
		uhead = uhead.replaceAll(" ", "+");
		if (uhead != null) {
			String temps = uhead.substring(0, 4);
			if (temps.equals("http")) {
				path = uhead;
			} else {
				// 将base64转化为图片并存在指定路径下
				BASE64Decoder decoder = new BASE64Decoder();
				byte[] b = decoder.decodeBuffer(uhead);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						// 调整异常数据
						b[i] += 256;
					}
				}

				String imgFilePath = super.getServletContext().getRealPath(
						"/img/note/" + headName + ".png");// 新生成的图片
				OutputStream os = new FileOutputStream(imgFilePath);
				os.write(b);
				os.flush();
				os.close();

				path = "img/note/" + headName + ".png";
			}
			// 链接更新到数据库
			if (dao.addPlayNotes(city_id, path, user_id, notes_title,
					notes_go_time, notes_cost, notes_days, notes_type,
					notes_publish_time, notes_travel_type, traces, datedetailid)) {
				response.getWriter().print(path);
			} else {
				response.getWriter().print("");
			}
		}

	}

	public void selectdetailnote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		DateDao dao = new DateDao();
		List<Map<String, Object>> dates = dao.selectNotes(Integer.parseInt(id));
		if (dates == null) {
			out.print("{info:\"error\"}");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < dates.size(); i++) {
				Map<String, Object> mapPlace = dates.get(i);
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

	public void adddetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int datedetailid = Integer.parseInt(request
				.getParameter("datedetailid"));
		int type = Integer.parseInt(request.getParameter("type"));
		int type_id = Integer.parseInt(request.getParameter("type_id"));
		DateDao dao = new DateDao();
		boolean flag = dao.addDateDetail(datedetailid, type, type_id);
		if (!flag) {
			out.print("{info:\"error\"}");
		} else {
			out.print("{info:\"suc\"}");
		}
		out.flush();
		out.close();
	}

	public void selectallscenery(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("city");
		SceneryDao dao = new SceneryDao();
		List<Map<String, Object>> dates = dao.selecteSceneryByCity(Integer
				.parseInt(id));
		if (dates == null) {
			out.print("{info:\"error\"}");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < dates.size(); i++) {
				Map<String, Object> mapPlace = dates.get(i);
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

	public void selectdetailscenery(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		DateDao dao = new DateDao();
		List<Map<String, Object>> dates = dao.selectScenert(Integer
				.parseInt(id));
		if (dates == null) {
			out.print("{info:\"error\"}");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < dates.size(); i++) {
				Map<String, Object> mapPlace = dates.get(i);
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

	public void selectDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String string = request.getParameter("date");
		Gson gson = new Gson();
		List<Date> ids = new ArrayList<Date>();
		ids = gson.fromJson(string, new TypeToken<ArrayList<Date>>() {
		}.getType());

		DateDao dao = new DateDao();
		List<Map<String, Object>> dates = dao.selectCount(ids);
		if (dates == null) {
			out.print("{info:\"error\"}");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < dates.size(); i++) {
				Map<String, Object> mapPlace = dates.get(i);
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

	public void selectOld(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DateDao dao = new DateDao();
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		List<Map<String, Object>> dates = dao.selectOlds(user_id);
		if (dates == null) {
			out.print("{info:\"error\"}");
		} else {
			City tm = new City();
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < dates.size(); i++) {
				Map<String, Object> mapPlace = dates.get(i);
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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
