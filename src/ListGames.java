
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class ListGames
 */
@WebServlet("/ListGames")
public class ListGames extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListGames() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Pass1
		GameRDG g = new GameRDG();
		List<Map<String, Object>> content = new ArrayList<>();
		Map<String, String> message = new HashMap<>();
		PrintWriter pw = response.getWriter();
		try {
			if (request.getSession(true).getAttribute("userid") == null) {
				message.put("message", "You have not logged in or your session is expired.");
				message.put("status", "fail");
				pw.println(JSON.toJSONString(message));
			}else {
				content = g.findAllGamesJ();
				if (content == null) {
					message.put("message", "No game found.");
					message.put("status", "fail");
					pw.println(JSON.toJSONString(message));
				} else {
					Map<String, List<Map<String, Object>>> outer = new HashMap<>();
					outer.put("games", content);
					pw.println(JSON.toJSONString(outer));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
