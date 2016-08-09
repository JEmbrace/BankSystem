package hjj.dcits.teller.deal;

import java.util.Map;

import hjj.dcits.teller.dao.AccountOperationDao;
import hjj.dcits.teller.entities.Account;

public class AccountOpen {

	public  static Map<String,String>  accountopen(Account c){
		AccountOperationDao aod = new AccountOperationDao();
		Map<String,String> map 	= aod.accountOpen(c);
		return map;
	}
	public static void main(String[] args) {
		
	}

}
