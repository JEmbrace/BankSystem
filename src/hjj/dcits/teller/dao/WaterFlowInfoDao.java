package hjj.dcits.teller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hjj.dcits.teller.entities.WaterFlow;
import hjj.dcits.teller.util.DB;

public class WaterFlowInfoDao {
    public Map<String,String> insertWaterFlowInfo(WaterFlow wf){
    	Map<String,String> map	= new HashMap<String,String>();
    	String sql 			 = "INSERT INTO DCITS_WATER_FLOW VALUES (?,?,?,?,?)";
    	Connection conn  	 = null;
    	PreparedStatement ps = null;
    	conn				 = DB.getConn();
    	ps  				 = DB.getPStmt(conn, sql);
    	int rs = 0;
    	try {
			ps.setString(1,wf.getFlow_number());
			ps.setInt(2, wf.getEmp_id());
			ps.setString(3,wf.getTrade_code());
			ps.setString(4,wf.getTime());
			ps.setString(5, wf.getFlag());
			rs	= ps.executeUpdate();
			String value = rs == 1?"插入流水表成功":"插入流水表失败";
			map.put("error_tips",value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return map;
    }
    public static void main(String[] args){
    	WaterFlow wi = new WaterFlow();
    	wi.setFlow_number("1000000000000");
    	wi.setEmp_id(1000);
    	wi.setTrade_code("10000000");
    	wi.setTime(Long.toString(new Date().getTime()));
    	WaterFlowInfoDao wfd = new WaterFlowInfoDao();
    	//int rs = wfd.insertWaterFlowInfo(wi);
    	//System.out.println(rs);
    }
}
