
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class ListPlayersPC
 */
@WebServlet("/ListPlayers")
public class ListPlayers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListPlayers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		long currentGameId = Long.parseLong(request.getParameter("game"));
		PrintWriter pw = response.getWriter();
		Map<String, String> m = new HashMap<>();
		if (request.getSession(true).getAttribute("userid") == null) {
			m.put("message", "Please login to list all players.");
			m.put("status", "fail");
			pw.println(JSON.toJSONString(m));
		} else {
			try {
				long currentPlayerId = (long) request.getSession(true).getAttribute("userid");
				UserRDG spy = new UserRDG();
				List<Map<String, Object>> list = new ArrayList<>();
				list = spy.findAllJ();
				if (list == null) {
					m.put("message", "No players yet.");
					m.put("status", "fail");
					pw.println(JSON.toJSONString(m));
				} else {
					Map<String, List<Map<String, Object>>> output = new HashMap<>();
					output.put("players", list);
					pw.println(JSON.toJSONString(output));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
