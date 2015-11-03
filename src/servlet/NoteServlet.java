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
import dao.NoteDao;

public class NoteServlet extends HttpServlet {
	public NoteServlet() {
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
		String a = request.getParameter("action");
		try {
			if (a.equals("all")) {
				this.selectAll(request, response);

			} else if (a.equals("some")) {
				this.select(request, response);
			} else if (a.equals("top")) {
				this.seletTop(request, response);
			} else if (a.equals("cityTop")) {
				this.selectCityNote(request, response);
				// 新增我的游记
			} else if (a.equals("mynote")) {
				this.selectMyNote(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void select(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		NoteDao noteDao = new NoteDao();
		List<Map<String, Object>> notes = noteDao.selectNotes(city_id);
		if (notes == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < notes.size(); i++) {
				Map<String, Object> mapPlace = notes.get(i);
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

	public void selectAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int note_id = Integer.parseInt(request.getParameter("note_id"));
		NoteDao noteDao = new NoteDao();
		List<Map<String, Object>> notes = noteDao.selectAllNotes(note_id);
		if (notes == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < notes.size(); i++) {
				Map<String, Object> mapPlace = notes.get(i);
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

	public void seletTop(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		NoteDao noteDao = new NoteDao();
		List<Map<String, Object>> notes = noteDao.seletTop();
		if (notes == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < notes.size(); i++) {
				Map<String, Object> mapPlace = notes.get(i);
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

	public void selectCityNote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int city_id = Integer.parseInt(request.getParameter("city_id"));
		NoteDao noteDao = new NoteDao();
		List<Map<String, Object>> notes = noteDao.seletTop(city_id);
		if (notes == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < notes.size(); i++) {
				Map<String, Object> mapPlace = notes.get(i);
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

	// 新增方法
	public void selectMyNote(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		NoteDao noteDao = new NoteDao();
		List<Map<String, Object>> notes = noteDao.seletMyNotes(user_id);
		if (notes == null) {
			out.print("[{info:\"error\"}]");
		} else {
			JSONStringer stringer = new JSONStringer();
			stringer.array();
			for (int i = 0; i < notes.size(); i++) {
				Map<String, Object> mapPlace = notes.get(i);
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
