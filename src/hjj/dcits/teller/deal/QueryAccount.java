package hjj.dcits.teller.deal;

import java.util.Map;

import hjj.dcits.teller.dao.AccountOperationDao;
import hjj.dcits.teller.entities.Account;
import hjj.dcits.teller.entities.Customer;

public class QueryAccount {
	
	public static Map<String,String> queryaccount(Account a){
		Map<String,String> map	= null;
		String card_id = a.getCard_id();
		String psw     = a.getCard_psw();
		AccountOperationDao   aod   = new AccountOperationDao();
		map							= aod.getAccountInfo(card_id, psw);
		return map;
	}
}
