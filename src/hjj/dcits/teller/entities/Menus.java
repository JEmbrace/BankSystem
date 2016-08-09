package hjj.dcits.teller.entities;

public class Menus {
	private int power_id;
	private String	power_name;
	private String	power_available;
	private String	power_desc;
	private int power_pid;
	private String menu_url;
	
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public int getPower_id() {
		return power_id;
	}
	public void setPower_id(int power_id) {
		this.power_id = power_id;
	}
	public String getPower_name() {
		return power_name;
	}
	public void setPower_name(String power_name) {
		this.power_name = power_name;
	}
	public String getPower_available() {
		return power_available;
	}
	public void setPower_available(String rs_power_avil) {
		this.power_available = rs_power_avil;
	}
	public String getPower_desc() {
		return power_desc;
	}
	public void setPower_desc(String power_desc) {
		this.power_desc = power_desc;
	}
	public int getPower_pid() {
		return power_pid;
	}
	public void setPower_pid(int power_pid) {
		this.power_pid = power_pid;
	}
	@Override
	public String toString() {
		return "Menus [power_id=" + power_id + ", power_name=" + power_name + ", power_available=" + power_available
				+ ", menu_url=" + menu_url + "]";
	}
    
	
	
	
}
