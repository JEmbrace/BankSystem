package hjj.dcits.teller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hjj.dcits.teller.entities.Menus;
import hjj.dcits.teller.util.DB;


public class GetDynamicMenuDao{
	public List<Menus> getMenu(int user_id){
		
		int arg_user_id = user_id;
		System.out.println(arg_user_id);
		List<Menus> dynamic_menu  = new ArrayList<Menus>();
		List<Integer> list_power_id = new ArrayList<Integer>();
		Connection conn = DB.getConn();
		
	
		String sql = "SELECT BANK_ROLE_ID from DCITS_BANK_USER_ROLE WHERE BANK_USER_ID=?";
		PreparedStatement pstmt = DB.getPStmt(conn, sql);
		ResultSet rs = null;
		try {
			pstmt.setInt(1,arg_user_id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				int rs_bank_role_id = rs.getInt("bank_role_id");
				

				String sql2 = "select bank_power_id from DCITS_ROLE_POWER where bank_role_id = ?";
				pstmt = DB.getPStmt(conn, sql2);
				pstmt.setInt(1,rs_bank_role_id);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					int rs_bank_power_id = rs.getInt("bank_power_id");
					list_power_id.add(rs_bank_power_id);
				}
				System.out.println(list_power_id.size());
				

				for(int id:list_power_id){
					System.out.println("È¨ÏÞid:"+id);
					String sql3 = "select * from DCITS_BANK_POWER where bank_power_id = ?";
					pstmt = DB.getPStmt(conn, sql3);
					pstmt.setInt(1, id);
					rs = pstmt.executeQuery();
					if(rs.next()){
						int rs_power_id      = rs.getInt("BANK_POWER_ID");
						String rs_power_name = rs.getString("BANK_POWER_NAME").trim();
						String rs_power_desc = rs.getString("BANK_POWER_DISC").trim();
						String rs_power_avil = rs.getString("BANK_POWER_AVAILABLE").trim();
						int rs_power_pid     = rs.getInt("BANK_POWER_PID");
						String  rs_power_url = rs.getString("BANK_POWER_URL");
						Menus menus = new Menus();
						menus.setPower_id(rs_power_id);
						menus.setPower_name(rs_power_name);
						menus.setPower_desc(rs_power_desc);
						menus.setPower_available(rs_power_avil);
						menus.setPower_pid(rs_power_pid);
						menus.setMenu_url(rs_power_url);
						System.out.println("dao---"+menus);
						
						dynamic_menu.add(menus);
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("dao---"+dynamic_menu.size());
		return dynamic_menu;
	}
	
	public static void main(String[] args){
		GetDynamicMenuDao gded = new GetDynamicMenuDao();
		List<Menus> menus = gded.getMenu(1000);
		System.out.println(menus.size());

		
	}
}
