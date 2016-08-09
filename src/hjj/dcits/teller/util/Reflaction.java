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
	 * 根据交易码在配置文件中获取 操作数据库 的类名
	 * param	:String trading_code  交易码
	 * return 	:String className
	 */
	public static String getDaoClassNameFormProp(String trading_code){
		String path="E://JavaWeb//WIN64//DCITS//SixDayUp//src//trade_code_to_core.properties";
		InputStream in = null;
		//System.out.println("交易码 trading_code = "+trading_code);
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
		
		//System.out.print("类名："+className);
		return className;
	}
	
	/*
	 * 根据交易码获取本次交易对应的实体类对象名
	 * param	:String trading_code
	 * return	:String className
	 */
	public static String getFormPropBean(String trading_code){
		String path="E://JavaWeb//WIN64//DCITS//SixDayUp//src//trade_code_to_bean.properties";
		InputStream in = null;
		System.out.println("交易码 trading_code = "+trading_code);
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
		
		System.out.print("类名："+className);
		return className;
	}
	
	/*
	 *根据实体类的类名反射出 一个实体类，并返回该类的一个对象
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
	 * 根据报文创建对应的实体类
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
	 * 根据报文反射并调用操作数据库的Dao类
	 */
	public static Map<String,String> reflactClassAndMethod(String xml){
		Map<String,String> map = new HashMap<String,String>();
		//*****************************************
		String trading_code = Dom4Reader.parseMessageHeader(xml)[2];
		Object javaBeanObj	= createJavaBean(xml);
		String className    = getDaoClassNameFormProp(trading_code);
		
		System.out.println("反射得到实体类javaBeanObj="+javaBeanObj);
		System.out.println("反射得到Dao类类名="+className);
		//*****************************************
		Class classes = null;
		Object daoObj      = null;
			
		try {
			classes 	= Class.forName("hjj.dcits.teller.deal."+className);
			daoObj 			= classes.newInstance();
			System.out.println("反射得到的Dao类");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}		
		//System.out.println("创建的javabean对象"+javaBeanObj);
		//System.out.println("创建的Deal对象"+daoObj);
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
