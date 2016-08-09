package Dom4;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import hjj.dcits.teller.entities.Account;
import hjj.dcits.teller.entities.Customer;

public class Dom4Reader {	
	/*
	 * message[0] = 交易流水号
	 * message[1] = 交易柜员编号
	 * message[2] = 交易码
	 * message[3] = 交易时间
	 * 
	 */
	public static Map<String[],Object>  parseMessage(String xmlString){
		Map<String[],Object> map = new HashMap<String[],Object>();
		String[] message_header  = new String[4];
		
		//解析头部，因为所有报文头部信息一致，所以统一采用同一方法解析
		message_header			 = parseMessageHeader(xmlString);
		
		//拿到交易码，根据交易码调用不同的解析方法，返回一个对象
		String water_flow_number = message_header[2];
		System.out.println("解析报文的操作码water_flow_number="+water_flow_number);
		
		Object o = new Object();
		if(water_flow_number.equals("1000000000")){
			o=parseMessageCustomerOpen(xmlString);
		}
		if(water_flow_number.equals("1000000001")){
			o=parseMessageAccountOpen(xmlString);
		}
		if(water_flow_number.equals("1000001001")){
			o=parseMessageDepositMoney(xmlString);
		}
		if(water_flow_number.equals("1000001010")){
			o=parseMessageWithDrawals(xmlString);
		}
		if(water_flow_number.equals("1000001011")){
			o=parseMessageAccountQuery(xmlString);
		}

		map.put(message_header, o);
		return map;
	}
	public static Account parseMessageAccountQuery(String xmlString) {
		Account a = parseMessageDepositMoney(xmlString);
		return a;
	}
	private static Account parseMessageWithDrawals(String xmlString) {
		System.out.println("客户取款要解析的报文为"+xmlString);
		Account account = new Account();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM操作的时候是要取得根节点
		Element root = doc.getRootElement();// 取得根节点 message
		// 现在应该根据根节点找到全部的子节点，linkman
		Iterator iter = root.elementIterator();
		
		while (iter.hasNext()) {
			
			Element linkman = (Element) iter.next();
			if(linkman.getName().equals("message_body")){	
				int id 		 	 = Integer.parseInt(linkman.elementText("id"));
				String card_id 	 = linkman.elementText("card_id");
				String card_psw 	 = linkman.elementText("card_psw");
				float money 	 = Float.parseFloat(linkman.elementText("money"));			
				account.setId(id);
				account.setCard_id(card_id);
				account.setCard_psw(card_psw);
				account.setMoney(money);
				
				
			}
			
		}
		return account;
	}
	/*
	 * 解析存款报文
	 */
	private static Account parseMessageDepositMoney(String xmlString) {
		
		System.out.println("客户存款要解析的报文为"+xmlString);
		Account account = new Account();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM操作的时候是要取得根节点
		Element root = doc.getRootElement();// 取得根节点 message
		// 现在应该根据根节点找到全部的子节点，linkman
		Iterator iter = root.elementIterator();
		
		while (iter.hasNext()) {
			
			Element linkman = (Element) iter.next();
			if(linkman.getName().equals("message_body")){	
				int id 		 	 = Integer.parseInt(linkman.elementText("id"));
				String card_id 	 = linkman.elementText("card_id");
				String card_psw 	 = linkman.elementText("card_psw");
				float money 	 = Float.parseFloat(linkman.elementText("money"));
			
				account.setId(id);
				account.setCard_id(card_id);
				account.setCard_psw(card_psw);
				account.setMoney(money);
				
				
			}
			
		}
		return account;
	}

