import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HandRDG {
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
		String query = "INSERT hand (owner) VALUES(?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.getOwner());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}
	
	public long findHandId(long owner) throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id FROM hand WHERE owner=?";
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
	public long findHandOwner(long id) throws SQLException{
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT owner FROM hand WHERE id=?";
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
