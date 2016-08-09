package hjj.dcits.teller.deal;


import java.util.Map;

import hjj.dcits.teller.dao.AccountOperationDao;
import hjj.dcits.teller.entities.Account;
import hjj.dcits.teller.entities.Customer;

public class DepositMoney {
	
	public static Map<String,String> depositmoney(Account account){
		Map<String,String> map = null;
		AccountOperationDao dmd   = new AccountOperationDao(); 
		//存款先得到用户信息
		map = dmd.getAccountInfo(account.getCard_id(),account.getCard_psw());
		if(map.size() >1){
			//用户信息正确
			//开始存款
			map = dmd.depositMoney(account);
		}
		return map;
	}
}
