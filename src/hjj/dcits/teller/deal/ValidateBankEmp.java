package hjj.dcits.teller.deal;

import hjj.dcits.teller.dao.BankEmpDao;
import hjj.dcits.teller.entities.Employees;

public class ValidateBankEmp {
	public  Employees validateEmp(String name,String psw,int org,int net){		
	
		BankEmpDao bed = new BankEmpDao();
		Employees emp = bed.getBankEmpByName(name);
		//System.out.println("ValidateBankEmp:"+emp);
		
		if(emp != null){
			//System.out.println("ValidateBankEmp 的emp不为空");
			String rs_psw 	= emp.getUser_psw();
			int rs_org 		= emp.getOrg_number();
			int rs_net 		= emp.getNet_number();
			
			
			if(rs_psw.equals(psw) && rs_org == org && rs_net == net){
				//System.out.println("比较成功");
				emp.setUser_psw("*****");
				return emp;
			}
		}
		
		return null;
	}
	public static void main(String[] args) {
		

	}

}
