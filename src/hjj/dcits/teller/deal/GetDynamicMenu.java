package hjj.dcits.teller.deal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hjj.dcits.teller.dao.GetDynamicMenuDao;
import hjj.dcits.teller.entities.Menus;

public class GetDynamicMenu{
	public List<Menus>  getMenu(int user_id){		
		GetDynamicMenuDao gdd = new GetDynamicMenuDao();
		List<Menus> menus = gdd.getMenu(user_id);
//		System.out.println(menus.get(0));
//		System.out.println(menus.get(1));
//		System.out.println(menus.get(2));
//		System.out.println(menus.get(2));
		
		return menus;
	}
	public static void main(String[] args){
		GetDynamicMenu gde = new GetDynamicMenu();
		gde.getMenu(1000);
		
	}
}
