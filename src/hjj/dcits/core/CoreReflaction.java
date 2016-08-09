package hjj.dcits.core;
import java.util.Map;

import hjj.dcits.teller.util.Reflaction;

public class CoreReflaction {
	
	public Map<String,String> reflactionClass(String xml){
			System.out.println("反射开始");
			Map<String,String> map = Reflaction.reflactClassAndMethod(xml);
			return map;
	}

	public static void main(String[] args){
		CoreReflaction cr       = new CoreReflaction();
		String xml = "<?xml version='1.0' encoding='UTF-8'?><message><message_header><emp_number>20000</emp_number><time>2016-07-17 20:43:57</time><trading_code>1000000000</trading_code><serial_number>14687594371251000000000</serial_number></message_header><message_body><customer_id>1</customer_id><customer_ename>dshf</customer_ename><customer_cname>hhh</customer_cname><customer_contry>jjj</customer_contry><customer_province>陕西</customer_province><customer_city>dspfji</customer_city><customer_tel>127410</customer_tel><customer_creator>11</customer_creator><customer_address>西安</customer_address></message_body></message>";
		cr.reflactionClass(xml);
	}
}
