package hjj.dcits.teller.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DB {
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");    
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConn(){
		Connection conn = null;
		System.out.println("-----------开始连接数据库--------");
	
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	    String username = "scott";
	    String password = "scott";
	    try {
			conn = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    if(conn != null){
	    	System.out.println("----------数据库连接成功--------");
		}
	    
	    return conn;
	}
	
	public static void closeConn(Connection conn){
		if(conn != null){
		
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static PreparedStatement getPStmt(Connection conn,String sql){
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public static void closePStmt(PreparedStatement PStmt ){
		if(PStmt != null){
			try {
				PStmt.close();
				PStmt = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Statement getStmt(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;  
	}
	
	public static void closeStmt(Statement stmt) {
		if(stmt != null){
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static ResultSet exectQueryStmt(Statement stmt,String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void closeRS(ResultSet rs) {
		if(rs != null){
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
 
	public static void main(String[] args){
		getConn();
		
	}
}

