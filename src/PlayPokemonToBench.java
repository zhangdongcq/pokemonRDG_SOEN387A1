

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
 * Servlet implementation class PlayPokemonToBench
 */
@WebServlet("/PlayPokemonToBench")
public class PlayPokemonToBench extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayPokemonToBench() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long currentPlayerId = (long)request.getSession(true).getAttribute("userid");
		long currentGameId = Long.parseLong(request.getParameter("game"));
		long cardToPlay = Long.parseLong(request.getParameter("card"));
		CardRDG card = new CardRDG();
		card.setOwner(currentPlayerId);
		card.setGame(currentGameId);
		card.setId(cardToPlay);
		PrintWriter pw = response.getWriter();
		Map<String, String> message = new HashMap<>();
		try {
			card = card.findById(cardToPlay);
			if(card == null) {
				message.put("message", "No such a card or not in your game.");
				message.put("status", "fail");
			}else if(currentGameId != card.getGame()){
				message.put("message", "You are not in this game, no card to draw.");
				message.put("status", "fail");
			}else if(cardToPlay > 40 || cardToPlay < 0){
				message.put("message", "Wrong card number to play.");
				message.put("status", "fail");
			}else if(!card.getPosition().equals("hand")){
				message.put("message", "The card to play is not in your HAND, it is in your "+ card.getPosition().toUpperCase());
				message.put("status", "fail");
			}else if(!card.getType().equals("p")){
				message.put("message", "The card to play is not a pokemon.");
				message.put("status", "fail");
			}else{
//				pw.println("card id is "+card.getId());
//				pw.println("card position is "+card.getPosition());
				card.setOwner(currentPlayerId);
				card.setGame(currentGameId);
				card.setPosition("bench");
				card.updatePosition();
				message.put("message", "You play a card to bench, card new position is "+card.getPosition());
				message.put("status", "success");
			}
			pw.println(JSON.toJSONString(message));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		PrintWriter pw = response.getWriter();
//		pw.println("Hand id is : "+handId);
//		for(CardRDG c : cardsOnHand) {
//			pw.println(c.getName() + ":" + c.getType());
//		}
//		
//		request.setAttribute("message", "A card has been drawn and put in hand!");
//		request.getRequestDispatcher("success.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