	/*
	 * 账户开户,从账户中取款 和存款 的报文体解析方法
	 */
	public static Account parseMessageAccountOpen(String xmlString) {
		System.out.println("账户开户要解析的报文为"+xmlString);
		Account account = new Account();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM操作的时候是要取得根节点
		Element root = doc.getRootElement();// 取得根节点 message
		// 现在应该根据根节点找到全部的子节点，linkman
		Iterator iter = root.elementIterator();
		
		while (iter.hasNext()) {
			
			Element linkman = (Element) iter.next();
			if(linkman.getName().equals("message_body")){	
				int id 		 	 = Integer.parseInt(linkman.elementText("id"));
				String card_id 	 = linkman.elementText("card_id");
				String card_psw 	 = linkman.elementText("card_psw");
				float money 	 = Float.parseFloat(linkman.elementText("money"));
				String type  = linkman.elementText("type");
				int emp_id 	 = Integer.parseInt(linkman.elementText("emp_id"));
				int cuerstom_id   	 = Integer.parseInt(linkman.elementText("cuerstom_id"));
				
				account.setId(id);
				account.setCard_id(card_id);
				account.setCard_psw(card_psw);
				account.setType(type);
				account.setMoney(money);
				account.setEmp_id(emp_id);
				account.setCuerstom_id(cuerstom_id);
				
				
			}
			
		}
		
		return account;
	}
	
	
	/*
	 * 客户开户的报文体解析方法
	 */
	public static Customer parseMessageCustomerOpen(String xmlString) {
		Map<Integer,Customer> map = new HashMap<Integer,Customer>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM操作的时候是要取得根节点
		Element root = doc.getRootElement();// 取得根节点 message
		// 现在应该根据根节点找到全部的子节点，linkman
		Iterator iter = root.elementIterator();
		
		Customer c  = null;
		while (iter.hasNext()) {
			
			Element linkman = (Element) iter.next();
			if(linkman.getName().equals("message_body")){
//				System.out.println("编号：" + linkman.elementText("id"));
//				System.out.println("英文名：" + linkman.elementText("ename")) ;
//				System.out.println("中文名：" + linkman.elementText("cname")) ;
//				System.out.println("国家：" + linkman.elementText("contry")) ;
//				System.out.println("省份：" + linkman.elementText("province")) ;
//				System.out.println("城市：" + linkman.elementText("city"));
//				System.out.println("电话：" + linkman.elementText("tel")) ;
//				System.out.println("创建者：" + linkman.elementText("creator_id")) ;
//				System.out.println("地址：" + linkman.elementText("address")) ;	
				String id 		 = linkman.elementText("id");
				String ename 	 = linkman.elementText("ename");
				String cname 	 = linkman.elementText("cname");
				String contory 	 = linkman.elementText("contry");
				String province  = linkman.elementText("province");
				String city 	 = linkman.elementText("city");
				String tel		 = linkman.elementText("tel");
				int creator   = Integer.parseInt(linkman.elementText("creator_id"));
				String address 	 = linkman.elementText("address");
				
				c = new Customer();
				c.setId(Integer.parseInt(id));
				c.setEname(ename);
				c.setCname(cname);
				c.setContry(contory);
				c.setProvince(province);
				c.setCity(city);
				c.setTel(tel);
				c.setCreator_id(creator);
				c.setAddress(address);
				
			}
			
		}
		System.out.println(c);
		return c;
	}
	/*
	 * 解析xml的头部，返回值为一个message数组
	 * message[0] = 交易流水号
	 * message[1] = 交易柜员编号
	 * message[2] = 交易码
	 * message[3] = 交易时间
	 * message[4] = 交易成功与否的标志
	 */
	public static String[] parseMessageHeader(String xmlString) {
		String[] message_header = new String[5];
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM操作的时候是要取得根节点
		Element root = doc.getRootElement();// 取得根节点 message
		// 现在应该根据根节点找到全部的子节点，message
		Iterator iter = root.elementIterator();
		
				
		
		while(iter.hasNext()) {
			Element message_hb = (Element) iter.next();
		
			if(message_hb.getName().equals("message_header")){
				
				String emp_id			   = message_hb.elementText("emp_number");
				String time 			   = message_hb.elementText("time");
				String trading_code	   = message_hb.elementText("trading_code");
				String water_flow_number  = message_hb.elementText("serial_number");
				String flag  = message_hb.elementText("flag");
				
				message_header[0] = water_flow_number;
				message_header[1] = emp_id;
				message_header[2] = trading_code;
				message_header[3] = time;
				message_header[4] = flag;
		
			}			
		}		
		return message_header;
	}
	/*
	 * 解析返回结果的xml报文
	 */
    public static Map<String,String> parseResult(String xmlString) {
    	Map<String,String> map = new HashMap<String,String>();
    	System.out.println("要解析的返回报文为"+xmlString);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM操作的时候是要取得根节点
		Element root = doc.getRootElement();// 取得根节点 message
		// 现在应该根据根节点找到全部的子节点，return_info
		Iterator iter = root.elementIterator();
		
		while (iter.hasNext()) {
			Element linkman = (Element) iter.next();
			System.out.println("解析返回结果的xml报文"+linkman.getName());
			
			if(linkman.getName().equals("error_tips")){	
				String error_tips	 = linkman.getStringValue();
				System.out.println(error_tips);
				map.put("error_tips", error_tips);	
			}else{
				String vaule	 = linkman.getStringValue();
				String name = linkman.getName();
				System.out.println(name);
				System.out.println(vaule);
				map.put(name, vaule);
				
			}
			
		}
		
		return map;
	}


		
}
