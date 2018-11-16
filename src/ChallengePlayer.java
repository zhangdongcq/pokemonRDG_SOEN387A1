
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
 * Servlet implementation class ChallengePlayer
 */
@WebServlet("/ChallengePlayer")
public class ChallengePlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChallengePlayer() {
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
		Map<String, String> m = new HashMap<>();
		if (request.getParameter("player") == null) {
			m.put("message", "Please input a player id.");
			m.put("status", "fail");
			pw.println(JSON.toJSONString(m));
		} else if (request.getSession(true).getAttribute("userid") == null) {
			m.put("message", "You have not logged in or your session is expired.");
			m.put("status", "fail");
			pw.println(JSON.toJSONString(m));
		}else {
			long currentPlayerId = (long) request.getSession().getAttribute("userid");
			long challengee = Long.parseLong(request.getParameter("player"));
			try {
				if(new DeckRDG().findDeckId(currentPlayerId)==null) {
					m.put("message", "You have no deck to start the challenge.");
					m.put("status", "fail");
				}else if (currentPlayerId == challengee) {
					m.put("message", "You cannot challenge yourself!");
					m.put("status", "fail");
				} else if(challengee < 0){
					m.put("message", "Wrong challengee Id, cannot be negative id!");
					m.put("status", "fail");
				}else {
					ChallengeRDG c = new ChallengeRDG();
					c.setChallenger(currentPlayerId);
					c.setChallengee(challengee);
					c.setStatus(0);
					try {
						c.insert();
						m.put("message", "A challenge has been made by you (Challenger:"+currentPlayerId+") to challenge against Challengee: "+challengee+".");
						m.put("status", "success");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println(JSON.toJSONString(m));
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
