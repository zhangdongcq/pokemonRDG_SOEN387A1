

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
 * Servlet implementation class AcceptChallenge
 */
@WebServlet("/AcceptChallenge")
public class AcceptChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptChallenge() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		Map<String, String> message = new HashMap<>();
		if (request.getParameter("challenge") == null) {
			message.put("message", "Please input a challenge player id!");
			message.put("status", "fail");
		} else if (request.getSession(true).getAttribute("userid") == null) {
			message.put("message", "Please login first or your session is expired!");
			message.put("status", "fail");
		} else {
			long challengeId = Long.parseLong(request.getParameter("challenge"));
			long currentPlayerId = (long) request.getSession(true).getAttribute("userid");
			try {
				ChallengeRDG ch = new ChallengeRDG().find(challengeId);
				if(ch==null) {
					message.put("message", "The challengee does not exist!");
					message.put("status", "fail");
				}else if(ch.getChallengee()!=currentPlayerId) {
					message.put("message", "The challengee is not you, you cannot refuse this challenge!");
					message.put("status", "fail");
				}else if(ch.getStatus()!=0) {
					message.put("message", "The challenge has already been refused, withdrawl or accepted before!");
					message.put("status", "fail");
				}else {
					ch.setStatus(3);
					ch.updateStatus();
					/**
					 * Add a game record in game table and return new game id
					 */
					long challengerId = ch.getChallenger();
					long challengeeId = ch.getChallengee();
					GameRDG g = new GameRDG();
					g.setChallenger(challengerId);
					g.setChallengee(challengeeId);
					long gameId = g.insertGetId();
					/**
					 * Update the game id for all cards under both names;
					 */
					new CardRDG().updateGameIdForBothPlayers(gameId, challengerId, challengeeId);
					/**
					 * Update the game id for the new challenge
					 */
					ch.setGame(gameId);
					ch.updateGameId();
					
					message.put("message", "You have accpeted a challenge!");
					message.put("status", "success");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pw.println(JSON.toJSONString(message));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
