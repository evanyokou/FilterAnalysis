package FilterAnalysis;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
public class HandlerTest {

	@Test
	public void handlerTest() {
		try {
			Class<Controller> cls = (Class<Controller>) Class.forName("wb.TestAction");
			System.out.println(cls.isAnnotationPresent(FilterAnalysis.Action.class));
			Method m = cls.getMethod("testFilter");
			String string = (String)m.invoke(cls.newInstance(), null);
			System.out.println("..."+string+"...");
			m = cls.getMethod("setRequest",HttpServletRequest.class);
			m = cls.getMethod("test", Integer.class,String.class);
			m.invoke(cls.newInstance(), 1,"ss");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
