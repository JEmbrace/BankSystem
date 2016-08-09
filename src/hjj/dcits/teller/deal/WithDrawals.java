package hjj.dcits.teller.deal;

import java.util.Map;
import java.util.Map.Entry;

import hjj.dcits.teller.dao.AccountOperationDao;
import hjj.dcits.teller.entities.Account;

public class WithDrawals {
	public static  Map<String,String> withdrawals(Account account){
		Map<String,String> map	= null;
		AccountOperationDao aod = new AccountOperationDao();
		//����ȵõ��û���Ϣ
		map                     = aod.getAccountInfo(account.getCard_id(),account.getCard_psw());
		System.out.println("����map��С"+map.size());
		for(Map.Entry<String, String> e:map.entrySet()){
			System.out.println(e.getKey());
			System.out.println(e.getValue());
			
		}
		if(map.size() > 1){
			//˵���û���Ϣ��ȷ
			for(Map.Entry<String, String> e:map.entrySet()){
				
				if(e.getKey().equals("a_money")){
					//�õ��˻����
					float money = Float.parseFloat(e.getValue());
					
					if(account.getMoney() > money){
						System.out.println("�Բ����˻�����");
						map.clear();
						map.put("error_tips","�Բ����˻�����");
						return map;
					}else{
						//ȡ��
						map = aod.withDrawals(account);
					}
				}
			}
		}
		return map;
	}
}
