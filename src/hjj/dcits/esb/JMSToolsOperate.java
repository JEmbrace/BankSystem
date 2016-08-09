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
	// 定义并创建链接工厂
	private ConnectionFactory factory = new ActiveMQConnectionFactory(
			"tcp://localhost:61616");
	// 定义到消息队列的链接
	private Connection connection;
	// 定义本次链接的会话
	private Session session;
	
	private String id;
	public void sendMsg(String message, String queueName) {
		try {
			// 创建连接
			connection = factory.createConnection();
			// 打开连接
			connection.start();
			// 创建本次链接的会话
			//Session.AUTO_ACKNOWLEDGE自动确认 即消费者收到消息之后，必须向信息提供者确认 否则会被认为消息没有被消费
			//可保证
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列，如果本队列存在则使用之，否则创建之
			Destination queue = session.createQueue(queueName);
			// 创建一个消息一条消息可被一个消费者消费
			Message msg = session.createTextMessage(message);
			
			
			//-------------------------begin-------------------------------------------
			//给消息设置id，防止串包
			//该id为线程id和时间戳的拼接
			
			//获取当前线程thread_id
			String thread_id  	 		= Long.toString(Thread.currentThread().getId());
			//当前时间戳
			String timeStamp    = Long.toString(System.currentTimeMillis());
			//拼接线程id和时间戳
			 id = thread_id+timeStamp;
			 System.out.println("给报文设置的id="+id);
			////给消息设置id，防止串包
			msg.setStringProperty("MESSAGE_ID",id);
			
			//------------------------end------------------------------------------------
			
			
			
			// 创建一个生产者,即消息的发送者，并指定他要发送消息的目的地（即消息队列）
			MessageProducer producer = session.createProducer(queue);
			// 发送消息
			producer.send(msg);
			// 释放资源

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
			// 创建一个消息的消费者，即消息的接受者，并指定他的接受目的地（即从哪个消息队列里取消息）
			MessageConsumer consumer = session.createConsumer(queue);
			//----------------------begin----------------------------------------------
			//拿到message对象
			Message msg 		 = consumer.receive();
			
			//拿到id
			String recive_msg_id = msg.getStringProperty("MESSAGE_ID");
			System.out.println("ESB端得到的报文的id="+recive_msg_id);
			//如果拿到的id是之前设置的id，则接收
			if(id.equals(id)){
				message = ((TextMessage)msg).getText();
			}
			//------------------------end--------------------------------------------
			
			// 接受消息
			//message = ((TextMessage) consumer.receive()).getText();
			
			
			// 释放资源
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
