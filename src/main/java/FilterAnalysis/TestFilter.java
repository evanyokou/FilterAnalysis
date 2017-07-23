package FilterAnalysis;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �ե��륿���饹
 * 
 * @summary �L���򥤥󥿩`���ץȤ��ƒQ��
 * @author Yokou
 *
 */
//@WebFilter(filterName = "tf", urlPatterns = "/tf")
public class TestFilter implements Filter {
	/**
	 * �ե��륿�����å��饹
	 */
	private FilterConfig filterConfig;

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * �ե��륿�δ��ڤ�ʤ�����
	 */
	public void destroy() {
		System.out.println("...filter destroy...");
	}

	/**
	 * ���˥ե��륿����ץ���
	 * 
	 * @param req
	 *            ServletRequest
	 * @param resp
	 *            ServletResponse
	 * @param chain
	 *            FilterChain
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("...filter doFilter...");
		// �¤���URL�إ����פ��롣URL�ϸ��¤��롣
		// HttpServletRequest request = (HttpServletRequest) req;
		// HttpServletResponse response = (HttpServletResponse) resp;
		// response.sendRedirect(request.getContextPath()+"/index.jsp");
		// ����ĤʒQ������
		Handler handler = new Handler();
		handler.handleUrl(req, resp);
		// �¤���URL����롣URL�ϸ��¤��ʤ��Ǥ���
		// req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	/**
	 * �ե��륿����ڻ����롣
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("...filter init...");
		this.filterConfig = filterConfig;
	}

}
