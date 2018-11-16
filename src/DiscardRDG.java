import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscardRDG {
	private long id;
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
	public void insert() throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "INSERT discard (owner) VALUES(?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.getOwner());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}
	
	public long findDiscardId(long owner) throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id FROM discard WHERE owner=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, owner);
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
	public long findDiscardOwner(long id) throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT owner FROM discard WHERE id=?";
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
	
}
