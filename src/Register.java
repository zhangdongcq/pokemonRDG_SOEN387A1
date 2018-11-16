
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
 * Servlet implementation class RegisterPC
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
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
		Map<String, String> m = new HashMap<>();
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		if (user == null || user.isEmpty() || pass == null || pass.isEmpty() || user.equals("\"\"") || pass.equals("\"\"")) {
			m.put("message", "Please enter a username or a password or they are invalid.");
			m.put("status", "fail");
		} else {
			UserRDG u = null;
			try {
				u = new UserRDG().find(user);
				if (u != null) {
					m.put("message", "That user has already registered.");
					m.put("status", "fail");
				} else {
					u = new UserRDG(user, pass);
					try {
						u.insert();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					UserRDG realUser = new UserRDG().find(user, pass);
					long id = realUser.getId();
					request.getSession(true).setAttribute("userid", id);
					m.put("message", "You have successfully registered with username: " + user + ". Your id is " + id);
					m.put("status", "success");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		pw.println(JSON.toJSONString(m));
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
