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
	 * message[0] = ������ˮ��
	 * message[1] = ���׹�Ա���
	 * message[2] = ������
	 * message[3] = ����ʱ��
	 * 
	 */
	public static Map<String[],Object>  parseMessage(String xmlString){
		Map<String[],Object> map = new HashMap<String[],Object>();
		String[] message_header  = new String[4];
		
		//����ͷ������Ϊ���б���ͷ����Ϣһ�£�����ͳһ����ͬһ��������
		message_header			 = parseMessageHeader(xmlString);
		
		//�õ������룬���ݽ�������ò�ͬ�Ľ�������������һ������
		String water_flow_number = message_header[2];
		System.out.println("�������ĵĲ�����water_flow_number="+water_flow_number);
		
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
		System.out.println("�ͻ�ȡ��Ҫ�����ı���Ϊ"+xmlString);
		Account account = new Account();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM������ʱ����Ҫȡ�ø��ڵ�
		Element root = doc.getRootElement();// ȡ�ø��ڵ� message
		// ����Ӧ�ø��ݸ��ڵ��ҵ�ȫ�����ӽڵ㣬linkman
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
	 * ��������
	 */
	private static Account parseMessageDepositMoney(String xmlString) {
		
		System.out.println("�ͻ����Ҫ�����ı���Ϊ"+xmlString);
		Account account = new Account();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM������ʱ����Ҫȡ�ø��ڵ�
		Element root = doc.getRootElement();// ȡ�ø��ڵ� message
		// ����Ӧ�ø��ݸ��ڵ��ҵ�ȫ�����ӽڵ㣬linkman
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
	 * �˻�����,���˻���ȡ�� �ʹ�� �ı������������
	 */
	public static Account parseMessageAccountOpen(String xmlString) {
		System.out.println("�˻�����Ҫ�����ı���Ϊ"+xmlString);
		Account account = new Account();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM������ʱ����Ҫȡ�ø��ڵ�
		Element root = doc.getRootElement();// ȡ�ø��ڵ� message
		// ����Ӧ�ø��ݸ��ڵ��ҵ�ȫ�����ӽڵ㣬linkman
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
	 * �ͻ������ı������������
	 */
	public static Customer parseMessageCustomerOpen(String xmlString) {
		Map<Integer,Customer> map = new HashMap<Integer,Customer>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM������ʱ����Ҫȡ�ø��ڵ�
		Element root = doc.getRootElement();// ȡ�ø��ڵ� message
		// ����Ӧ�ø��ݸ��ڵ��ҵ�ȫ�����ӽڵ㣬linkman
		Iterator iter = root.elementIterator();
		
		Customer c  = null;
		while (iter.hasNext()) {
			
			Element linkman = (Element) iter.next();
			if(linkman.getName().equals("message_body")){
//				System.out.println("��ţ�" + linkman.elementText("id"));
//				System.out.println("Ӣ������" + linkman.elementText("ename")) ;
//				System.out.println("��������" + linkman.elementText("cname")) ;
//				System.out.println("���ң�" + linkman.elementText("contry")) ;
//				System.out.println("ʡ�ݣ�" + linkman.elementText("province")) ;
//				System.out.println("���У�" + linkman.elementText("city"));
//				System.out.println("�绰��" + linkman.elementText("tel")) ;
//				System.out.println("�����ߣ�" + linkman.elementText("creator_id")) ;
//				System.out.println("��ַ��" + linkman.elementText("address")) ;	
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
	 * ����xml��ͷ��������ֵΪһ��message����
	 * message[0] = ������ˮ��
	 * message[1] = ���׹�Ա���
	 * message[2] = ������
	 * message[3] = ����ʱ��
	 * message[4] = ���׳ɹ����ı�־
	 */
	public static String[] parseMessageHeader(String xmlString) {
		String[] message_header = new String[5];
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM������ʱ����Ҫȡ�ø��ڵ�
		Element root = doc.getRootElement();// ȡ�ø��ڵ� message
		// ����Ӧ�ø��ݸ��ڵ��ҵ�ȫ�����ӽڵ㣬message
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
	 * �������ؽ����xml����
	 */
    public static Map<String,String> parseResult(String xmlString) {
    	Map<String,String> map = new HashMap<String,String>();
    	System.out.println("Ҫ�����ķ��ر���Ϊ"+xmlString);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// JDOM������ʱ����Ҫȡ�ø��ڵ�
		Element root = doc.getRootElement();// ȡ�ø��ڵ� message
		// ����Ӧ�ø��ݸ��ڵ��ҵ�ȫ�����ӽڵ㣬return_info
		Iterator iter = root.elementIterator();
		
		while (iter.hasNext()) {
			Element linkman = (Element) iter.next();
			System.out.println("�������ؽ����xml����"+linkman.getName());
			
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
