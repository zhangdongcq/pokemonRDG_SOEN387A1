
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
 * Servlet implementation class RefuseChallenge
 */
@WebServlet("/RefuseChallenge")
public class RefuseChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RefuseChallenge() {
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
				if (ch == null) {
					message.put("message", "The challenge dose not exist!");
					message.put("status", "fail");
				} else {
					long challengerIdInRecord = ch.getChallenger();
					long challengeeIdInRecord = ch.getChallengee();
					if (challengerIdInRecord == currentPlayerId) {
						ch.setStatus(2);
						ch.updateStatus();
						message.put("message", "You (" + currentPlayerId + ") withdrawn the challenge made by you ("
								+ challengerIdInRecord + ".");
						message.put("status", "success");
					} else if (ch.getStatus() != 0) {
						message.put("message", "The challenge has already been refused, withdrawl or accepted before!");
						message.put("status", "fail");
					} else if (challengeeIdInRecord == currentPlayerId) {
						ch.setStatus(1);
						ch.updateStatus();
						message.put("message", "You (" + currentPlayerId + ") have refused a challenge made by ("
								+ challengerIdInRecord + ".");
						message.put("status", "success");
					} else {
						message.put("message", "You are not in this challenge!");
						message.put("status", "fail");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pw.println(JSON.toJSONString(message));
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
