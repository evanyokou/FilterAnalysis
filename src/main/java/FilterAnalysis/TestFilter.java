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
 * フィルタクラス
 * 
 * @summary �L��をインタ�`セプトして�Qう
 * @author Yokou
 *
 */
//@WebFilter(filterName = "tf", urlPatterns = "/tf")
public class TestFilter implements Filter {
	/**
	 * フィルタの塘崔クラス
	 */
	private FilterConfig filterConfig;

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * フィルタの贋壓をなくする
	 */
	public void destroy() {
		System.out.println("...filter destroy...");
	}

	/**
	 * 麼にフィルタするプロセス
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
		// 仟たなURLへジャンプする。URLは厚仟する。
		// HttpServletRequest request = (HttpServletRequest) req;
		// HttpServletResponse response = (HttpServletResponse) resp;
		// response.sendRedirect(request.getContextPath()+"/index.jsp");
		// 醤悶議な�Qう圭隈
		Handler handler = new Handler();
		handler.handleUrl(req, resp);
		// 仟たなURLを塘る。URLは厚仟しないです。
		// req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	/**
	 * フィルタを兜豚晒する。
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("...filter init...");
		this.filterConfig = filterConfig;
	}

}
