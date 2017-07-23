package FilterAnalysis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * コントローラークラス
 * 
 * @author Yokou
 *
 */
@Action
public class TestAction extends Controller {

	/**
	 * http://127.0.0.1/{項目の名前}/ ってURLとマッチングして扱うファンクション
	 * 
	 * @return
	 */
	@ActionMapping(value = "/")
	public String testFilter() {
		// 現すページへデータを伝送する。
		request.setAttribute("t1", "t59");
		return "/index.jsp";
	}

	/**
	 * クラスの内部で使うファンクションです。走査られないです。
	 * 
	 * @param p
	 * @param s
	 */
	public void test(Integer p, String s) {
		System.out.println("..." + p + "   " + s + "...");
	}
}
