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
 * �L���¼��Υ��`�Щ`��Q��
 * 
 * @author Yokou
 *
 */
//@WebServlet(name = "ts", urlPatterns = "/*", loadOnStartup = 1)
public class TestServlet extends HttpServlet {

	/**
	 * ���åȤη��������ֱ�� ���`�ѩ`�η�����ֱ�ӵĤ˥��`�뤹��У���������`�򷵤���
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("...servlet doGet...");
		// super.doGet(req, resp);
	}

	/**
	 * �ݥ��ȷ��������ֱ�� ���`�ѩ`�η�����ֱ�ӵĤ˥��`�뤹��У���������`�򷵤���
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("...servlet doPost...");
		// super.doPost(req, resp);
	}

}
