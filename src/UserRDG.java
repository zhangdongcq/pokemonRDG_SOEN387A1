import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class UserRDG {
	private long id;
	private String username;
	private String password;
	private int version = 1;

	public long getId() {
		return id;
	}

	private String status = "playing";

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRDG() {
	}

	public UserRDG(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public UserRDG(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserRDG(long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public UserRDG(long id, String username, String password, int version) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.version = version;
	}

	public void insert() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "INSERT INTO player (username, password, version, status) VALUES (?, md5(?),?, ?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, this.getUsername());
		ps.setString(2, this.getPassword());
		ps.setInt(3, this.getVersion());
		ps.setString(4, this.getStatus());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public void updatePassword() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "UPDATE player SET password=md5(?) WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, this.getPassword());
		ps.setLong(2, this.getId());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public void updateStatus() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "UPDATE player SET status=?  WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, this.getStatus());
		ps.setLong(2, this.getId());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public void delete() throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "DELETE * FROM player WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, this.getId());
		ps.executeUpdate();
		ps.close();
		conn.close();
	}

	public UserRDG find(long id) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, username, version, status FROM player WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		UserRDG tmp = new UserRDG();
		while (rs.next()) {
			tmp.setId(rs.getLong(1));
			tmp.setUsername(rs.getString(2));
			tmp.setVersion(rs.getInt(3));
			tmp.setStatus(rs.getString(4));
		}
		rs.close();
		ps.close();
		conn.close();
		return tmp;
	}

	public UserRDG find(String username) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, username, version, status FROM player WHERE username=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		UserRDG tmp = null;
		while (rs.next()) {
			tmp = new UserRDG(rs.getLong(1), rs.getString(2));
			tmp.setVersion(rs.getInt(3));
			tmp.setStatus(rs.getString(4));
		}
		rs.close();
		ps.close();
		conn.close();
		return tmp;
	}
	
	public long findIdByNameInDB(String username) throws SQLException {
		new BaseDao();
		long result=0;
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id FROM player WHERE username=?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result = rs.getLong(1);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}
	

	public UserRDG find(String username, String password) throws SQLException {
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, username, status FROM player WHERE username=? AND password=md5(?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		UserRDG tmp = null;
		while (rs.next()) {
			tmp = new UserRDG();
			tmp.setId(rs.getLong(1));
			tmp.setUsername(rs.getString(2));
			tmp.setStatus(rs.getString(3));
		}
		rs.close();
		ps.close();
		conn.close();
		return tmp;
	}

	public List<UserRDG> findAll() throws SQLException {
		List<UserRDG> result = new ArrayList<UserRDG>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, username FROM player";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(new UserRDG(rs.getLong(1), rs.getString(2)));
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public List<Map<String, Object>> findAllJ() throws SQLException {
		List<Map<String, Object>> result = new ArrayList<>();
		new BaseDao();
		Connection conn = BaseDao.getConnection();
		String query = "SELECT id, username FROM player";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Map<String, Object> singleRecord = new HashMap<>();
			singleRecord.put("id", rs.getLong(1));
			singleRecord.put("user", rs.getString(2));
			result.add(singleRecord);
		}
		rs.close();
		ps.close();
		conn.close();
		return result;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
