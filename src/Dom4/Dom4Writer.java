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
	 * 创建报文头，因为所有交易报文头都一样，因此所有交易组报文头的方法是一致的
	 * 报文头的格式如下：
	 * <message>
	 * 		<message-header>
	 * 			<emp_number>20000</emp_number>   
	 * 			<time>2016-07-17 18-44-50</time>
	 * 			<trading_code>1000000000</trading_code>
	 * 			<serial_number>14687576979131000000000</serial_number>
	 * 		</message-header>
	 * <message>
	 * 
	 * param :	String emp_number   对应 <emp_number>*</emp_number> 
	 * param :	String trading_code 对应 <trading_code>*</emp_number>
	 * return:	Document doc 返回该文档对象
	 * 备注：1.报文头中的time，在组报文的 时候创建
	 *      2.报文头中的serial_number，由trading_code和time拼接而成
	 * 
	 */
	public static Document createMessageHeader(String emp_number,String trading_code){
		//拿到文档对象
		Document doc 		   = DocumentHelper.createDocument() ;
		
		//创建根节点 messsage
		Element message		   = doc.addElement("message") ;
		//创建节点
		Element message_header = message.addElement("message_header") ;	
		
		//给message_header父节点添加子节点，共4个子节点
		Element m_emp_number  	 = message_header.addElement("emp_number") ;
		Element m_time 			 = message_header.addElement("time") ;
		Element m_trading_code   = message_header.addElement("trading_code") ;
		Element m_serial_number  = message_header.addElement("serial_number") ;
		Element m_falg 			 = message_header.addElement("flag") ;
		
		//创建节点的值
		String current_time		 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String serial_number = Long.toString(System.currentTimeMillis());
		serial_number += trading_code;
	
		//给对应节点设置值
		m_emp_number.setText(emp_number);
		m_time.setText(current_time);
		m_trading_code.setText(trading_code);
		m_serial_number.setText(serial_number);
		m_falg.setText("true");
		
		//返回该文档对象，方便以后创建报文体
		return doc;
	}
	
	
	/*
	 * 创建报文体
	 * param  :Object bean  	该报文对应的实体类对象
	 * param  :Document doc 	文档对象
	 * return :String xmlString 返回组装好的整个xml格式报文字符串
	 */
	public static String createMessageBody(Object bean,Document doc ){
		
		//拿到文档的根节点
		Element message 	     = doc.getRootElement();
		//创建 message_body报文体节点
		Element message_body     = message.addElement("message_body") ;
		
		//利用BeanUtils工具类获取bean对象的键值对：属性-值
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
		//遍历map，获取键和值，在添加到父节点message_body
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
		
		//返回
		return xmlString;
		
	}
	
	public static String  createMessage(String emp_number,String serial_number,Object o){
		String xmlString	= null;
		Document doc 	 = createMessageHeader(emp_number,serial_number);
		xmlString 		 = createMessageBody(o,doc);
		return xmlString;
	}

	/*
	 * 返回结果组包
	 */
	//操作成功返回消息
		 //不同的交易返回结果不一样，组装的xml不一样
		 //因此要根据交易码组装xml
		 //客户开户返回："成功"或者"失败"
		 //账户开户返回:"成功"或者"失败"
		 //客户存款返回 :"账户不存在或者密码错误"或者"存款失败"或者"存款成功"
		 //客户取款返回："账户不存在或者密码错误"或者"余额不足取款失败"或者"取款成功"
	public static String getOpDBResultXml(Map<String,String> map) {
		//拿到文档对象
		Document doc 		   = DocumentHelper.createDocument() ;
		//创建根节点 return_info
		Element return_info		   = doc.addElement("return_info") ;	
				
		//给return_info父节点添加子节点
		//循环map得到key-value
		for(Map.Entry<String,String> entry:map.entrySet()){
			String key   = entry.getKey();
			String value = entry.getValue();
			
			Element rs			 = return_info.addElement(key);
			rs.setText(value);
		}
		String xmlString = doc.asXML();
		xmlString 		 = xmlString.replaceAll("\n","");
		//返回该文档对象，方便以后创建报文体
		return xmlString;
	}

	 
	

}
