import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardRDG {
	private long id;
	private long private_card_id;

	public long getPrivate_card_id() {
		return private_card_id;
	}

	public void setPrivate_card_id(long private_card_id) {
		this.private_card_id = private_card_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String type;
	private String name;
	private String position = "deck"; // deck, bench, discardPile, hand
	private long owner;
	private long game = 0;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public long getGame() {
		return game;
	}

	public void setGame(long game) {
		this.game = game;
	}

	public CardRDG() {
	}

	public CardRDG(long private_card_id, String type, String name, long owner) {
		this.private_card_id = private_card_id;
		this.type = type;
		this.name = name;
		this.owner = owner;
	}

	public CardRDG(String type, String name, String position, long owner, long game) {
		this.type = type;
		this.name = name;
		this.position = position;
		this.owner = owner;
		this.game = game;
	}

	public CardRDG(long private_card_id, String type, String name, String position, long owner) {
		this.type = type;
		this.name = name;
		this.position = position;
		this.owner = owner;
		this.private_card_id = private_card_id;
	}

	public CardRDG(long private_card_id, String type, String name, String position, long owner, long game) {
		this.type = type;
		this.name = name;
		this.position = position;
		this.owner = owner;
		this.private_card_id = private_card_id;
		this.game = game;
	}

	public void insert() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "INSERT INTO card (private_card_id, type, name, owner, position, game) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.getPrivate_card_id());
		ps.setString(2, this.getType());
		ps.setString(3, this.getName());
		ps.setLong(4, this.getOwner());
		ps.setString(5, this.getPosition());
		ps.setLong(6, this.getGame());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	/**
	 * DrawCard will change the position amongst deck, hand, bench...
	 * 
	 * @return
	 * @throws SQLException
	 */
	public void updatePosition() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "UPDATE card SET position = ?, game = ? WHERE private_card_id=? AND owner=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, this.getPosition());
		ps.setLong(2, this.getGame());
		ps.setLong(3, this.getPrivate_card_id());
		ps.setLong(4, this.getOwner());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public CardRDG findTopCard(long owner, String position) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT private_card_id, type, name, position, owner, game FROM card WHERE owner=? AND position=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, owner);
		ps.setString(2, position);
		ResultSet rs = ps.executeQuery();
		CardRDG result = null;
		rs.next();
		result = new CardRDG(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5),
				rs.getLong(6));
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public CardRDG findById(long private_card_id) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, type, name, position, owner, game FROM card WHERE private_card_id=? AND owner =?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, private_card_id);
		ps.setLong(2, this.getOwner());
		ResultSet rs = ps.executeQuery();
		CardRDG tmp = null;
		while (rs.next()) {
			tmp = new CardRDG(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5),
					rs.getLong(6));
		}
		rs.close();
		ps.close();
		conn.close();
		return tmp;
	}

