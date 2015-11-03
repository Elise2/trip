package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;
import sun.misc.BASE64Decoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.HunterDao;
import entity.Trace;

public class HunterServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public HunterServlet() {
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

		String action = request.getParameter("action");
		if ("add".equals(action)) {
			try {
				this.addHunter(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("addmain".equals(action)) {
			try {
				this.addHunterMain(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (action.equals("tophunter")) {
			this.selectHunterTop(request, response);
		} else if (action.equals("detailhunter")) {
			this.selectHunterDetail(request, response);
		} else if (action.equals("login")) {
			this.login(request, response);
		} else if ("selectHunterByHunter".equals(action)) {
			this.selectHunterByHunter(request, response);
		} else if ("addNo".equals(action)) {
			try {
				this.addNoHunter(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void addNoHunter(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		HunterDao dao = new HunterDao();
		PrintWriter out = response.getWriter();

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddss");
		String headName = sDateFormat.format(new java.util.Date());
		String indetify = request.getParameter("indetify");
		String pwd = request.getParameter("pwd");

		String tel = request.getParameter("tel");
		String uhead = request.getParameter("img");

		String sexs = request.getParameter("sex");
		int sex = 0;
		if ("女".equals(sexs)) {
			sex = 1;
		}

		// 链接更新到数据库
		List<Map<String, Object>> hunters = dao.updateHunter(name, uhead, sex,
				content, indetify, tel, id, pwd);

		if (hunters == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < hunters.size(); i++) {
				Map<String, Object> mapPlace = hunters.get(i);
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

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String hunter_name = request.getParameter("username");
		String hunter_pwd = request.getParameter("userpwd");
		HunterDao hunterdetals = new HunterDao();
		List<Map<String, Object>> hunters = hunterdetals.login(hunter_name,
				hunter_pwd);

		if (hunters == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < hunters.size(); i++) {
				Map<String, Object> mapPlace = hunters.get(i);
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

	private void selectHunterByHunter(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("hunter_id"));
		HunterDao hunterDao = new HunterDao();
		List<Map<String, Object>> hunters = hunterDao.selectHunterByHunter(id);
		if (hunters == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < hunters.size(); i++) {
				Map<String, Object> mapPlace = hunters.get(i);
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

	private void selectHunterTop(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		HunterDao hunterDao = new HunterDao();
		List<Map<String, Object>> hunters = hunterDao.selectHunter(city_id);
		if (hunters == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < hunters.size(); i++) {
				Map<String, Object> mapPlace = hunters.get(i);
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

	private void selectHunterDetail(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int huntermain_id = Integer.parseInt(request
				.getParameter("huntermain_id"));
		HunterDao hunterdetals = new HunterDao();
		List<Map<String, Object>> hunters = hunterdetals
				.selectHunterDetail(huntermain_id);
		if (hunters == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < hunters.size(); i++) {
				Map<String, Object> mapPlace = hunters.get(i);
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

	public void addHunterMain(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		HunterDao dao = new HunterDao();
		int hunter_id = Integer.parseInt(request.getParameter("hunter_id"));
		int price = Integer.parseInt(request.getParameter("price"));
		String des = request.getParameter("des");
		String location = request.getParameter("location");
		String lable = request.getParameter("lable");
		String details = request.getParameter("details");
		String hunter_time = request.getParameter("reserveTime");
		Gson gson = new Gson();
		List<Trace> traces = new ArrayList<Trace>();
		traces = gson.fromJson(details, new TypeToken<ArrayList<Trace>>() {
		}.getType());
		// System.out.print(traces.get(0).getImg());

		for (int j = 0; j < traces.size(); j++) {
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddss");
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
							"/img/hunterdetail/" + headName + ".png");// 新生成的图片
					OutputStream os = new FileOutputStream(imgFilePath);
					os.write(b);
					os.flush();
					os.close();
					path = "img/hunterdetail/" + headName + ".png";
					traces.get(j).setImg(path);
				}
			}
		}
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddss");
		String headName = sDateFormat.format(new java.util.Date());
		String uhead = request.getParameter("img"), path = "";
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
						"/img/huntermain/" + headName + ".png");// 新生成的图片
				OutputStream os = new FileOutputStream(imgFilePath);
				os.write(b);
				os.flush();
				os.close();

				path = "img/huntermain/" + headName + ".png";
			}
			// 链接更新到数据库
			if (dao.addHunterMain(hunter_id, path, price, des, location, lable,
					traces, hunter_time)) {
				response.getWriter().print(path);
			} else {
				response.getWriter().print("");
			}
		}

	}

	public void addHunter(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		HunterDao dao = new HunterDao();
		PrintWriter out = response.getWriter();

		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddss");
		String headName = sDateFormat.format(new java.util.Date());
		String indetify = request.getParameter("indetify");
		String pwd = request.getParameter("pwd");

		String tel = request.getParameter("tel");
		String uhead = request.getParameter("img"), path = "";
		uhead = uhead.replaceAll(" ", "+");
		String sexs = request.getParameter("sex");
		int sex = 0;
		if ("女".equals(sexs)) {
			sex = 1;
		}
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
						"/img/hunter/" + headName + ".png");// 新生成的图片
				OutputStream os = new FileOutputStream(imgFilePath);
				os.write(b);
				os.flush();
				os.close();

				path = "img/hunter/" + headName + ".png";
			}
			// 链接更新到数据库
			List<Map<String, Object>> hunters = dao.updateHunter(name, path,
					sex, content, indetify, tel, id, pwd);

			if (hunters == null) {
				out.print("[{info:\"error\"}]");
			} else {
				JSONStringer stringer = new JSONStringer();
				stringer.array();
				for (int i = 0; i < hunters.size(); i++) {
					Map<String, Object> mapPlace = hunters.get(i);
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
