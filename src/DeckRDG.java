import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeckRDG {
	private long id=1;
	private long owner;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}
	public DeckRDG(){}
	public DeckRDG(long id, long owner) {
		this.id=id;
		this.owner = owner;
	}
	
	public void insert() throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "INSERT deck (owner) VALUES(?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.getOwner());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public Long findDeckId(long owner) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id FROM deck WHERE owner=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, owner);
		ResultSet rs = ps.executeQuery();
		Long result = null;
		while (rs.next()) {
			result = rs.getLong(1);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public long findDeckOwner(long id) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT owner FROM deck WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		long result = -1;
		while (rs.next()) {
			result = rs.getLong(1);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<DeckRDG> findAllDecks() throws SQLException {
		List<DeckRDG> list = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, owner FROM deck";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new DeckRDG(rs.getLong(1), rs.getLong(2)));
		}
		rs.close();
		ps.close();
		conn.close();
		return list;
	}
	
	public List<long[]> findAllDecksJ() throws SQLException {
		List<long[]> list = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, owner FROM deck";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			list.add(new long[] {rs.getLong(1), rs.getLong(2)});
		}
		rs.close();
		ps.close();
		conn.close();
		return list;
	}

}
