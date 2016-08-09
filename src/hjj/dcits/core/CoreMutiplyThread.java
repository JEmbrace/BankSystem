package hjj.dcits.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Message;
import javax.jms.TextMessage;

import Dom4.Dom4Reader;
import Dom4.Dom4Writer;
import hjj.dcits.esb.JMSToolsOperate;
import hjj.dcits.teller.deal.WaterFlowInfo;
import hjj.dcits.teller.entities.Account;
import hjj.dcits.teller.entities.Customer;
import hjj.dcits.teller.entities.WaterFlow;

public class CoreMutiplyThread implements Runnable{
	private Message message = null;
	public CoreMutiplyThread(Message msg){
		message = msg;
	}	
	@Override
	public void run() {
		// TODO Auto-generated method stub
				if (message instanceof TextMessage) {
					TextMessage TextMsg = (TextMessage) message;
					try {
						String xml = (String) TextMsg.getText();
						//拿到xml形式的String后
						//解析成需要的东西
						//具体操作如下
						//1.把xml解析成对象
						//Customer c = Dom4Reader.parseXMLString(xml);
						//2.调用操作数据库的方法,进行插入
						
						System.out.println("CORE收到的消息是"+xml);
						
						//返回大写，但是之后Dom4不能解析。会出现错误
						//org.dom4j.DocumentException: Error on line 1 of document  
						//: 不允许有匹配 "[xX][mM][lL]" 的处理指令目标。
						//Nested exception: 不允许有匹配 "[xX][mM][lL]" 的处理指令目标。
						//String newxml = xml.toUpperCase(); 
						
						//把xml解析出来，是一个map，键为交易码，值为customer对象
						String[] messageHeader = new String[5];
						
						 System.out.println("开始解析报文头部------------");
						 messageHeader       = Dom4Reader.parseMessageHeader(xml);
						 String teading_code = messageHeader[2];
						 System.out.println(messageHeader[0]);
						 System.out.println(messageHeader[1]);
						 System.out.println(messageHeader[2]);
						 System.out.println(messageHeader[3]);
						 System.out.println(messageHeader[4]);
						 System.out.println("解析出的报文头中的交易码为："+teading_code);
						 
						 //根据交易码使用反射机制调用对应的core类，操作数据库
						 
						
						//更新相应的业务逻辑表
						 System.out.println("**********开始利用反射操作数据库！*********");
						 CoreReflaction cr = new CoreReflaction();
						 Map<String,String> map = null;
						 map    = (Map<String,String>)cr.reflactionClass(xml);						 
						 System.out.println("******操作数据库结束！*******");
						/////////////////////////////////////////
						 
						 //更新流水表
						 String rs_str = "";
						 if(map.size() == 1){
							for(Map.Entry<String,String> entry:map.entrySet()){
								rs_str = entry.getValue();
							}
						 }else{
							 rs_str="账户查询成功";
						 }
						 System.out.println("*********开始操作流水表！*********");
						 messageHeader[4] = rs_str;
						 WaterFlowInfo.insertWaterFlowInfo(messageHeader);
						 System.out.println("**********流水表操作成功！********");
						 
						 //操作成功返回消息
						 //不同的交易返回结果不一样，组装的xml不一样
						 //因此要根据交易码组装xml
						 //客户开户返回："成功"或者"失败"
						 //账户开户返回:"成功"或者"失败"
						 //客户存款返回 :"账户不存在或者密码错误"或者"存款失败"或者"存款成功"
						 //客户取款返回："账户不存在或者密码错误"或者"余额不足取款失败"或者"取款成功"
						 //查询账户信息返回："账户不存在或者密码错误"或者一个Map<Customer,Account>
						 //-------------------------------------
						 String return_xml = null;
				         return_xml = Dom4Writer.getOpDBResultXml(map);
						//------------------------------------
						JMSToolsOperate tools = new JMSToolsOperate();
						tools.sendMsg(return_xml, "C2E");
						System.out.println("CORE发回的消息是"+return_xml);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
	}
	}
