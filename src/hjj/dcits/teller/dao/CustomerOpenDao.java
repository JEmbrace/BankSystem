package hjj.dcits.teller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import hjj.dcits.teller.entities.Customer;
import hjj.dcits.teller.util.DB;

public class CustomerOpenDao {
	
	public Map<String,String> insertCustomerInfo(Customer c){
		Map<String,String> map	= new HashMap<String,String>();
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		
		conn = DB.getConn();
		String sql = "INSERT INTO DCITS_CUSTOMER ("
				+ "	CUSTOMER_ID, 	CUSTOMER_ENAME,		 CUSTOMER_CNAME, "
				+ "	CUSTOMER_CONTRY, CUSTOMER_PROVINCE,	CUSTOMER_CITY,"
				+ " CUSTOMER_TEL,	CUSTOMER_CREATOR_ID, 	CUSTOMER_ADDRESS) VALUES "
				+ "(?,?,?,?,?,?,?,?,?)";
		int rs = 0;
		pstmt = DB.getPStmt(conn, sql);
		try {
			pstmt.setInt(1,c.getId());
			pstmt.setString(2,c.getEname());
			pstmt.setString(3, c.getCname());
			pstmt.setString(4,c.getContry());
			pstmt.setString(5, c.getProvince());
			pstmt.setString(6, c.getCity());
			pstmt.setString(7, c.getTel());
			pstmt.setInt(8,c.getCreator_id());
			pstmt.setString(9, c.getAddress());
			rs = pstmt.executeUpdate();	
			String value = rs== 1?"客户开户成功":"客户开户失败";
			map.put("error_tips", value);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	public Customer getCustomerInfo(int customer_id){
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		ResultSet rs 			= null;
		String sql 				= "SELECT * FROM　DCITS_CUSTOMER WHERE CUSTOMER_ID = ?";
		
		conn 					= DB.getConn();
		Customer c 				= new Customer();
		pstmt 					= DB.getPStmt(conn, sql);
		try {
			
			pstmt.setInt(1,customer_id);
			rs	= pstmt.executeQuery();
			if(rs.next()){
				c.setId(rs.getInt("CUSTOMER_ID"));
				c.setEname(rs.getString("CUSTOMER_ENAME"));
				c.setCname(rs.getString("CUSTOMER_CNAME"));
				c.setContry(rs.getString("CUSTOMER_CONTRY"));
				c.setProvince(rs.getString("CUSTOMER_PROVINCE"));
				c.setCity(rs.getString("CUSTOMER_CITY"));
				c.setTel(rs.getString("CUSTOMER_TEL"));
				c.setCreator_id(Integer.parseInt(rs.getString("CUSTOMER_CREATOR_ID")));
				c.setAddress(rs.getString("CUSTOMER_ADDRESS"));
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}
	public static void main(String[] args){
		Customer c = new Customer();
		c.setId(10000);
		c.setCname("侯娇娇");
		c.setEname("Embarce");
		c.setContry("China");
		c.setCity("shanghai");
		c.setProvince("XXXX");
		c.setTel("4444444444");
		c.setAddress("xxxxxxx");
		c.setCreator_id(1000);
		
		CustomerOpenDao cod = new CustomerOpenDao();
		//int rs  = cod.insertCustomerInfo(c);
		//System.out.println(rs);
	}
}
