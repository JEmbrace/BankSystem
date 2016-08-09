package hjj.dcits.teller.deal;

import java.util.HashMap;
import java.util.Map;

import hjj.dcits.teller.dao.WaterFlowInfoDao;
import hjj.dcits.teller.entities.WaterFlow;

public class WaterFlowInfo {
	 public static Map<String,String> insertWaterFlowInfo(String[] messageHeader){
	     Map<String,String> map = new HashMap<String,String>();
	 
		 WaterFlow wf = new WaterFlow();
		 wf.setFlow_number(messageHeader[0]);
		 wf.setEmp_id(Integer.parseInt(messageHeader[1]));
		 wf.setTrade_code(messageHeader[2]);
		 wf.setTime(messageHeader[3]);
		 wf.setFlag(messageHeader[4]);
		
		WaterFlowInfoDao wfi = new WaterFlowInfoDao();
		map 				 = wfi.insertWaterFlowInfo(wf);		 
		return map;
		 
	 }
}
