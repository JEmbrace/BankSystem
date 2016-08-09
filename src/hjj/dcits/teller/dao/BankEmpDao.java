package hjj.dcits.teller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import hjj.dcits.teller.entities.Employees;
import hjj.dcits.teller.util.DB;

public class BankEmpDao {
	public Employees getBankEmpByName(String name){
		String arg_name = name;
		Employees emp = null;
		String sql = "SELECT * FROM DCITS_BANK_EMP WHERE BANK_USER_NAME = ?";
		Connection conn = DB.getConn();
		PreparedStatement PStmt = DB.getPStmt(conn, sql);
		ResultSet rs = null;
		try {
			
			PStmt.setString(1,arg_name);
			rs = PStmt.executeQuery();
			
			if(rs.next()){
				int rs_id = rs.getInt("bank_user_id");
				String rs_name = rs.getString("bank_user_name");
				String rs_psw = rs.getString("bank_user_psw");
				String rs_sex = rs.getString("bank_user_sex");
				int rs_age = rs.getInt("bank_user_age");
				String rs_tel = rs.getString("bank_user_tel");
				String rs_address = rs.getString("bank_user_address");
				String rs_time = rs.getString("bank_user_entry_time");
				int rs_org = rs.getInt("bank_user_org_number");
				int rs_net = rs.getInt("bank_user_net_number");
				emp = new Employees();
				emp.setUser_id(rs_id);
				emp.setUser_name(rs_name);
				emp.setUser_psw(rs_psw);
				emp.setUser_sex(rs_sex);
				emp.setUser_age(rs_age);
				emp.setUser_tel(rs_tel);
				emp.setUser_adress(rs_address);
				emp.setEntry_time(rs_time);
				emp.setOrg_number(rs_org);
				emp.setNet_number(rs_net);
				//System.out.println("BankEmpDao:"+emp);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DB.closeConn(conn);
		DB.closeStmt(PStmt);
		DB.closeRS(rs);
		
		return emp;
		
	}
	
	
	
	
	
	
}
