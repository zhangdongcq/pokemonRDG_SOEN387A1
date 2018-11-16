

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
 * Servlet implementation class LogoutPC
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = (Long)request.getSession(true).getAttribute("userid");
		PrintWriter pw = response.getWriter();
		Map<String, String> m = new HashMap<>();
		if(id!=null) {
			try {
				UserRDG u = new UserRDG().find(id);
				request.getSession(true).invalidate();
				m.put("status", "success");
				m.put("message", "You ("+u.getUsername()+") successfully logout. Bye.");
				request.getSession(true).invalidate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			m.put("status", "fail");
			m.put("message", "The user has not logged in. You must login first");
		}
		pw.println(JSON.toJSONString(m));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
