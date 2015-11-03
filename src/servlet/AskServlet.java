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
import dao.AskDao;
import dao.SceneryDao;

public class AskServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AskServlet() {
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
				this.selectAsk(request, response);
			} else if ("add".equals(a)) {
				this.addAsk(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addAsk(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String city_id = request.getParameter("city_id");
		int ask_user_id = Integer.parseInt(request.getParameter("ask_user_id"));
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String ask_time = sDateFormat.format(new java.util.Date());
		String ask_problem_content = request
				.getParameter("ask_problem_content");
		String ask_problem_title = request.getParameter("ask_problem_title");

		AskDao askDao = new AskDao();
		boolean flag = askDao.addAsk(city_id, ask_problem_content, ask_user_id,
				ask_time, ask_problem_title);
		if (flag) {
			out.print("{info:\"suc\"}");
		} else {
			out.print("{info:\"error\"}");
		}
		out.flush();
		out.close();
	}

	public void selectAsk(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		AskDao askDao = new AskDao();
		List<Map<String, Object>> asks = askDao.selectAskByCityId(city_id);
		if (asks == null) {
			out.print("error");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < asks.size(); i++) {
				Map<String, Object> mapPlace = asks.get(i);
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
