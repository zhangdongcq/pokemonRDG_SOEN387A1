import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRDG {
	private int id;
	private long challenger;
	private long challengee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getChallenger() {
		return challenger;
	}

	public void setChallenger(long challenger) {
		this.challenger = challenger;
	}

	public long getChallengee() {
		return challengee;
	}

	public void setChallengee(long challengee) {
		this.challengee = challengee;
	}

	public List<Map<String, Object>> findAllGamesJ() throws SQLException {
		new BaseDao();
		List<Map<String, Object>> answer = new ArrayList<>();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, challenger, challengee FROM game";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Map<String, Object> singleRecord = new HashMap<>();
			singleRecord.put("id", rs.getLong(1));
			singleRecord.put("players", new long[] {rs.getLong(2), rs.getLong(3)});
			answer.add(singleRecord);
		}
		rs.close();
		ps.close();
		conn.close();
		return answer;
	}

	public void insert() throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "INSERT INTO game (challenger, challengee) VALUES (?, ?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.getChallenger());
		ps.setLong(2, this.getChallengee());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}
	
	public long insertGetId() throws SQLException{
		new BaseDao();
		long gameId = -1;
		Connection conn = BaseDao.getConnection();
		String query = "INSERT INTO game (challenger, challengee) VALUES (?, ?)";
		PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.getChallenger());
		ps.setLong(2, this.getChallengee());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()) {
			gameId = rs.getInt(1);
		}
		rs.close();
		ps.close();
		conn.close();
		return gameId;
	}
	
	public long[] findPlayers(long gameId) throws SQLException {
		new BaseDao();
		long[] result = new long[2];
		Connection conn = BaseDao.getConnection();
		String query = "SELECT challenger, challengee FROM game WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, gameId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result[0] = rs.getLong(1);
			result[1] = rs.getLong(2);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}
	
	public long findGameByPlayerId(long playerId) throws SQLException{
		new BaseDao();
		long gameId = -1;
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id FROM game WHERE challenger=? OR challengee=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, playerId);
		ps.setLong(2, playerId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			gameId = rs.getLong(1);
		}
		rs.close();
		ps.close();
		conn.close();
		return gameId;
	}
}
