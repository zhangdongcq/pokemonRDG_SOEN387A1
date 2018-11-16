
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class UploadDeck
 */
@WebServlet("/UploadDeck")
public class UploadDeck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadDeck() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// pass1

//		String deck = "e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"p \"Charizard\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"p \"Charizard\"\n" +
//				"p \"Meowth\"\n" +
//				"e \"Fire\"\n" +
//				"t \"Misty\"\n" +
//				"t \"Misty\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"p \"Charizard\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"p \"Charizard\"\n" +
//				"p \"Meowth\"\n" +
//				"e \"Fire\"\n" +
//				"t \"Misty\"\n" +
//				"t \"Misty\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"p \"Charizard\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"p \"Charizard\"\n" +
//				"p \"Meowth\"\n" +
//				"e \"Fire\"\n" +
//				"t \"Misty\"\n" +
//				"t \"Misty\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"e \"Fire\"\n" +
//				"p \"Charizard\"\n" +
//				"e \"Fire\"\n";
		PrintWriter pw = response.getWriter();
		Map<String, String> m = new HashMap<>();
		String deck = (String) request.getParameter("deck");
		if (deck == null || deck.length()==0) {
			m.put("message", "Please input a deck with card list.");
			m.put("status", "fail");
		} else {
			if (request.getSession().getAttribute("userid") == null) {
				m.put("message", "You have not logged in or your session is expired.");
				m.put("status", "fail");
				pw.println(JSON.toJSONString(m));
			} else {
				long currentPlayerId = (long)request.getSession(true).getAttribute("userid");
				deck = request.getParameter("deck");
				int cardsCount = deck.split("\n").length;
				if (cardsCount > 40) {
					m.put("message", "The cards are more than 40 pieces, should be exact 40.");
					m.put("status", "fail");
					pw.println(JSON.toJSONString(m));
				} else if (cardsCount < 40) {
					m.put("message", "The cards are less than 40 pieces, should be exact 40.");
					m.put("status", "fail");
					pw.println(JSON.toJSONString(m));
				} else {
					String[] cards = deck.split("\n");
					for (int i = 0; i < cards.length; i++) {
						String line = cards[i];
						String type = line.substring(0, 1);
						String name = line.substring(3, line.length() - 1);
						try {
							CardRDG c = new CardRDG();
							c.setPrivate_card_id(i+1);
							c.setType(type);
							c.setName(name);
							c.setOwner(currentPlayerId);
							c.insert();
//							pw.println(type + ":" + name + ":" + currentPlayerId);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					/**
					 * Insert new deck, hand, discard records
					 */
					DeckRDG newDeck = new DeckRDG();
					newDeck.setOwner(currentPlayerId);
					HandRDG hand = new HandRDG();
					hand.setOwner(currentPlayerId);
					DiscardRDG discard = new DiscardRDG();
					discard.setOwner(currentPlayerId);
					try {
						newDeck.insert();
						hand.insert();
						discard.insert();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.put("message", "40 cards are uploaded successfully under your id." + currentPlayerId);
					m.put("status", "success");	
					pw.println(JSON.toJSONString(m));
				}
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
