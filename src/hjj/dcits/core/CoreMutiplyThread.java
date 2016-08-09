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
						//�õ�xml��ʽ��String��
						//��������Ҫ�Ķ���
						//�����������
						//1.��xml�����ɶ���
						//Customer c = Dom4Reader.parseXMLString(xml);
						//2.���ò������ݿ�ķ���,���в���
						
						System.out.println("CORE�յ�����Ϣ��"+xml);
						
						//���ش�д������֮��Dom4���ܽ���������ִ���
						//org.dom4j.DocumentException: Error on line 1 of document  
						//: ��������ƥ�� "[xX][mM][lL]" �Ĵ���ָ��Ŀ�ꡣ
						//Nested exception: ��������ƥ�� "[xX][mM][lL]" �Ĵ���ָ��Ŀ�ꡣ
						//String newxml = xml.toUpperCase(); 
						
						//��xml������������һ��map����Ϊ�����룬ֵΪcustomer����
						String[] messageHeader = new String[5];
						
						 System.out.println("��ʼ��������ͷ��------------");
						 messageHeader       = Dom4Reader.parseMessageHeader(xml);
						 String teading_code = messageHeader[2];
						 System.out.println(messageHeader[0]);
						 System.out.println(messageHeader[1]);
						 System.out.println(messageHeader[2]);
						 System.out.println(messageHeader[3]);
						 System.out.println(messageHeader[4]);
						 System.out.println("�������ı���ͷ�еĽ�����Ϊ��"+teading_code);
						 
						 //���ݽ�����ʹ�÷�����Ƶ��ö�Ӧ��core�࣬�������ݿ�
						 
						
						//������Ӧ��ҵ���߼���
						 System.out.println("**********��ʼ���÷���������ݿ⣡*********");
						 CoreReflaction cr = new CoreReflaction();
						 Map<String,String> map = null;
						 map    = (Map<String,String>)cr.reflactionClass(xml);						 
						 System.out.println("******�������ݿ������*******");
						/////////////////////////////////////////
						 
						 //������ˮ��
						 String rs_str = "";
						 if(map.size() == 1){
							for(Map.Entry<String,String> entry:map.entrySet()){
								rs_str = entry.getValue();
							}
						 }else{
							 rs_str="�˻���ѯ�ɹ�";
						 }
						 System.out.println("*********��ʼ������ˮ��*********");
						 messageHeader[4] = rs_str;
						 WaterFlowInfo.insertWaterFlowInfo(messageHeader);
						 System.out.println("**********��ˮ������ɹ���********");
						 
						 //�����ɹ�������Ϣ
						 //��ͬ�Ľ��׷��ؽ����һ������װ��xml��һ��
						 //���Ҫ���ݽ�������װxml
						 //�ͻ��������أ�"�ɹ�"����"ʧ��"
						 //�˻���������:"�ɹ�"����"ʧ��"
						 //�ͻ����� :"�˻������ڻ����������"����"���ʧ��"����"���ɹ�"
						 //�ͻ�ȡ��أ�"�˻������ڻ����������"����"����ȡ��ʧ��"����"ȡ��ɹ�"
						 //��ѯ�˻���Ϣ���أ�"�˻������ڻ����������"����һ��Map<Customer,Account>
						 //-------------------------------------
						 String return_xml = null;
				         return_xml = Dom4Writer.getOpDBResultXml(map);
						//------------------------------------
						JMSToolsOperate tools = new JMSToolsOperate();
						tools.sendMsg(return_xml, "C2E");
						System.out.println("CORE���ص���Ϣ��"+return_xml);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
	}
	}
