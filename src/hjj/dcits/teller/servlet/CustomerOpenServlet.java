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
		System.out.println("*******************Teller�˿�ʼִ��***********************************");
		response.setContentType("text/html;charset=utf-8"); 
		//���ô��ݺͽ��ղ����ı���
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //********************************************************
        //��ȡ���׺� �� ��Ա��id
        String trading_code = request.getParameter("powerid");
        String emp_id = request.getParameter("empid");
        String emp_name = request.getParameter("empname");
        System.out.println("��ν��׵Ľ��׺�trading_code ="+trading_code);
        System.out.println("��ν��׵Ĺ�Ա��emp_id ="+emp_id);        
        System.out.println("��ν��׵Ĺ�Ա��emp_name ="+emp_name);
        //********************************************************
        
        //**********************��ȡform���ļ�ֵ�Բ������Ϊxml��ʽ���ַ���******************
        //1.��ȡform���ļ�ֵ��
        Map<String,String[]> map = new HashMap<String,String[]>();
        map						 = request.getParameterMap();
        
        //���ݽ������ȡ��ν��׶�Ӧ��ʵ����
        String beanName = Reflaction.getFormPropBean(trading_code);
        Object beanObj  = Reflaction.getBeanByClassName(beanName);
        System.out.println("���ν��׶�Ӧ��bean������beanName="+beanName);
        System.out.println("���ν��׶�Ӧ��bean����beanObj="+beanObj);
        
        for(Map.Entry<String,String[]> key_value:map.entrySet()){
        	String key   = key_value.getKey();
        	String value = key_value.getValue()[0];
        	System.out.println("�ӱ���ȡ��key="+key+",value="+value);
        	//2.����BeanUtils���������Ѽ�ֵ��װ�䵽bean
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
        System.out.print("����BeanUtils������װ����bean����beanObj="+beanObj);
        //3.����xml��ʽ�ı���
		String str_info = Dom4Writer.createMessage(emp_id,trading_code,beanObj);
		System.out.println("���ν��״����ı����������£�"+str_info);
		
		//����socketͨ��
        //���ӷ�����
        Socket s=new Socket(url,port);
        System.out.println("TELLER���Ѿ��ɹ������ӵ�ESB�ˣ�");
        //��װ���������
        PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        //��socketͨ��д����Ϣ
        pw.println(str_info);
        System.out.println("TELLER���Ѿ��ɹ�����ESB�˷�����Ϣ  "+str_info);
        //��socketͨ��ȡ����˷��صĽ��
        String result=br.readLine();
        System.out.println("TELLER���Ѿ��ɹ��Ĵ�ESB�˽��յ���Ӧ��Ϣ " +result);
        
        //Teller�˽������ؽ��
        System.out.println("TELLER�˿�ʼ������Ӧ��Ϣ ");
        Map<String,String> rs_map = null;
        rs_map = Dom4Reader.parseResult(result);
        String tip = null;
        for(Map.Entry<String,String> e:rs_map.entrySet()){
        	tip = e.getValue();
        }
        System.out.print("�ͻ��������ؽ����"+tip);
        PrintWriter rs_pw = response.getWriter();
        rs_pw.print("<html>");
        rs_pw.print("<body bgColor='#FF4081'>");
        rs_pw.print("<center>");
        rs_pw.print("<h1>�������:"+tip+"</h1>");
        rs_pw.print("</center>");
        rs_pw.print("</body>");
        rs_pw.print("</html>");
        
        System.out.println("*******************Teller��ִ�н���***********************************");
        //**************************end**********************************************************
     
        
	}

}
