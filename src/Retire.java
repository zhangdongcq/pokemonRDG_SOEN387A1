
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class Retire
 */
@WebServlet("/Retire")
public class Retire extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Retire() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long currentPlayerId = (long) request.getSession(true).getAttribute("userid");
		Long gameId = Long.parseLong(request.getParameter("game"));
		PrintWriter pw = response.getWriter();
		Map<String, String> message = new HashMap<>();
		try {
			if (currentPlayerId == null) {
				message.put("message", "You have not logged in or your session is expired, action failed.");
				message.put("status", "fail");
			} else {
				long[] playersInGame = new GameRDG().findPlayers(gameId);
				if (playersInGame[0] != currentPlayerId && playersInGame[1] != currentPlayerId) {
					message.put("message", "Your are not in the game, action failed!.");
					message.put("status", "fail");
				} else {
					UserRDG u = new UserRDG().find(currentPlayerId);
					if (u.getStatus().equals("retired")) {
						message.put("message", "Your are not in the game, action failed!.");
						message.put("status", "fail");
					} else {
						u.setStatus("retired");
						u.updateStatus();
						message.put("message", "You have successfully retired from the game.");
						message.put("status", "success");
					}
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println(JSON.toJSONString(message));
//		request.setAttribute("message", "You are retired now!");
//		request.getRequestDispatcher("success.jsp").forward(request, response);
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
