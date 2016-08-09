package hjj.dcits.teller.entities;

public class WaterFlow {
	private String flow_number;
	private int emp_id;
	private String trade_code;
	private String time;
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlow_number() {
		return flow_number;
	}
	public void setFlow_number(String flow_number) {
		this.flow_number = flow_number;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getTrade_code() {
		return trade_code;
	}
	public void setTrade_code(String trade_code) {
		this.trade_code = trade_code;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "WaterFlow [flow_number=" + flow_number + ", emp_id=" + emp_id + ", trade_code=" + trade_code + ", time="
				+ time + "]";
	}
	
	
	
	
}
