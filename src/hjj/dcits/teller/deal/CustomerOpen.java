package hjj.dcits.teller.deal;

import java.util.Map;

import hjj.dcits.teller.dao.CustomerOpenDao;
import hjj.dcits.teller.entities.Customer;

public class CustomerOpen{
	
	public  static Map<String,String> customeropen(Customer c){
		CustomerOpenDao co = new CustomerOpenDao();
		Map<String,String> map	= null;
		map    		   = co.insertCustomerInfo(c);
		
		return map;
	}
	public static void main(String[] args) {
		

	}

}
