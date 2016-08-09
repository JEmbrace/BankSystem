package hjj.dcits.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import Dom4.Dom4Reader;
import Dom4.Dom4Writer;
import hjj.dcits.esb.JMSToolsOperate;
import hjj.dcits.teller.deal.CustomerOpen;
import hjj.dcits.teller.entities.Customer;


public class CoreListener implements MessageListener{

	public CoreListener() {
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616");

		Connection connection;
		try {
			connection = factory.createConnection();
			connection.start();

			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("E2C");

			MessageConsumer consumer = session.createConsumer(destination);

			consumer.setMessageListener(this);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onMessage(Message message) {
		new Thread(new CoreMutiplyThread(message)).start();
	}
	
}
