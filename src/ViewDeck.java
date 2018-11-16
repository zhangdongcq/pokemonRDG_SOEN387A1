
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
@WebServlet("/ViewDeck")
public class ViewDeck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewDeck() {
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
		if (request.getSession(true).getAttribute("userid") == null) {
			message.put("message", "You have not logged in or your session is expired.");
			message.put("status", "fail");
			pw.println(JSON.toJSONString(message));
		} else {
			long currentPlayerId = (long) request.getSession(true).getAttribute("userid");
			DeckRDG dr = new DeckRDG();
			CardRDG card = new CardRDG();
			Map<String, List<Map<String, String>>> output = new HashMap<>();
			List<Map<String, String>> cardList = new ArrayList<>();
			card.setOwner(currentPlayerId);
			try {
				cardList = card.findCardsByPositionForDeckViewJ();
				List<long[]> deckList = new ArrayList<>();
				deckList = dr.findAllDecksJ();
				if (deckList.size() == 0 || deckList == null) {
					message.put("message", "No deck record yet!");
					message.put("status", "fail");
					pw.println(JSON.toJSONString(message));
				} else if (cardList.size() == 0 || cardList == null) {
					message.put("message", "This guy might have not uploaded any cards yet!");
					message.put("status", "fail");
					pw.println(JSON.toJSONString(message));
				} else {
					output.put("cards", cardList);
					long deckId = dr.findDeckId(currentPlayerId);
					if(deckId<0) {
						message.put("message", "Current player ("+currentPlayerId+") has no deck yet!");
						message.put("status", "fail");
						pw.println(JSON.toJSONString(message));
					}else {
						Map<String, Object> content = new HashMap<>();
						content.put("id", deckId);
						content.put("cards", cardList);
						Map<String, Object> result = new HashMap<>();
						result.put("deck", content);
						pw.println(JSON.toJSONString(result));
					}
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
