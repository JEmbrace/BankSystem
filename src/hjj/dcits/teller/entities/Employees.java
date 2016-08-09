package hjj.dcits.teller.entities;

public class Employees {
	private int user_id;
	private String user_name;
	private String user_psw;
	private String user_sex;
	private int user_age;
	private String user_tel;
	private String user_adress;
	private String entry_time;
	private int org_number;
	private int net_number;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_psw() {
		return user_psw;
	}
	public void setUser_psw(String user_psw) {
		this.user_psw = user_psw;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public int getUser_age() {
		return user_age;
	}
	public void setUser_age(int user_age) {
		this.user_age = user_age;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_adress() {
		return user_adress;
	}
	public void setUser_adress(String user_adress) {
		this.user_adress = user_adress;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public int getOrg_number() {
		return org_number;
	}
	public void setOrg_number(int org_number) {
		this.org_number = org_number;
	}
	public int getNet_number() {
		return net_number;
	}
	public void setNet_number(int net_number) {
		this.net_number = net_number;
	}
	@Override
	public String toString() {
		return "Employees [user_name=" + user_name + ", user_psw=" + user_psw + ", org_number=" + org_number
				+ ", net_number=" + net_number + "]";
	}
	
	
	

}
