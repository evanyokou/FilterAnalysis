package FilterAnalysis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����ȥ�`��`���饹
 * 
 * @author Yokou
 *
 */
@Action
public class TestAction extends Controller {

	/**
	 * http://127.0.0.1/{�Ŀ����ǰ}/ �ä�URL�ȥޥå��󥰤��ƒQ���ե��󥯥����
	 * 
	 * @return
	 */
	@ActionMapping(value = "/")
	public String testFilter() {
		// �F���ک`���إǩ`�����ͤ��롣
		request.setAttribute("t1", "t59");
		return "/index.jsp";
	}

	/**
	 * ���饹���ڲ���ʹ���ե��󥯥����Ǥ����ߖˤ��ʤ��Ǥ���
	 * 
	 * @param p
	 * @param s
	 */
	public void test(Integer p, String s) {
		System.out.println("..." + p + "   " + s + "...");
	}
}
