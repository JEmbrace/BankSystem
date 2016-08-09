package hjj.dcits.esb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ESBListener implements Runnable {

	private JMSToolsOperate tools=new JMSToolsOperate();
	private Socket s;
	
	public ESBListener(Socket s){
		this.s=s;
	}
	
	public void run() {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
			//接收从TELLER端发来的消息
			String reMsg=br.readLine();
			System.out.println("ESB端已经成功的接收到TELLER端的请求消息 "+reMsg);
			//将消息放入消息队列E2C中
			tools.sendMsg(reMsg, "E2C");
			System.out.println("ESB端已经成功的向消息队列E2C放入消息 "+reMsg);
			//从消息队列C2E中取出消息
			String result=tools.receiveMsg("C2E");
			System.out.println("ESB端已经成功的从消息队列C2E中取出响应消息 "+result);
			//将返回消息发往TELLER端
			pw.println(result);
			System.out.println("ESB端已经成功的向TELLER端发送响应消息 "+result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
