package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;
import dao.AnswerDao;
import dao.AskDao;

public class AnswerServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AnswerServlet() {
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
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			try {
				this.addNum(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("down".equals(action)) {
			try {
				this.downNum(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("select".equals(action)) {
			try {
				this.selectAnswer(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addNum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		AnswerDao answerDao = new AnswerDao();
		boolean flag = answerDao.addSupport(id);
		if (flag) {
			out.print("success");
		} else {
			out.print("error");
		}
	}

	public void downNum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		AnswerDao answerDao = new AnswerDao();
		boolean flag = answerDao.downSupport(id);
		if (flag) {
			out.print("success");
		} else {
			out.print("error");
		}

	}

	public void selectAnswer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		int ask_id = Integer.parseInt(request.getParameter("ask_id"));

		AnswerDao answerDao = new AnswerDao();
		List<Map<String, Object>> asks = answerDao.selectAskByCityId(city_id,
				ask_id);
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
