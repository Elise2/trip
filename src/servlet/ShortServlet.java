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

import sun.misc.BASE64Decoder;
import util.WebUtil;
import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;
import dao.CityDao;
import dao.ShortDao;
import entity.City;

public class ShortServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ShortServlet() {
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
			if (a.equals("select")) {
				this.selectShort(request, response);
			} else if ("add".equals(a)) {
				this.addShort(request, response);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addShort(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		ShortDao dao = new ShortDao();

		int useId = Integer.parseInt(request.getParameter("userId"));
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = sDateFormat.format(new java.util.Date());
		String location = request.getParameter("location");
		String content = request.getParameter("content");
		sDateFormat = new SimpleDateFormat("yyMMddss");
		String headName = sDateFormat.format(new java.util.Date());
		String uhead = request.getParameter("img"), path = "";
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
						"/img/short/" + headName + ".png");// 新生成的图片
				OutputStream os = new FileOutputStream(imgFilePath);
				os.write(b);
				os.flush();
				os.close();

				path = "img/short/" + headName
						+ ".png";
			}
			// 链接更新到数据库
			if (dao.addShort(useId, path, time, location, content)) {
				response.getWriter().print(path);
			} else {
				response.getWriter().print("");
			}
		}

	}

	public void selectShort(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ShortDao dao = new ShortDao();
		List<Map<String, Object>> citys = dao.selectAll();
		if (citys == null) {
			out.print("[{error}]");
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
