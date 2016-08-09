package Dom4;



import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import hjj.dcits.teller.entities.Account;
import hjj.dcits.teller.entities.Customer;


public class Dom4Writer {
	/*
	 * ��������ͷ����Ϊ���н��ױ���ͷ��һ����������н����鱨��ͷ�ķ�����һ�µ�
	 * ����ͷ�ĸ�ʽ���£�
	 * <message>
	 * 		<message-header>
	 * 			<emp_number>20000</emp_number>   
	 * 			<time>2016-07-17 18-44-50</time>
	 * 			<trading_code>1000000000</trading_code>
	 * 			<serial_number>14687576979131000000000</serial_number>
	 * 		</message-header>
	 * <message>
	 * 
	 * param :	String emp_number   ��Ӧ <emp_number>*</emp_number> 
	 * param :	String trading_code ��Ӧ <trading_code>*</emp_number>
	 * return:	Document doc ���ظ��ĵ�����
	 * ��ע��1.����ͷ�е�time�����鱨�ĵ� ʱ�򴴽�
	 *      2.����ͷ�е�serial_number����trading_code��timeƴ�Ӷ���
	 * 
	 */
	public static Document createMessageHeader(String emp_number,String trading_code){
		//�õ��ĵ�����
		Document doc 		   = DocumentHelper.createDocument() ;
		
		//�������ڵ� messsage
		Element message		   = doc.addElement("message") ;
		//�����ڵ�
		Element message_header = message.addElement("message_header") ;	
		
		//��message_header���ڵ�����ӽڵ㣬��4���ӽڵ�
		Element m_emp_number  	 = message_header.addElement("emp_number") ;
		Element m_time 			 = message_header.addElement("time") ;
		Element m_trading_code   = message_header.addElement("trading_code") ;
		Element m_serial_number  = message_header.addElement("serial_number") ;
		Element m_falg 			 = message_header.addElement("flag") ;
		
		//�����ڵ��ֵ
		String current_time		 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String serial_number = Long.toString(System.currentTimeMillis());
		serial_number += trading_code;
	
		//����Ӧ�ڵ�����ֵ
		m_emp_number.setText(emp_number);
		m_time.setText(current_time);
		m_trading_code.setText(trading_code);
		m_serial_number.setText(serial_number);
		m_falg.setText("true");
		
		//���ظ��ĵ����󣬷����Ժ󴴽�������
		return doc;
	}
	
	
	/*
	 * ����������
	 * param  :Object bean  	�ñ��Ķ�Ӧ��ʵ�������
	 * param  :Document doc 	�ĵ�����
	 * return :String xmlString ������װ�õ�����xml��ʽ�����ַ���
	 */
	public static String createMessageBody(Object bean,Document doc ){
		
		//�õ��ĵ��ĸ��ڵ�
		Element message 	     = doc.getRootElement();
		//���� message_body������ڵ�
		Element message_body     = message.addElement("message_body") ;
		
		//����BeanUtils�������ȡbean����ļ�ֵ�ԣ�����-ֵ
		Map<String,String> map = new HashMap<String,String>();
		try {
			map						 = BeanUtils.describe(bean);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//����map����ȡ����ֵ������ӵ����ڵ�message_body
		for(Map.Entry<String,String> key_value : map.entrySet()){
			String key 		= key_value.getKey();
			String value   		= key_value.getValue();
			if(!(value == null)){
				Element e_key		= message_body.addElement(key);
				e_key.setText(value);
			}
			
		}

		String xmlString = doc.asXML();
		xmlString 		 = xmlString.replaceAll("\n","");
		
		//����
		return xmlString;
		
	}
	
	public static String  createMessage(String emp_number,String serial_number,Object o){
		String xmlString	= null;
		Document doc 	 = createMessageHeader(emp_number,serial_number);
		xmlString 		 = createMessageBody(o,doc);
		return xmlString;
	}

	/*
	 * ���ؽ�����
	 */
	//�����ɹ�������Ϣ
		 //��ͬ�Ľ��׷��ؽ����һ������װ��xml��һ��
		 //���Ҫ���ݽ�������װxml
		 //�ͻ��������أ�"�ɹ�"����"ʧ��"
		 //�˻���������:"�ɹ�"����"ʧ��"
		 //�ͻ����� :"�˻������ڻ����������"����"���ʧ��"����"���ɹ�"
		 //�ͻ�ȡ��أ�"�˻������ڻ����������"����"����ȡ��ʧ��"����"ȡ��ɹ�"
	public static String getOpDBResultXml(Map<String,String> map) {
		//�õ��ĵ�����
		Document doc 		   = DocumentHelper.createDocument() ;
		//�������ڵ� return_info
		Element return_info		   = doc.addElement("return_info") ;	
				
		//��return_info���ڵ�����ӽڵ�
		//ѭ��map�õ�key-value
		for(Map.Entry<String,String> entry:map.entrySet()){
			String key   = entry.getKey();
			String value = entry.getValue();
			
			Element rs			 = return_info.addElement(key);
			rs.setText(value);
		}
		String xmlString = doc.asXML();
		xmlString 		 = xmlString.replaceAll("\n","");
		//���ظ��ĵ����󣬷����Ժ󴴽�������
		return xmlString;
	}

	 
	

}
