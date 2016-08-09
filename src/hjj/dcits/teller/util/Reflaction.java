package hjj.dcits.teller.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import Dom4.Dom4Reader;

public class Reflaction {
	
	/*
	 * ���ݽ������������ļ��л�ȡ �������ݿ� ������
	 * param	:String trading_code  ������
	 * return 	:String className
	 */
	public static String getDaoClassNameFormProp(String trading_code){
		String path="E://JavaWeb//WIN64//DCITS//SixDayUp//src//trade_code_to_core.properties";
		InputStream in = null;
		//System.out.println("������ trading_code = "+trading_code);
		try {
			in = new BufferedInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		Properties p = new Properties();
		
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(p);		
		String className = p.getProperty(trading_code);
		
		//System.out.print("������"+className);
		return className;
	}
	
	/*
	 * ���ݽ������ȡ���ν��׶�Ӧ��ʵ���������
	 * param	:String trading_code
	 * return	:String className
	 */
	public static String getFormPropBean(String trading_code){
		String path="E://JavaWeb//WIN64//DCITS//SixDayUp//src//trade_code_to_bean.properties";
		InputStream in = null;
		System.out.println("������ trading_code = "+trading_code);
		try {
			in = new BufferedInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		Properties p = new Properties();
		
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(p);		
		String className = p.getProperty(trading_code);
		
		System.out.print("������"+className);
		return className;
	}
	
	/*
	 *����ʵ�������������� һ��ʵ���࣬�����ظ����һ������
	 *return	:	Object o
	 */
	public static Object getBeanByClassName(String className){
		Class classes = null;
		Object bean      = null;
			
		try {
			classes 	= Class.forName("hjj.dcits.teller.entities."+className);
			bean 		= classes.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	/*
	 * ���ݱ��Ĵ�����Ӧ��ʵ����
	 */
	public static Object createJavaBean(String xml) {
		 Object o = null;
		 Map<String[], Object> map = new  HashMap<String[], Object>();
		 map = Dom4Reader.parseMessage(xml);
		 for(Map.Entry<String[],Object> entry : map.entrySet()){
			o   = entry.getValue();
		 }
		return o;
	}
	
	/*
	 * ���ݱ��ķ��䲢���ò������ݿ��Dao��
	 */
	public static Map<String,String> reflactClassAndMethod(String xml){
		Map<String,String> map = new HashMap<String,String>();
		//*****************************************
		String trading_code = Dom4Reader.parseMessageHeader(xml)[2];
		Object javaBeanObj	= createJavaBean(xml);
		String className    = getDaoClassNameFormProp(trading_code);
		
		System.out.println("����õ�ʵ����javaBeanObj="+javaBeanObj);
		System.out.println("����õ�Dao������="+className);
		//*****************************************
		Class classes = null;
		Object daoObj      = null;
			
		try {
			classes 	= Class.forName("hjj.dcits.teller.deal."+className);
			daoObj 			= classes.newInstance();
			System.out.println("����õ���Dao��");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}		
		//System.out.println("������javabean����"+javaBeanObj);
		//System.out.println("������Deal����"+daoObj);
		try {
			Method method  =  classes.getMethod(className.toLowerCase(),new Class[]{javaBeanObj.getClass()});
			map	   =(Map<String, String>) method.invoke(null,javaBeanObj);
			
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void main(String[] args) {
		
	}

}
