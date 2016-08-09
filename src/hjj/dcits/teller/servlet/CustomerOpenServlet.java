package hjj.dcits.teller.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import Dom4.Dom4Reader;
import Dom4.Dom4Writer;
import hjj.dcits.teller.dao.BankEmpDao;
import hjj.dcits.teller.deal.GetDynamicMenu;
import hjj.dcits.teller.entities.Customer;
import hjj.dcits.teller.entities.Employees;
import hjj.dcits.teller.entities.Menus;
import hjj.dcits.teller.util.Reflaction;

@WebServlet("/CustomerOpenServlet")
public class CustomerOpenServlet extends HttpServlet {
	private static final long serialVersionUID = 4L;
    
	private String url="127.0.0.1";
	private int port=8888;
		
    public CustomerOpenServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //*************************************begin***********************************************
		System.out.println("*******************Teller端开始执行***********************************");
		response.setContentType("text/html;charset=utf-8"); 
		//设置传递和接收参数的编码
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //********************************************************
        //获取交易号 和 柜员的id
        String trading_code = request.getParameter("powerid");
        String emp_id = request.getParameter("empid");
        String emp_name = request.getParameter("empname");
        System.out.println("这次交易的交易号trading_code ="+trading_code);
        System.out.println("这次交易的柜员号emp_id ="+emp_id);        
        System.out.println("这次交易的柜员名emp_name ="+emp_name);
        //********************************************************
        
        //**********************获取form表单的键值对并组包成为xml格式的字符串******************
        //1.获取form表单的键值对
        Map<String,String[]> map = new HashMap<String,String[]>();
        map						 = request.getParameterMap();
        
        //根据交易码获取这次交易对应的实体类
        String beanName = Reflaction.getFormPropBean(trading_code);
        Object beanObj  = Reflaction.getBeanByClassName(beanName);
        System.out.println("本次交易对应的bean的名称beanName="+beanName);
        System.out.println("本次交易对应的bean对象beanObj="+beanObj);
        
        for(Map.Entry<String,String[]> key_value:map.entrySet()){
        	String key   = key_value.getKey();
        	String value = key_value.getValue()[0];
        	System.out.println("从表单获取的key="+key+",value="+value);
        	//2.利用BeanUtils这个工具类把键值对装配到bean
        	try {
				BeanUtils.setProperty(beanObj,key,value);
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        System.out.print("利用BeanUtils工具类装配后的bean对象beanObj="+beanObj);
        //3.创建xml格式的报文
		String str_info = Dom4Writer.createMessage(emp_id,trading_code,beanObj);
		System.out.println("本次交易创建的报文内容如下："+str_info);
		
		//建立socket通信
        //连接服务器
        Socket s=new Socket(url,port);
        System.out.println("TELLER端已经成功的连接到ESB端！");
        //封装输入输出流
        PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        //向socket通道写入消息
        pw.println(str_info);
        System.out.println("TELLER端已经成功的向ESB端发送消息  "+str_info);
        //从socket通道取出后端返回的结果
        String result=br.readLine();
        System.out.println("TELLER端已经成功的从ESB端接收到响应消息 " +result);
        
        //Teller端解析返回结果
        System.out.println("TELLER端开始解析响应消息 ");
        Map<String,String> rs_map = null;
        rs_map = Dom4Reader.parseResult(result);
        String tip = null;
        for(Map.Entry<String,String> e:rs_map.entrySet()){
        	tip = e.getValue();
        }
        System.out.print("客户开户返回结果："+tip);
        PrintWriter rs_pw = response.getWriter();
        rs_pw.print("<html>");
        rs_pw.print("<body bgColor='#FF4081'>");
        rs_pw.print("<center>");
        rs_pw.print("<h1>操作结果:"+tip+"</h1>");
        rs_pw.print("</center>");
        rs_pw.print("</body>");
        rs_pw.print("</html>");
        
        System.out.println("*******************Teller端执行结束***********************************");
        //**************************end**********************************************************
     
        
	}

}
