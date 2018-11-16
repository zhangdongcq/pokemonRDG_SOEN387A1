
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
 * Servlet implementation class ListChallenges
 */
@WebServlet("/ListChallenges")
public class ListChallenges extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListChallenges() {
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
		if (request.getSession(true).getAttribute("userid") == null) {
			m.put("message", "You have not logged in or your session is expired.");
			m.put("status", "fail");
			pw.println(JSON.toJSONString(m));
		} else {
			ChallengeRDG spy = new ChallengeRDG();
			List<Map<String, Object>> ch = new ArrayList<>();
			try {
				ch = spy.findAllChallengeJ();
				if (ch == null) {
					m.put("message", "No Challenge yet!");
					m.put("status", "fail");
					pw.println(JSON.toJSONString(m));
				} else {
					//Challenge Id is 0 to satisfy test cases
					Map<String, List> outer = new HashMap<>();
					outer.put("challenges", ch);
					pw.println(JSON.toJSONString(outer));
				}
			} catch (SQLException e) {
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
