package hjj.dcits.teller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hjj.dcits.teller.entities.Account;
import hjj.dcits.teller.entities.Customer;
import hjj.dcits.teller.util.DB;

public class AccountOperationDao {
	/*
	 * 账户开户
	 * @author:hjj
	 */
	public Map<String,String> accountOpen(Account c){
		Map<String,String> map = new HashMap<String,String>();
		String value = null;
		String sql = "INSERT INTO　DCITS_BANK_ACCOUNT("
				+ "ACCOUNT_OPEN_ID,ACCOUNT_OPEN_CARD_ID,"
				+ "ACCOUNT_OPEN_TIME,ACCOUNT_OPNE_TYPE,"
				+ "ACCOUNT_OPNE_EMP_ID,ACCOUNT_OPNE_CUSTOMER_ID,"
				+ "ACCOUNT_MONEY,ACCOUNT_PASSWORD"
				+ ")　VALUES (?,?,?,?,?,?,?,?)";
		Connection conn     	= null;
		PreparedStatement pstmt = null;
		conn 					= DB.getConn();
		pstmt					= DB.getPStmt(conn, sql);
		String current_time     = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		try {
			
			pstmt.setInt(1,c.getId());
			pstmt.setString(2, c.getCard_id());
			pstmt.setString(3,current_time);
			pstmt.setString(4,c.getType());
			pstmt.setInt(5, c.getEmp_id());
			pstmt.setInt(6, c.getCuerstom_id());
			pstmt.setFloat(7,0f);
			pstmt.setString(8,c.getCard_psw());
			
			int rs = pstmt.executeUpdate();
			value  = rs == 1 ? "账户开户成功":"账户开户失败";
			map.put("error_tips",value);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return map;
	}
	
	
	/*
	 * 存款
	 * @author:hjj
	 */
	public Map<String,String>  depositMoney(Account account){
		Map<String,String> map = new HashMap<String,String>();
		String value = null;
		String sql = "UPDATE　DCITS_BANK_ACCOUNT SET ACCOUNT_MONEY = ACCOUNT_MONEY + ? WHERE ACCOUNT_OPEN_CARD_ID = ?";
		Connection conn     	= null;
		PreparedStatement pstmt = null;
		conn 					= DB.getConn();
		pstmt					= DB.getPStmt(conn, sql);		
		try {
			
			float money = account.getMoney();
			pstmt.setFloat(1, money);
			pstmt.setString(2,account.getCard_id());
			int rs = pstmt.executeUpdate();
			
			value  = rs == 1 ? "客户存款成功":"客户存款失败";
			map.put("error_tips",value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return map;
	}
	
	/*
	 * 取款
	 * @author:hjj
	 */
	public Map<String,String> withDrawals(Account account){
		Map<String,String> map = new HashMap<String,String>();
		String value = null;
		String sql = "UPDATE　DCITS_BANK_ACCOUNT SET ACCOUNT_MONEY = ACCOUNT_MONEY - ? WHERE ACCOUNT_OPEN_CARD_ID = ?";
		Connection conn     	= null;
		PreparedStatement pstmt = null;
		conn 					= DB.getConn();
		pstmt					= DB.getPStmt(conn, sql);		
		try {
			
			float money = account.getMoney();
			pstmt.setFloat(1, money);
			pstmt.setString(2,account.getCard_id());
			int rs = pstmt.executeUpdate();
			
			value  = rs == 1 ? "客户取款成功":"客户取款失败";
			map.put("error_tips",value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return map;
	}

	/*
	 * 记录账户流水表
	 * @author:hjj
	 */
	public Map<String,String> opAccountWaterFlow(int id,String trading_code,int emp_id,float money,String psw){
		Map<String,String> map = new HashMap<String,String>();
		String value = null;
		String sql = "INSERT INTO DCITS_ACCOUNT_WATER_FLOW ("
				+ "ACCOUNT_OP_ID,ACCOUNT_OP_CODE,ACCOUTN_OP_EMP_ID,"
				+ "ACCOUNT_OP_MONEY,ACCOUNT_OP_PSW,ACCOUNT_OP_TIME"
				+ ") VALUES (?,?,?,?,?,?)";
		
		Connection conn 		= null;
		PreparedStatement pstmt = null;
		conn					= DB.getConn();
		pstmt					= DB.getPStmt(conn, sql);
		
		try {
			pstmt.setInt(1,id);
			pstmt.setString(2,trading_code);
			pstmt.setInt(3,emp_id);
			pstmt.setFloat(4,money);
			pstmt.setString(5,psw);
			pstmt.setString(6,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			int rs = pstmt.executeUpdate();
			
			value  = rs == 1 ? "记录账户流水成功":"记录账户流水失败";
			map.put("error_tips",value);
			
		} catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	/*
	 * 账户查询
	 * param:String card_id                 银行卡号
	 * param:String psw   				            银行卡密码
	 * return:boolean Map<String,String>    返回一个map对象
	 */
	public Map<String,String> getAccountInfo(String card_id,String psw){
		
		 Map<String,String> map = new HashMap<String,String>();
		 String sql				   = "SELECT * FROM DCITS_BANK_ACCOUNT WHERE ACCOUNT_OPEN_CARD_ID = ?";
		 Connection conn		   = DB.getConn();
		 PreparedStatement pstmt   = DB.getPStmt(conn, sql);
		 ResultSet rs 			   = null;
		 try {
			pstmt.setString(1,card_id);
			rs       =   pstmt.executeQuery();
			if(rs.next()){
				String rs_psw = rs.getString("ACCOUNT_PASSWORD");
				if(psw.equals(rs_psw)){
					int rs_id	       =	rs.getInt("ACCOUNT_OPEN_ID");
					String rs_card_id  = rs.getString("ACCOUNT_OPEN_CARD_ID");
					String rs_time     = rs.getString("ACCOUNT_OPEN_TIME");
					String rs_type     = rs.getString("ACCOUNT_OPNE_TYPE");
					int rs_emp_id      = rs.getInt("ACCOUNT_OPNE_EMP_ID");
					int rs_customer_id = rs.getInt("ACCOUNT_OPNE_CUSTOMER_ID");
					float rs_money	   = rs.getFloat("ACCOUNT_MONEY");

					
					CustomerOpenDao cod = new CustomerOpenDao();
					Customer         c  = cod.getCustomerInfo(rs_customer_id);
					
					map.put("c_cname",c.getCname());
					map.put("c_ename",c.getEname());
					map.put("a_type",rs_type );
					map.put("a_money",Float.toString(rs_money));
				}else{
					System.out.println("对不起，密码错误!");
					map.put("error_tips","对不起，密码错误");
					
				}
				
			}else{
				System.out.println("该账户不存在!");
				map.put("error_tips","该账户不存在");
			}
			
		} catch (SQLException e) {	
			e.printStackTrace();
		} 
		 return map;
		 
		
	}
}
