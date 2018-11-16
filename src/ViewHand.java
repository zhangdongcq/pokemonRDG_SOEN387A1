
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
 * Servlet implementation class ViewDeck
 */
@WebServlet("/ViewHand")
public class ViewHand extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewHand() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Pass1
		PrintWriter pw = response.getWriter();
		Map<String, String> message = new HashMap<>();
		if (request.getParameter("game") == null) {
			message.put("message", "Please input a game number.");
			message.put("status", "fail");
			pw.println(JSON.toJSONString(message));
		} else if (request.getSession(true).getAttribute("userid") == null) {
			message.put("message", "You have not logged in or your session is expired.");
			message.put("status", "fail");
			pw.println(JSON.toJSONString(message));
		} else {
			long currentPlayerId = (long) request.getSession(true).getAttribute("userid");
			Long currentGameId = Long.parseLong(request.getParameter("game"));
			CardRDG card = new CardRDG();
			card.setOwner(currentPlayerId);
			card.setGame(currentGameId);
			List<Long> cardsOnHand = null;
			try {
				cardsOnHand = card.findCardsByPositionHandJ();
				if (currentGameId != new GameRDG().findGameByPlayerId(currentPlayerId)) {
					message.put("message", "You are not in this game, no record for your hand.");
					message.put("status", "fail");
					pw.println(JSON.toJSONString(message));
				} else 
//					if (cardsOnHand == null) {
//					Map<String, List<Long>> output = new HashMap<>();
//					output.put("hand", cardsOnHand);
//					pw.println(JSON.toJSONString(output));
//					
//					
////					message.put("message", "Nothing in your hand, please draw card first!");
////					message.put("status", "fail");
//					
//				} else 
				{
					Map<String, List<Long>> output = new HashMap<>();
					output.put("hand", cardsOnHand);
					message.put("message", "There are some cards in your hand now!");
					message.put("status", "success");
					pw.println(JSON.toJSONString(output));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
