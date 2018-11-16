import java.sql.*;
public class BaseDao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/mtldong?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    private static final String USER="root";
//    private static final String PASS="password";
//    private static final String DB_URL = "jdbc:mysql://dumbledore.encs.concordia.ca:"
//    		+ "3306/mtldong?useUnicode=true&characterEncoding=utf8&useJDBCCompliant"
//    		+ "TimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_URL = "jdbc:mysql://localhost/mtldong?user=mtldong&password=ennickpi&characterEncoding=UTF-8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true";
    
//    private static final String USER="mtldong";
//    private static final String PASS="ennickpi";
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException{
//        return DriverManager.getConnection(DB_URL, USER, PASS);
    	return DriverManager.getConnection(DB_URL);
    }

}

