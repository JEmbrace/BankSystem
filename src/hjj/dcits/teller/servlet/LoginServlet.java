package hjj.dcits.teller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hjj.dcits.teller.deal.GetDynamicMenu;
import hjj.dcits.teller.deal.ValidateBankEmp;
import hjj.dcits.teller.entities.Employees;
import hjj.dcits.teller.entities.Menus;



@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public LoginServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		System.out.println("我是servlet!");
		
		String bank_user_name = request.getParameter("username");       
		String bank_user_psw  = request.getParameter("password");
		int org_number    	  = Integer.parseInt(request.getParameter("orgnumber"));  //锟斤拷锟斤拷锟斤拷
		int net_number    	  = Integer.parseInt(request.getParameter("netnumber"));  //锟斤拷锟斤拷锟?
		
		
		System.out.println("bank_user_name:"+bank_user_name);
		System.out.println("bank_user_psw:"+bank_user_psw);
		System.out.println("org_number:"+org_number);
		System.out.println("net_number:"+net_number);

		PrintWriter pw =  response.getWriter();;
		ValidateBankEmp vbe = new ValidateBankEmp();
		GetDynamicMenu gde = new GetDynamicMenu();
		Employees rs_emp = vbe.validateEmp(bank_user_name, bank_user_psw, org_number, net_number);

		if( rs_emp != null){ 			
			int bank_user_id = rs_emp.getUser_id();
			
			List<Menus> enums = gde.getMenu(bank_user_id);
			
			HttpSession session = request.getSession();
			//设置前端jsp页面需要用到的属性
			//set动态菜单
			session.setAttribute("dynamic_enums",enums);
			//set柜员对象
			session.setAttribute("current_user",rs_emp); 
			//System.out.println("servlet---"+enums.size());	
			request.getRequestDispatcher("findex.jsp").forward(request,response);
			
		}else{
			pw.print("<script>alert('登录失败');</script>");
			response.sendRedirect("login.jsp");
		}
		
		
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
