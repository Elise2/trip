package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;
import dao.CityDao;
import dao.DiscussDao;
import dao.ShortDao;
import entity.City;

public class DiscussServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public DiscussServlet() {
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

		String a = request.getParameter("action");
		try {

			if ("select".equals(a)) {
				this.selectDiscuss(request, response);
			} else if ("add".equals(a)) {
				this.addDiscuss(request, response);

			} else if ("addNote".equals(a)) {
				this.addNoteDiscuss(request, response);

			} else if ("selectNote".equals(a)) {
				this.selectNoteDiscuss(request, response);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectNoteDiscuss(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));

		DiscussDao dao = new DiscussDao();

		List<Map<String, Object>> citys = dao.selectNoteDiscuss(id);
		if (citys == null) {
			out.print("error");
		} else {
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

	public void addNoteDiscuss(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

		int useId = Integer.parseInt(request.getParameter("userId"));
		int shortComment_discuss_id = Integer.parseInt(request
				.getParameter("id"));
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = sDateFormat.format(new java.util.Date());
		String content = request.getParameter("content");

		DiscussDao dao = new DiscussDao();

		boolean flag = dao.addNoteDiscuss(useId, time, shortComment_discuss_id,
				content);
		if (flag) {
			out.print("{info:\"suc\"}");
		} else {
			out.print("{info:\"error\"}");
		}
		out.flush();
		out.close();
	}

	public void selectDiscuss(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("shortCommentID"));

		DiscussDao dao = new DiscussDao();

		List<Map<String, Object>> citys = dao.selectDiscuss(id);
		if (citys == null) {
			out.print("error");
		} else {
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

	public void addDiscuss(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");

		int useId = Integer.parseInt(request.getParameter("userId"));
		int shortComment_discuss_id = Integer.parseInt(request
				.getParameter("id"));
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = sDateFormat.format(new java.util.Date());
		String content = request.getParameter("content");

		DiscussDao dao = new DiscussDao();

		boolean flag = dao.addDiscuss(useId, time, shortComment_discuss_id,
				content);
		if (flag) {
			out.print("{info:\"suc\"}");
		} else {
			out.print("{info:\"error\"}");
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
