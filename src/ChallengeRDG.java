import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChallengeRDG {
	private long id;
	private long challenger;
	private long challengee;
	// 0 - open; 1 - refused; 2 - withdrawn; 3 - accepted
	private int status = 0;
	private long game = 0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ChallengeRDG() {
	}

	public ChallengeRDG(long id, long challenger, long challengee, int status) {
		this.id = id;
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}

	public void insert() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "INSERT INTO challenge (challenger, challengee, status, game) VALUES(?, ?, ?, ?);";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.challenger);
		ps.setLong(2, this.challengee);
		ps.setInt(3, this.getStatus());
		ps.setLong(4, this.getGame());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public void updateStatus() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "UPDATE challenge SET status=? WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, this.status);
		ps.setLong(2, this.id);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public void delete() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "DELETE * FROM challenge WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.id);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public ChallengeRDG find(long challengeId) throws SQLException {

		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, challenger, challengee, status FROM challenge WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, challengeId);
		ResultSet rs = ps.executeQuery();
		ChallengeRDG tmp = null;
		while (rs.next()) {
			tmp = new ChallengeRDG(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getInt(4));
		}
		rs.close();
		ps.close();
		conn.close();
		return tmp;

	}

	public List<ChallengeRDG> findOpenByChallenger(long challenger) throws SQLException {
		List<ChallengeRDG> result = new ArrayList<>();

		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, challenger, challengee, status FROM challenge WHERE challenger=? AND status=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, challenger);
		ps.setInt(2, 0);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			result.add(new ChallengeRDG(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getInt(4)));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;

	}

	public List<ChallengeRDG> findAllChallenge() throws SQLException {
		List<ChallengeRDG> result = new ArrayList<ChallengeRDG>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, challenger, challengee, status FROM challenge;";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new ChallengeRDG(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getInt(4)));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<Map<String, Object>> findAllChallengeJ() throws SQLException {
		List<Map<String, Object>> result = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, challenger, challengee, status FROM challenge;";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Map<String, Object> singleRecord = new HashMap<>();
			singleRecord.put("id", rs.getLong(1));
			singleRecord.put("challenger", rs.getLong(2));
			singleRecord.put("challengee", rs.getLong(3));
			singleRecord.put("status", rs.getInt(4));
			result.add(singleRecord);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<ChallengeRDG> findOpenByChallengee(long challengee) throws SQLException {
		List<ChallengeRDG> result = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, challenger, challengee, status FROM challenge WHERE challengee=? AND status=0";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, challengee);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new ChallengeRDG(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getInt(4)));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;

	}

	public List<ChallengeRDG> findAllOpen() throws SQLException {
		List<ChallengeRDG> result = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, challenger, challengee, status FROM challenge WHERE status=0";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new ChallengeRDG(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getInt(4)));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}
	
	public void updateGameId() throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "UPDATE challenge SET game=? WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.game);
		ps.setLong(2, this.id);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public long getGame() {
		return game;
	}

	public void setGame(long game) {
		this.game = game;
	}
}
