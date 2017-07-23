package FilterAnalysis;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 訪問事件のサーバーを扱う
 * 
 * @author Yokou
 *
 */
//@WebServlet(name = "ts", urlPatterns = "/*", loadOnStartup = 1)
public class TestServlet extends HttpServlet {

	/**
	 * ゲットの方法を書き直す スーパーの方法を直接的にコールすれば４０５エラーを返す。
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("...servlet doGet...");
		// super.doGet(req, resp);
	}

	/**
	 * ポスト方法を書き直す スーパーの方法を直接的にコールすれば４０５エラーを返す。
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("...servlet doPost...");
		// super.doPost(req, resp);
	}

}