//	public CardRDG findByIdAndGame(long cardId, long gameId) throws SQLException {
//		new BaseDao();
//		Connection conn = BaseDao.getConnection();
//		String query = "SELECT id, type, name, position, owner, game FROM card WHERE id=? AND game=?";
//		PreparedStatement ps = conn.prepareStatement(query);
//		ps.setLong(1, cardId);
//		ps.setLong(1, gameId);
//		ResultSet rs = ps.executeQuery();
//		CardRDG tmp = null;
//		while (rs.next()) {
//			tmp = new CardRDG(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5),
//					rs.getLong(6));
//		}
//		rs.close();
//		ps.close();
//		conn.close();
//		return tmp;
//	}

	public List<CardRDG> findCardsByPosition(String position) throws SQLException {
		List<CardRDG> result = new ArrayList<CardRDG>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT private_card_id, type, name, position, owner, game FROM card WHERE position = ? AND owner = ? AND game=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, position);
		ps.setLong(2, this.getOwner());
		ps.setLong(3, this.getGame());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new CardRDG(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5),
					rs.getLong(6)));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<Map<String, String>> findCardsByPositionJ(String position) throws SQLException {
		List<Map<String, String>> result = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT private_card_id, type, name FROM card WHERE position = ? AND owner = ? AND game=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, position);
		ps.setLong(2, this.getOwner());
		ps.setLong(3, this.getGame());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Map<String, String> singleRecord = new HashMap<>();
			singleRecord.put("id", rs.getLong(1) + "");
			singleRecord.put("t", rs.getString(2));
			singleRecord.put("n", rs.getString(3));
			result.add(singleRecord);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<Long> findCardsByPositionHandJ() throws SQLException {
		List<Long> result = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT private_card_id FROM card WHERE position = ? AND owner = ? AND game=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "hand");
		ps.setLong(2, this.getOwner());
		ps.setLong(3, this.getGame());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(rs.getLong(1));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<CardRDG> findCardsByPositionForDeckView() throws SQLException {
		List<CardRDG> result = new ArrayList<CardRDG>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT private_card_id, type, name, position, owner, game FROM card WHERE position = ? AND owner = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "deck");
		ps.setLong(2, this.getOwner());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new CardRDG(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5),
					rs.getLong(6)));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<Map<String, String>> findCardsByPositionForDeckViewJ() throws SQLException {
		List<Map<String, String>> result = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT type, name FROM card WHERE position = ? AND owner = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "deck");
		ps.setLong(2, this.getOwner());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Map<String, String> singleRecord = new HashMap<>();
			singleRecord.put("t", rs.getString(1));
			singleRecord.put("n", rs.getString(2));
			result.add(singleRecord);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public int findSizeByPosition(String position) throws SQLException {
		int result = 0;
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT count(private_card_id) FROM card WHERE position = ? AND owner = ? AND game=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, position);
		ps.setLong(2, this.getOwner());
		ps.setLong(3, this.getGame());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result = rs.getInt(1);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public Map<String, Object> findSizeByPositionForReportJ() throws SQLException {
		Map<String, Object> stats = new HashMap<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		/**
		 * Get Status
		 */
		stats.put("status", new UserRDG().find(this.getOwner()).getStatus());
		/**
		 * Get hand size
		 */
		String query = "SELECT count(private_card_id) FROM card WHERE position = ? AND owner = ? AND game = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "hand");
		ps.setLong(2, this.getOwner());
		ps.setLong(3, this.getGame());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			stats.put("handsize", rs.getInt(1));
		}
		rs.close();
		ps.close();
		/**
		 * Get deck size
		 */
		PreparedStatement ps2 = conn.prepareStatement(query);
		ps2.setString(1, "deck");
		ps2.setLong(2, this.getOwner());
		ps2.setLong(3, this.getGame());
		ResultSet deckrs = ps2.executeQuery();
		while (deckrs.next()) {
			stats.put("decksize", deckrs.getInt(1));
		}
		deckrs.close();
		ps2.close();
		/**
		 * Get descard size
		 */
		PreparedStatement ps3 = conn.prepareStatement(query);
		ps3.setString(1, "discard");
		ps3.setLong(2, this.getOwner());
		ps3.setLong(3, this.getGame());
		ResultSet discardrs = ps3.executeQuery();
		while (discardrs.next()) {
			stats.put("discardsize", discardrs.getInt(1));
		}
		discardrs.close();
		ps3.close();
		conn.close();
		/**
		 * Get bench cards
		 */
		List<Map<String, String>> benchCards = this.findCardsByPositionJ("bench");
		stats.put("bench", benchCards);
		/**
		 * Return unit stats
		 */
//		Map<String, Map<String, Object>> result = new HashMap<>();
//		result.put(this.getOwner() + "", stats);
		return stats;
	}

	public void updateGameIdForBothPlayers(long gameId, long challenger, long challengee) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "UPDATE card SET game=? WHERE owner=? OR owner=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, gameId);
		ps.setLong(2, challenger);
		ps.setLong(3, challengee);
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

}
