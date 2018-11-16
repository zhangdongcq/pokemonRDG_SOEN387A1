
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class ViewBoard
 */
@WebServlet("/ViewBoard")
public class ViewBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewBoard() {
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
		if (request.getParameter("game") == null) {
			message.put("message", "Please input a game id!");
			message.put("status", "fail");
			pw.println(JSON.toJSONString(message));
		} else if (request.getSession(true).getAttribute("userid") == null) {
			message.put("message", "Please login first or your session is expired!");
			message.put("status", "fail");
			pw.println(JSON.toJSONString(message));
		} else {
			long currentPlayerId = (long) request.getSession(true).getAttribute("userid");
			long gameId = Long.parseLong(request.getParameter("game"));
			long[] players = new long[2];
			long[] decks = new long[2];
			try {
				Map<String, Object> result = new HashMap<>();
				result.put("id", gameId);
				players = new GameRDG().findPlayers(gameId);
				if(players == null) {
					message.put("message", "There is no payers in this game!");
					message.put("status", "fail");
					pw.println(JSON.toJSONString(message));
				}else if(players[0]!=currentPlayerId&&players[1]!=currentPlayerId) {
					message.put("message", "It's not your board, no way to see the board!");
					message.put("status", "fail");
					pw.println(JSON.toJSONString(message));
				}else {
					result.put("players", players);
					decks[0] = new DeckRDG().findDeckId(players[0]);
					decks[1] = new DeckRDG().findDeckId(players[1]);
					result.put("decks", decks);
					/**
					 * Get two play infos
					 */
					CardRDG c = new CardRDG();
					c.setGame(gameId);
					c.setOwner(players[0]);
					Map<String, Object> player1BenchCards = c.findSizeByPositionForReportJ();
					c.setOwner(players[1]);
					Map<String, Object> player2BenchCards = c.findSizeByPositionForReportJ();
					/**
					 * Concat "play" section
					 */
					Map<String, Object> playSection = new HashMap<>();
					playSection.put(players[0] + "", player1BenchCards);
					playSection.put(players[1] + "", player2BenchCards);
					result.put("play", playSection);
					/**
					 * Add into group
					 */
					Map<String, Object> board = new HashMap<>();
					board.put("board", result);

					pw.println(JSON.toJSONString(board));
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

//	protected Map<String, String> getPlayerInfo(long playerId, long gameId) throws SQLException {
//		Map<String, String> player = new HashMap<>();
//		String status = new UserRDG().find(playerId).getStatus();
//		player.put("status", status);
//		CardRDG c = new CardRDG();
//		c.setOwner(playerId);
//		c.setGame(gameId);
//		String handsize = c.findSizeByPosition("hand") + "";
//		String decksize = c.findSizeByPosition("deck") + "";
//		String discardsize = c.findSizeByPosition("discard") + "";
//		String bench = "[";
//		List<CardRDG> cl = c.findCardsByPosition("bench");
//		for (CardRDG cr : cl) {
//			bench = bench + cr.getId() + ",";
//		}
//		bench = bench.substring(0, bench.length() - 1) + "]";
//		player.put("handsize", handsize);
//		player.put("decksize", decksize);
//		player.put("discardsize", discardsize);
//		player.put("bench", bench);
//		return player;
//	}

}
