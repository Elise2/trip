package servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import sun.misc.BASE64Decoder;
import dao.DateDao;
import dao.DiscussDao;
import dao.OrderDao;
import dao.ShortDao;
import entity.City;

public class OrderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public OrderServlet() {
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
				this.selectOrder(request, response);
			} else if ("add".equals(a)) {
				this.addOrder(request, response);
			} else if ("selectOrderHunter".equals(a)) {
				this.selectOrderHunter(request, response);
			} else if ("update".equals(a)) {
				this.updateOrder(request, response);
			} else if ("cancel".equals(a)) {
				this.cancelOrder(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int order_id = Integer.parseInt(request.getParameter("order_id"));
		OrderDao dao = new OrderDao();
		boolean flag = dao.cancelOrder(order_id);
		if (flag) {
			out.print("[{info:\"suc\"}]");
		} else {
			out.print("[{info:\"error\"}]");
		}
		out.flush();
		out.close();
	}

	public void updateOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int order_id = Integer.parseInt(request.getParameter("order_id"));
		OrderDao dao = new OrderDao();
		boolean flag = dao.updateOrder(order_id);
		if (flag) {
			out.print("[{info:\"suc\"}]");
		} else {
			out.print("[{info:\"error\"}]");
		}
		out.flush();
		out.close();
	}

	public void selectOrderHunter(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		OrderDao dao = new OrderDao();
		int is_receive = Integer.parseInt(request.getParameter("is_receive"));
		int hunter_id = Integer.parseInt(request.getParameter("hunter_id"));
		List<Map<String, Object>> dates = dao.selectOrderByHunter(hunter_id,
				is_receive);
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

	public void selectOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		OrderDao dao = new OrderDao();
		int top = Integer.parseInt(request.getParameter("top"));
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		List<Map<String, Object>> dates = dao.selectOrder(top, user_id);
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

	public void addOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int user_id = Integer.parseInt(request.getParameter("userId"));
		String name = request.getParameter("name");
		int num = Integer.parseInt(request.getParameter("num"));
		int price = Integer.parseInt(request.getParameter("price"));
		int isReceiver = Integer.parseInt(request.getParameter("isReceiver"));

		String img = request.getParameter("img");
		String cName = request.getParameter("cName");
		String Ctel = request.getParameter("Ctel");
		int top = Integer.parseInt(request.getParameter("top"));
		String time = request.getParameter("time");
		int sale_id = Integer.parseInt(request.getParameter("sale_id"));
		OrderDao dao = new OrderDao();
		boolean flag = dao.addOrder(name, time, num, price, img, user_id,
				cName, Ctel, top, sale_id, isReceiver);
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
