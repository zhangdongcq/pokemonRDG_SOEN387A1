
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
 * Servlet implementation class DrawCard
 */
@WebServlet("/DrawCard")
public class DrawCard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DrawCard() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		Map<String, String> remind = new HashMap<>();
		if (request.getParameter("game") == null) {
			remind.put("message", "Please input a game id!");
			remind.put("status", "fail");
			pw.println(JSON.toJSONString(remind));
		} else if (request.getSession(true).getAttribute("userid") == null) {
			remind.put("message", "Please login first or your session is expired!");
			remind.put("status", "fail");
			pw.println(JSON.toJSONString(remind));
		} else {
			Long currentPlayerId = (long) request.getSession(true).getAttribute("userid");
			long gameId = Long.parseLong(request.getParameter("game"));
			try {
				if (currentPlayerId != null) {
					long[] players = new GameRDG().findPlayers(gameId);
					if (players[0] != currentPlayerId && players[1] != currentPlayerId) {
						remind.put("message", "You are not in this game, cannot draw any card.");
						remind.put("status", "fail");
					} else {
						CardRDG card = new CardRDG();
						card = card.findTopCard(currentPlayerId, "deck");
						if (card == null) {
							remind.put("message", "No more card to draw.");
							remind.put("status", "fail");
						} else {
							card.setGame(gameId);
							card.setPosition("hand");
							card.setOwner(currentPlayerId);
							card.updatePosition();
							remind.put("message", "You successfully draw a card.");
							remind.put("status", "success");
						}
					}
				} else {
					remind.put("message", "You have not logged in or your session is expired.");
					remind.put("status", "fail");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			pw.println(JSON.toJSONString(remind));
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
