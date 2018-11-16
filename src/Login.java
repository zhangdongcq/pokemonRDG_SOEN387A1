
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
 * Servlet implementation class LoginPC
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		if (username == null || username.length() == 0) {
			m.put("message", "A good username is needed.");
			m.put("status", "fail");
		} else if (password == null || password.length() == 0) {
			m.put("message", "A password is needed.");
			m.put("status", "fail");
		} else {
			try {
				UserRDG u = new UserRDG().find(username, password);
				if (u == null) {
					m.put("message", "No such an user! Please register first.");
					m.put("status", "fail");
				} else {
					m.put("message", "You ("+username+") successfully logged in, welcome! ");
					m.put("status", "success");
					long id = u.getId();
					request.getSession(true).setAttribute("userid", id);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
