package hjj.dcits.teller.entities;

public class Account {
	private int id;
	private String card_id;
	private String card_psw;
	
	private String time;
	private String type;
	private int emp_id; 
	private int cuerstom_id;
	private float money;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCard_psw() {
		return card_psw;
	}
	public void setCard_psw(String card_psw) {
		this.card_psw = card_psw;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public int getCuerstom_id() {
		return cuerstom_id;
	}
	public void setCuerstom_id(int cuerstom_id) {
		this.cuerstom_id = cuerstom_id;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", card_id=" + card_id + ", time=" + time + ", type=" + type + ", emp_id=" + emp_id
				+ ", cuerstom_id=" + cuerstom_id + ", money=" + money + "]";
	}
	
	

}
