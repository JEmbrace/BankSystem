package hjj.dcits.teller.deal;

import java.util.Map;
import java.util.Map.Entry;

import hjj.dcits.teller.dao.AccountOperationDao;
import hjj.dcits.teller.entities.Account;

public class WithDrawals {
	public static  Map<String,String> withdrawals(Account account){
		Map<String,String> map	= null;
		AccountOperationDao aod = new AccountOperationDao();
		//存款先得到用户信息
		map                     = aod.getAccountInfo(account.getCard_id(),account.getCard_psw());
		System.out.println("存款的map大小"+map.size());
		for(Map.Entry<String, String> e:map.entrySet()){
			System.out.println(e.getKey());
			System.out.println(e.getValue());
			
		}
		if(map.size() > 1){
			//说明用户信息正确
			for(Map.Entry<String, String> e:map.entrySet()){
				
				if(e.getKey().equals("a_money")){
					//拿到账户余额
					float money = Float.parseFloat(e.getValue());
					
					if(account.getMoney() > money){
						System.out.println("对不起，账户余额不足");
						map.clear();
						map.put("error_tips","对不起，账户余额不足");
						return map;
					}else{
						//取款
						map = aod.withDrawals(account);
					}
				}
			}
		}
		return map;
	}
}
