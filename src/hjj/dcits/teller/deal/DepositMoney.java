package hjj.dcits.teller.deal;


import java.util.Map;

import hjj.dcits.teller.dao.AccountOperationDao;
import hjj.dcits.teller.entities.Account;
import hjj.dcits.teller.entities.Customer;

public class DepositMoney {
	
	public static Map<String,String> depositmoney(Account account){
		Map<String,String> map = null;
		AccountOperationDao dmd   = new AccountOperationDao(); 
		//����ȵõ��û���Ϣ
		map = dmd.getAccountInfo(account.getCard_id(),account.getCard_psw());
		if(map.size() >1){
			//�û���Ϣ��ȷ
			//��ʼ���
			map = dmd.depositMoney(account);
		}
		return map;
	}
}
