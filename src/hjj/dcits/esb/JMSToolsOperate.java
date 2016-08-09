package hjj.dcits.esb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSToolsOperate {
	// ���岢�������ӹ���
	private ConnectionFactory factory = new ActiveMQConnectionFactory(
			"tcp://localhost:61616");
	// ���嵽��Ϣ���е�����
	private Connection connection;
	// ���屾�����ӵĻỰ
	private Session session;
	
	private String id;
	public void sendMsg(String message, String queueName) {
		try {
			// ��������
			connection = factory.createConnection();
			// ������
			connection.start();
			// �����������ӵĻỰ
			//Session.AUTO_ACKNOWLEDGE�Զ�ȷ�� ���������յ���Ϣ֮�󣬱�������Ϣ�ṩ��ȷ�� ����ᱻ��Ϊ��Ϣû�б�����
			//�ɱ�֤
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// ����һ����Ϣ���У���������д�����ʹ��֮�����򴴽�֮
			Destination queue = session.createQueue(queueName);
			// ����һ����Ϣһ����Ϣ�ɱ�һ������������
			Message msg = session.createTextMessage(message);
			
			
			//-------------------------begin-------------------------------------------
			//����Ϣ����id����ֹ����
			//��idΪ�߳�id��ʱ�����ƴ��
			
			//��ȡ��ǰ�߳�thread_id
			String thread_id  	 		= Long.toString(Thread.currentThread().getId());
			//��ǰʱ���
			String timeStamp    = Long.toString(System.currentTimeMillis());
			//ƴ���߳�id��ʱ���
			 id = thread_id+timeStamp;
			 System.out.println("���������õ�id="+id);
			////����Ϣ����id����ֹ����
			msg.setStringProperty("MESSAGE_ID",id);
			
			//------------------------end------------------------------------------------
			
			
			
			// ����һ��������,����Ϣ�ķ����ߣ���ָ����Ҫ������Ϣ��Ŀ�ĵأ�����Ϣ���У�
			MessageProducer producer = session.createProducer(queue);
			// ������Ϣ
			producer.send(msg);
			// �ͷ���Դ

		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public String receiveMsg(String queueName) {
		String message = null;
		try {
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination queue = session.createQueue(queueName);
			// ����һ����Ϣ�������ߣ�����Ϣ�Ľ����ߣ���ָ�����Ľ���Ŀ�ĵأ������ĸ���Ϣ������ȡ��Ϣ��
			MessageConsumer consumer = session.createConsumer(queue);
			//----------------------begin----------------------------------------------
			//�õ�message����
			Message msg 		 = consumer.receive();
			
			//�õ�id
			String recive_msg_id = msg.getStringProperty("MESSAGE_ID");
			System.out.println("ESB�˵õ��ı��ĵ�id="+recive_msg_id);
			//����õ���id��֮ǰ���õ�id�������
			if(id.equals(id)){
				message = ((TextMessage)msg).getText();
			}
			//------------------------end--------------------------------------------
			
			// ������Ϣ
			//message = ((TextMessage) consumer.receive()).getText();
			
			
			// �ͷ���Դ
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		return message;
	}
}
